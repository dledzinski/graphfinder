/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa do budowy lancucha wezlow
 * @author damian
 */
public class ChainBuilder extends Builder {

	/**
	 * Tworzy budowniczego lancucha
	 * @param parrentGraph 
	 */
	public ChainBuilder(Graph parrentGraph) {
		super(parrentGraph);
	}
	
	@Override
	protected void build() {
		// tworzenie kopi
		Graph copy = new Graph(parrentGraph);
		// laczenie
		for (int i = 0; i < copy.getNodeNumber() - 1; i++) {
			copy.setConnection(i, i + 1);
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
