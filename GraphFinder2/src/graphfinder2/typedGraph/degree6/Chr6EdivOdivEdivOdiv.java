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
public class Chr6EdivOdivEdivOdiv extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr6EdivOdivEdivOdiv instance = null;

	private Chr6EdivOdivEdivOdiv() {
		super(2, 4);
	}

	public synchronized static Chr6EdivOdivEdivOdiv getInstance() {
		if (instance == null) {
			instance = new Chr6EdivOdivEdivOdiv();
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
				ringGraph.createDivisible(params[2], complexity, 0);
				ringGraph.createDivisible(params[3], complexity, 1);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[0] != params[2] && params[1] != params[3] && RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[1], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[2], complexity) && RingGraph.isValidDivisibleLength(nodeNumber, params[3], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
