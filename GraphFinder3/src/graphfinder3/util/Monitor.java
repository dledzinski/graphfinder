/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * Klasa do monitoringu
 *
 * @author damian
 */
public class Monitor {

	// logger
	private static final Logger logger = Logger.getLogger(Monitor.class);
	// czas wysylki danych
	private static final int MONITORING_PERIOD = 5000;
	// czas usredniania pomiarow
	private static final int AVERAGE_MEASUREMENT_PERIOD = 120000;
	// jedyny egzemplaz 
	private static Monitor instance = null;
	// podlaczeni klienci
	private final Set<MonitoringDataListener> listeners = new CopyOnWriteArraySet<MonitoringDataListener>();
	// timer
	private final Timer timer = new Timer("MonitorTimer");
	// licznik grafow
	private volatile long graphCounter = 0;
	// czas od ostatniego 
	private long lastTick = System.currentTimeMillis();
	// kolejka wynikow pomiarow
	private Deque<Double> graphPerSecondQueue = new LinkedList<Double>();

	public static synchronized Monitor getInstance() {
		if (instance == null) {
			instance = new Monitor();
		}
		return instance;
	}

	private Monitor() {
		timer.schedule(getTimerTask(), 0, MONITORING_PERIOD);
	}

	/**
	 * Dodaje sluchacza
	 *
	 * @param listener
	 */
	public void addMonitorLietener(MonitoringDataListener listener) {
		listeners.add(listener);
	}

	/**
	 * Usuwa sluchacza
	 *
	 * @param listener
	 */
	public void removeMonitorListener(MonitoringDataListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Metoda dodaje kolejne grafy do licznika, potzreba w celu okreslenia
	 * ilosci grafow na sekunde
	 *
	 * @param value
	 */
	public synchronized void addToGraphCounter(long value) {
		graphCounter += value;
	}

	/**
	 * Zwraca ilosc grafow na sekunde za ostatni okres
	 *
	 * @return
	 */
	private synchronized double getGraphsPesSecond() {
		// teraz
		long now = System.currentTimeMillis();
		// czas zbierania grafow
		long processingTime = now - lastTick;
		// aktualizacja
		lastTick = now;
		// ilosc grafow na sekunde
		double graphsPerSecond = (processingTime > 0) ? (graphCounter * 1.0) / (processingTime / 1000.0) : 0;
		// wyerowanie licznika
		graphCounter = 0;
		return graphsPerSecond;
	}

	/**
	 * Zwraca usredniona ilosc grafow na sekunde
	 *
	 * @return
	 */
	private double getAverageGraphsPesSecond() {
		// dodawanie do kolejki
		graphPerSecondQueue.addLast(getGraphsPesSecond());
		if (graphPerSecondQueue.size() > (AVERAGE_MEASUREMENT_PERIOD / MONITORING_PERIOD)) {
			graphPerSecondQueue.removeFirst();
		}
		double sum = 0;
		for (double graphsPerSecond : graphPerSecondQueue) {
			sum += graphsPerSecond;
		}
		return sum / graphPerSecondQueue.size();
	}

	/**
	 * Akcja timera
	 */
	private TimerTask getTimerTask() {
		return new TimerTask() {

			@Override
			public void run() {
				try {
					// zbieranie informacji
					MonitoringData data = getMonitorData();
					// powiadamianie sluchaczy
					for (MonitoringDataListener listener : listeners) {
						listener.newMonitoringData(data);
					}
				} catch (Exception e) {
					logger.error("Blad w czasie monitorowaia systemu", e);
				}
			}
		};
	}

	/**
	 * Zwraca obiekt z danymi monitora
	 *
	 * @return
	 * @throws Exception
	 */
	private MonitoringData getMonitorData() throws Exception {
		// pobieranie wartosci
		double graphsPerSecond = getAverageGraphsPesSecond();
		double cpuUsage = getCpuUsage();
		double ramUsage = getRamUsage();
		String uptime = getUptime();
		double heap = getHeap();
		return new MonitoringData(graphsPerSecond, cpuUsage, ramUsage, uptime, heap);
	}

	/**
	 * Metoda zwraca wyjscie komendy systemowej
	 *
	 * @param command
	 * @return
	 */
	private String getProcessOutput(String command) throws IOException, InterruptedException {
		StringBuilder sb = new StringBuilder();
		// uruchamianie procesu
		Process process = Runtime.getRuntime().exec(command);
		// pobieranie wyniku
		if (process.waitFor() == 0) {
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
		}
		return sb.toString();
	}

	/**
	 * Zwraca wielkosc sterty
	 *
	 * @return
	 */
	private double getHeap() {
		Runtime runtime = Runtime.getRuntime();
		double heap = (runtime.totalMemory() * 1.0 - runtime.freeMemory()) / runtime.totalMemory();
		return heap;
	}

	/**
	 * Zwraca zuzycie procesora (user + sys)
	 *
	 * @return
	 * @throws Exception
	 */
	private double getCpuUsage() throws Exception {
		double cpuUsage = -1;
		if (isLinux()) {
			String topOutput = getProcessOutput("top -n 2 -b -d 0.2");
			Pattern cpuLinePattern = Pattern.compile("Cpu\\(s\\):.*\n");
			Matcher cpuLineMatcher = cpuLinePattern.matcher(topOutput);

			// jesli 2 razy znaleziono
			if (cpuLineMatcher.find() && cpuLineMatcher.find()) {
				String res = cpuLineMatcher.group();
				Pattern p = Pattern.compile("\\d+\\.\\d+");
				Matcher m = p.matcher(res);
				m.find();
				double us = Double.parseDouble(m.group());
				m.find();
				double sy = Double.parseDouble(m.group());
				cpuUsage = ((us + sy) * Runtime.getRuntime().availableProcessors()) / 100.0;
			}
		}
		return cpuUsage;
	}

	/**
	 * Zwraca zuzycie pamieci przez system - tylko linux
	 *
	 * @return
	 * @throws Exception
	 */
	private double getRamUsage() throws Exception {
		double ramUsage = -1;
		if (isLinux()) {
			String freeOutput = getProcessOutput("free -b");
			String memline = freeOutput.split("\n")[1];
			String[] fields = memline.replaceAll(".*Mem:", "").split("\\s+");
			double total = Double.parseDouble(fields[1]);
			double used = Double.parseDouble(fields[2]);
			ramUsage = used / total;
		}
		return ramUsage;
	}

	/**
	 * Zwraca uptime - tylko linux
	 *
	 * @return
	 * @throws Exception
	 */
	private String getUptime() throws Exception {
		String uptime = "";
		if (isLinux()) {
			String uptimeOutput = getProcessOutput("uptime");
//			double cpuLoad = Double.parseDouble(uptimeOutput.replaceAll(".*average:", "").replaceAll(",.*", "").trim());
			uptime = uptimeOutput.replaceAll(".*up", "").replaceAll(",.*", "").trim();
		}
		return uptime;
	}

	/**
	 * Zwraca nazwe komputera
	 *
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		return addr.getHostName();
	}

	public static boolean isLinux() {
		return System.getProperty("os.name").equals("Linux");
	}
}
