/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Edytor danych w tabeli za pomoca comboboxa
 *
 * @author damian
 */
public class ComboBoxCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	// komponent
	private final JComboBox comboBox;
	
	/**
	 * Tworzy obiekt
	 * @param items wartosci jakie moze przyjmowac combobox
	 */
	public ComboBoxCellEditor(Object[] items) {
		comboBox = new JComboBox(items);
		
		// powiadamianie o edycji
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				stopCellEditing();
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		comboBox.setSelectedItem(value);
		return comboBox;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		comboBox.setSelectedItem(value);
		return comboBox;
	}
}
