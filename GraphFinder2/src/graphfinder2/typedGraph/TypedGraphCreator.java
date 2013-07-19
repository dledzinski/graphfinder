/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.typedGraph;

import java.util.List;

/**
 * Interfejs kreatorow grafow
 * @author damian
 */
public interface TypedGraphCreator {

	/**
	 * Zwraca zlozonosc grafu
	 * @return
	 */
	public int getComplexity();

	/**
	 * Zwraca nazwe typu grafu
	 * @return
	 */
	public String getTypeName();

	/**
	 * Mtoda tworzy graf okreslonego typu
	 * @param nodeNumber ilosc wezlow
	 * @param params parametry
	 * @return
	 */
	public TypedGraph create(int nodeNumber, int ... params);

	/**
	 * Okresla czy ta ilosc wezlow jest prawidlowa dla tego grafu
	 * @param nodeNumber
	 * @return
	 */
	public boolean isValidNodeNumber(int nodeNumber);

	/**
	 * Zwraca dopuszczalne ilosci wezlow dla tego typu grafu
	 * @param rangeFrom zakres od
	 * @param rangeTo zakres do
	 * @return
	 */
	public List<Integer> getValidNodeNumber(int rangeFrom, int rangeTo);

	/**
	 * Okresla czy dane parametry sa prawidlowe
	 * @param nodeNUmber
	 * @param params
	 * @return
	 */
	public boolean isValidParams(int nodeNumber, int[] params);

	/**
	 * Zwraca liste dopuszczalnych parametrow
	 * @param nodeNumber
	 * @param optimalize optymalizacja pod wzgledem wyszukiwania
	 * @return
	 */
	public List<int[]> getValidParams(int nodeNumber, boolean optimalize);

	
	/**
	 * Okresla czy dany zestaw parametrow jest optymalny pod wzgledem wyszukiwania
	 * @param nodeNUmber
	 * @return
	 */
	public boolean isOptimalParams(int nodeNumber, int[] params);


	/**
	 * Zwraca ilosc wymaganych parametrow
	 * @return
	 */
	public int getRequiredParamsNumber();
	
	/**
	 * Zwraa true jesli to NDR
	 * @return 
	 */
	public boolean isNdr();

}
