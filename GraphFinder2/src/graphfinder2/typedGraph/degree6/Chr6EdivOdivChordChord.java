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
public class Chr6EdivOdivChordChord extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr6EdivOdivChordChord instance = null;

	private Chr6EdivOdivChordChord() {
		super(2, 4);
	}

	public synchronized static Chr6EdivOdivChordChord getInstance() {
		if (instance == null) {
			instance = new Chr6EdivOdivChordChord();
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
				ringGraph.createDivisible(params[1], complexity, 1);
				ringGraph.createChord(params[2], complexity/2, 0);
				ringGraph.createChord(params[3], complexity/2, 0);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[2] != params[3] && RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[1], complexity) && RingGraph.isValidChordLength(nodeNumber, params[2], complexity/2) && RingGraph.isValidChordLength(nodeNumber, params[3], complexity/2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
