/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph.degree5;

import graphfinder2.typedGraph.*;
import graphfinder2.graph.Graph;
import graphfinder2.graph.RingGraph;

/**
 *
 * @author damian
 */
public class Chr5DivisibleDiameter extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr5DivisibleDiameter instance = null;

	private Chr5DivisibleDiameter() {
		super(1, 1);
	}

	public synchronized static Chr5DivisibleDiameter getInstance() {
		if (instance == null) {
			instance = new Chr5DivisibleDiameter();
		}
		return instance;
	}

	/**
	 * Tworzy graf
	 * @param nodeNumber
	 * @param params
	 * @return
	 */
	public TypedGraph create(int nodeNumber, int... params) {
		return new AbstractTypedGraph(this, nodeNumber, params) {

			@Override
			protected Graph createGraph(int nodeNUmber, int[] params) {
				// tworzenie grafu
				RingGraph ringGraph = new RingGraph(nodeNumber);
				// dodawanie cieciw
				ringGraph.createDivisible(params[0], complexity, 0);
				ringGraph.createDiameter();
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return true;
	}
}
