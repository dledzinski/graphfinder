/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa do budowy lancucha wezlow
 *
 * @author damian
 */
public class FreeBuilder extends Builder {

	// pierwszy wolny wezel
	private final int node1;
	// prawidlowe parametry
	private final Set<Integer> validParams;

	/**
	 * Tworzy budowniczego lancucha
	 *
	 * @param parrentGraph
	 */
	public FreeBuilder(Graph parrentGraph) {
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
					// dodawanie do zbioru
					params.add(i - node1);
				}
			}
		}
		return params;
	}
}
