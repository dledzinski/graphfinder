/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Graf
 *
 * @author damian
 */
public class Graph implements Serializable {

	// stopien grafu
	private final int degree;
	// tablica sasiadow
	// pierwszy indeks to wezel, a drugi to numer sasiada
	private final int[][] connections;

	/**
	 * Tworzy graf bez polaczen (indeksy sasiadow rowne -1)
	 *
	 * @param nodeNumber ilosc wezlow
	 * @param degree stopien grafu
	 */
	public Graph(int nodeNumber, int degree) {
		this.degree = degree;
		connections = new int[nodeNumber][degree];
		// zaznaczanie braku polaczen
		for (int i = 0; i < connections.length; i++) {
			for (int j = 0; j < degree; j++) {
				connections[i][j] = -1;
			}
		}
	}

	/**
	 * Konstruktor kopiujacy
	 *
	 * @param anotjerGraph graf do skopiowania
	 */
	public Graph(Graph anotjerGraph) {
		this.degree = anotjerGraph.degree;
		connections = new int[anotjerGraph.connections.length][degree];
		for (int i = 0; i < connections.length; i++) {
			System.arraycopy(anotjerGraph.connections[i], 0, connections[i], 0, degree);
		}
	}

	/**
	 * Zwraca stopien grafu
	 *
	 * @return
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Zwraca polaczenia w grafie
	 *
	 * @return
	 */
	public int[][] getConnections() {
		return connections;
	}

	/**
	 * Zwraca ilosc wezlow
	 *
	 * @return
	 */
	public int getNodeNumber() {
		return connections.length;
	}

	/**
	 * Metoda zwraca sasiadow wezla o podanym indeksie
	 *
	 * @param index indeks
	 * @return
	 */
	public int[] getNeighbours(int index) {
		return connections[index];
	}

	/**
	 * Metoda zwraca ilosc niewykorzystanych polaczen przez wezel o podanym
	 * indeksie
	 *
	 * @param index
	 * @return
	 */
	public int getNodeUnusedConnectionsNumber(int index) {
		for (int i = 0; i < degree; i++) {
			if (connections[index][i] == -1) {
				return degree - i;
			}
		}
		return 0;
	}

	/**
	 * Zwraca wezel z niewykorzystanym polaczeniem
	 *
	 * @return indeks wezla lub -1
	 */
	public int getFirstNodeWithUnusedConnection() {
		for (int i = 0; i < connections.length; i++) {
			for (int j = 0; j < degree; j++) {
				if (connections[i][j] == -1) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Zwraca wezel z niewykorzystanym polaczeniem
	 *
	 * @param skippedNode pomijany wezel
	 * @return indeks wezla lub -1
	 */
	public int getNodeWithUnusedConnection(int skippedNode) {
		for (int i = 0; i < connections.length; i++) {
			if (i != skippedNode) {
				for (int j = 0; j < degree; j++) {
					if (connections[i][j] == -1) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Zwraca ilosc wezlow z niewykorzystanym polaczeniem
	 *
	 * @return
	 */
	public int getNodeWithUnusedConnectionNumber() {
		int counter = 0;
		for (int i = 0; i < connections.length; i++) {
			for (int j = degree - 1; j >= 0; j--) {
				if (connections[i][j] == -1) {
					counter++;
					break;
				}
			}

		}
		return counter;
	}

	/**
	 * Metoda zwraca true jesli jest jakikolwiek wezel z niewykorzystanym
	 * polaczeniem
	 *
	 * @return
	 */
	public boolean isAnyUnusedConnection() {
		// sprawdzanie od tylu, zeby bylo szybciej
		for (int i = connections.length - 1; i >= 0; i--) {
			for (int j = degree - 1; j >= 0; j--) {
				if (connections[i][j] == -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Tworzy polaczenia miedzy dwoma wezlami
	 *
	 * @param index1
	 * @param index2
	 */
	public void setConnection(int index1, int index2) {
		connections[index1][getFirstUnusedConnectionIndex(index1)] = index2;
		connections[index2][getFirstUnusedConnectionIndex(index2)] = index1;
	}

	/**
	 * Metoda zwraca indeks pierwszego nieuzywanego polaczenia dla wezla o
	 * podanym indeksie
	 *
	 * @param index
	 * @return indeks polaczenia do wykorzystania lub -1 jesli takich nie ma
	 */
	private int getFirstUnusedConnectionIndex(int index) {
		for (int i = 0; i < degree; i++) {
			if (connections[index][i] == -1) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Graph other = (Graph) obj;
		if (this.degree != other.degree) {
			return false;
		}
		if (!Arrays.deepEquals(this.connections, other.connections)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + this.degree;
		hash = 23 * hash + Arrays.deepHashCode(this.connections);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Graph{" + "degree=").append(degree).append(", connections={");
		for (int i = 0; i < connections.length; i++) {
			s.append(i).append(":");
			s.append(Arrays.toString(connections[i]));
			if (i < connections.length - 1) {
				s.append(", ");
			}
		}
		s.append("}}");
		return s.toString();
	}
}
