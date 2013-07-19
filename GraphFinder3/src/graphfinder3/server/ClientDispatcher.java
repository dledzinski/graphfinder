/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.network.Command;
import graphfinder3.network.Message;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.log4j.Logger;

/**
 * Klasa zajmujaca sie dyspozycja klientow
 *
 * @author damian
 */
public class ClientDispatcher {

	// logger
	private static final Logger logger = Logger.getLogger(ClientDispatcher.class);
	// czas wysylki danych
	private static final int PINGING_PERIOD = 60000;
	// jedyny egzemplaz 
	private static ClientDispatcher instance = null;
	// podlaczeni klienci
	private final Set<ClientHandler> clientHandlers = new CopyOnWriteArraySet<ClientHandler>();
	// timer
	private final Timer timer = new Timer("ClientDispatcherTimer");

	public static synchronized ClientDispatcher getInstance() {
		if (instance == null) {
			instance = new ClientDispatcher();
		}
		return instance;
	}

	private ClientDispatcher() {
		timer.schedule(getTimerTask(), 0, PINGING_PERIOD);
	}

	/**
	 * Rejestracja podlaczenia nowego klieta
	 *
	 * @param clientHandler
	 */
	public void clientConnected(ClientHandler clientHandler) {
		clientHandlers.add(clientHandler);
	}

	/**
	 * Rejestracja rozlaczenia
	 *
	 * @param clientHandler
	 */
	public void clientDisconnected(ClientHandler clientHandler) {
		clientHandlers.remove(clientHandler);
	}

	/**
	 * Zwraca zadanie dla timera - rozlaczanie po timeouciei pngi
	 *
	 * @return
	 */
	private TimerTask getTimerTask() {
		return new TimerTask() {

			@Override
			public void run() {
				// wysylanie ping do wszystkich
				for (ClientHandler clientHandler : clientHandlers) {
					// ewentualne rozlaczanie
					if (clientHandler.getLastActivityTime() + PINGING_PERIOD * 10 < System.currentTimeMillis()) {
						clientHandler.disconnect();
					} else {
						// wysylanie ping
						clientHandler.send(new Message(Command.PING, null));
					}
				}

			}
		};
	}
}
