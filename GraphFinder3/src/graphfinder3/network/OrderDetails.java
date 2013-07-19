/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import graphfinder3.data.Problem;
import graphfinder3.data.ResultSet;
import java.io.Serializable;

/**
 *
 * @author damian
 */
public class OrderDetails implements Serializable {

	// informacje o zadaniu
	private final OrderInfo orderInfo;
	// problem
	private final Problem problem;
	// aktualny wynik
	private final ResultSet currentResultSet;

	/**
	 * Tworzy obiekt szczegulowych informacji o zadaniu
	 *
	 * @param orderInfo
	 * @param problem
	 * @param currentResultSet
	 */
	public OrderDetails(OrderInfo orderInfo, Problem problem, ResultSet currentResultSet) {
		this.orderInfo = orderInfo;
		this.problem = problem;
		this.currentResultSet = currentResultSet;
	}

	/**
	 * Aktualny wynik zadania
	 *
	 * @return
	 */
	public ResultSet getCurrentResultSet() {
		return currentResultSet;
	}

	/**
	 * Informacje o zadaniu
	 *
	 * @return
	 */
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	/**
	 * Problem
	 *
	 * @return
	 */
	public Problem getProblem() {
		return problem;
	}

	@Override
	public String toString() {
		return "OrderDetails{" + "orderInfo=" + orderInfo + ", problem=" + problem + ", currentResultSet=" + currentResultSet + '}';
	}
}
