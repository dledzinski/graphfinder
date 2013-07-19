/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;

/**
 *
 * @author damian
 */
public class OrderResult implements Serializable {

	// nazwa problemu
	private final String orderName;
	// zbior wynikow
	private final ResultSet resultSet;
	// ilosc przetworzonych grafow
	private final long graphCounter;
	// czas wykonywania
	private final long processingTime;

	/**
	 * Tworzy raport wyniku wykonywania zlecenia
	 *
	 * @param orderName
	 * @param resultSet
	 * @param graphCounter
	 * @param processingTime
	 */
	public OrderResult(String orderName, ResultSet resultSet, long graphCounter, long processingTime) {
		this.orderName = orderName;
		this.resultSet = resultSet;
		this.graphCounter = graphCounter;
		this.processingTime = processingTime;
	}

	/**
	 * Zwraca nazwe rozkazu do ktorego to bylo przypisane
	 *
	 * @return
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * Zwraca czas wykonywania tego rozkazu
	 *
	 * @return
	 */
	public long getProcessingTime() {
		return processingTime;
	}

	/**
	 * Zwraca zbior wynikow
	 *
	 * @return
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/**
	 * Zwraca ilosc grafow przetworzonych
	 *
	 * @return
	 */
	public long getGraphCounter() {
		return graphCounter;
	}

	@Override
	public String toString() {
		return "OrderResult{" + "orderName=" + orderName + ", resultSet=" + resultSet + ", graphCounter=" + graphCounter + ", processingTime=" + processingTime + '}';
	}
}
