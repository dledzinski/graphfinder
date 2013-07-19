/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.util;

/**
 *
 * @author damian
 */
public interface MonitoringDataListener {
	
	/**
	 * Metoda wywolywana przez monitor kiedy beda nowe dane
	 * @param monitoringData 
	 */
	public void newMonitoringData(MonitoringData monitoringData);
	
}
