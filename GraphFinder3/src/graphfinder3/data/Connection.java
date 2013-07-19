/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;

/**
 * Klasa reprezentuje polaczenie pomiedzy dwoma wezlami, na uzytek algorytmow
 *
 * @author damian
 */
public class Connection implements Serializable {

	private final int node1;
	private final int node2;

	/**
	 * Tworzy polaczenia, jesli node2 ma nizsza wartosc od node1, zamienia
	 * miejscami
	 *
	 * @param node1
	 * @param node2
	 */
	public Connection(int node1, int node2) {
		if (node1 <= node2) {
			this.node1 = node1;
			this.node2 = node2;
		} else {
			this.node1 = node2;
			this.node2 = node1;
		}
	}

	/**
	 * Zwraca wezel o nizszym indeksie
	 * @return 
	 */
	public int getNode1() {
		return node1;
	}

	/**
	 * Zwraca wezel o wyzszym indeksie
	 * @return 
	 */
	public int getNode2() {
		return node2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Connection other = (Connection) obj;
		if (this.node1 != other.node1) {
			return false;
		}
		if (this.node2 != other.node2) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 89 * hash + this.node1;
		hash = 89 * hash + this.node2;
		return hash;
	}

	@Override
	public String toString() {
		return "Connection{" + "node1=" + node1 + ", node2=" + node2 + '}';
	}
}
