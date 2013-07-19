/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.ClientInfo;
import graphfinder3.util.Formater;
import graphfinder3.util.MonitoringData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model tabeli z klientami
 *
 * @author damian
 */
public class ClientInfoTableModel extends AbstractTableModel {
	
	// dane
	private List<ClientInfo> clientInfos = new ArrayList<ClientInfo>();

	@Override
	public int getRowCount() {
		return clientInfos.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		final MonitoringData monitoringData = clientInfos.get(rowIndex).getMonitoringData();
		switch (columnIndex) {
			case 0:
				return clientInfos.get(rowIndex).getClientName();
			case 1:
				return clientInfos.get(rowIndex).getIpAddress();
			case 2:
				return (clientInfos.get(rowIndex).isActive() ? "online" : "offline");
			case 3:
				return (monitoringData != null)? Formater.doubleFormat(monitoringData.getGraphsPesSecond()) : "";
			case 4:
				return (monitoringData != null && monitoringData.getCpuUsage() >= 0) ? Formater.percentFormat(monitoringData.getCpuUsage()) : "";
			case 5:
				return (monitoringData != null && monitoringData.getRamUsage() >= 0) ? Formater.percentFormat(monitoringData.getRamUsage()) : "";
//			case 5:
//				return clientInfos.get(rowIndex).getMonitoringData() != null ? Formater.percentFormat(clientInfos.get(rowIndex).getMonitoringData().getHeapUsage()) : "";
			case 6:
				return monitoringData != null ? monitoringData.getUptime() : "";
			case 7:
				return Formater.dateFormat(clientInfos.get(rowIndex).getLastActivityTime());
			default:
				return "Unknown";
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Name";
			case 1:
				return "IP Address";
			case 2:
				return "Status";
			case 3:
				return "Graphs/s";
			case 4:
				return "CPU";
			case 5:
				return "RAM";
//			case 5:
//				return "Heap";	
			case 6:
				return "uptime";
			case 7:
				return "Last activity";
			default:
				return "Unknown";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Metoda umieszcza nowe dane w tabeli
	 *
	 * @param data
	 */
	public void setData(Collection<ClientInfo> data) {
		clientInfos = new ArrayList<ClientInfo>(data);
		fireTableDataChanged();
	}
}
