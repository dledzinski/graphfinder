/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author damian
 */
public class Formater {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("#0.00%");
	private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#0.0");
	
	/**
	 * Formatuje wartosc procentowa
	 * @param value
	 * @return 
	 */
	public static String percentFormat(double value) {
		return PERCENT_FORMAT.format(value);
	}
	
	/**
	 * Formatuje wartosc liczbowa
	 * @param value
	 * @return 
	 */
	public static String doubleFormat(double value) {
		return NUMBER_FORMAT.format(value);
	}
	
	/**
	 * Formatuje date
	 * @param date
	 * @return 
	 */
	public static String dateFormat(long date) {
		return DATE_FORMAT.format(new Date(date));
	} 

	/**
	 * Formatuje date na potrzeby nazw plikow
	 * @param date
	 * @return 
	 */
	public static String fileDateFormat(long date) {
		return FILE_DATE_FORMAT.format(new Date(date));
	}
	
	/**
	 * Formatije czas procesowania
	 * @param processingTime
	 * @return 
	 */
	public static String processingTimeFormat(long processingTime) {
		processingTime /= 1000;
		StringBuilder sb = new StringBuilder();
		if (processingTime > (3600 * 24)) {
			sb.append(processingTime / (3600 * 24)).append("d ");
		}
		processingTime %= 3600 * 24;
		int h = (int) (processingTime / 3600);
		int m = (int) ((processingTime % 3600) / 60);
		int s = (int) (processingTime % 60);
		sb.append(h < 10 ? 0 : "").append(h).append(":");
		sb.append(m < 10 ? 0 : "").append(m).append(":");
		sb.append(s < 10 ? 0 : "").append(s);
		return sb.toString();
	}
}
