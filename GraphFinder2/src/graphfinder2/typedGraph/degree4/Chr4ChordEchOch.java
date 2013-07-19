/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph.degree4;

import graphfinder2.graph.Graph;
import graphfinder2.graph.NdrGraph;
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
public class Chr4ChordEchOch extends AbstractTypedGraphCreator {

	// jedyny obiekt
	private static Chr4ChordEchOch instance = null;

	private Chr4ChordEchOch() {
		super(2, 3);
	}

	public synchronized static Chr4ChordEchOch getInstance() {
		if (instance == null) {
			instance = new Chr4ChordEchOch();
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
				ringGraph.createChord(params[0], complexity/2, 0);
				ringGraph.createChord(params[1], complexity, 0);
				ringGraph.createChord(params[2], complexity, 1);
				return ringGraph;
			}
		};

	}

	public boolean isValidNodeNumber(int nodeNumber) {
		return nodeNumber % (2 * complexity) == 0;
	}

	public boolean isValidParams(int nodeNUmber, int[] params) {
		return RingGraph.isValidChordLength(nodeNUmber, params[0], complexity/2) && RingGraph.isValidChordLength(nodeNUmber, params[1], complexity) && RingGraph.isValidChordLength(nodeNUmber, params[2], complexity);
	}

	public boolean isOptimalParams(int nodeNumber, int[] params) {
		return params[1] < params[2];
	}
}
