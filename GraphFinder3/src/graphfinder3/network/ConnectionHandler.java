/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 * Klasa niskopoziomowej obslugi klienta
 * @author damian
 */
public class ConnectionHandler implements Runnable {

	// logger
	private static final Logger logger = Logger.getLogger(ConnectionHandler.class);
	
	// sluchacz komunikatow
	private final MessageListener listener;
	// gniazdo strumienie
	private final Socket socket;
	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	// flaga dzialania
	private volatile boolean threadWorking = true;

	/**
	 * Konstruktor stroniy serwera
	 * @param socket
	 * @param listener 
	 */
	public ConnectionHandler(Socket socket, MessageListener listener) throws IOException {
		this.listener = listener;
		this.socket = socket;
		// otwieranie strumieni
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		// uruchamianie watku
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Konstruktor stroniy serwera
	 * @param host host serwera
	 * @param port port serwera
	 * @param listener 
	 */
	public ConnectionHandler(String host, int port, MessageListener listener) throws UnknownHostException, IOException {
		this.listener = listener;
		// laczenie i tworzenie strumieni
		socket = new Socket(host, port);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		// uruchamianie watku
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * Metoda rozlacza klienta
	 */
	public synchronized void disconnect() {
		try {
			socket.close();
			ois.close();
			oos.close();
		} catch (IOException ex) {
			logger.error("Nieudana proba rozlaczenia", ex);
		}
		// watek nie musi dzialac
		threadWorking = false;
		// powiadamianie sluchacza
		listener.disconnected();
	}
	
	/**
	 * Metoda do wysylania komunikatow
	 * @param message 
	 */
	public synchronized void send(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
			oos.reset();
			logger.debug("wyslano komunikat: " + message);
		} catch (IOException ex) {
			logger.warn("Nieudana proba wyslania komunikatu", ex);
			// rozlaczanie
			disconnect();
		}
	}

	/**
	 * Zwraca zdalny adres ip
	 * @return 
	 */
	public String getRemoteIPAddress() {
		return socket.getInetAddress().getHostAddress();
	}
	
	@Override
	public void run() {
		while (threadWorking) {
			try {
				// odbieranie komunikatu
				Message message = (Message) ois.readObject();
				logger.debug("Odebrano komunikat: " + message);
				// przekazywanie sluchaczowi
				listener.newMessage(message);
			} catch (IOException ex) {
				logger.warn("Nieudana proba odebrania komunikatu", ex);
				disconnect();
			} catch (ClassNotFoundException ex) {
				logger.error("Nieprawidlowa klasa odebranego komunikatu", ex);
				disconnect();
			}
		}
	}
	
	
}
