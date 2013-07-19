/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.ClientInfo;
import graphfinder3.network.OrderDetails;
import graphfinder3.network.OrderInfo;
import graphfinder3.worker.Worker;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class Application extends JFrame implements CommunicationListener {

	// logger
	private static final Logger logger = Logger.getLogger(Application.class);
	// dane do polaczenia
	private final String serverHost;
	private final int serverPort;
	private CommunicationHandler communicationHandler = null;
	// komponenty
	private final ClientInfoPanel clientInfoPanel;
	private final NewOrderPanel newOrderPanel;
	private final OrderInfoPanel orderInfoPanel;

	/**
	 * Twozry i uruchamian aplikacje
	 *
	 * @param serverHost
	 * @param serverPort
	 */
	public Application(String serverHost, int serverPort) {
		// zmiana look and feel
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (Exception e) {
//			logger.warn("Nie znaleziony LAF", e);
//		}

		this.serverHost = serverHost;
		this.serverPort = serverPort;
		// parametry
		setTitle("GraphFinder3 - " + serverHost + ":" + serverPort);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ustawianie layoutu
		setLayout(new BorderLayout());
		// menu
		setJMenuBar(createMenu());

		// tworzenie zakladek
		JTabbedPane tabbedPane = new JTabbedPane();
		newOrderPanel = new NewOrderPanel();
		clientInfoPanel = new ClientInfoPanel();
		orderInfoPanel = new OrderInfoPanel();
		tabbedPane.addTab("Orders", orderInfoPanel);
		tabbedPane.addTab("New order", newOrderPanel);
		tabbedPane.addTab("Clients", clientInfoPanel);

		// dodawanie na srodku
		add(tabbedPane, BorderLayout.CENTER);

		// konczenie
		pack();
		setVisible(true);

		// polaczenie
		connect();

	}

	/**
	 * Laczenie z serwerem
	 */
	private void connect() {
		try {
			communicationHandler = new CommunicationHandler(serverHost, serverPort);
			communicationHandler.addCommunicationListener(this);
			orderInfoPanel.setCommunicationHandler(communicationHandler);
			newOrderPanel.setCommunicationHandler(communicationHandler);
			clientInfoPanel.setCommunicationHandler(communicationHandler);
			logger.info("Polaczono z serwerem");
			// login
			communicationHandler.login();
		} catch (Exception ex) {
			logger.error("Blad polaczenia z serwerem", ex);
			JOptionPane.showMessageDialog(this, "Connection problem: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private JMenuBar createMenu() {
		final JMenuBar menuBar = new JMenuBar();
		// dodawanie pozycji
		final JMenu workerMenu = new JMenu("Worker");
		menuBar.add(workerMenu);
		final JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(toolsMenu);
		final JMenuItem startSingleThreadWorkerItem = new JMenuItem("Start single-thread Worker");
		workerMenu.add(startSingleThreadWorkerItem);
		final JMenuItem startMultiThreadWorkerItem = new JMenuItem("Start multi-thread Worker");
		workerMenu.add(startMultiThreadWorkerItem);
		// akcje
		startSingleThreadWorkerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Worker(serverHost, serverPort, true, 1);
			}
		});
		// obsluga dzwieku
		startMultiThreadWorkerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Worker(serverHost, serverPort, true, 0);
			}
		});
		return menuBar;
	}

	@Override
	public void orderDetails(OrderDetails orderDetails) {
	}

	@Override
	public void orderInfos(Set<OrderInfo> orderInfos) {
	}

	@Override
	public void clientInfos(Set<ClientInfo> clientInfos) {
	}

	@Override
	public void loginOk(String clientName) {
		setTitle("GraphFinder3 - " + serverHost + ":" + serverPort + " - " + clientName);
	}

	@Override
	public void error(String message) {
		logger.error("Serwer przysla informacje o bledzie: " + message);
		JOptionPane.showMessageDialog(this, "Server error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void fatalError(String message) {
		logger.error("Blad polaczenia: " + message);
		JOptionPane.showMessageDialog(this, "Fatal error: " + message, "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	@Override
	public void disconnected() {
		logger.error("Rozlaczenie z serwerem");
//		JOptionPane.showMessageDialog(this, "Server disconnected", "Error", JOptionPane.ERROR_MESSAGE);
		if (JOptionPane.showConfirmDialog(this, "Server disconnected, try again?", "Disconnected", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			connect();
		} else {
			System.exit(0);
		}

	}
}
