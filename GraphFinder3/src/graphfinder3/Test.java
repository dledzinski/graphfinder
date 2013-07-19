/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.data.*;
import graphfinder3.processing.DivideListener;
import graphfinder3.processing.ProblemDivider;
import graphfinder3.processing.Solver;
import java.net.UnknownHostException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class Test {
	// logger

	private static final Logger logger = Logger.getLogger(Test.class);
	private static long counter = 0;

	public static void main(String[] args) throws UnknownHostException, Exception {





		List<BuildRule> buildRules = new ArrayList<BuildRule>();
//		buildRules.add(BuildRule.RING);
//		buildRules.add(BuildRule.CHAIN);
		buildRules.add(BuildRule.FULL_TREE);
//		buildRules.add(BuildRule.FREE);
		buildRules.add(BuildRule.NRFREE);

		Set<Task> tasks = new HashSet<Task>();
		tasks.add(Task.BEST_RADIUS);
		tasks.add(Task.BEST_AVERAGE);
		tasks.add(Task.DIAMETER_FROM_ALL);
		tasks.add(Task.AVERAGE_FROM_ALL);


		Problem problem = new Problem(buildRules, tasks, new Graph(16, 3), new ArrayList<Integer>());
//		Solver solver = new Solver(problem);
//		System.out.println(solver.getResultSet());
		ProblemDivider divider = new ProblemDivider(problem, 1);
		System.out.println(divider.getSubProblems().iterator().next().getCurrentGraph());
		
		
//		for (int i = 6; i < 100; i += 2) {
//
//			long time = System.currentTimeMillis();
//			Problem problem = new Problem(buildRules, tasks, new Graph(i, 3), new ArrayList<Integer>());
//
//			new ProblemDivider(problem, 100).generateSubProblems(new DivideListener() {
//
//				@Override
//				public void newSubProblem(Problem subProblem) {
//					counter++;
//				}
//			});
//
//			time = System.currentTimeMillis() - time;
//			System.out.println(i + " - " + counter + ", " + time/1000 + "s");
//			counter = 0;
//		}
	}
}
