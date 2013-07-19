/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import graphfinder3.util.MonitoringData;
import java.io.Serializable;

/**
 *
 * @author damian
 */
public class ClientInfo implements Serializable, Comparable<ClientInfo> {

	// Nazwa klienta
	private final String clientName;
	// Adres ip klienta
	private final String ipAddress;
	// Dane monitoringu
	private MonitoringData monitoringData = null;
	// Czas ostatniego kontaktu
	private long lastActivityTime = System.currentTimeMillis();
	// flaga aktywnosci
	private boolean active = true;

	/**
	 * Tworzy nowy obiekt informacji o kliencie
	 *
	 * @param clientName
	 * @param clientType
	 * @param protocolVersion
	 * @param ipAddress
	 */
	public ClientInfo(String clientName, String clientIp) {
		this.clientName = clientName;
		this.ipAddress = clientIp;
	}

	/**
	 * Okresla czy klient jest aktywny
	 *
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Zwraca ip klienta
	 *
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Zwraca nazwe klienta
	 *
	 * @return
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Zwraca czas ostatniego kontaktu
	 *
	 * @return
	 */
	public long getLastActivityTime() {
		return lastActivityTime;
	}

	/**
	 * Zwraca dane z monitoringu klienta
	 *
	 * @return
	 */
	public MonitoringData getMonitoringData() {
		return monitoringData;
	}

	/**
	 * Ustawia flage aktywnosci
	 *
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Ustawia czas ostatniego kontaktu
	 *
	 * @param lastContactTime
	 */
	public void setLastActivityTime(long lastContactTime) {
		this.lastActivityTime = lastContactTime;
	}

	/**
	 * Ustawia dane z monitoringu
	 *
	 * @param monitorData
	 */
	public void setMonitoringData(MonitoringData monitorData) {
		this.monitoringData = monitorData;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ClientInfo other = (ClientInfo) obj;
		if ((this.clientName == null) ? (other.clientName != null) : !this.clientName.equals(other.clientName)) {
			return false;
		}
		if ((this.ipAddress == null) ? (other.ipAddress != null) : !this.ipAddress.equals(other.ipAddress)) {
			return false;
		}
		if (this.monitoringData != other.monitoringData && (this.monitoringData == null || !this.monitoringData.equals(other.monitoringData))) {
			return false;
		}
		if (this.lastActivityTime != other.lastActivityTime) {
			return false;
		}
		if (this.active != other.active) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (this.clientName != null ? this.clientName.hashCode() : 0);
		hash = 97 * hash + (this.ipAddress != null ? this.ipAddress.hashCode() : 0);
		hash = 97 * hash + (this.monitoringData != null ? this.monitoringData.hashCode() : 0);
		hash = 97 * hash + (int) (this.lastActivityTime ^ (this.lastActivityTime >>> 32));
		hash = 97 * hash + (this.active ? 1 : 0);
		return hash;
	}

	
	
	@Override
	public String toString() {
		return "ClientInfo{" + "clientName=" + clientName + ", ipAddress=" + ipAddress + ", lastActivityTime=" + lastActivityTime + ", active=" + active + '}';
	}

	@Override
	public int compareTo(ClientInfo o) {
		return clientName.compareTo(o.clientName);
	}
}
