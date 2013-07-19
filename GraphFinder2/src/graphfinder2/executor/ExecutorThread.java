/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.executor;

/**
 * Watek wykonujacy zadania
 * @author damian
 */
public class ExecutorThread extends Thread {

	// nadzorca
	private final Supervisor supervisor;

	/**
	 * Tworzy i uruchamia watek
	 * @param supervisor
	 * @param kolejny numer watku
	 */
	public ExecutorThread(Supervisor supervisor, int no) {
		this.supervisor = supervisor;
		setName("ExecutionThread-" + no);
		start();
	}

	@Override
	public void run() {
		while (true) {
			// pobieranie zadania
			SubTask subTask = supervisor.nextSubtask();
			if (subTask == null) {
				break;
			}
			// wykonywanie zadania
			subTask.solve();
			// zwracanie zadania
			supervisor.subtaskSolved(subTask);
		}
	}




}
