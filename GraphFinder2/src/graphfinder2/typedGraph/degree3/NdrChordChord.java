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
public class NdrChordChord extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static NdrChordChord instance = null;

	private NdrChordChord() {
		super(1 * 2, 2, true);
	}

	public synchronized static NdrChordChord getInstance() {
		if (instance == null) {
			instance = new NdrChordChord();
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
				NdrGraph ndrGraph = new NdrGraph(nodeNumber);
				// dodawanie cieciw
				ndrGraph.createChord(params[0], complexity / 2, 0);
				ndrGraph.createChord(params[1], complexity / 2, 0);
				return ndrGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return params[0] != params[1] && NdrGraph.isValidChordLength(nodeNUmber, params[0], complexity / 2) && NdrGraph.isValidChordLength(nodeNUmber, params[1], complexity / 2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
