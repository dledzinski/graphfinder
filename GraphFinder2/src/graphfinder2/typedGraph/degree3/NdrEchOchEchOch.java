/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree3;

import graphfinder2.graph.Graph;
import graphfinder2.graph.NdrGraph;
import graphfinder2.graph.RingGraph;
import graphfinder2.typedGraph.AbstractTypedGraph;
import graphfinder2.typedGraph.AbstractTypedGraphCreator;
import graphfinder2.typedGraph.TypedGraph;

/**
 *
 * @author damian
 */
public class NdrEchOchEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static NdrEchOchEchOch instance = null;

	private NdrEchOchEchOch() {
		super(2 * 2, 4, true);
	}

	public synchronized static NdrEchOchEchOch getInstance() {
		if (instance == null) {
			instance = new NdrEchOchEchOch();
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
				RingGraph ndrGraph = new RingGraph(nodeNumber);
				// dodawanie cieciw
				ndrGraph.createChord(params[0], complexity / 2, 0);
				ndrGraph.createChord(params[1], complexity / 2, 1);
				ndrGraph.createChord(params[2], complexity / 2, 0);
				ndrGraph.createChord(params[3], complexity / 2, 1);
				return ndrGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return params[0] != params[2] && params[1] != params[3] && RingGraph.isValidChordLength(nodeNUmber, params[0], complexity / 2) && RingGraph.isValidChordLength(nodeNUmber, params[1], complexity / 2) && RingGraph.isValidChordLength(nodeNUmber, params[2], complexity / 2) && NdrGraph.isValidChordLength(nodeNUmber, params[3], complexity / 2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
