/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

/**
 * Polecenia do wykonania do rozwiazania problemow
 * @author damian
 */
public enum Task {
	/**
	 * Najkrutszy promien grafu
	 */
	BEST_RADIUS,
	/**
	 * Promien grafu (najdluzsza sciezka z wezla 0)
	 */
	RADIUS_FROM_0,
	/**
	 * Srednica (najdluzsza sciezka ze wszystkich wezlow)
	 */
	DIAMETER_FROM_ALL,
	/**
	 * Srednica z wezlow 0,1
	 */
	DIAMETER_FROM_0_TO_1,
	/**
	 * Srednica z wezlow 0,1,2,3
	 */
	DIAMETER_FROM_0_TO_3,
	/**
	 * Najlepsza srednia dlugosc sciezki
	 */
	BEST_AVERAGE,
	/**
	 * Srednia dlugosc dlugosc sciezki z wezla 0
	 */
	AVERAGE_FROM_0,
	/**
	 * Srednia dlugosc sciezki z kazdego wezla
	 */
	AVERAGE_FROM_ALL,
	/**
	 * Srednia dlugosc dlugosc sciezki z wezla 0,1
	 */
	AVERAGE_FROM_0_TO_1,
	/**
	 * Srednia dlugosc dlugosc sciezki z wezla 0,1,2,3
	 */
	AVERAGE_FROM_0_TO_3,
	/**
	 * Srednia dlugosc dlugosc sciezki z wezla 0,1,2,3
	 */
	AVERAGE_FROM_0_TO_7,
	/**
	 * Nic nie robi, na potrzeby GUI
	 */
	NONE,
}
