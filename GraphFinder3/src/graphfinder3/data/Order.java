/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;

/**
 * Klasa reprezentujaca rozkaz
 *
 * @author damian
 */
public class Order implements Serializable {

	// nazwa rozkazu
	private final String orderName;
	// rozkaz do wykonania
	private final Problem problem;
	// priorytet
	private final int priority;
	// pozio podzialu
	private final int divisibleLevel;

	/**
	 * Tworzy rozkaz
	 *
	 * @param orderName proponowana nazwa
	 * @param problem problem do rozwiazania
	 * @param startPriority priorytet startowy
	 * @param divisibleLevel poziom podzialu
	 */
	public Order(String orderName, Problem problem, int startPriority, int divisibleLevel) {
		this.orderName = orderName;
		this.problem = problem;
		this.priority = startPriority;
		this.divisibleLevel = divisibleLevel;
	}

	/**
	 * Zwraca nazwe
	 *
	 * @return
	 */
	public String getOrderName() {
		return orderName;
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
	 * Zwraca poziom podzialu
	 *
	 * @return
	 */
	public int getDivisibleLevel() {
		return divisibleLevel;
	}

	/**
	 * Zwraca priorytet
	 *
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		return "Order{" + "orderName=" + orderName + ", problem=" + problem + ", priority=" + priority + ", divisibleLevel=" + divisibleLevel + '}';
	}
}
