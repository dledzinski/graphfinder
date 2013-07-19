/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;
import java.util.List;

/**
 * Klas reprezentujaca wynik
 *
 * @author damian
 */
public class Result implements Serializable {

	// zadanie na jakie to jest wynik
	private Task task;
	// wartosc wyniku
	private final double value;
	// stos parametrow
	private final List<Integer> parametersStack;
	// graf dla ktorego to jest wynik
	private final Graph graph;
	// ilosc grafow ktore mialy taki sam wynik
	private final long graphCounter;

	/**
	 * Tworzy obiekt wyniku
	 * @param task
	 * @param value
	 * @param parametersStack
	 * @param graph
	 * @param graphCounter 
	 */
	public Result(Task task, double value, List<Integer> parametersStack, Graph graph, long graphCounter) {
		this.task = task;
		this.value = value;
		this.parametersStack = parametersStack;
		this.graph = graph;
		this.graphCounter = graphCounter;
	}

	/**
	 * Tworzy obiekt wyniku taki sam jak inny obiekt ale o nawej ilosci grafow spelniajacych warunek
	 * @param anotherResult
	 * @param graphCounter 
	 */
	public Result(Result anotherResult, long graphCounter) {
		this.task = anotherResult.task;
		this.value = anotherResult.value;
		this.parametersStack = anotherResult.parametersStack;
		this.graph = anotherResult.graph;
		this.graphCounter = graphCounter;
	}

	/**
	 * Zwraca graf dla ktorego jest ten wynik
	 *
	 * @return
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Zwraca stos parametrow tworzenia grafu
	 *
	 * @return
	 */
	public List<Integer> getParametersStack() {
		return parametersStack;
	}

	/**
	 * Zwraca zadanie
	 *
	 * @return
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Zwraca wartosc wyniku
	 *
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Zwraca ilosc grafow z takim wynikiem
	 * @return 
	 */
	public long getGraphCounter() {
		return graphCounter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Result other = (Result) obj;
		if (this.task != other.task) {
			return false;
		}
		if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		if (this.parametersStack != other.parametersStack && (this.parametersStack == null || !this.parametersStack.equals(other.parametersStack))) {
			return false;
		}
		if (this.graphCounter != other.graphCounter) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (this.task != null ? this.task.hashCode() : 0);
		hash = 31 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
		hash = 31 * hash + (this.parametersStack != null ? this.parametersStack.hashCode() : 0);
		hash = 31 * hash + (int) (this.graphCounter ^ (this.graphCounter >>> 32));
		return hash;
	}

	@Override
	public String toString() {
		return "Result{" + "task=" + task + ", value=" + value + ", parametersStack=" + parametersStack + ", graphCounter=" + graphCounter + '}';
	}

	
}
