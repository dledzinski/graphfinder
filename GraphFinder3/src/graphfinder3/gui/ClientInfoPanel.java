/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.*;
import java.awt.EventQueue;
import java.util.List;
import java.util.Set;

/**
 *
 * @author damian
 */
public class ClientInfoPanel extends javax.swing.JPanel implements CommunicationListener {

	// polaczenie
	private CommunicationHandler communicationHandler = null;
	// model
	private final ClientInfoTableModel clientInfoTableModel = new ClientInfoTableModel();

	/**
	 * Creates new form ClientInfoPanel
	 */
	public ClientInfoPanel() {
		initComponents();
		// pozostale elementy
		clientInfoTable.setModel(clientInfoTableModel);
	}

	/**
	 * Ustawia obiekt polaczenia
	 *
	 * @param communicationHandler
	 */
	public void setCommunicationHandler(CommunicationHandler communicationHandler) {
		this.communicationHandler = communicationHandler;
		// rejestacja
		communicationHandler.addCommunicationListener(this);
		// rejestracja na zdarzenia
		communicationHandler.sendToServer(new Message(Command.CLIETN_INFO_REGISTER, null));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        refreshButton = new javax.swing.JButton();
        deleteOfflinesButon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientInfoTable = new javax.swing.JTable();

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        deleteOfflinesButon.setText("Delete offlines");
        deleteOfflinesButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteOfflinesButonActionPerformed(evt);
            }
        });

        clientInfoTable.setAutoCreateRowSorter(true);
        clientInfoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(clientInfoTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteOfflinesButon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refreshButton)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteOfflinesButon, refreshButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshButton)
                    .addComponent(deleteOfflinesButon))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void deleteOfflinesButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteOfflinesButonActionPerformed
		if (communicationHandler != null) {
			communicationHandler.sendToServer(new Message(Command.CLIETN_INFO_DELETE_UNACTIVES, null));
		}
	}//GEN-LAST:event_deleteOfflinesButonActionPerformed

	private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
		if (communicationHandler != null) {
			communicationHandler.sendToServer(new Message(Command.CLIETN_INFO_REGISTER, null));
		}
	}//GEN-LAST:event_refreshButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable clientInfoTable;
    private javax.swing.JButton deleteOfflinesButon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

	@Override
	public void orderDetails(OrderDetails orderDetails) {
	}

	@Override
	public void orderInfos(Set<OrderInfo> orderInfos) {
	}

	@Override
	public void clientInfos(final Set<ClientInfo> clientInfos) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				clientInfoTableModel.setData(clientInfos);
			}
		});
	}

	@Override
	public void loginOk(String clientName) {
		// rejestracja na zdarzenia
		communicationHandler.sendToServer(new Message(Command.CLIETN_INFO_REGISTER, null));
	}

	@Override
	public void error(String message) {
	}

	@Override
	public void fatalError(String message) {
	}

	@Override
	public void disconnected() {
	}
}
