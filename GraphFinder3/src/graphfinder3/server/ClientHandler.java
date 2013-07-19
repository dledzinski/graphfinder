/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.data.Order;
import graphfinder3.data.OrderResult;
import graphfinder3.network.*;
import graphfinder3.util.MonitoringData;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class ClientHandler implements MessageListener {

	// logger
	private static final Logger logger = Logger.getLogger(ClientHandler.class);
	// polaczenie
	private final ConnectionHandler connectionHandler;
	// nazwa klienta
	private String clientName = "noName";
	// czas ostatniej aktywnosci
	private volatile long lastActivityTime;

	/**
	 * Obiekt polaczenia z klientem
	 *
	 * @param socket
	 * @throws IOException
	 */
	public ClientHandler(Socket socket) throws IOException {
		// tworzenie uchwytu polaczenia
		connectionHandler = new ConnectionHandler(socket, this);
		// zapis czasu
		this.lastActivityTime = System.currentTimeMillis();
		// rejestracja
		ClientDispatcher.getInstance().clientConnected(this);
		logger.info("Nowy klient podlaczany: " + socket);
	}

	/**
	 * Zwraca czas ostatniej aktywnosci
	 *
	 * @return
	 */
	public long getLastActivityTime() {
		return lastActivityTime;
	}

	/**
	 * Rozlaczanie klienta
	 */
	public void disconnect() {
		connectionHandler.disconnect();
	}

	/**
	 * Wysylanie komunikatu
	 *
	 * @param message
	 */
	public void send(Message message) {
		connectionHandler.send(message);
	}

	/**
	 * Metoda wywolywana kiedy zostana zaktualizowane dane o klientach
	 *
	 * @param clientInfos
	 */
	public void clientInfosUpdated(Set<ClientInfo> clientInfos) {
		send(new Message(Command.CLIENT_INFOS, clientInfos));
	}

	/**
	 * Metoda wywolywana kiedy zostana zaktualizowane dane o zadaniach
	 *
	 * @param orderInfos
	 */
	public void orderInfoUpdated(Set<OrderInfo> orderInfos) {
		send(new Message(Command.ORDER_INFOS, orderInfos));
	}

	/**
	 * Informuje ze rozkazy zostaly anulowane
	 */
	public void ordersCancelled() {
		send(new Message(Command.ORDERS_CANCELLED, null));
	}
	
	@Override
	public void newMessage(Message message) {
		try {
			logger.debug("Klient " + clientName + " przyslal rozkaz: " + message);
			switch (message.getCommand()) {
				case PING:
					send(new Message(Command.PONG, null));
					break;
				case PONG:
					break;
				case ERROR:
					logger.error("Klient: " + clientName + " przyslal komunikat o bledzie: " + message.getParam());

				// obsluga klientow
				case LOGIN: {
					LoginData loginData = (LoginData) message.getParam();
					// rejestracja
					clientName = ClientInfoDispatcher.getInstance().clientLogged(loginData.getClientName(), connectionHandler.getRemoteIPAddress(), System.currentTimeMillis());
					send(new Message(Command.LOGIN_OK, clientName));
					break;
				}
				case MONITORING_DATA:
					ClientInfoDispatcher.getInstance().newMonitoringData(clientName, (MonitoringData) message.getParam());
					break;

				// obsluga GUI
				case CLIETN_INFO_REGISTER:
					ClientInfoDispatcher.getInstance().addClientInfoLietener(this);
					send(new Message(Command.CLIENT_INFOS, ClientInfoDispatcher.getInstance().getClientInfos()));
					break;
				case CLIETN_INFO_UNREGISTER:
					ClientInfoDispatcher.getInstance().removeClientInfoListener(this);
					break;
				case CLIETN_INFO_DELETE_UNACTIVES:
					ClientInfoDispatcher.getInstance().deleteDisconnected();
					break;
				case ORDER_INFO_REGISTER:
					OrderInfoDispatcher.getInstance().addOrderInfoLietener(this);
					send(new Message(Command.ORDER_INFOS, OrderDispatcher.getInstance().getOrderInfos()));
					break;
				case ORDER_INFO_UNREGISTER:
					OrderInfoDispatcher.getInstance().removeOrderInfoListener(this);
					break;
				case NEW_ORDER:
					OrderDispatcher.getInstance().newOrder((Order) message.getParam());
					break;
				case DELETE_ORDER:
					OrderDispatcher.getInstance().deleteOrder((String) message.getParam());
					break;
				case ORDER_SET_PRIORITY:
					OrderDispatcher.getInstance().setPriority((OrderPriority) message.getParam());
					break;
				case ORDER_GET_DETAILS: {
					OrderDetails orderDetails = OrderDispatcher.getInstance().getOrderDetails((String) message.getParam());
					if (orderDetails != null) {
						send(new Message(Command.ORDER_DETAILS, orderDetails));
					} else {
						send(new Message(Command.ERROR, "Brak rozkazu o nazwie: " + message.getParam()));
					}
					break;
				}
				case WORKER_INFO_REGISTER:
					WorkerInfoDispatcher.getInstance().addOrderInfoLietener(this);
					break;
				case WORKER_INFO_UNREGISTER:
					WorkerInfoDispatcher.getInstance().removeOrderInfoListener(this);
					break;

				// obsluga workerow
				case ORDERS_REQUEST: {
					Set<Order> orders = OrderDispatcher.getInstance().orderRequest();
					if (orders != null) {
						send(new Message(Command.ORDERS, orders));
					}
					break;
				}
				case ORDER_ERROR:
					logger.error("Blad wykonywania zlecenia: " + message.getCommand());
					break;
				case ORDER_RESULTS:
					OrderDispatcher.getInstance().newOrderResults((Set<OrderResult>) message.getParam());
					break;

				default:
					logger.error("Klient " + clientName + " przyslal nieobslugiwany rozkaz: " + message);
			}
			// aktualizacja
			lastActivityTime = System.currentTimeMillis();
			ClientInfoDispatcher.getInstance().newActivity(clientName, lastActivityTime);
		} catch (Exception e) {
			logger.error("Blad w czasie obslugi rozkazu: " + message + " od klienta: " + clientName, e);
		}
	}

	/**
	 * Metoda wywolywana przez uchwyt polacznia kiedy nastapi rozlaczenie trzeba
	 * posprzatac
	 */
	@Override
	public void disconnected() {
		// wyrejestrowujemy sie
		ClientInfoDispatcher.getInstance().removeClientInfoListener(this);
		OrderInfoDispatcher.getInstance().removeOrderInfoListener(this);
		WorkerInfoDispatcher.getInstance().removeOrderInfoListener(this);
		// powiadamianie dyspozytora informacji
		ClientInfoDispatcher.getInstance().clientDisconnected(clientName);
		// informowanie dyspozytora
		ClientDispatcher.getInstance().clientDisconnected(this);
		logger.info("Klient rozlaczony: " + clientName);
	}
}
