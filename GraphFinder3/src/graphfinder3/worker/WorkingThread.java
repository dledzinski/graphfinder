/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.worker;

import graphfinder3.data.Problem;
import graphfinder3.data.ResultSet;
import graphfinder3.data.Order;
import graphfinder3.data.OrderResult;
import graphfinder3.processing.Solver;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class WorkingThread extends Thread{
	
	// logger
	private static final Logger logger = Logger.getLogger(Worker.class);
	
	private final Worker worker;

	public WorkingThread(String threadName, Worker worker) {
		super(threadName);
		this.worker = worker;
		// start watku
		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// pobieranie rozkazu
				Order order = worker.getOrder();
				logger.debug("Wykonuje zadanie: " + order.getOrderName());
				long startTime = System.currentTimeMillis();
				// wyluskiwanie problemu
				Problem problem = order.getProblem();
				// rozwiazywanie
				Solver solver = new Solver(problem);
				ResultSet resultSet = solver.getResultSet();
				long endTime = System.currentTimeMillis();
				// tworzenie wyniku
				OrderResult orderResult = new OrderResult(order.getOrderName(), resultSet, solver.getGraphCounter(), endTime - startTime);
				// odsylanie wyniku
				worker.putOrderResult(orderResult);
			} catch (Exception ex) {
				logger.error("Blad wykonywania zlecenia", ex);
				// odsylanie komunikatu
				worker.reportError(ex.getMessage());
			}
		}
	}
	
	
	
}
