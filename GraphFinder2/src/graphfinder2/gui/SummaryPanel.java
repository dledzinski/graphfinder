/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SummaryPanel.java
 *
 * Created on 2011-09-15, 15:01:55
 */

package graphfinder2.gui;

import graphfinder2.SummaryCreator;
import graphfinder2.typedGraph.Degrees;
import graphfinder2.typedGraph.TypedGraphCreators;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author damian
 */
public class SummaryPanel extends javax.swing.JPanel {

    /** Creates new form SummaryPanel */
    public SummaryPanel() {
        initComponents();
		fillStageComboBox();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        stageComboBox = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));

        jLabel1.setText("Degree");

        stageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stageComboBoxActionPerformed(evt);
            }
        });

        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stageComboBox, 0, 305, Short.MAX_VALUE))
                    .addComponent(generateButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(stageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(generateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void stageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stageComboBoxActionPerformed
		// brak akcji
	}//GEN-LAST:event_stageComboBoxActionPerformed

	private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
		// generowanie
		createSummary();
	}//GEN-LAST:event_generateButtonActionPerformed

	/**
	 * Uzupelna stopnie grafu
	 */
	private void fillStageComboBox() {
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
		dcbm.addElement("ALL");
		for (Degrees stage : Degrees.values()) {
			dcbm.addElement(stage.name());
		}
		dcbm.setSelectedItem("ALL");
		stageComboBox.setModel(dcbm);
	}

	private void createSummary() {
		// ustalenie filtru
		Degrees stage = null;
		// jesli nie wszystkie
		if (!((String) stageComboBox.getModel().getSelectedItem()).equals("ALL")) {
			stage = Degrees.valueOf((String) stageComboBox.getModel().getSelectedItem());
		}

		SummaryCreator creator = new SummaryCreator(new File("."));
		try {
			creator.createSumery(stage);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR);
		}

	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox stageComboBox;
    // End of variables declaration//GEN-END:variables

}
