/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui.analyzer;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author damian
 */
public class DistributionTableModel extends AbstractTableModel {

	private final int[][] data;

	/**
	 * Tworzy obiekt, uzupelnia danymi
	 *
	 * @param data
	 */
	public DistributionTableModel(List<Map<Integer, Integer>> data) {
		this.data = createData(data);
	}

	@Override
	public int getRowCount() {
		if (data.length > 0) {
			return data[0].length;
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return data.length + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[columnIndex][rowIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return (column == 0) ? "Node" : "" + (column - 1);
	}

	/**
	 * Tworzy tablice z danymi
	 *
	 * @param data
	 * @return
	 */
	private int[][] createData(List<Map<Integer, Integer>> data) {
		// znajdowanie maksymalnej wartosci
		int max = 0;
		for (Map<Integer, Integer> map : data) {
			max = Math.max(max, map.size());
		}
		// tworzenie tablicy z danymi
		int[][] array = new int[max][data.size()];
		// uzupelnianie
		for (int i = 0; i < data.size(); i++) {
			Map<Integer, Integer> map = data.get(i);
			for (int key : map.keySet()) {
				array[key][i] = map.get(key);
			}
		}
		return array;
	}
}
