/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph.degree6;

import graphfinder2.graph.Graph;
import graphfinder2.graph.RingGraph;
import graphfinder2.typedGraph.AbstractTypedGraph;
import graphfinder2.typedGraph.AbstractTypedGraphCreator;
import graphfinder2.typedGraph.TypedGraph;

/**
 *
 * @author damian
 */
public class Chr6DivisibleDivisible extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr6DivisibleDivisible instance = null;

	private Chr6DivisibleDivisible() {
		super(1, 2);
	}

	public synchronized static Chr6DivisibleDivisible getInstance() {
		if (instance == null) {
			instance = new Chr6DivisibleDivisible();
		}
		return instance;
	}

	public TypedGraph create(int nodeNumber, int... params) {
		return new AbstractTypedGraph(this, nodeNumber, params) {

			@Override
			protected Graph createGraph(int nodeNUmber, int[] params) {
				// tworzenie grafu
				RingGraph ringGraph = new RingGraph(nodeNumber);
				// dodawanie cieciw
				ringGraph.createDivisible(params[0], complexity, 0);
				ringGraph.createDivisible(params[1], complexity, 0);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[0] != params[1] && RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[1], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
