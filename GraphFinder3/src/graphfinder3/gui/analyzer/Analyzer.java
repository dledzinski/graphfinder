/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui.analyzer;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 * Okno analizatora
 * @author damian
 */
public class Analyzer extends JFrame {

	/**
	 * Konstruktor domyslny, tworzy nowy graf
	 * @throws HeadlessException 
	 */
	public Analyzer() {
		// parametry
		setTitle("Analyzer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ustawianie layoutu
		setLayout(new BorderLayout());
		// panel analizatora
		AnalyzerPanel panel = new AnalyzerPanel();
		// dodawanie na srodku
		add(panel, BorderLayout.CENTER);
		// konczenie
		pack();
		setVisible(true);
	}
	
	
	
	
}
