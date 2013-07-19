/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.util;

import java.io.Serializable;

/**
 * Klas do przechowywania danych domonitoringu
 *
 * @author damian
 */
public class MonitoringData implements Serializable {

	// ilosc grafow na sekunde
	private final double graphsPesSecond;
	// obciazenie cpu
	private final double cpuUsage;
	// zajetasc pamieci
	private final double ramUsage;
	// uptime
	private final String uptime;
	// pamiec jvm
	private final double heapUsage;

	/**
	 * Tworzy obiekt z danymi monitoringu
	 *
	 * @param graphsPesSecond
	 * @param cpuUsage
	 * @param ramUsage
	 * @param uptime
	 * @param heapUsage
	 */
	public MonitoringData(double graphsPesSecond, double cpuUsage, double ramUsage, String uptime, double heapUsage) {
		this.graphsPesSecond = graphsPesSecond;
		this.cpuUsage = cpuUsage;
		this.ramUsage = ramUsage;
		this.uptime = uptime;
		this.heapUsage = heapUsage;
	}

	/**
	 * Zwraca zuzycie procesora
	 *
	 * @return
	 */
	public double getCpuUsage() {
		return cpuUsage;
	}

	/**
	 * Zwraca ilosc grafow na sekunde
	 *
	 * @return
	 */
	public double getGraphsPesSecond() {
		return graphsPesSecond;
	}

	/**
	 * Zwraca zajetosc pamieci
	 *
	 * @return
	 */
	public double getRamUsage() {
		return ramUsage;
	}

	/**
	 * Zwraca uptime
	 *
	 * @return
	 */
	public String getUptime() {
		return uptime;
	}

	/**
	 * Zwraca zajetosc sterty
	 *
	 * @return
	 */
	public double getHeapUsage() {
		return heapUsage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MonitoringData other = (MonitoringData) obj;
		if (Double.doubleToLongBits(this.graphsPesSecond) != Double.doubleToLongBits(other.graphsPesSecond)) {
			return false;
		}
		if (Double.doubleToLongBits(this.cpuUsage) != Double.doubleToLongBits(other.cpuUsage)) {
			return false;
		}
		if (Double.doubleToLongBits(this.ramUsage) != Double.doubleToLongBits(other.ramUsage)) {
			return false;
		}
		if ((this.uptime == null) ? (other.uptime != null) : !this.uptime.equals(other.uptime)) {
			return false;
		}
		if (Double.doubleToLongBits(this.heapUsage) != Double.doubleToLongBits(other.heapUsage)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (int) (Double.doubleToLongBits(this.graphsPesSecond) ^ (Double.doubleToLongBits(this.graphsPesSecond) >>> 32));
		hash = 17 * hash + (int) (Double.doubleToLongBits(this.cpuUsage) ^ (Double.doubleToLongBits(this.cpuUsage) >>> 32));
		hash = 17 * hash + (int) (Double.doubleToLongBits(this.ramUsage) ^ (Double.doubleToLongBits(this.ramUsage) >>> 32));
		hash = 17 * hash + (this.uptime != null ? this.uptime.hashCode() : 0);
		hash = 17 * hash + (int) (Double.doubleToLongBits(this.heapUsage) ^ (Double.doubleToLongBits(this.heapUsage) >>> 32));
		return hash;
	}

	@Override
	public String toString() {
		return "MonitoringData{" + "graphsPesSecond=" + graphsPesSecond + ", cpuUsage=" + cpuUsage + ", ramUsage=" + ramUsage + ", uptime=" + uptime + ", heapUsage=" + heapUsage + '}';
	}
}
