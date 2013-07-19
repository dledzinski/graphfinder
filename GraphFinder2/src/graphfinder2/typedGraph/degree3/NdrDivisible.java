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
public class NdrDivisible extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static NdrDivisible instance = null;

	private NdrDivisible() {
		super(1 * 2, 1, true);
	}

	public synchronized static NdrDivisible getInstance() {
		if (instance == null) {
			instance = new NdrDivisible();
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
				ndrGraph.createDivisible(params[0], complexity / 2, 0);
				return ndrGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return NdrGraph.isValidDivisibleLength(nodeNUmber, params[0], complexity / 2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return true;
	}
}
