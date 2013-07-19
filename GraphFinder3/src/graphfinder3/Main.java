/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import graphfinder3.gui.GuiMain;
import graphfinder3.gui.analyzer.AnalyzerMain;
import graphfinder3.server.ServerMain;
import graphfinder3.worker.WorkerMain;

/**
 *
 * @author damian
 */
public class Main {
	
	public static void main(String[] args) {
		
		// jesli to gui
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("server")) {
				String[] a = new String[args.length - 1];
				System.arraycopy(args, 1, a, 0, a.length);
				ServerMain.main(a);
			} else if (args[0].equalsIgnoreCase("worker")) {
				String[] a = new String[args.length - 1];
				System.arraycopy(args, 1, a, 0, a.length);
				WorkerMain.main(a);
			} else if (args[0].equalsIgnoreCase("analyzer")) {
				AnalyzerMain.main(args);
			} else {
				GuiMain.main(args);
			}
		} else {
			String[] a = new String[] {"89.191.131.146", "5556"};
			GuiMain.main(a);
		}
		
	}
	
	
}
