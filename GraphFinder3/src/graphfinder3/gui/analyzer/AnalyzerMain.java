/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui.analyzer;

import graphfinder3.gui.Application;
import graphfinder3.gui.GuiMain;
import java.awt.EventQueue;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author damian
 */
public class AnalyzerMain {
	
	// logger
	private static final Logger logger = Logger.getLogger(GuiMain.class);
	
	public static void main(String[] args) {
		
		// uruchomienie loggera
		PropertyConfigurator.configure(GuiMain.class.getResource("/res/client.properties"));
		logger.info("Logowanie uruchomione");
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Analyzer();
			}
		});
		
	}
	
}
