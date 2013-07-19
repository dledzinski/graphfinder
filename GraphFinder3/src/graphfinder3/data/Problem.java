/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Klasa reprezentuje problem do rozwiazania, czyli sposob budowy grafu i
 * zadania dla tej budowy, oraz wyniki tego problemu
 *
 * @author damian
 */
public class Problem implements Serializable {

	// reguly budowy grafow, jesli brak, znaczy ze problem nadaje sie do rozwiazywania
	private final List<BuildRule> buildRules;
	// polecenia do wykonania
	private final Set<Task> tasks;
	// obecny graf
	private final Graph currentGraph;
	// stos parametrow dotarcia do tego miejsca
	private final List<Integer> parametersStack;

	/**
	 * Tworzy problem
	 *
	 * @param buildRules lista regol budowy grafu
	 * @param tasks lista zadan do rozwiazania
	 * @param currentGraph aktualny graf na tym etapie rozwiazywania problemu
	 * @param parametersStack stos parametrow uzuskany w czasie budowy
	 */
	public Problem(List<BuildRule> buildRules, Set<Task> tasks, Graph currentGraph, List<Integer> parametersStack) {
		this.buildRules = buildRules;
		this.tasks = tasks;
		this.currentGraph = currentGraph;
		this.parametersStack = parametersStack;
	}

	/**
	 * Zwraca true jesli problem jest podzielny na podproblemy
	 *
	 * @return
	 */
	public boolean isDivisible() {
		return !buildRules.isEmpty();
	}

	/**
	 * Zwraca reguly budowy grafu
	 *
	 * @return
	 */
	public List<BuildRule> getBuildRules() {
		return buildRules;
	}

	/**
	 * Zwraca graf na tym etapie budowy
	 *
	 * @return
	 */
	public Graph getCurrentGraph() {
		return currentGraph;
	}

	/**
	 * Zwraca stos parametrow
	 *
	 * @return
	 */
	public List<Integer> getParametersStack() {
		return parametersStack;
	}

	/**
	 * Zwraca zbior zadan
	 *
	 * @return
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Problem other = (Problem) obj;
		if (this.buildRules != other.buildRules && (this.buildRules == null || !this.buildRules.equals(other.buildRules))) {
			return false;
		}
		if (this.tasks != other.tasks && (this.tasks == null || !this.tasks.equals(other.tasks))) {
			return false;
		}
		if (this.parametersStack != other.parametersStack && (this.parametersStack == null || !this.parametersStack.equals(other.parametersStack))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 31 * hash + (this.buildRules != null ? this.buildRules.hashCode() : 0);
		hash = 31 * hash + (this.tasks != null ? this.tasks.hashCode() : 0);
		hash = 31 * hash + (this.parametersStack != null ? this.parametersStack.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "Problem{" + "buildRules=" + buildRules + ", tasks=" + tasks + ", parametersStack=" + parametersStack + '}';
	}
}
