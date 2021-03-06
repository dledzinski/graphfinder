/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree3;

import graphfinder2.graph.Graph;
import graphfinder2.graph.NdrGraph;
import graphfinder2.typedGraph.AbstractTypedGraph;
import graphfinder2.typedGraph.AbstractTypedGraphCreator;
import graphfinder2.typedGraph.TypedGraph;

/**
 *
 * @author damian
 */
public class NdrEhamOdiv extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static NdrEhamOdiv instance = null;

	private NdrEhamOdiv() {
		super(2 * 2, 2, true);
	}

	public synchronized static NdrEhamOdiv getInstance() {
		if (instance == null) {
			instance = new NdrEhamOdiv();
		}
		return instance;
	}

	public TypedGraph create(int nodeNumber, int... params) {
		return new AbstractTypedGraph(this, nodeNumber, params) {

			@Override
			protected Graph createGraph(int nodeNUmber, int[] params) {
				// tworzenie grafu
				NdrGraph ndrGraph = new NdrGraph(nodeNumber);
				// dodawanie cieciw
				ndrGraph.createHamilton(params[0], complexity / 2, 0);
				ndrGraph.createDivisible(params[1], complexity / 2, 1);
				return ndrGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return NdrGraph.isValidHamiltonLength(nodeNumber, params[0], complexity / 2) && NdrGraph.isValidDivisibleLength(nodeNumber, params[1], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return true;
	}
}
