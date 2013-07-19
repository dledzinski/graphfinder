/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui.analyzer;

import graphfinder3.data.BuildRule;
import graphfinder3.data.Graph;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Model musi dzialac w nastepujacy sposob: <ul> <li>jesli jest pusty, to tworzy
 * part o regule NONE</li> <li>jesli ostatnia regula zostaje zmieniona to
 * podmieniany jest part<li> <li>jesli nie wybrano parametru (lub jest zly), to
 * nie pozwala na tworzenie kelejnych partow</li> <li>jesli zostaje wybrany
 * parametr to w nastepnym wierszu pojawia sie part o regule NONE</li> <li>jesli
 * jakas srodkowa regula lub jej parametr sa zmienione to wszystkie nastepne sa
 * usuwane</li> </ul>
 *
 * @author damian
 */
public class BuildPartTableModel extends AbstractTableModel {

	private final List<BuildPart> buildParts = new ArrayList<BuildPart>();
	private final Graph parrentGraph;

	/**
	 * Tworzy model
	 * @param parrentGraph 
	 */
	public BuildPartTableModel(Graph parrentGraph) {
		this.parrentGraph = parrentGraph;
		addLastNone();
	}
	
	
	
	@Override
	public int getRowCount() {
		return buildParts.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return buildParts.get(rowIndex).getBuildRule();
		}
		if (columnIndex == 1) {
			return buildParts.get(rowIndex).getBuildParam();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Build rule";
			case 1:
				return "Build param";
			default:
				return "Unknown";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// usuwanie wszystkiego co jest po ustawianym parcie
		while(buildParts.size() >= rowIndex) {
			buildParts.remove(buildParts.size() - 1);
		}
		// ustalanie reguly budowy
		if (columnIndex == 0) {
			// usuwanie tego ktory trzeba zastapic
			buildParts.remove(rowIndex);
			// poprzedni graf
			Graph previousGraph = (buildParts.isEmpty()) ? parrentGraph : buildParts.get(rowIndex - 1).getCurrentGraph();
			// tworzenie partu
			BuildPart newPart = new BuildPart(previousGraph, (BuildRule) aValue);
			// wybieranie z automatu jesli jest tyko jeden parametr
			if (newPart.getValidParams().size() == 1) {
				newPart.setBuildParam(newPart.getValidParams().iterator().next());
			}	
			buildParts.add(newPart);
		}
		// ustalanie parametru
		if (columnIndex == 1) {
			buildParts.get(rowIndex).setBuildParam((Integer) aValue);
		}
		// ewentualne dodawanie ostatniego
		addLastNone();
	}
	
	private void addLastNone() {
		// jeski nic nie ma
		if (buildParts.isEmpty()) {
			buildParts.add(new BuildPart(parrentGraph, BuildRule.NONE));
			return;
		}
		// ostatni part
		BuildPart lastPart = buildParts.get(buildParts.size() - 1);
		// jesli co jest i ostatni jest prawidlowy
		if (lastPart.getCurrentGraph() != null) {
			buildParts.add(new BuildPart(lastPart.getCurrentGraph(), BuildRule.NONE));
			return;
		}
		fireTableDataChanged();
	}
}
