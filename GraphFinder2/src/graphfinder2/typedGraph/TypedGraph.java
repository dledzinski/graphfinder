/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph;

import graphfinder2.graph.Graph;

/**
 * Interfejs do grafow okreslonego typu
 * @author damian
 */
public interface TypedGraph {

	/**
	 * Zwraca graf
	 * @return
	 */
	public Graph getGraph();

	/**
	 * Zwraca ilosc wezlow
	 * @return
	 */
	public int getNodeNumber();

	/**
	 * Zwraca parametry do tworzenia grafu
	 * @return
	 */
	public int[] getParams();

	/**
	 * Zwraca srednia dlugosc sciezki
	 * @return
	 */
	public double getAverage();

	/**
	 * Zwraca najwieksza srednice
	 * @return
	 */
	public int getDiameter();

	/**
	 * Zwraca linijke z wynikami
	 * @return
	 */
	public String getResultLine();

	/**
	 * Zwraca preambule wynikow
	 * @return
	 */
	public String getResultPreamble();

	/**
	 * Zwraca nazwe typu
	 * @return
	 */
	public String getTypeName();
	
}
