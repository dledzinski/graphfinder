/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.data.Graph;
import graphfinder3.graphbuilding.FullTreeBuilder;
import graphfinder3.graphbuilding.RingBuilder;
import graphfinder3.processing.Calculation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.combinatorics.PermutationGenerator;

/**
 *
 * @author damian
 */
public class Ch4HamiltonGenerator {

	public static void main(String[] args) {

		int nn = 19;
		for (int i = nn; i < nn + 1; i++) {
			find(i);
		}


	}

	private static void find(int nodeNumber) {
		System.out.println("NodeNumber: " + nodeNumber);

//		Generator gen = new Generator(nodeNumber);
		RandomGenerator gen = new RandomGenerator(nodeNumber);

		final double bestAverage = getBestAverage(nodeNumber);
		System.out.println("BestAverage (from Tree): " + bestAverage);
		Graph bestGraph = null;
		double minAvgFromAll = Double.MAX_VALUE;
		Graph currentGraph;
		while ((currentGraph = gen.next()) != null) {
			Calculation c = new Calculation(currentGraph);
			double avgFromAll = c.getAveragePathLengthFromAll();
			if (avgFromAll < minAvgFromAll) {
				minAvgFromAll = avgFromAll;
				bestGraph = currentGraph;
				System.out.println("Current avg: " + avgFromAll);
				System.out.println(bestGraph);
				// sprawdzanie czy dalej ma sesn
				if (minAvgFromAll <= bestAverage) {
					System.out.println("Perfect");
					break;
				}
			}
		}
		System.out.println("Best avg: " + minAvgFromAll);
		System.out.println(bestGraph);
		System.out.println();
	}

	public static double getBestAverage(int nodeNumber) {
		FullTreeBuilder tb = new FullTreeBuilder(new Graph(nodeNumber, 4));
		Calculation c = new Calculation(tb.getSubGraph(0));
		return c.getBestAveragePathLength();
	}

	public static class RandomGenerator {
		
		private final int nodeNumber;
		private final Graph ring;
		private final List<Integer> nodes = new ArrayList<Integer>();
		private final Random random = new Random(System.currentTimeMillis());
		
		public RandomGenerator(int nodeNumber) {
			this.nodeNumber = nodeNumber;
			this.ring = new RingBuilder(new Graph(nodeNumber, 4)).getSubGraph(0);
			for (int i = 1; i < nodeNumber; i++) {
				nodes.add(i);
			}
		}
		
		/**
		 * Zwraca kolejny wygenerowany garf lub null
		 *
		 * @return
		 */
		public Graph next() {
			boolean found = false;
			while (!found) {
				// kolejny przypadek
				Collections.shuffle(nodes, random);
				found = true;
				// sprawdzanie czy to ma sesn - nie powtarza sie z ringiem
				if (nodes.get(0) == 1 || nodes.get(nodes.size() - 1) == nodes.size()) {
					found = false;
					continue;
				}
				for (int i = 0; i < nodes.size() - 1; i++) {
					// polaczenia jeden po drugim
					if ((nodes.get(i) == nodes.get(i + 1) - 1) || (nodes.get(i) == nodes.get(i + 1) + 1)) {
						found = false;
						break;
					}
				}
			}

			// kopiowanie
			Graph copy = new Graph(ring);
			// laczenie
			for (int i = 0; i < nodes.size() - 1; i++) {
				copy.setConnection(nodes.get(i), nodes.get(i + 1));
			}
			// zamkniecie
			copy.setConnection(0, nodes.get(0));
			copy.setConnection(nodes.get(nodes.size() - 1), 0);
			return copy;
		}
		
	}
	
	public static class Generator {

		private final int nodeNumber;
		private final Graph ring;
		private final PermutationGenerator<Integer> generator;

		
		
		public Generator(int nodeNumber) {
			this.nodeNumber = nodeNumber;
			this.ring = new RingBuilder(new Graph(nodeNumber, 4)).getSubGraph(0);
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 1; i < nodeNumber; i++) {
				list.add(i);
			}
			generator = new PermutationGenerator<Integer>(list);
		}

		/**
		 * Zwraca kolejny wygenerowany garf lub null
		 *
		 * @return
		 */
		public Graph next() {
			boolean found = false;
			List<Integer> nodes = null;
			while (!found) {
				// kolejny przypadek
				if (generator.getRemainingPermutations() <= 0) {
					// nie ma juz permutacji
					return null;
				}
				nodes = generator.nextPermutationAsList();
				found = true;
				// sprawdzanie czy to ma sesn - nie powtarza sie z ringiem
				if (nodes.get(0) == 1 || nodes.get(nodes.size() - 1) == nodes.size()) {
					found = false;
					continue;
				}
				for (int i = 0; i < nodes.size() - 1; i++) {
					// polaczenia jeden po drugim
					if ((nodes.get(i) == nodes.get(i + 1) - 1) || (nodes.get(i) == nodes.get(i + 1) + 1)) {
						found = false;
						break;
					}
				}
			}

			// kopiowanie
			Graph copy = new Graph(ring);
			// laczenie
			for (int i = 0; i < nodes.size() - 1; i++) {
				copy.setConnection(nodes.get(i), nodes.get(i + 1));
			}
			// zamkniecie
			copy.setConnection(0, nodes.get(0));
			copy.setConnection(nodes.get(nodes.size() - 1), 0);
			return copy;
		}
	}
}
