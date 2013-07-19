/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.*;
import graphfinder3.util.Monitor;
import graphfinder3.util.MonitoringData;
import graphfinder3.util.MonitoringDataListener;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class CommunicationHandler implements MessageListener, MonitoringDataListener {

	// logger
	private static final Logger logger = Logger.getLogger(GuiMain.class);
	// uchwyt polacznia
	private final ConnectionHandler connectionHandler;
	// sluchacz
	private final Set<CommunicationListener> listeners = new CopyOnWriteArraySet<CommunicationListener>();
	// nazwa klienta
	private String clientName = null;

	/**
	 * Laczy sie z serwerem
	 *
	 * @param host
	 * @param port
	 * @param listener
	 * @throws Exception
	 */
	public CommunicationHandler(String host, int port) throws Exception {

		logger.info("Laczenie z serwerem");
		this.connectionHandler = new ConnectionHandler(host, port, this);

		// monitor
		Monitor.getInstance().addMonitorLietener(this);
	}

	/**
	 * Dodaje sluchacza
	 *
	 * @param listener
	 */
	public void addCommunicationListener(CommunicationListener listener) {
		listeners.add(listener);
	}

	/**
	 * Usuwa sluchacza
	 *
	 * @param listener
	 */
	public void removeCommunicationListener(CommunicationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Wysyla komunikat do serwera kiedy klient jest juz zalogowany
	 *
	 * @param message
	 */
	public void sendToServer(Message message) {
		if (clientName != null) {
			send(message);
		}
	}

	/**
	 * Wysyla login
	 * @throws UnknownHostException 
	 */
	public void login() throws UnknownHostException {
		// login
		send(new Message(Command.LOGIN, new LoginData(Monitor.getHostName() + "-GUI", "GUI")));
	}

	/**
	 * wysyla komunikat do serwera, bezwarunkowo
	 *
	 * @param message
	 */
	private void send(Message message) {
		connectionHandler.send(message);
	}

	@Override
	public void newMessage(Message message) {
		try {
			switch (message.getCommand()) {
				case PING:
					send(new Message(Command.PONG, null));
					break;
				case ERROR:
					logger.error("Przyszedl komunikat o bledzie: " + message);
					for (CommunicationListener listener : listeners) {
						listener.error((String) message.getParam());
					}
					break;
				case LOGIN_OK:
					clientName = (String) message.getParam();
					for (CommunicationListener listener : listeners) {
						listener.loginOk(clientName);
					}
					break;
				case LOGIN_ERROR:
					logger.error("Blad logowania: " + message);
					for (CommunicationListener listener : listeners) {
						listener.fatalError("Connection error: " + message.getParam());
					}
					break;
				case CLIENT_INFOS:
					for (CommunicationListener listener : listeners) {
						listener.clientInfos((Set<ClientInfo>) message.getParam());
					}
					break;
				case ORDER_INFOS:
					for (CommunicationListener listener : listeners) {
						listener.orderInfos((Set<OrderInfo>) message.getParam());
					}
					break;
				case ORDER_DETAILS:
					for (CommunicationListener listener : listeners) {
						listener.orderDetails((OrderDetails) message.getParam());
					}
					break;

				default:
					logger.error("Przyszedl nieobslugiwany rozkaz: " + message);
			}
		} catch (Exception e) {
			logger.error("Blad obslugi rozkazu przychodzacego", e);
		}
	}

	@Override
	public void disconnected() {
		clientName = null;
		// powiadamianie poziom wyzej
		for (CommunicationListener listener : listeners) {
			listener.disconnected();
		}
	}

	@Override
	public void newMonitoringData(MonitoringData monitoringData) {
		if (clientName != null) {
			send(new Message(Command.MONITORING_DATA, monitoringData));
		}
	}
}
