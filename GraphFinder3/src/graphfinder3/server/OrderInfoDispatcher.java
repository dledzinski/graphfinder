/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.network.OrderInfo;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.log4j.Logger;

/**
 * Dystrybutor informacji o zleceniach
 * @author damian
 */
public class OrderInfoDispatcher {
	
	// logger
	private static final Logger logger = Logger.getLogger(OrderInfoDispatcher.class);
	// czas wysylki danych
	private static final int TIMER_PERIOD = 5000;
	// jedyny egzemplaz 
	private static OrderInfoDispatcher instance = null;
	// zbior klientow do informowania
	private final Set<ClientHandler> listeners = new CopyOnWriteArraySet<ClientHandler>();
	// flaga mowiaca ze dane sie zminily
	private volatile boolean dataChanged = false;
	// timer
	private final Timer timer = new Timer("OrderInfoDispatcherTimer");
	
	public static synchronized OrderInfoDispatcher getInstance() {
		if (instance == null) {
			instance = new OrderInfoDispatcher();
		}
		return instance;
	}

	private OrderInfoDispatcher() {
		timer.schedule(getTimerTask(), 0, TIMER_PERIOD);
	}
	
	/**
	 * Metoda wywolywana aby powiadomic ze sie dane zmienily
	 */
	public void fireDataChanged() {
		dataChanged = true;
	}
	
	/**
	 * Metoda wywolywana aby wymusic natychmaistowe powiadomienie klientwo
	 */
	public void forceUpdate() {
		dataChanged = true;
		timer.schedule(getTimerTask(), 0);
	}
	
	/**
	 * Dodaje listenera
	 *
	 * @param clientHandler
	 */
	public void addOrderInfoLietener(ClientHandler clientHandler) {
		listeners.add(clientHandler);
	}

	/**
	 * Usuwa sluchacza
	 *
	 * @param clientHandler
	 */
	public void removeOrderInfoListener(ClientHandler clientHandler) {
		listeners.remove(clientHandler);
	}
	
	/**
	 * Zwraca informacje 
	 * @return 
	 */
	public Set<OrderInfo> getOrderInfos() {
		return OrderDispatcher.getInstance().getOrderInfos();
	}
	
	/**
	 * Akcja timera
	 */
	private TimerTask getTimerTask() {
		return new TimerTask() {

			@Override
			public void run() {
				if (dataChanged) {
					dataChanged = false;
					Set<OrderInfo> orderInfos = OrderDispatcher.getInstance().getOrderInfos();
					for (ClientHandler clientHandler : listeners) {
						clientHandler.orderInfoUpdated(orderInfos);
					}
				}
			}
		};
	}
	
}
