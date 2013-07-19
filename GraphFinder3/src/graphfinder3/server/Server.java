/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 * Serwer obliczeniowy
 *
 * @author damian
 */
public class Server implements Runnable {

	// logger
	private static final Logger logger = Logger.getLogger(Server.class);
	// port
	private final int port;
	// gniazdo
	private final ServerSocket serverSocket;

	public Server(int port) throws IOException {
		this.port = port;
		// laczenie
		serverSocket = new ServerSocket(port);
		// watek
		Thread thread = new Thread(this, "ServerThread");
		thread.start();
		logger.info("Serwer nasluchuje na porcie: " + port);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new ClientHandler(socket);
			} catch (IOException ex) {
				logger.error("Blad obslugi klienta", ex);
			}
		}
	}
}
