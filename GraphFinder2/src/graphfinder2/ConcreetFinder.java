/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2;

import graphfinder2.executor.Supervisor;
import graphfinder2.typedGraph.TypedGraphCreator;
import graphfinder2.typedGraph.TypedGraphCreators;

/**
 *
 * @author damian
 */
public class ConcreetFinder {

	public static void main(String[] args) {

		TypedGraphCreator creator = TypedGraphCreators.valueOf(args[1]).getCreator();
		int from = Integer.parseInt(args[2]);
		int to = Integer.parseInt(args[3]);
		Supervisor supervisor = new Supervisor(creator, from, to, true);
		
	}


}
