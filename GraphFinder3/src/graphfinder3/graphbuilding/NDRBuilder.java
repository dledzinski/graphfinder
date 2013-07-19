/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.Graph;
import java.util.Collections;
import java.util.Set;

/**
 * Klasa do budowy lancucha wezlow
 * @author damian
 */
public class NDRBuilder extends Builder {

	/**
	 * Tworzy budowniczego lancucha
	 * @param parrentGraph 
	 */
	public NDRBuilder(Graph parrentGraph) {
		super(parrentGraph);
	}
	
	@Override
	protected void build() {
		// tworzenie kopi
		Graph copy = new Graph(parrentGraph);
		// laczenie zewnetrznego pierscienia
		for (int i = 0; i < copy.getNodeNumber() - 2; i += 2) {
			copy.setConnection(i, i + 2);
		}
		copy.setConnection(copy.getNodeNumber() - 2, 0);
		// dolaczanie wewnetrznego pierscienia
		for (int i = 1; i < copy.getNodeNumber(); i += 2) {
			copy.setConnection(i - 1, i);
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
