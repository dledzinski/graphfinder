/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Connection;
import graphfinder3.data.Graph;
import graphfinder3.processing.SingleCalculation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Builder ktory tworzy grafy o najdluzszych polaczeniach
 *
 * @author damian
 */
public class LongestsBuilder extends Builder {

	// lista polaczen
	private final List<Connection> longestConnections = new ArrayList<Connection>();

	/**
	 * Tworzy builder ktory laczy najdluzsze polaczenia
	 *
	 * @param parrentGraph
	 */
	public LongestsBuilder(Graph parrentGraph) {
		super(parrentGraph);
		// uzupelnia dopuszczalen polaczenia
		fillConnections();
	}

	@Override
	public Set<Integer> getValidParams() {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < longestConnections.size(); i++) {
			set.add(i);
		}
		return set;
	}

	@Override
	public boolean isValidParam(int param) {
		// czy jest taki indeks listy
		return param >= 0 && param < longestConnections.size();
	}

	@Override
	protected void build(int param) {
		// tworzenie kopi
		Graph copy = new Graph(parrentGraph);
		copy.setConnection(longestConnections.get(param).getNode1(), longestConnections.get(param).getNode2());
		// umieszczanie w mapie
		subGraphs.put(param, copy);
	}

	@Override
	protected void build() {
		for (int i = 0; i < longestConnections.size(); i++) {
			build(i);
		}
	}

	/**
	 * Uzupelnia mozliwe polaczenia
	 */
	private void fillConnections() {

		int longestPathLength = 0;

		// przejscie po wezlach
		for (int i = 0; i < parrentGraph.getNodeNumber(); i++) {
			// sprawdzanie czy wezel posiada nieuzywane polaczenie
			if (parrentGraph.getNodeUnusedConnectionsNumber(i) > 0) {

				// dla kazdego wyliczenia
				SingleCalculation sc = new SingleCalculation(parrentGraph, i);

				// przejscie po dlugosci sciezek
				for (int j = 0; j < parrentGraph.getNodeNumber(); j++) {
					// sprawdzanie czy wezel posiada nieuzywane polaczenie
					if (i < j && parrentGraph.getNodeUnusedConnectionsNumber(j) > 0) {
						int pathLength = sc.getPathLengths()[j];

						// jest polaczenie
						if (pathLength >= 0) {
							// jesli jest dluzsze od najdluzszej i nie ma braku polaczenia
							if (pathLength >= longestPathLength && longestPathLength != -1) {
								// jesli jest dluzszy od obecnego najdluzszego
								if (pathLength > longestPathLength) {
									longestPathLength = pathLength;
									longestConnections.clear();
								}
								// dodawanie
								Connection connection = new Connection(i, j);
								if (!longestConnections.contains(connection)) {
									longestConnections.add(connection);
								}
							}
						} else {
							// brak polaczenia
							// jesli nie natrafiono wczesniej na cos takiego
							if (longestPathLength != -1) {
								longestPathLength = -1;
								longestConnections.clear();
							}
							// dodawanie
							Connection connection = new Connection(i, j);
							if (!longestConnections.contains(connection)) {
								longestConnections.add(connection);
							}
						}
					}
				}
			}
		}
	}
}
