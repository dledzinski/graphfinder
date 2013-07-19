/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author damian
 */
public class NdrEchBuilder extends Builder {

	/**
	 * Twozry polaczenia typu chord
	 *
	 * @param parrentGraph
	 */
	public NdrEchBuilder(Graph parrentGraph) {
		super(parrentGraph);
	}

	@Override
	public Set<Integer> getValidParams() {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i <= parrentGraph.getNodeNumber() / 4; i++) {
			if (isValidParam(i)) {
				set.add(i);
			}
		}
		return set;
	}

	@Override
	public boolean isValidParam(int param) {
		if ((param > 2) && (param <= parrentGraph.getNodeNumber() / 4) && (param % 2 == 0) && ((param / 2) % 2 == 1)) {
			return true;
		}
		return false;
	}

	@Override
	protected void build(int param) {
		// tworzenie kopii
		Graph copy = new Graph(parrentGraph);

		for (int i = 1; i < copy.getNodeNumber(); i += 8) {
			// 2 * zeby ominac zewnetrzny ring
			int neighbour = (i + 2 * param) % copy.getNodeNumber();
			copy.setConnection(i, neighbour);
		}
		// umieszczanie w mapie
		subGraphs.put(param, copy);
	}

	@Override
	protected void build() {
		for (int i : getValidParams()) {
			build(i);
		}
	}
}
