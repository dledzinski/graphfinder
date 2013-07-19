/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.data.BuildRule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author damian
 */
public class BuildRuleTableModel extends AbstractTableModel {

	private List<BuildRule> buildRules = new ArrayList<BuildRule>();

	public BuildRuleTableModel() {
		addNone();
	}
	
	@Override
	public int getRowCount() {
		return buildRules.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return buildRules.get(rowIndex);
	}

	@Override
	public String getColumnName(int column) {
		return "Build rules";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		buildRules.set(rowIndex, (BuildRule) aValue);
		addNone();
	}
	
	/**
	 * czysci
	 */
	public void clear() {
		buildRules.clear();
		addNone();
	}

	/**
	 * Zwraca liste regol budowy
	 * @return 
	 */
	public List<BuildRule> getBuildRules() {
		List<BuildRule> buildRuleList = new ArrayList<BuildRule>(buildRules);
		buildRuleList.removeAll(Collections.singleton(BuildRule.NONE));
		return buildRuleList;
	}
	
	/**
	 * Jesli to potrzebne dodaje ostatni wiersz
	 */
	private void addNone() {
		if (buildRules.isEmpty() || (!(buildRules.get(buildRules.size() - 1).equals(BuildRule.NONE)))) {
			buildRules.add(BuildRule.NONE);
		}
		fireTableDataChanged();
	}
}
