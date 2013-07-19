/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.executor;

import graphfinder2.typedGraph.TypedGraphCreator;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author damian
 */
public class Supervisor extends Thread {

	// zakres
	private final int rangeFarom;
	private final int rangeTo;
	// kreator grafu
	private final TypedGraphCreator creator;
	// ilosci wierszy do rozwiazania
	private final List<Integer> validNodeNumbers;
	// aktualne zadanie
	private volatile Task currentTask = null;
	// calkowita ilosc zadan
	private final int totalTaskNumber;
	// licznik ilosc wykonanych zadan
	private volatile int solvedTaskCounter = 0;
	// informacja o zakonczeniu
	private volatile boolean finished = false;
	// optymalizacja
	private final boolean optimalize;

	/**
	 * Tworzy nadzorce
	 * @param range
	 */
	public Supervisor(TypedGraphCreator creator, int rangeFrom, int rangeTo, boolean optimalize) {
		this.rangeFarom = rangeFrom;
		this.rangeTo = rangeTo;
		this.creator = creator;
		this.optimalize = optimalize;
		validNodeNumbers = creator.getValidNodeNumber(rangeFrom, rangeTo);
		totalTaskNumber = validNodeNumbers.size();
		setName("SupervisorThread");
		start();
	}

	@Override
	public void run() {
		// okresla czy pierwsze przejscie petli
		boolean first = true;
		for (Integer nodeNumber : validNodeNumbers) {
			System.out.println("Iteracja dla ilosci wezlow: " + nodeNumber);
			// tworzenie zadania
			currentTask = new Task(creator, nodeNumber, optimalize);

			// rozwiazywanie zadania
			solveTask();

			if (currentTask.getBestResult() != null) {
				// jesli pierwsze przejscie petli
				if (first) {
					first = false;
					savePreamble(currentTask.getBestResult().getTypeName(), currentTask.getBestResult().getResultPreamble());
				}
				saveResult(currentTask.getBestResult().getTypeName(), currentTask.getBestResult().getResultLine());
			}
			solvedTaskCounter++;
		}
		finished = true;
	}

	/**
	 * Metoda wywolywana przez egzekutor dostac podzadanie
	 * @return
	 */
	public SubTask nextSubtask() {
		return currentTask.nextSubtask();
	}

	/**
	 * Metoda wywolywana przez egzekutor kiedy zadanie jest wykonane
	 * @return
	 */
	public void subtaskSolved(SubTask subTask) {
		currentTask.subtaskSolved(subTask);
	}

	private void solveTask() {
		List<ExecutorThread> executors = new ArrayList<ExecutorThread>();
		// tworzenie watkow
		for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			ExecutorThread executor = new ExecutorThread(this, i);
			executors.add(executor);
		}
		// oczekiwanie na zakonczenie
		for (ExecutorThread executorThread : executors) {
			try {
				executorThread.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * zapisuje naglowek pliku
	 * @param typeName
	 * @param preamble
	 */
	private void savePreamble(String typeName, String preamble) {
		try {
			FileWriter fw = new FileWriter(typeName + ".csv");
			fw.write(preamble + "\n");
			fw.close();
			System.out.println(preamble);
		} catch (IOException ex) {
			Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * zapisuje naglowek pliku
	 * @param typeName
	 * @param preamble
	 */
	private void saveResult(String typeName, String resultLine) {
		try {
			FileWriter fw = new FileWriter(typeName + ".csv", true);
			fw.write(resultLine + "\n");
			fw.close();
			System.out.println(resultLine);
		} catch (IOException ex) {
			Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public int getTotalTaskNumber() {
		return totalTaskNumber;
	}

	public int getSolvedTaskCounter() {
		return solvedTaskCounter;
	}

	public boolean isFinished() {
		return finished;
	}
	
}
