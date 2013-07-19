/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.BuildRule;
import graphfinder3.data.Graph;
import graphfinder3.data.Problem;
import graphfinder3.data.ResultSet;
import graphfinder3.graphbuilding.*;
import java.util.*;

/**
 * Klas sluzaca do rozbijania problemu na drobne i skladania wyniku w calosc
 *
 * @author damian
 */
public class ProblemDivider {

	// problem do rozwiazania
	private final Problem problemToDivide;
	// poziom rozbicia
	private final int divisibleLevel;

	/**
	 * Tworzy obiekt rozbijacza
	 *
	 * @param problemToDivide
	 * @param divisibleLevel
	 */
	public ProblemDivider(Problem problemToDivide, int divisibleLevel) {
		this.problemToDivide = problemToDivide;
		this.divisibleLevel = divisibleLevel;
	}

	/**
	 * Metoda generuje kolejne przypadki grafow, po kazdym odnalezieniu wywoluje
	 * metode listenera
	 *
	 * @param listener
	 */
	public void generateSubProblems(DivideListener listener) {
		generateSubProblems(problemToDivide, divisibleLevel, listener);
	}

	/**
	 * Metoda generuje problemy, wywolujac metoda interfejsu za kazdym razem
	 * kiedy znajdzie problem
	 *
	 * @param problem problem do rozbicia
	 * @param level ilosc poziomow na ktore ma byc rozbite
	 * @param listener sluchacz
	 */
	private void generateSubProblems(Problem problem, int level, DivideListener listener) {

		// jesli problem jest podzielny i rozbijamy dalej
		if (problem.isDivisible() && level > 0) {
			// rozbijanie na podproblemy
			final Set<Problem> subProblems = getSubProblems(problem);
			// przejscie po podproblemach
			for (Problem subProblem : subProblems) {
				generateSubProblems(subProblem, level - 1, listener);
			}
		} else {
			listener.newSubProblem(problem);
		}

	}

	/**
	 * Zwraca problemy do rozwiazania
	 *
	 * @return
	 */
	public Set<Problem> getSubProblems() {
		// tu benda wyniki
		// zapisujemy problem pocztkowy
		Set<Problem> subProblems = new HashSet<Problem>(Collections.singleton(problemToDivide));
		// petla po ilosci poziomow zaglebiania sie
		for (int i = 0; i < divisibleLevel; i++) {

			// sproawdzanie czy element jest podzielny
			if ((!subProblems.isEmpty()) && subProblems.iterator().next().isDivisible()) {
				subProblems = getSubProblems(subProblems);
			} else {
				break;
			}
		}
		return subProblems;
	}

	/**
	 * Rozbija zbior problemow na podproblemy
	 *
	 * @param problems
	 * @return
	 */
	private Set<Problem> getSubProblems(Set<Problem> problems) {
		// wyniki
		final Set<Problem> subProblems = new HashSet<Problem>();
		// petla po wszystkich
		for (Problem problem : problems) {
			subProblems.addAll(getSubProblems(problem));
		}
		return subProblems;
	}

	/**
	 * Metoda rozbija problem na podproblemy w jednym poziomie
	 *
	 * @param problemToSolve
	 * @return zbior podproblemow
	 */
	private Set<Problem> getSubProblems(Problem problem) {
		// wyluskiwanie grafow bedacych podzbiorami problemu
		final Map<Integer, Graph> subGraphs = buildSubGraphs(problem);
		// wyniki
		final Set<Problem> subProblems = new HashSet<Problem>();

		// nowa lista regul budowy
		final List<BuildRule> newBuildRules = new ArrayList<BuildRule>(problem.getBuildRules());
		// jesli pierwsze zadanie to nie FREE, NRFREE, LONGESTS
		if ((newBuildRules.size() > 1) || ((!newBuildRules.get(0).equals(BuildRule.FREE)) && (!newBuildRules.get(0).equals(BuildRule.NRFREE)) && (!newBuildRules.get(0).equals(BuildRule.LONGESTS)))) {
			// usuwanie piwerszego zadania - wykonane
			newBuildRules.remove(0);
		} else {
			// sprawdzanie ile wolnych polaczen ma graf
			if (problem.getCurrentGraph().getNodeWithUnusedConnectionNumber() <= 2) {
				// nie trzeba bedzie laczyc dalej
				newBuildRules.remove(0);
			}
		}

		// przejscie po rozwiazaniach
		for (Integer param : subGraphs.keySet()) {
			// stos parametrow
			final List<Integer> newParametersStack = new ArrayList<Integer>(problem.getParametersStack());
			newParametersStack.add(param);
			// tworzenie podproblemu
			Problem subProblem = new Problem(newBuildRules, problem.getTasks(), subGraphs.get(param), newParametersStack);
			// dodawanie do zbioru
			subProblems.add(subProblem);
		}
		return subProblems;
	}

	/**
	 * Metoda dobiera odpowiedniego budowniczego i buduje kombinacje grafow
	 *
	 * @return
	 */
	private Map<Integer, Graph> buildSubGraphs(Problem problem) {
		Builder builder = Builder.getBuilder(problem.getBuildRules().get(0), problem.getCurrentGraph());
		return builder.getSubGraphs();
	}
}
