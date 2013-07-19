/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author damian
 */
public class ServerMain {

	// logger
	private static final Logger logger = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) {

		// uruchomienie loggera
		PropertyConfigurator.configure(ServerMain.class.getResource("/res/server.properties"));
		logger.info("Logowanie uruchomione");

		int serverPort = 5556;

		if (args.length >= 1) {
			serverPort = Integer.parseInt(args[0]);
		}

		logger.info("Start serwera");
		try {
			new Server(serverPort);
		} catch (Exception e) {
			logger.error("Blad otwierania gniazda serwera", e);
		}
	}
}
