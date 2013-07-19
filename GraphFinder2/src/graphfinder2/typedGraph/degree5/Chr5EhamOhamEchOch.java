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
public class Chr5EhamOhamEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr5EhamOhamEchOch instance = null;

	private Chr5EhamOhamEchOch() {
		super(2, 4);
	}

	public synchronized static Chr5EhamOhamEchOch getInstance() {
		if (instance == null) {
			instance = new Chr5EhamOhamEchOch();
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
				ringGraph.createHamilton(params[0], complexity, 0);
				ringGraph.createHamilton(params[1], complexity, 1);
				ringGraph.createChord(params[2], complexity, 0);
				ringGraph.createChord(params[3], complexity, 1);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNumber, int[] params) {
		return params[0] != params[2] && params[1] != params[3] && RingGraph.isValidHamiltonLength(nodeNumber, params[0], complexity) && RingGraph.isValidHamiltonLength(nodeNumber, params[1], complexity) && RingGraph.isValidChordLength(nodeNumber, params[2], complexity) && RingGraph.isValidChordLength(nodeNumber, params[3], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[0] < params[1];
	}
}
