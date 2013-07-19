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
public final class Chr5HamiltonChord extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr5HamiltonChord instance = null;

	private Chr5HamiltonChord() {
		super(1, 2);
	}

	public synchronized static Chr5HamiltonChord getInstance() {
		if (instance == null) {
			instance = new Chr5HamiltonChord();
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
				ringGraph.createChord(params[1], complexity, 0);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[0] != params[1] && RingGraph.isValidHamiltonLength(nodeNumber, params[0], complexity) && RingGraph.isValidChordLength(nodeNumber, params[1], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return true;
	}
}
