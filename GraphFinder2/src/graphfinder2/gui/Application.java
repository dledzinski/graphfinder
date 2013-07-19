/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.gui;

import graphfinder2.gui.GeneratorPanel;
import graphfinder2.gui.ShowerPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Aplikacja
 * @author damian
 */
public class Application extends JFrame {

	public Application() {
		// parametry
		setTitle("Graph Finder 2 by UTP Bydgoszcz");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// dodawanie zakladek
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Generate CSV", new GeneratorPanel());
		tabbedPane.addTab("Summary", new SummaryPanel());
		tabbedPane.addTab("Show graph", new ShowerPanel());


		// dodawanie do okna
		add(tabbedPane);
		pack();
		setVisible(true);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new Application();
				} catch (Error e) {
					System.out.println(e.getMessage());
					e.printStackTrace(System.out);
					System.out.println("Cause:");
					e.getCause().printStackTrace(System.out);
					System.exit(1);
				}
			}
		});
	}
}
