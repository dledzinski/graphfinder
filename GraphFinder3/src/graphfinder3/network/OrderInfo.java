/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import java.io.Serializable;

/**
 * Klasa do przechowywynia informacji o rozkazach
 *
 * @author damian
 */
public class OrderInfo implements Serializable, Comparable<OrderInfo> {

	// nazwa
	private final String orderName;
	// status
	private final boolean finished;
	// priorytet
	private final int priority;
	// ilosc wykonanych zadan
	private final int resultCounter;
	// calkowita ilosc rozkazow
	private final int totalResultNumber;
	// ilosc przetworzonych grafow
	private final long graphCounter;
	// czas procesowania
	private final long processingTime;
	// czas utworzenia zadania
	private final long creationTime;

	/**
	 * Informacje o zadaniach
	 *
	 * @param orderName
	 * @param finished
	 * @param priority
	 * @param resultCounter
	 * @param totalResultNumber
	 * @param graphCounter
	 * @param processingTime
	 * @param creationTime
	 */
	public OrderInfo(String orderName, boolean finished, int priority, int resultCounter, int totalResultNumber, long graphCounter, long processingTime, long creationTime) {
		this.orderName = orderName;
		this.finished = finished;
		this.priority = priority;
		this.resultCounter = resultCounter;
		this.totalResultNumber = totalResultNumber;
		this.graphCounter = graphCounter;
		this.processingTime = processingTime;
		this.creationTime = creationTime;
	}

	/**
	 * zwraca czas utworzenia
	 *
	 * @return
	 */
	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * Okresla czy jest skonczone
	 *
	 * @return
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Zwraca nazwe zadania
	 *
	 * @return
	 */
	public String getOrderName() {
		return orderName;
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
	 * Zwraca czas procesowania
	 *
	 * @return
	 */
	public long getProcessingTime() {
		return processingTime;
	}

	/**
	 * Zwraca ilosc wykonanych podzadan
	 *
	 * @return
	 */
	public int getResultCounter() {
		return resultCounter;
	}

	/**
	 * Zwraca calkowita liczbe podzadan
	 *
	 * @return
	 */
	public int getTotalResultNumber() {
		return totalResultNumber;
	}

	/**
	 * Zwraca ilosc przetworzonych grafow
	 *
	 * @return
	 */
	public long getGraphCounter() {
		return graphCounter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final OrderInfo other = (OrderInfo) obj;
		if ((this.orderName == null) ? (other.orderName != null) : !this.orderName.equals(other.orderName)) {
			return false;
		}
		if (this.finished != other.finished) {
			return false;
		}
		if (this.priority != other.priority) {
			return false;
		}
		if (this.resultCounter != other.resultCounter) {
			return false;
		}
		if (this.totalResultNumber != other.totalResultNumber) {
			return false;
		}
		if (this.graphCounter != other.graphCounter) {
			return false;
		}
		if (this.processingTime != other.processingTime) {
			return false;
		}
		if (this.creationTime != other.creationTime) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.orderName != null ? this.orderName.hashCode() : 0);
		hash = 53 * hash + (this.finished ? 1 : 0);
		hash = 53 * hash + this.priority;
		hash = 53 * hash + this.resultCounter;
		hash = 53 * hash + this.totalResultNumber;
		hash = 53 * hash + (int) (this.graphCounter ^ (this.graphCounter >>> 32));
		hash = 53 * hash + (int) (this.processingTime ^ (this.processingTime >>> 32));
		hash = 53 * hash + (int) (this.creationTime ^ (this.creationTime >>> 32));
		return hash;
	}

	@Override
	public String toString() {
		return "OrderInfo{" + "orderName=" + orderName + ", finished=" + finished + ", priority=" + priority + ", resultCounter=" + resultCounter + ", totalResultNumber=" + totalResultNumber + '}';
	}

	@Override
	public int compareTo(OrderInfo o) {
		return orderName.compareTo(o.orderName);
	}
}
