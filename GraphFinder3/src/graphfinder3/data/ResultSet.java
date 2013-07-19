/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

import java.io.Serializable;
import java.util.Map;

/**
 * Klasa przechowujaca rozwiazania problemu
 *
 * @author damian
 */
public class ResultSet implements Serializable {

	// rozwiazania
	private final Map<Task, Result> results;

	/**
	 * Tworzy obiet rozwiazan
	 *
	 * @param results
	 */
	public ResultSet(Map<Task, Result> results) {
		this.results = results;
	}

	/**
	 * Zwraca rozwiazania problemow
	 *
	 * @return
	 */
	public Map<Task, Result> getResults() {
		return results;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ResultSet other = (ResultSet) obj;
		if (this.results != other.results && (this.results == null || !this.results.equals(other.results))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + (this.results != null ? this.results.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "ResultSet{" + "results=" + results + '}';
	}
}
