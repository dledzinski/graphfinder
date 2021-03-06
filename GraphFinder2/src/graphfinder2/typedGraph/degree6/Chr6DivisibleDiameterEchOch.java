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
public class Chr6DivisibleDiameterEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr6DivisibleDiameterEchOch instance = null;

	private Chr6DivisibleDiameterEchOch() {
		super(2, 3);
	}

	public synchronized static Chr6DivisibleDiameterEchOch getInstance() {
		if (instance == null) {
			instance = new Chr6DivisibleDiameterEchOch();
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
				ringGraph.createDivisible(params[0], complexity/2, 0);
				ringGraph.createDiameter();
				ringGraph.createChord(params[1], complexity, 0);
				ringGraph.createChord(params[2], complexity, 1);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return RingGraph.isValidDivisibleLength(nodeNumber, params[0], complexity/2) && RingGraph.isValidChordLength(nodeNumber, params[1], complexity) && RingGraph.isValidChordLength(nodeNumber, params[2], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[1] < params[2];
	}
}
