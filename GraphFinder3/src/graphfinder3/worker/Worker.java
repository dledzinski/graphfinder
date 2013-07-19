/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.worker;

import graphfinder3.data.OrderResult;
import graphfinder3.data.Order;
import graphfinder3.network.*;
import graphfinder3.util.Monitor;
import graphfinder3.util.MonitoringData;
import graphfinder3.util.MonitoringDataListener;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class Worker implements MessageListener, MonitoringDataListener {

	// logger
	private static final Logger logger = Logger.getLogger(Worker.class);
	// preferowana wielkosc kolejki
	private static final int PREFERED_ORDERS_BUFFER = 200;
	// czas pomiedzy polaczeniami
	private static final int DELAY_BETWEEN_CONNECTIONS = 3000;
	// czas pomiedzy aktywnoscia timera
	private static final int TIMER_ACTIVITY_DELAY = 200;
	// kolejka rozkazow do wykonania
	private final BlockingQueue<Order> orderQueue = new LinkedBlockingQueue<Order>();
	// kolejka wynikow rozkazow
	private final BlockingQueue<OrderResult> orderResultqQueue = new LinkedBlockingQueue<OrderResult>();
	// polaczenie z serwerem
	private ConnectionHandler connectionHandler = null;
	// timer
	private final Timer timer = new Timer("WorkerTimer");
	// nazwa workera
	private String workerName = null;
	// host serwera
	private final String serverHost;
	// port serwera
	private final int serverPort;
	// okreslenie, czy robic reconnecty
	private final boolean tryReconnct;

	/**
	 * Tworzy obiekt workera
	 *
	 * @param serverHost host serwera
	 * @param serverPort port serwera
	 * @param tryReconnct czy probowac polaczyc ponownie
	 * @param preferedOrdersBuffer preferowana wielkosc bufora
	 * @param thradLimit limit watkow
	 */
	public Worker(String serverHost, int serverPort, boolean tryReconnct, int thradLimit) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.tryReconnct = tryReconnct;


		// laczenie
		connect();
		// start timera
		timerStart();
		// tworzenie watkow
		for (int i = 0; i < Math.min(thradLimit > 0 ? thradLimit : Integer.MAX_VALUE, Runtime.getRuntime().availableProcessors()); i++) {
			new WorkingThread("WorkingThread-" + i, this);
		}
		// monitoring
		Monitor.getInstance().addMonitorLietener(this);
		logger.info("Worker wystartowal");
	}

	/**
	 * Metoda raportuje blad serwerowi
	 *
	 * @param error
	 */
	public void reportError(String error) {
		sendToServer(new Message(Command.ORDER_ERROR, error));
	}

	/**
	 * Metoda blokujaca, pobiera rozkaz
	 *
	 * @return
	 */
	public Order getOrder() throws InterruptedException {
		return orderQueue.take();
	}

	/**
	 * Oddaje wynik rozkazu
	 *
	 * @param orderResult
	 */
	public void putOrderResult(OrderResult orderResult) {
		orderResultqQueue.offer(orderResult);
	}

	@Override
	public void newMessage(Message message) {
		try {
			switch (message.getCommand()) {
				case PING:
					sendToServer(new Message(Command.PONG, null));
					break;
				case ERROR:
					logger.error("Przyszedl komunikat o bledzie: " + message);
					break;
				case LOGIN_OK:
					workerName = (String) message.getParam();
					sendToServer(new Message(Command.WORKER_INFO_REGISTER, null));
					sendToServer(new Message(Command.ORDERS_REQUEST, null));
					break;
				case LOGIN_ERROR:
					logger.error("Blad logowania: " + message);
					System.exit(0);
					break;
				case ORDERS:
					// zapisywanie nowych rozkazow w kolejce
					for (Order order : (Collection<Order>) message.getParam()) {
						orderQueue.offer(order);
					}
					break;
				case ORDERS_CANCELLED:
					// czyszczenie kolejki
					orderQueue.clear();
					sendToServer(new Message(Command.ORDERS_REQUEST, null));
					break;
				default:
					logger.error("Przyszedl nieobslugiwany rozkaz: " + message);
			}
		} catch (Exception e) {
			logger.error("Blad w czasie wykonywania rozkazu: " + message, e);
		}
	}

	@Override
	public synchronized void disconnected() {
		// referencja nie moze wskazywac na niedzialajace polaczenie
		connectionHandler = null;
		workerName = null;
		// ewentualne planowanie polaczenia
		if (tryReconnct) {
			planReconnect();
		} else {
			System.exit(0);
		}

	}

	@Override
	public void newMonitoringData(MonitoringData monitoringData) {
		if (workerName != null) {
			sendToServer(new Message(Command.MONITORING_DATA, monitoringData));
		}
	}

	/**
	 * Laczenie z serwerem
	 */
	private synchronized void connect() {
		try {
			logger.info("Laczenie z serwerem");
			ConnectionHandler ch = new ConnectionHandler(serverHost, serverPort, this);
			// przypisanie do zmiennej jesli wszystko poszlo dobrze
			connectionHandler = ch;
			// login
			sendToServer(new Message(Command.LOGIN, new LoginData(Monitor.getHostName() + "-Worker", "Worker")));
		} catch (Exception ex) {
			logger.error("Nieudana proba polaczenia z serwerem", ex);
			// ewentualne planowanie polaczenia
			if (tryReconnct) {
				planReconnect();
			} else {
				System.exit(0);
			}
		}
	}

	/**
	 * Metoda wysyla komunikat do serwera
	 *
	 * @param message
	 */
	private synchronized void sendToServer(Message message) {
		if (connectionHandler != null) {
			connectionHandler.send(message);
		} else {
			System.err.println("Proba wyslania komunikatu przez niedzialajace polacznie");
		}
	}

	private void timerStart() {
		logger.debug("Start dzialania timera");
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// jesli jest polaczenie
				if (workerName != null) {
					logger.debug("Dzialanie timera orderQueue: " + orderQueue.size() + " resultQueue: " + orderResultqQueue.size());
					// wyluskiwanie i odsylanie wynikow do serwera
					Set<OrderResult> orderResults = new HashSet<OrderResult>();
					OrderResult orderResult;
					while ((orderResult = orderResultqQueue.poll()) != null) {
						// dodawanie wyniku
						orderResults.add(orderResult);
						// informowanie monitora
						Monitor.getInstance().addToGraphCounter(orderResult.getGraphCounter());
					}
					if (!orderResults.isEmpty()) {
						sendToServer(new Message(Command.ORDER_RESULTS, orderResults));
					}
					// sprawdzanie czy kolejka jest za mala i poprosic o nowe rozkazy
					if (orderQueue.size() < PREFERED_ORDERS_BUFFER / 2) {
						sendToServer(new Message(Command.ORDERS_REQUEST, null));
					}
				}
			}
		}, 0, TIMER_ACTIVITY_DELAY);
	}

	/**
	 * Metoda planuje ponowne polaczenie
	 */
	private void planReconnect() {
		logger.info("Planowanie ponownego polaczenia");
		// planowanie ponownego polaczenia
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				connect();
			}
		}, DELAY_BETWEEN_CONNECTIONS);
	}
}
