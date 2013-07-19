/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.data.*;
import graphfinder3.graphbuilding.Builder;
import graphfinder3.graphbuilding.ChainBuilder;
import graphfinder3.graphbuilding.FreeBuilder;
import graphfinder3.graphbuilding.RingBuilder;
import graphfinder3.processing.ProblemDivider;
import graphfinder3.processing.Solver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author damian
 */
public class GraphFinder3 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO code application logic here

		PrintWriter printWriter = new PrintWriter("results-" + System.currentTimeMillis() + ".txt");

//		Graph graph = new Graph(4, 3);
//		
//		Builder b = new ChainBuilder(graph);
//		Builder b1 = new FreeBuilder(b.getSubGraphs().get(0));
//		
//		for (int i : b1.getSubGraphs().keySet()) {
//			System.out.println("\t" + b1.getSubGraphs().get(i));
//		}
//		
		int nodeNumber = 12;

		List<BuildRule> buildRules = new ArrayList<BuildRule>();
//		buildRules.add(BuildRule.RING);
		buildRules.add(BuildRule.CHAIN);
//		buildRules.add(BuildRule.NDR);
		buildRules.add(BuildRule.FREE);

		Set<Task> tasks = new HashSet<Task>();
		tasks.add(Task.BEST_RADIUS);
		tasks.add(Task.BEST_AVERAGE);
		tasks.add(Task.DIAMETER_FROM_ALL);
		tasks.add(Task.AVERAGE_FROM_ALL);

		for (int i = 4; i <= 20; i += 1) {
			nodeNumber = i;

			long time = System.currentTimeMillis();

			String problemName = "FREE-" + nodeNumber;
			Problem problem = new Problem(buildRules, tasks, new Graph(nodeNumber, 3), new ArrayList<Integer>());

			ResultSet resultSet = new Solver(problem).getResultSet();
			StringBuilder sb = new StringBuilder();
			sb.append(getResultValues(problemName, resultSet)).append("\n");
			sb.append(getResultGraphs(resultSet));

			sb.append("time: ").append(System.currentTimeMillis() - time).append("ms\n");

			System.out.println(sb.toString());
			printWriter.println(sb.toString());
			printWriter.flush();

		}

		printWriter.close();
	}

	public static String getResultValues(String problemName, ResultSet resultSet) {
		StringBuilder sb = new StringBuilder();
		sb.append(problemName);
		sb.append(": ");
		for (Result result : resultSet.getResults().values()) {
			sb.append(result.getTask()).append("=").append(result.getValue()).append(", ");
		}
		return sb.toString();
	}

	public static String getResultGraphs(ResultSet resultSet) {
		StringBuilder sb = new StringBuilder();
		for (Result result : resultSet.getResults().values()) {
			sb.append(result.getTask()).append(": ").append(result.getGraph()).append("\n");
		}
		return sb.toString();
	}

}
