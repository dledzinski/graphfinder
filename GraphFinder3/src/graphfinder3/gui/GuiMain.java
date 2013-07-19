/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import java.awt.EventQueue;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author damian
 */
public class GuiMain {
	
	// logger
	private static final Logger logger = Logger.getLogger(GuiMain.class);
	
	public static void main(String[] args) {
		
		// uruchomienie loggera
		PropertyConfigurator.configure(GuiMain.class.getResource("/res/client.properties"));
		logger.info("Logowanie uruchomione");
		
		final String serverHost = (args.length >= 2) ? args[0] : "localhost";
		final int serverPort = (args.length >= 2) ? Integer.parseInt(args[1]) : 5556;
	
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Application(serverHost, serverPort);
			}
		});
		
	}
	
	
}
