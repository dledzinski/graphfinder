/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2;

import graphfinder2.executor.Supervisor;
import graphfinder2.gui.Application;
import graphfinder2.typedGraph.TypedGraphCreators;

/**
 *
 * @author damian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

//		Supervisor supervisor = new Supervisor(TypedGraphCreators.NDR_CHORD_ECHOCH.getCreator(), 600, 600, true);
		
		System.out.println("-nogiu type from to - for conceete");

		if (args.length > 0 && args[0].equals("-nogui")) {
			ConcreetFinder.main(args);
		} else {
			// okno
			Application.main(args);
		}
    }

}
