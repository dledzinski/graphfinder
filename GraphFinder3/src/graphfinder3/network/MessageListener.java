/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

/**
 * Interfejs sluchacza komunikatow
 * @author damian
 */
public interface MessageListener {
	
	/**
	 * Metoda wywolywana kiedy przyjdzie nowy komunikat
	 * @param message 
	 */
	public void newMessage(Message message);
	
	/**
	 * Metoda wywolywana kiedy nastapi rozlaczenie
	 */
	public void disconnected();
}
