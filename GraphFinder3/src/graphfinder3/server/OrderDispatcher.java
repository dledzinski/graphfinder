/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.data.Order;
import graphfinder3.data.OrderResult;
import graphfinder3.network.OrderDetails;
import graphfinder3.network.OrderInfo;
import graphfinder3.network.OrderPriority;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;

/**
 * Dystrybutor zlecen
 *
 * @author damian
 */
public class OrderDispatcher {

	// logger
	private static final Logger logger = Logger.getLogger(OrderDispatcher.class);
	// ilosc zadan naraz wysylana
	private static final int ORDERS_IN_RESPONSE = 10;
	// jedyny egzemplaz 
	private static OrderDispatcher instance = null;
	// zadania wykonywane
	private final Map<String, OrderServicer> orderServicers = new ConcurrentHashMap<String, OrderServicer>();
	// licznik
	private volatile int counter = 0;

	public static synchronized OrderDispatcher getInstance() {
		if (instance == null) {
			instance = new OrderDispatcher();
		}
		return instance;
	}

	private OrderDispatcher() {
	}

	/**
	 * Metoda zwraca informacje o zadaniach
	 *
	 * @return
	 */
	public Set<OrderInfo> getOrderInfos() {
		Set<OrderInfo> orderInfos = new TreeSet<OrderInfo>();
		for (OrderServicer orderServicer : orderServicers.values()) {
			orderInfos.add(orderServicer.getOrderInfo());
		}
		return orderInfos;
	}

	/**
	 * Przyszedl nowy rozkaz
	 *
	 * @param order
	 */
	public void newOrder(Order order) {
		// ustalanie nazwy
		String prefix = "" + counter;
		counter++;
		String name = prefix + "-" + order.getOrderName();
		// zapis
		orderServicers.put(prefix, new OrderServicer(name, order.getProblem(), order.getDivisibleLevel(), order.getPriority()));
		// powiadamianie workerow
		WorkerInfoDispatcher.getInstance().fireOrdersCanceled();
		OrderInfoDispatcher.getInstance().forceUpdate();
		logger.info("Utworzono zadanie: " + name);
	}

	/**
	 * Nowy wynik doszedl
	 *
	 * @param orderResult
	 */
	public void newOrderResult(OrderResult orderResult) {
		String prefix = getOrderNamePrefix(orderResult.getOrderName());
		OrderServicer orderServicer = orderServicers.get(prefix);
		if (orderServicer != null) {
			orderServicer.newResult(orderResult);
		}
	}

	/**
	 * Przekazuje zbior wynikow
	 *
	 * @param orderResults
	 */
	public void newOrderResults(Collection<OrderResult> orderResults) {
		for (OrderResult orderResult : orderResults) {
			newOrderResult(orderResult);
		}
	}

	/**
	 * Rozkaz usunicia zadania
	 *
	 * @param orderName
	 */
	public void deleteOrder(String orderName) {
		String prefix = getOrderNamePrefix(orderName);
		if (orderServicers.get(prefix) != null && orderServicers.get(prefix).getOrderName().equals(orderName)) {
			orderServicers.remove(prefix);
			logger.info("Usunieto zadanie: " + orderName);
			// powiadamianie workerow
			WorkerInfoDispatcher.getInstance().fireOrdersCanceled();
			OrderInfoDispatcher.getInstance().forceUpdate();
		} else {
			logger.warn("Brak zadania o nazwie: " + orderName);
		}
	}

	/**
	 * Metoda zmienia priorytet zadnia
	 *
	 * @param orderName
	 * @param priority
	 */
	public void setPriority(OrderPriority orderPriority) {
		String prefix = getOrderNamePrefix(orderPriority.getOrderName());
		if (orderServicers.get(prefix) != null) {
			orderServicers.get(prefix).setPriority(orderPriority.getPriority());
			// powiadamianie workerow
			WorkerInfoDispatcher.getInstance().fireOrdersCanceled();
			OrderInfoDispatcher.getInstance().forceUpdate();
		}
	}

	/**
	 * zwraca szczegoly rozkazu
	 *
	 * @param orderName nazwa rozkazu
	 * @return dane lub null jeski nie ma zadania o takiej nazwie
	 */
	public OrderDetails getOrderDetails(String orderName) {
		String prefix = getOrderNamePrefix(orderName);
		OrderServicer orderServicer = orderServicers.get(prefix);
		if (orderServicer != null) {
			return orderServicer.getOrderDetails();
		}
		return null;
	}

	/**
	 * Metoda pyta o rozkazy
	 *
	 * @return zostaja zwrocone rozkazy z zadan z najwyzszym priorytetem, lub
	 * null jesli zadnych nie ma
	 */
	public Set<Order> orderRequest() {
		// zbior tych z ktoych bedziemy pobierac zadania
		Set<OrderServicer> servicers = new HashSet<OrderServicer>();
		int maxPriority = -1;
		// przejscie po wszystkich
		for (OrderServicer orderServicer : orderServicers.values()) {
			int currentPriority = orderServicer.getPriority();
			// jesli ma wyzszy priorytet i nie jest skonczony
			if (!orderServicer.isFinished() && currentPriority >= maxPriority) {
				// jesli mamy do czynienia z wyzszym priorytetem
				if (currentPriority > maxPriority) {
					maxPriority = currentPriority;
					servicers = new HashSet<OrderServicer>();
				}
				servicers.add(orderServicer);
			}
		}
		// pobieranie zadan z tych ktorzy maja najwyzszy priorytet

		if (!servicers.isEmpty()) {
			Set<Order> orders = new HashSet<Order>();
			// ilosc zadan od kazdego rozdawacza
			int ordersPerService = (ORDERS_IN_RESPONSE / servicers.size()) > 0 ? ORDERS_IN_RESPONSE / servicers.size() : 1;
			for (OrderServicer orderServicer : servicers) {
				orders.addAll(orderServicer.takeOrders(ordersPerService));
			}
			return orders;
		}
		return null;
	}

	/**
	 * Metoda zwraca prefiks nazwy rozkazu
	 *
	 * @param orderName nazwa rozkazu
	 * @return
	 */
	private String getOrderNamePrefix(String orderName) {
		return orderName.split("-")[0];
	}
}
