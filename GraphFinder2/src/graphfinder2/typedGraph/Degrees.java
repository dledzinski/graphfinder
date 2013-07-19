/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph;

/**
 * stopien grafu - ilosc polaczen
 * @author damian
 */
public enum Degrees {

	DEGREE3(3),
	DEGREE4(4),
	DEGREE5(5),
	DEGREE6(6),
	;
	private final int neighboursNumber;

	private Degrees(int neighboursNumber) {
		this.neighboursNumber = neighboursNumber;
	}

	/**
	 * Zwraca ilosc sasiadow kazdego wezla
	 * @return
	 */
	public int getNeighboursNumber() {
		return neighboursNumber;
	}

}
