/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.Result;
import graphfinder3.data.ResultSet;
import graphfinder3.data.Task;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Klasa do zbierania wynikow
 *
 * @author damian
 */
public class ResultCollector {

	// polecenia do wykonania
	private final Set<Task> tasks;
	// wyniki
	private final Map<Task, Result> results = new EnumMap<Task, Result>(Task.class);

	/**
	 * Tworzy obiekt
	 *
	 * @param tasks zadania dla ktorych wyniki beda zbierane
	 */
	public ResultCollector(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Zwraca zbior wynikow
	 * @return 
	 */
	public ResultSet getResultSet() {
		return new ResultSet(new EnumMap<Task, Result>(results));
	}
	
	/**
	 * Dodaje nowy zbior wynikow
	 * @param resultSet 
	 */
	public void newResultSet(ResultSet resultSet) {
		for (Result result : resultSet.getResults().values()) {
			newResult(result);
		}
	}
	
	/**
	 * dodaje nowy wynik do analizy
	 * @param result 
	 */
	public void newResult(Result result) {
		// sprawdzanie poprawnosci
		if (tasks.contains(result.getTask())) {
			// jesli nie ma takiego wyniku
			if (!results.containsKey(result.getTask())) {
				results.put(result.getTask(), result);
				return;
			}
			// dla pozostalyc przypadkow
			switch (result.getTask()) {
				case BEST_RADIUS:
				case RADIUS_FROM_0:
				case DIAMETER_FROM_ALL:
				case DIAMETER_FROM_0_TO_1:
				case DIAMETER_FROM_0_TO_3:
					// jesli wynik jest mniejszy to zapisujemy
					if (result.getValue() < results.get(result.getTask()).getValue()) {
						results.put(result.getTask(), result);
					} 
					// jesli ilosc jest rowna
					if (result.getValue() == results.get(result.getTask()).getValue()) {
						// nowy licznikk grafow spelniajacy warunek
						long newGraphCounter = results.get(result.getTask()).getGraphCounter() + result.getGraphCounter();
						// tworzenie nowego wyniku ze zmienionym licznikiem grafow
						Result newResult = new Result(results.get(result.getTask()), newGraphCounter);
						results.put(result.getTask(), newResult);
					} 
					break;
				case BEST_AVERAGE:
				case AVERAGE_FROM_0:
				case AVERAGE_FROM_0_TO_1:
				case AVERAGE_FROM_0_TO_3:
				case AVERAGE_FROM_0_TO_7:
				case AVERAGE_FROM_ALL:
					// jesli wynik jest mniejszy to zapisujemy
					if (result.getValue() < results.get(result.getTask()).getValue()) {
						results.put(result.getTask(), result);
					}
					// jesli ilosc jest rowna
					if (result.getValue() == results.get(result.getTask()).getValue()) {
						// nowy licznikk grafow spelniajacy warunek
						long newGraphCounter = results.get(result.getTask()).getGraphCounter() + result.getGraphCounter();
						// tworzenie nowego wyniku ze zmienionym licznikiem grafow
						Result newResult = new Result(results.get(result.getTask()), newGraphCounter);
						results.put(result.getTask(), newResult);
					} 
					break;
				default:
					throw new RuntimeException("Unsupported task: " + result.getTask());
			}
		} else {
			throw new RuntimeException("Unsupported task: " + result.getTask());
		}
	}
}
