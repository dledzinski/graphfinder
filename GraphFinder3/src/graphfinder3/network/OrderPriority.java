/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import java.io.Serializable;

/**
 *
 * @author damian
 */
public class OrderPriority implements Serializable {

	// nazwa
	private final String orderName;
	// priorytet
	private final int priority;

	/**
	 *
	 * @param orderName
	 * @param priority
	 */
	public OrderPriority(String orderName, int priority) {
		this.orderName = orderName;
		this.priority = priority;
	}

	public String getOrderName() {
		return orderName;
	}

	public int getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return "OrderPriority{" + "orderName=" + orderName + ", priority=" + priority + '}';
	}
}
