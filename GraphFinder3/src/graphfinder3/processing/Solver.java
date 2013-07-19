/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.Problem;
import graphfinder3.data.Result;
import graphfinder3.data.ResultSet;
import graphfinder3.data.Task;

/**
 * klasa egzekucji rozwiazania problemu
 * @author damian
 */
public class Solver {
	
	// problem do rozwiazania
	private final Problem problemToSolve;
	// zbior rozwiazan
	private final ResultSet resultSet; 
	// ilosc grafow
	private long graphCounter = 0;
	
	/**
	 * Tworzy obiekt egzekutora
	 * @param problemToSolve 
	 */
	public Solver(Problem problemToSolve) {
		this.problemToSolve = problemToSolve;
		// wykonywanie obliczen i zapis wyniku
		resultSet = solve(problemToSolve);
	}
	
	/**
	 * Zwraca zbior wynikow
	 * @return 
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	/**
	 * Zwraca licznik przeliczonych grafow
	 * @return 
	 */
	public long getGraphCounter() {
		return graphCounter;
	}
	
	/**
	 * Wykonuje wlasciwe obliczenia na grafie
	 */
	private ResultSet solve(Problem problem) {
		// czy problem jest podzielny
		if (problem.isDivisible()) {
			// dzielenie problemu
			ProblemDivider divider = new ProblemDivider(problem, 1);
			ResultCollector resultCollector = new ResultCollector(problem.getTasks());
			// dla kazdego kawalka zbieranie rozwiazan
			for (Problem subProblem : divider.getSubProblems()) {
				resultCollector.newResultSet(solve(subProblem));
			}
			// zwracanie wyniku
			return resultCollector.getResultSet();

		} else {
			// zwiekszanie licznika
			graphCounter++;
			// zwracanie wyniku
			return executeCalculation(problem);
		}
	}
	
	/**
	 * Wyznaczanie wartosci z grafu
	 * @param problem 
	 */
	private ResultSet executeCalculation(Problem problem) {
		// tworzenie obiektu kalkulacji
		Calculation calculation = new Calculation(problem.getCurrentGraph());
		ResultCollector resultCollector = new ResultCollector(problem.getTasks());
		// pobieranie wynikow
		for (Task task : problem.getTasks()) {
			double value = calculation.getResultValue(task);
			Result result = new Result(task, value, problem.getParametersStack(), problem.getCurrentGraph(), 1);
			// zapisywanie wyniku
			resultCollector.newResult(result);
		}
		// zwracanie wyniku
		return resultCollector.getResultSet();
	}
	
}
