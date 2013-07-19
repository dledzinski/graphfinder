/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.data.Order;
import graphfinder3.data.OrderResult;
import graphfinder3.data.Problem;
import graphfinder3.data.ResultSet;
import graphfinder3.network.OrderDetails;
import graphfinder3.network.OrderInfo;
import graphfinder3.processing.ProblemDivider;
import graphfinder3.processing.ResultCollector;
import graphfinder3.util.DiscSaver;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Klasa do obslugi rozkazu
 *
 * @author damian
 */
public class OrderServicer {

	// logger
	private static final Logger logger = Logger.getLogger(OrderServicer.class);
	// nazwa
	private final String orderName;
	// problem
	private final Problem problem;
	// poziom podzialu
	private final int divisibleLevel;
	// priorytet
	private volatile int priority;
	// rozkazy do wykonania
	private final Set<Order> ordersToSolve;
	// problemy rozwiazywane nazwa - rozkaz
	private final Map<String, Order> ordersUnderProcessing = new HashMap<String, Order>();
	// zbieracz wynikow
	private final ResultCollector resultCollector;
	// calkowita ilosc rozkazow
	private final int totalResultNumber;
	// licznik
	private volatile int resultCounter = 0;
	// ilosc przetworzonych grafow
	private long graphCounter = 0;
	// calkowity czas liczenia
	private long processingTime = 0;
	// okresla czy zadanie jest skonczone
	private volatile boolean finished = false;
	// czas utworzenia
	private final long creationTime;

	/**
	 * Tworzy obiekt obslugi rozkazow
	 *
	 * @param orderName
	 * @param problem
	 * @param divisibleLevel
	 * @param priority
	 */
	public OrderServicer(String orderName, Problem problem, int divisibleLevel, int priority) {
		this.orderName = orderName;
		this.problem = problem;
		this.divisibleLevel = divisibleLevel;
		this.priority = priority;
		// tworzenie rozkazow
		ordersToSolve = createOrders();
		totalResultNumber = ordersToSolve.size();
		// zbieracz wynikow
		resultCollector = new ResultCollector(problem.getTasks());
		creationTime = System.currentTimeMillis();
	}

	/**
	 * Metoda zapisuje wynik
	 *
	 * @param orderResult
	 */
	public synchronized void newResult(OrderResult orderResult) {
		// proba usuniecia z mapy
		if (ordersUnderProcessing.remove(orderResult.getOrderName()) != null) {
			// zapis wyniku
			resultCollector.newResultSet(orderResult.getResultSet());
			// zapis ilosci grafow
			graphCounter += orderResult.getGraphCounter();
			// zapis czasu
			processingTime += orderResult.getProcessingTime();

			// licznik wykonanych rozkazow
			resultCounter++;
			// ewentualna zmiana statusu
			if (resultCounter == totalResultNumber) {
				finished = true;
				// powiadamianie workerow
				WorkerInfoDispatcher.getInstance().fireOrdersCanceled();
				OrderInfoDispatcher.getInstance().forceUpdate();
				try {
					DiscSaver.getInstance().save(getOrderDetails());
					logger.info("Rozkaz wykonany: " + orderName);
				} catch (IOException ex) {
					logger.error("Blad zapisu do pliku", ex);
				}
			} else {
				OrderInfoDispatcher.getInstance().fireDataChanged();
			}
		}
	}

	/**
	 * Metoda zwraca okreslona ilosc rozkazow jesli to mozliwe, w przypadku
	 * braku zwyklych rozkazow zwraca te do
	 *
	 * @param number ilosc rozkazow do pobrania
	 * @return rozkazy lub null
	 */
	public synchronized Set<Order> takeOrders(int number) {
		// proba pobrania rozkazow
		Set<Order> orders = takeOrdersToSolve(number);
		// jesli zbior jest pusty
		if (orders.isEmpty()) {
			// zmiejszenie priorytetu
			if (priority > 10) {
				priority--;
			}
			// douzupelnianie z mapy
			int i = 0;
			for (Order order : ordersUnderProcessing.values()) {
				orders.add(order);
				i++;
				if (i >= number / 2 + 1) {
					break;
				}
			}
		}
		// jesli nadal pusto, to null
		if (orders.isEmpty()) {
			return null;
		}
		return orders;
	}

	/**
	 * Okresla czy zadanie jest zakonczone
	 *
	 * @return
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Ustawia priorytet
	 *
	 * @param priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
		OrderInfoDispatcher.getInstance().fireDataChanged();
	}

	/**
	 * Zwraca priorytet
	 *
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Zwraca nazwe rozkazu
	 *
	 * @return
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * Zwraca informacje o zadaniu
	 *
	 * @return
	 */
	public OrderInfo getOrderInfo() {
		return new OrderInfo(orderName, finished, priority, resultCounter, totalResultNumber, graphCounter, processingTime, creationTime);
	}

	/**
	 * Zwraca problem
	 *
	 * @return
	 */
	public Problem getProblem() {
		return problem;
	}

	/**
	 * Zwraca aktualny wynik
	 *
	 * @return
	 */
	public ResultSet geResultSet() {
		return resultCollector.getResultSet();
	}

	/**
	 * Zwraca szczegoly zadania
	 *
	 * @return
	 */
	public OrderDetails getOrderDetails() {
		return new OrderDetails(getOrderInfo(), problem, geResultSet());
	}

	/**
	 * Metoda niesynchronizowana, pobiera rozkazy do rozwiazania i zapisuje je w
	 * mapie rozkazow przetwarzanych
	 *
	 * @param number maksymalna iloc rozkazow do pobrania
	 * @return
	 */
	private Set<Order> takeOrdersToSolve(int number) {
		Set<Order> orders = new HashSet<Order>();
		int i = 0;
		for (Iterator<Order> it = ordersToSolve.iterator(); it.hasNext() && i < number; i++) {
			// pobieranie i usuwanie
			Order order = it.next();
			it.remove();
			// zapis w mapie
			ordersUnderProcessing.put(order.getOrderName(), order);
			// zapis w zbiorze
			orders.add(order);
		}
		// zwracanie
		return orders;
	}

	/**
	 * Tworzy zbior podproblemow
	 *
	 * @return
	 */
	private Set<Order> createOrders() {
		Set<Order> orders = new HashSet<Order>();
		// obiekt dzielacy
		ProblemDivider divider = new ProblemDivider(problem, divisibleLevel);
		// zbior problemow
		Set<Problem> subProblems = divider.getSubProblems();
		int counter = 0;
		// opakowywanie 
		for (Problem subProblem : subProblems) {
			orders.add(new Order(orderName + "-" + counter, subProblem, priority, divisibleLevel));
			counter++;
		}
		return orders;
	}
}
