/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import java.io.Serializable;

/**
 * Dane do logowania
 *
 * @author damian
 */
public class LoginData implements Serializable {

	// Nazwa klienta
	private final String clientName;
	// Typ klienta
	private final String clientType;

	/**
	 * Dane do logowania
	 *
	 * @param clientName
	 * @param clientType
	 * @param protocolVersion
	 */
	public LoginData(String clientName, String clientType) {
		this.clientName = clientName;
		this.clientType = clientType;
	}

	/**
	 * Zwraca propozycje nazwy klienta
	 *
	 * @return
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Zwraca typ klienta
	 *
	 * @return
	 */
	public String getClientType() {
		return clientType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LoginData other = (LoginData) obj;
		if ((this.clientName == null) ? (other.clientName != null) : !this.clientName.equals(other.clientName)) {
			return false;
		}
		if ((this.clientType == null) ? (other.clientType != null) : !this.clientType.equals(other.clientType)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 23 * hash + (this.clientName != null ? this.clientName.hashCode() : 0);
		hash = 23 * hash + (this.clientType != null ? this.clientType.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "LoginData{" + "clientName=" + clientName + ", clientType=" + clientType + '}';
	}
}
