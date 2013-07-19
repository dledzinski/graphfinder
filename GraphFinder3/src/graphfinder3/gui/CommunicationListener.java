/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui;

import graphfinder3.network.ClientInfo;
import graphfinder3.network.OrderDetails;
import graphfinder3.network.OrderInfo;
import java.util.List;
import java.util.Set;

/**
 * Interfejs sluchacza komunikatow
 * @author damian
 */
public interface CommunicationListener {
	
	/**
	 * Nadeszly szczegoly rozkazu
	 * @param orderDetails 
	 */
	public void orderDetails(OrderDetails orderDetails);
	
	/**
	 * Nadeszly informacje o rozkazach
	 * @param orderInfos 
	 */
	public void orderInfos(Set<OrderInfo> orderInfos);
	
	/**
	 * nadeszly informacje o zalogowanych maszynach
	 * @param clientInfos 
	 */
	public void clientInfos(Set<ClientInfo> clientInfos);
	
	/**
	 * Pomyslne zalogowanie
	 * @param clientName 
	 */
	public void loginOk(String clientName);
	
	/**
	 * Blad do przedstawienia uzytkownikowi
	 * @param message 
	 */
	public void error(String message);
	
	/**
	 * Blad ktory musi zakonczyc sie zabiciem programu
	 * @param message 
	 */
	public void fatalError(String message);
	
	/**
	 * Rozlaczenie, musi nastapic zabicie programu
	 */
	public void disconnected();
	
}
