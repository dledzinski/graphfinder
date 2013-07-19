/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author damian
 */
public class Node {

	// numer wezla
	private final int index;
	// sasiedzi
	private final List<Integer> neighbours = new ArrayList<Integer>();

	/**
	 * Tworzy wezel
	 * @param index
	 */
	public Node(int index) {
		this.index = index;
	}

	/**
	 * Zwraca numer wezla
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Zwraca sosiadow
	 * @return
	 */
	public List<Integer> getNeighbours() {
		return neighbours;
	}
	
	/**
	 * Dodaje sasiada
	 * @param neighbourIndex
	 */
	public void addNeighbour(int neighbourIndex) {
		neighbours.add(neighbourIndex);
	}

	@Override
	public String toString() {
		return index + ":" + neighbours;
	}



}
