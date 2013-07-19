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
public class Chr6EhamOhamDiameterChord extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr6EhamOhamDiameterChord instance = null;

	private Chr6EhamOhamDiameterChord() {
		super(2, 3);
	}

	public synchronized static Chr6EhamOhamDiameterChord getInstance() {
		if (instance == null) {
			instance = new Chr6EhamOhamDiameterChord();
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
				ringGraph.createHamilton(params[0], complexity, 0);
				ringGraph.createHamilton(params[1], complexity, 1);
				ringGraph.createDiameter();
				ringGraph.createChord(params[2], complexity/2, 0);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return RingGraph.isValidHamiltonLength(nodeNumber, params[0], complexity) && RingGraph.isValidHamiltonLength(nodeNumber, params[1], complexity) && RingGraph.isValidChordLength(nodeNumber, params[2], complexity/2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
