/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.data.*;
import graphfinder3.graphbuilding.*;
import graphfinder3.processing.Calculation;
import graphfinder3.processing.ProblemDivider;
import graphfinder3.processing.SingleCalculation;
import graphfinder3.processing.Solver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author damian
 */
public class SimpleTest {

	public static void main(String[] args) {
	
		List<BuildRule> buildRules = new ArrayList<BuildRule>();
		buildRules.add(BuildRule.RING);
		buildRules.add(BuildRule.NRFREE);
		
		Set<Task> tasks = new HashSet<Task>();
		tasks.add(Task.AVERAGE_FROM_ALL);
		
		Problem problem = new Problem(buildRules, tasks, new Graph(12, 3), new ArrayList<Integer>());
		ProblemDivider divider = new ProblemDivider(problem, 7);
		
		double minimum = 100;
		int counter = 0;
		for (Problem p : divider.getSubProblems()) {
			System.out.println(p.getCurrentGraph());
			Solver s = new Solver(problem);
			ResultSet rs = s.getResultSet();
			
			double res = rs.getResults().get(Task.AVERAGE_FROM_ALL).getValue();
			
			System.out.println(res);
			minimum = Math.min(res, minimum);
			counter++;
		}
		System.out.println("minimum: " + minimum);
		System.out.println("counter: " + counter);
		
	}
}
