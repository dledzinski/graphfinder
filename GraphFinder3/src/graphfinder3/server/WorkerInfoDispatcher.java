/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import java.util.Set;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class WorkerInfoDispatcher {

	// logger
	private static final Logger logger = Logger.getLogger(WorkerInfoDispatcher.class);
	// jedyny egzemplaz 
	private static WorkerInfoDispatcher instance = null;
	// zbior klientow do informowania
	private final Set<ClientHandler> listeners = new CopyOnWriteArraySet<ClientHandler>();
	// flaga mowiaca ze dane sie zminily
	private volatile boolean dataChanged = false;

	public static synchronized WorkerInfoDispatcher getInstance() {
		if (instance == null) {
			instance = new WorkerInfoDispatcher();
		}
		return instance;
	}

	public WorkerInfoDispatcher() {
	}

	/**
	 * Dodaje listenera
	 *
	 * @param clientHandler
	 */
	public void addOrderInfoLietener(ClientHandler clientHandler) {
		listeners.add(clientHandler);
	}

	/**
	 * Usuwa sluchacza
	 *
	 * @param clientHandler
	 */
	public void removeOrderInfoListener(ClientHandler clientHandler) {
		listeners.remove(clientHandler);
	}

	public void fireOrdersCanceled() {
		for (ClientHandler clientHandler : listeners) {
			clientHandler.ordersCancelled();
		}
	}
}
