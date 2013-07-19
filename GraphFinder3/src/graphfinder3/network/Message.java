/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.network;

import java.io.Serializable;

/**
 * Klasa komunikatow przesylanych przez siec
 *
 * @author damian
 */
public class Message implements Serializable {

	// rozkaz
	private final Command command;
	// parametr
	private final Object param;

	/**
	 * Tworzy komunikat
	 *
	 * @param command
	 * @param param
	 */
	public Message(Command command, Object param) {
		this.command = command;
		this.param = param;
	}

	/**
	 * Zwraca tresc rozkazu
	 *
	 * @return
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * Zwraca zalacznik
	 *
	 * @return
	 */
	public Object getParam() {
		return param;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Message other = (Message) obj;
		if (this.command != other.command) {
			return false;
		}
		if (this.param != other.param && (this.param == null || !this.param.equals(other.param))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + (this.command != null ? this.command.hashCode() : 0);
		hash = 23 * hash + (this.param != null ? this.param.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "Message{" + "command=" + command + ", param=" + param + '}';
	}
}
