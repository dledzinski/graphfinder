/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.executor;

import graphfinder2.typedGraph.TypedGraph;
import graphfinder2.typedGraph.TypedGraphCreator;
import java.util.LinkedList;

/**
 * Zadanie polegajace na
 * @author damian
 */
public class Task {

	// kreator
	private final TypedGraphCreator creator;
	// ilosc wezlow
	private final int nodeNumber;
	// ilosc podzadan
	private final int totalSubtaskNumber;
	// zadania do rozwiazania
	private final LinkedList<SubTask> subtasksToSolve = new LinkedList<SubTask>();
	// ilosc zadan rozwiazanych
	private int solvedSubtaskNumber = 0;
	// najnizsza srednia
	private double lowestAverage = Double.MAX_VALUE;
	// najlepzy wynik
	private TypedGraph bestResult = null;
	// optymalizacja
	private final boolean optimalize;

	/**
	 * Tworzy
	 * @param nodeNumber
	 */
	public Task(TypedGraphCreator creator, int nodeNumber, boolean optimalize) {
		this.nodeNumber = nodeNumber;
		this.creator = creator;
		this.optimalize = optimalize;
		// wyluskiwanie parametrow
		for (int[] params : creator.getValidParams(nodeNumber, optimalize)) {
			subtasksToSolve.add(new SubTask(creator, nodeNumber, params));
		}
		this.totalSubtaskNumber = subtasksToSolve.size();
	}

	/**
	 * Zwraca zadanie lub null jesli takiego nie ma
	 * @return
	 */
	public synchronized SubTask nextSubtask() {
		if (!subtasksToSolve.isEmpty()) {
			return subtasksToSolve.removeFirst();
		}
		return null;
	}

	public synchronized void subtaskSolved(SubTask subTask) {
		TypedGraph result = subTask.getResult();
		// jesli jest lepszy
		if (result.getAverage() < lowestAverage) {
			// zapis najlepszego wyniku
			lowestAverage = result.getAverage();
			bestResult = result;
		}
		solvedSubtaskNumber++;
	}

	/**
	 * Metoda okresla czy zadanie rozwiazane
	 */
	public synchronized boolean isSolved() {
		return solvedSubtaskNumber == totalSubtaskNumber;
	}

	/**
	 * Zwraca najlepszy wynik
	 * @return
	 */
	public TypedGraph getBestResult() {
		return bestResult;
	}

}
