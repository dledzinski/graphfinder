/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.data.Graph;
import graphfinder3.graphbuilding.RingBuilder;
import graphfinder3.processing.Calculation;

/**
 *
 * @author damian
 */
public class Test2 {
	
	public static void main(String[] args) {
		
		int nn = 15;
		
		Graph g = new RingBuilder(new Graph(nn, 4)).getSubGraph(0);
		g.setConnection(0, 11);
		g.setConnection(0, 8);
		g.setConnection(1, 10);
		g.setConnection(1, 5);
		g.setConnection(2, 7);
		g.setConnection(2, 12);
		g.setConnection(3, 14);
		g.setConnection(3, 10);
		g.setConnection(4, 12);
		g.setConnection(4, 8);
		g.setConnection(5, 13);
		g.setConnection(6, 11);
		g.setConnection(6, 14);
		g.setConnection(7, 9);
		g.setConnection(9, 13);

		Calculation c = new Calculation(g);
		double avgFromAll = c.getAveragePathLengthFromAll();
		System.out.println(avgFromAll);
		
		for (int i = 0; i < nn; i++) {
			System.out.println(i + ": " + c.getAveragePathLengthFrom(i));
		}
	}
	
}
