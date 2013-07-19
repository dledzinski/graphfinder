/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.worker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author damian
 */
public class WorkerMain {

	// logger
	private static final Logger logger = Logger.getLogger(WorkerMain.class);
	
	public static void main(String[] args) {
		
		// uruchomienie loggera
		PropertyConfigurator.configure(WorkerMain.class.getResource("/res/server.properties"));
		logger.info("Logowanie uruchomione");
		
		String serverHost = "localhost";
		int serverPort = 5556;
		
		if (args.length >= 2) {
			serverHost = args[0];
			serverPort = Integer.parseInt(args[1]);
		}
		
		logger.info("Start workera");
		new Worker(serverHost, serverPort, false, 0);
	}

	public static String getMachineName() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (UnknownHostException ex) {
			return "Unknown";
		}
	}
}
