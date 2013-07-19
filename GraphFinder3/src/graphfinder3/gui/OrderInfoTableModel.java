/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.OrderInfo;
import graphfinder3.util.Formater;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author damian
 */
public class OrderInfoTableModel extends AbstractTableModel {

	// lista rozkazow
	private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();

	@Override
	public int getRowCount() {
		return orderInfos.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return orderInfos.get(rowIndex).getOrderName();
			case 1:
				return orderInfos.get(rowIndex).isFinished() ? "Finished" : "";
			case 2:
				return orderInfos.get(rowIndex).getPriority();
			case 3:
				return orderInfos.get(rowIndex).getResultCounter();
			case 4:
				return orderInfos.get(rowIndex).getTotalResultNumber();
			case 5:
				return orderInfos.get(rowIndex).getGraphCounter();
			case 6:
				return Formater.processingTimeFormat(orderInfos.get(rowIndex).getProcessingTime());
			case 7:
				return Formater.dateFormat(orderInfos.get(rowIndex).getCreationTime());
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
				return "Status";
			case 2:
				return "Priority";
			case 3:
				return "Solved parts";
			case 4:
				return "Total parts";
			case 5:
				return "Processed graphs";
			case 6:
				return "Processing time";
			case 7:
				return "Since";
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
	 * Ustawia nowe dane
	 * @param data 
	 */
	public void setData(Collection<OrderInfo> data) {
		orderInfos = new ArrayList<OrderInfo>(data);
		fireTableDataChanged();
	}
	
	/**
	 * Zwraca informacje o konkretnym rozkazie
	 * @param index
	 * @return 
	 */
	public OrderInfo get(int index) {
		return orderInfos.get(index);
	}
	
	
}
