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
public class Chr5EhamOhamDiameter extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr5EhamOhamDiameter instance = null;

	private Chr5EhamOhamDiameter() {
		super(2, 2);
	}

	public synchronized static Chr5EhamOhamDiameter getInstance() {
		if (instance == null) {
			instance = new Chr5EhamOhamDiameter();
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
				ringGraph.createHamilton(params[0], complexity, 0);
				ringGraph.createHamilton(params[1], complexity, 1);
				ringGraph.createDiameter();
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return RingGraph.isValidHamiltonLength(nodeNumber, params[0], complexity) && RingGraph.isValidHamiltonLength(nodeNumber, params[1], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
