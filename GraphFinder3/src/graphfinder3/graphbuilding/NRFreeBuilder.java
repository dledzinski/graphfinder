/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa do budowy wolnych polaczen bez powtarzania
 *
 * @author damian
 */
public class NRFreeBuilder extends Builder {

	// pierwszy wolny wezel
	private final int node1;
	// prawidlowe parametry
	private final Set<Integer> validParams;

	/**
	 * Tworzy budowniczego lancucha
	 *
	 * @param parrentGraph
	 */
	public NRFreeBuilder(Graph parrentGraph) {
		super(parrentGraph);
		// szukanie wezla poczatkowego
		node1 = parrentGraph.getFirstNodeWithUnusedConnection();
		// pasujace parametry
		validParams = createValidParams();
	}

	/**
	 * Zwraca parametry - dlugosci skokow miedzy pierwszym a jakims innym wolnym
	 * wezlem
	 *
	 * @return
	 */
	@Override
	public Set<Integer> getValidParams() {
		return validParams;
	}

	@Override
	public boolean isValidParam(int param) {
		return validParams.contains(param);
	}

	@Override
	protected void build(int param) {
		int node2 = node1 + param;
		// tworzenie kopi
		Graph copy = new Graph(parrentGraph);
		copy.setConnection(node1, node2);
		// umieszczanie w mapie
		subGraphs.put(param, copy);
	}

	@Override
	protected void build() {
		for (int param : validParams) {
			build(param);
		}
	}

	/**
	 * Tworzy pasujace parametry
	 *
	 * @return
	 */
	private Set<Integer> createValidParams() {
		Set<Integer> params = new HashSet<Integer>();
		// jesli wezel poczatkowy jest mniejszy od zera
		if (node1 < 0) {
			return params;
		}
		// polaczenia w grafie
		int[][] connections = parrentGraph.getConnections();
		int degree = parrentGraph.getDegree();

		// przejscie po wszystkich wezlach
		// przejscie po wszystkich wezlach
		for (int i = 0; i < connections.length; i++) {
			// omijanie juz przyjetego wezla
			if (i != node1) {
				// wolne polaczenie
				boolean nodeHasFreeConnection = false;
				// sprawdzanie czy jest wolne polaczenie
				for (int j = degree - 1; j >= 0; j--) {
					if (connections[i][j] == -1) {
						nodeHasFreeConnection = true;
						break;
					}
				}
				// jesli wezel ma wolne polaczenie
				if (nodeHasFreeConnection) {
					// czy istnieje juz polaczenie z pierwszysm
					boolean connectedWithNode1 = false;
					// sprawdzanie czy sie powtarza
					for (int j = 0; j < degree; j++) {
						if (connections[i][j] == node1) {
							connectedWithNode1 = true;
							break;
						}
					}
					// jesli sie nie powtarza
					if (!connectedWithNode1) {
						// dodawanie do zbioru
						params.add(i - node1);
					}
				}
			}
		}
		return params;
	}
}
