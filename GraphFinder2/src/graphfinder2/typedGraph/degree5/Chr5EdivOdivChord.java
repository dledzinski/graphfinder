/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree5;

import graphfinder2.typedGraph.*;
import graphfinder2.graph.Graph;
import graphfinder2.graph.RingGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public final class Chr5EdivOdivChord extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr5EdivOdivChord instance = null;

	private Chr5EdivOdivChord() {
		super(2, 3);
	}

	public synchronized static Chr5EdivOdivChord getInstance() {
		if (instance == null) {
			instance = new Chr5EdivOdivChord();
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
				ringGraph.createDivisible(params[1], complexity, 1);
				ringGraph.createChord(params[2], complexity/2, 0);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[0] != params[2] && params[1] != params[2] && RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[1], complexity) && RingGraph.isValidChordLength(nodeNumber, params[2], complexity/2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
