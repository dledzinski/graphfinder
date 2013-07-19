/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.graph;

/**
 * Graf
 * @author damian
 */
public class Graph {

	// wezly
	protected final Node[] nodes;

	/**
	 * Tworzy wezly
	 * @param nodeNumber ilosc wezlow
	 */
	public Graph(int nodeNumber) {
		nodes = new Node[nodeNumber];
		for (int i = 0; i < nodeNumber; i++) {
			Node node = new Node(i);
			nodes[i] = node;
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < nodes.length; i++) {
			s += nodes[i] + "\n";
		}
		return s;
	}

	/**
	 * Zwraca wezly
	 * @return
	 */
	public Node[] getNodes() {
		return nodes;
	}

	/**
	 * Metoda zwraca najwiekszy wspolny dzielnik
	 * @param a
	 * @param b
	 * @return
	 */
	public static int gcd(int a, int b) {
		int c;
		while (b != 0) {
			c = a % b;
			a = b;
			b = c;
		}
		return a;
	}
}
