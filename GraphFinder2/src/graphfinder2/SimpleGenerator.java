/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2;

import graphfinder2.executor.Supervisor;
import graphfinder2.typedGraph.TypedGraph;
import graphfinder2.typedGraph.TypedGraphCreator;
import graphfinder2.typedGraph.TypedGraphCreators;
import graphfinder2.typedGraph.degree3.NdrHamIlton;
import graphfinder2.typedGraph.degree4.Chr4ChordEchOch;
import graphfinder2.typedGraph.degree4.Chr4DiameterChord;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author damian
 */
public class SimpleGenerator {

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

		// przejscie po wszystkich typach
//		for (TypedGraphCreators creator : TypedGraphCreators.values()) {
//			if (creator.getStage().getNeighboursNumber() > 3 && creator.getCreator().getRequiredParamsNumber() < 3 && creator.getCreator().getComplexity() != 3) {
//				System.out.println("Rozpoczynanie przetwarzania: " + creator);
//				Supervisor supervisor = new Supervisor(creator.getCreator(), 2, 1000, true);
//				supervisor.join();
//			}
//		}

//		Supervisor supervisor = new Supervisor(Chr4DiameterChord.getInstance(), 2, 1000, true);

//		TypedGraph graph = NdrHamIlton.getInstance().create(10, new int[] {2});
//		System.out.println(graph.getGraph());
		
		Random random = new Random();
		int nodeNumber = 1000000;
		int[] params = new int[3];
		TypedGraphCreator creator = TypedGraphCreators.CHR4_CHORD_ECHOCH.getCreator();
		double bestAverage = Double.MAX_VALUE;
		TypedGraph bestGraph = null;
		
		if (!creator.isValidNodeNumber(nodeNumber)) {
			System.err.println("Nieprawilowa ilosc wezlow");
			return;
		}
		
		while (true) {
			params[0] = random.nextInt(nodeNumber / 2) * 2 + 1;
			params[1] = random.nextInt(nodeNumber / 2) * 4 - 2;
			params[2] = random.nextInt(nodeNumber / 2) * 4 - 2;
			
			if (creator.isValidParams(nodeNumber, params)) {
				TypedGraph graph = creator.create(nodeNumber, params);
				if (graph.getAverage() < bestAverage ) {
					bestAverage = graph.getAverage();
					bestGraph = graph;
					System.out.println("Avg: " + graph.getAverage());
					System.out.println("Params: " + Arrays.toString(params));
					System.out.println(graph.getResultLine());
				}
			}
		} 
		
		
    }

}
