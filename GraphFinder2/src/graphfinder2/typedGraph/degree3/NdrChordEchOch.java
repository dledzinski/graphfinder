/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree3;

import graphfinder2.graph.Graph;
import graphfinder2.graph.NdrGraph;
import graphfinder2.typedGraph.AbstractTypedGraph;
import graphfinder2.typedGraph.AbstractTypedGraphCreator;
import graphfinder2.typedGraph.TypedGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public class NdrChordEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static NdrChordEchOch instance = null;

	private NdrChordEchOch() {
		super(2 * 2, 3, true);
	}

	public synchronized static NdrChordEchOch getInstance() {
		if (instance == null) {
			instance = new NdrChordEchOch();
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
				NdrGraph ndrGraph = new NdrGraph(nodeNumber);
				// dodawanie cieciw
				ndrGraph.createChord(params[0], complexity / 4, 0);
				ndrGraph.createChord(params[1], complexity / 2, 0);
				ndrGraph.createChord(params[2], complexity / 2, 1);
				return ndrGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return NdrGraph.isValidChordLength(nodeNUmber, params[0], complexity / 4) && NdrGraph.isValidChordLength(nodeNUmber, params[1], complexity / 2) && NdrGraph.isValidChordLength(nodeNUmber, params[2], complexity / 2);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[1] < params[2];
	}
}
