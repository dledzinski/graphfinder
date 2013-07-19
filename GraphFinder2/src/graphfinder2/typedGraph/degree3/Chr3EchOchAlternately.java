/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph.degree3;

import graphfinder2.typedGraph.*;
import graphfinder2.graph.Graph;
import graphfinder2.graph.RingGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public class Chr3EchOchAlternately extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr3EchOchAlternately instance = null;

	private Chr3EchOchAlternately() {
		super(2, 2);
	}

	public synchronized static Chr3EchOchAlternately getInstance() {
		if (instance == null) {
			instance = new Chr3EchOchAlternately();
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
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return RingGraph.isValidAlternatelyChordLength(nodeNUmber, params[0], complexity, 0) && RingGraph.isValidAlternatelyChordLength(nodeNUmber, params[1], complexity, 1);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
