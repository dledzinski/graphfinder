/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.server;

import graphfinder3.network.ClientInfo;
import graphfinder3.util.Monitor;
import graphfinder3.util.MonitoringData;
import graphfinder3.util.MonitoringDataListener;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class ClientInfoDispatcher {

	// logger
	private static final Logger logger = Logger.getLogger(ClientInfoDispatcher.class);
	// czas wysylki danych
	private static final int TIMER_PERIOD = 3000;
	// jedyny egzemplaz 
	private static ClientInfoDispatcher instance = null;
	// mapa ze spisem klientow
	private final Map<String, ClientInfo> clientInfos = new HashMap<String, ClientInfo>();
	// zbior klientow do informowania
	private final Set<ClientHandler> listeners = new CopyOnWriteArraySet<ClientHandler>();
	// timer
	private final Timer timer = new Timer("ClientInfoDispatcherTimer");
	// nowe dane do wyslania
	private volatile boolean informationsChanged = false;

	public static synchronized ClientInfoDispatcher getInstance() {
		if (instance == null) {
			instance = new ClientInfoDispatcher();
		}
		return instance;
	}

	private ClientInfoDispatcher() {
		timer.schedule(getTimerTask(), 0, TIMER_PERIOD);
		createServerClientInfo();
	}

	/**
	 * Metoda rejestruje nowego klienta
	 *
	 * @param proposedName propozycja nazwy klienta
	 * @param clientType
	 * @param protocolVersion
	 * @param ipAddress
	 * @return nadana nazwa klienta
	 */
	public synchronized String clientLogged(String proposedName, String ipAddress, long time) {
		// wyluskiwanie nazwy
		String name = assignName(proposedName);
		ClientInfo clientInfo = new ClientInfo(name, ipAddress);
		clientInfo.setLastActivityTime(time);
		// zapis
		clientInfos.put(name, clientInfo);
		// informowanie natychmiastowe
		informationsChanged = true;
		timer.schedule(getTimerTask(), 0);
		// zwracnie nazyw
		return name;
	}

	/**
	 * Aktualizacja daty ostatniego widzenia klienta
	 *
	 * @param clientName nazwa klienta
	 */
	public synchronized void newActivity(String clientName, long time) {
		ClientInfo clientInfo = clientInfos.get(clientName);
		if (clientInfo != null) {
			clientInfo.setLastActivityTime(time);
			clientInfo.setActive(true);
			informationsChanged = true;
		}
	}

	/**
	 * Aktualizuje dane monitoringu klienta
	 *
	 * @param clientName
	 * @param monitorData
	 */
	public synchronized void newMonitoringData(String clientName, MonitoringData monitorData) {
		ClientInfo clientInfo = clientInfos.get(clientName);
		if (clientInfo != null) {
			clientInfo.setMonitoringData(monitorData);
			clientInfo.setActive(true);
			informationsChanged = true;
		}
	}

	/**
	 * Informuje o rozlaczeniu klienta
	 *
	 * @param clientName
	 */
	public synchronized void clientDisconnected(String clientName) {
		ClientInfo clientInfo = clientInfos.get(clientName);
		if (clientInfo != null) {
			clientInfo.setActive(false);
			// informowanie natychmiastowe
			informationsChanged = true;
			timer.schedule(getTimerTask(), 0);
		}
	}

	/**
	 * Zwraca lieste onformacji o klientach
	 *
	 * @return
	 */
	public synchronized Set<ClientInfo> getClientInfos() {
		return new TreeSet(clientInfos.values());
	}

	/**
	 * Usuwa informacje o rozlaczonych klientach
	 */
	public synchronized void deleteDisconnected() {
		Set<String> inactiveSet = new HashSet<String>();
		for (String name : clientInfos.keySet()) {
			if (!clientInfos.get(name).isActive()) {
				inactiveSet.add(name);
			}
		}
		for (String inactive : inactiveSet) {
			clientInfos.remove(inactive);
		}
		// informowanie natychmiastowe
		informationsChanged = true;
		timer.schedule(getTimerTask(), 0);
	}

	/**
	 * Dodaje listenera
	 *
	 * @param clientHandler
	 */
	public void addClientInfoLietener(ClientHandler clientHandler) {
		listeners.add(clientHandler);
	}

	/**
	 * Usuwa sluchacza
	 *
	 * @param clientHandler
	 */
	public void removeClientInfoListener(ClientHandler clientHandler) {
		listeners.remove(clientHandler);
	}

	/**
	 * Akcja timera
	 */
	private TimerTask getTimerTask() {
		return new TimerTask() {

			@Override
			public void run() {
				if (informationsChanged) {
					informationsChanged = false;
					Set<ClientInfo> clientInfos = getClientInfos();
					for (ClientHandler clientHandler : listeners) {
						clientHandler.clientInfosUpdated(clientInfos);
					}
				}
			}
		};
	}

	/**
	 * Metoda sprawdza czy ktorys aktywny klient posiada juz taka nazwe
	 *
	 * @param proposedName
	 * @return
	 */
	private String assignName(String proposedName) {
		// jesli klient istnieje 
		if (clientInfos.get(proposedName) != null) {
			// i jest aktywny
			if (clientInfos.get(proposedName).isActive()) {
				// jesli juz nazwa byla zmieniana
				if (proposedName.matches(".*-\\d+$")) {
					// tworzenie nowej nazwy
					String[] s = proposedName.split("-");
					int number = Integer.parseInt(s[s.length - 1]);
					number++;
					String newName = proposedName.replaceAll("\\d+$", "") + number;
					return assignName(newName);
				}
				return assignName(proposedName + "-1");
			}
		}
		return proposedName;
	}
	
	/**
	 * Dodaje serwer do listy klientow
	 */
	private void createServerClientInfo() {
		try {
			// rejestrowanie samego siebie
			final String name = clientLogged(Monitor.getHostName() + "-Server", "", System.currentTimeMillis());
			
			Monitor.getInstance().addMonitorLietener(new MonitoringDataListener() {

				@Override
				public void newMonitoringData(MonitoringData monitoringData) {
					ClientInfoDispatcher.getInstance().newMonitoringData(name, monitoringData);
				}

			});
		} catch (UnknownHostException ex) {
			logger.error("Problem z uzyskaniem nazwy hosta", ex);
		}
	}
}
