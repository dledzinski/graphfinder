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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public class Chr4Divisible extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr4Divisible instance = null;

	private Chr4Divisible() {
		super(1, 1);
	}

	public synchronized static Chr4Divisible getInstance() {
		if (instance == null) {
			instance = new Chr4Divisible();
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
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return RingGraph.isValidDivisibleLength(nodeNUmber, params[0], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return true;
	}
}
