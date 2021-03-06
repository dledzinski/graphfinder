/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Klasa do budowy lancucha wezlow
 *
 * @author damian
 */
public class TreeBuilder extends Builder {

	/**
	 * Tworzy budowniczego lancucha
	 *
	 * @param parrentGraph
	 */
	public TreeBuilder(Graph parrentGraph) {
		super(parrentGraph);
	}

	@Override
	protected void build() {
		// ograniczenie
		final int levelLimit = Integer.MAX_VALUE;
		// tworzenie kopi
		Graph copy = new Graph(parrentGraph);
		// stopien
		int degree = copy.getDegree();

		// sprawdzanie czy uda sie utworzyc proste drzewo
		if (copy.getNodeNumber() >= copy.getDegree()) {
			// kolejka wezlow oczekujacych
			Queue<Integer> queue = new LinkedList<Integer>();

			// tworzenie korzenia
			for (int i = 0; i < copy.getDegree(); i++) {
				copy.setConnection(0, i + 1);
				queue.add(i + 1);
			}
			// ilosc wezlow uzytych
			int nodeCounter = degree + 1;

			// kolejne pietra
			for (int i = 1; i <= levelLimit; i++) {
				// ilosc pozostalych wezlow
				int nodesLeft = copy.getNodeNumber() - nodeCounter;
				// ilosc wezlow potrzeban do stworzenia pietra
				int nodesNeed = queue.size() * (degree - 1);

				// jesli wystarczy wezlow na obstawienie calego pietra
				if (nodesLeft >= nodesNeed) {
					// nowa kolejka
					Queue<Integer> newQueue = new LinkedList<Integer>();
					// obstawiamy nowe pietro
					for (Integer previousLevelNode : queue) {
						// laczymy kazdy z poprzedniego pietra
						for (int j = 0; j < degree - 1; j++) {
							copy.setConnection(previousLevelNode, nodeCounter);
							// dodawanie do nowej kolejki (dla tego pietra)
							newQueue.add(nodeCounter);
							nodeCounter++;
						}

					}
					// podmiana kolejek
					queue = newQueue;
				} else {
					// wyjscie
					break;
				}
			}

		}

		// umieszczanie w mapie
		subGraphs.put(0, copy);
	}

	@Override
	public Set<Integer> getValidParams() {
		return Collections.singleton(0);
	}

	@Override
	public boolean isValidParam(int param) {
		return param == 0;
	}

	@Override
	protected void build(int param) {
		build();
	}
}
