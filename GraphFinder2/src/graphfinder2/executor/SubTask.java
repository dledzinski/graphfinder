/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.executor;

import graphfinder2.typedGraph.TypedGraph;
import graphfinder2.typedGraph.TypedGraphCreator;

/**
 * Podzadanie polegajace na policzeniu konkretnego przypadku grafu
 * @author damian
 */
public class SubTask {

	// kreator
	private final TypedGraphCreator creator;
	// ilosc wezlow
	private final int nodeNumber;
	// parametry
	private final int[] params;
	// wynik
	private TypedGraph result = null;

	public SubTask(TypedGraphCreator creator, int nodeNumber, int[] params) {
		this.creator = creator;
		this.nodeNumber = nodeNumber;
		this.params = params;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public int[] getParams() {
		return params;
	}

	/**
	 * Zwraca wynik jesli zadanie zostalo wykonane lub null jesli nie
	 * @return
	 */
	public TypedGraph getResult() {
		return result;
	}

	/**
	 * Ustawia wynik
	 * @param result
	 */
	public void setResult(TypedGraph result) {
		this.result = result;
	}

	/**
	 * Metoda rozwazuje zadanie
	 */
	public void solve() {
		result = creator.create(nodeNumber, params);
	}

}
