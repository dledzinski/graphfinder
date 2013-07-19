/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree4;

import graphfinder2.graph.Graph;
import graphfinder2.graph.RingGraph;
import graphfinder2.typedGraph.AbstractTypedGraph;
import graphfinder2.typedGraph.AbstractTypedGraphCreator;
import graphfinder2.typedGraph.TypedGraph;

/**
 *
 * @author damian
 */
public class Chr4EchOchEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr4EchOchEchOch instance = null;

	private Chr4EchOchEchOch() {
		super(2, 4);
	}

	public synchronized static Chr4EchOchEchOch getInstance() {
		if (instance == null) {
			instance = new Chr4EchOchEchOch();
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
				ringGraph.createChord(params[0], complexity, 0);
				ringGraph.createChord(params[1], complexity, 1);
				ringGraph.createChord(params[2], complexity, 0);
				ringGraph.createChord(params[3], complexity, 1);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return params[0] != params[2] && params[1] != params[3] && RingGraph.isValidChordLength(nodeNUmber, params[0], complexity) && RingGraph.isValidChordLength(nodeNUmber, params[1], complexity) && RingGraph.isValidChordLength(nodeNUmber, params[2], complexity) && RingGraph.isValidChordLength(nodeNUmber, params[3], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
