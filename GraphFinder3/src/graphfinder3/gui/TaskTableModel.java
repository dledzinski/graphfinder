/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.data.Task;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author damian
 */
public class TaskTableModel extends AbstractTableModel {

	private final List<Task> tasks = new ArrayList<Task>();

	public TaskTableModel() {
		addNone();
	}

	@Override
	public int getRowCount() {
		return tasks.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tasks.get(rowIndex);
	}

	@Override
	public String getColumnName(int column) {
		return "Tasks";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tasks.set(rowIndex, (Task) aValue);
		addNone();
	}

	/**
	 * Czysci tabele
	 */
	public void clear() {
		tasks.clear();
		addNone();
	}
	
	/**
	 * Zwraca zbior zadan
	 * @return 
	 */
	public Set<Task> getTasks() {
		Set<Task> taskSet = new HashSet<Task>(tasks);
		taskSet.remove(Task.NONE);
		return taskSet;
	}
	
	public void addTask(Task task) {
		// jesli ostatni to none to wstawianie jako przedostatni
		if (tasks.get(tasks.size() - 1).equals(Task.NONE)) {
			tasks.add(tasks.size() - 1, task);
		} else {
			tasks.add(task);
		}
		
	}
	
	/**
	 * Jesli to potrzebne dodaje ostatni wiersz
	 */
	private void addNone() {
		if (tasks.isEmpty() || (!(tasks.get(tasks.size() - 1).equals(Task.NONE)))) {
			tasks.add(Task.NONE);
		}
		fireTableDataChanged();
	}
}
