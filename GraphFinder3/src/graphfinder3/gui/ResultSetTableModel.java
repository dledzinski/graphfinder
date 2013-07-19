/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.data.Result;
import graphfinder3.data.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author damian
 */
public class ResultSetTableModel extends AbstractTableModel {

	// lista rozkazow
	private List<Result> results = new ArrayList<Result>();

	@Override
	public int getRowCount() {
		return results.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return results.get(rowIndex).getTask();
			case 1:
				return results.get(rowIndex).getValue();
			case 2:
				return results.get(rowIndex).getGraphCounter();
			case 3:
				return results.get(rowIndex).getParametersStack();
			default:
				return "Unknown";
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Task";
			case 1:
				return "Value";
			case 2:
				return "Matching graphs";
			case 3:
				return "Parameters stack";
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
	 *
	 * @param data
	 */
	public void setData(ResultSet resultSet) {
		results = new ArrayList<Result>(resultSet.getResults().values());
		fireTableDataChanged();
	}

	/**
	 * Zwraca konkretny wynik
	 *
	 * @param index
	 * @return
	 */
	public Result get(int index) {
		return results.get(index);
	}
}
