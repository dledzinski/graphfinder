/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.util;

import graphfinder3.data.Result;
import graphfinder3.network.OrderDetails;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author damian
 */
public class DiscSaver {
	
	// logger
	private static final Logger logger = Logger.getLogger(DiscSaver.class);
	// jedyny egzemplaz 
	private static DiscSaver instance = null;
	
	public static synchronized DiscSaver getInstance() {
		if (instance == null) {
			instance = new DiscSaver();
		}
		return instance;
	}
	
	/**
	 * Metoda zapisuje na dysk
	 * @param orderDetails 
	 */
	public void save(OrderDetails orderDetails) throws IOException {
		File dir = new File("results");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, getFileName(orderDetails));
		FileWriter fw = new FileWriter(file);
		fw.write(getFileContent(orderDetails));
		fw.close();
	}
	
	/**
	 * Zwraca nazwe pliku
	 * @return 
	 */
	private String getFileName(OrderDetails orderDetails) {
		return orderDetails.getOrderInfo().getOrderName().replaceAll("^\\d+-", "") + "_" + Formater.fileDateFormat(System.currentTimeMillis()) + ".txt";
	}
	
	/**
	 * Zwraca zawartosc pliku
	 * @param orderDetails
	 * @return 
	 */
	private String getFileContent(OrderDetails orderDetails) {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(orderDetails.getOrderInfo().getOrderName()).append("\n");
		
		if (orderDetails.getOrderInfo().isFinished()) {
			sb.append("Finished").append("\n");
		} else {
			double progress = (1.0 * orderDetails.getOrderInfo().getResultCounter()) / orderDetails.getOrderInfo().getTotalResultNumber();
			sb.append("UNFINISHED! progress: ").append(Formater.percentFormat(progress)).append("\n");;
		}
		
		sb.append("Processed graphs: ").append(orderDetails.getOrderInfo().getGraphCounter()).append("\n");
		sb.append("Processing time: ").append(Formater.processingTimeFormat(orderDetails.getOrderInfo().getProcessingTime()));
		sb.append(" since: ").append(Formater.dateFormat(orderDetails.getOrderInfo().getCreationTime())).append("\n");
		sb.append("Build rules: ").append(orderDetails.getProblem().getBuildRules()).append("\n");
		
		for (Result result : orderDetails.getCurrentResultSet().getResults().values()) {
			sb.append("\nTask: ").append(result.getTask()).append(" result: ").append(result.getValue()).append("\n");
			sb.append("Parameters: ").append(result.getParametersStack()).append("\n");
			sb.append("Graph: ").append(result.getGraph()).append("\n");
		}
		
		return sb.toString();
	}
}
