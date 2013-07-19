/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author damian
 */
public abstract class AbstractTypedGraphCreator implements TypedGraphCreator {

	// zlozonosc
	protected final int complexity;
	// wymagana ilosc parametrow
	protected final int requiredParamsNumber;
	// nazwa typu
	protected String typeName = null;
	// ndr
	protected final boolean ndr;

	/**
	 * 
	 * @param complexity zlozonosc
	 * @param requiredParamsNumber ilosc wymaganych parametrow
	 * @param typeName
	 */
	public AbstractTypedGraphCreator(int complexity, int requiredParamsNumber, boolean ndr) {
		this.complexity = complexity;
		this.requiredParamsNumber = requiredParamsNumber;
		this.ndr = ndr;
	}

	/**
	 * 
	 * @param complexity
	 * @param requiredParamsNumber 
	 */
	public AbstractTypedGraphCreator(int complexity, int requiredParamsNumber) {
		this(complexity, requiredParamsNumber, false);
	}
	
	/**
	 * Zwraca zlozonosc
	 * @return
	 */
	public int getComplexity() {
		return complexity;
	}

	/**
	 * Zwraca nazwe typu
	 * @return
	 */
	public String getTypeName() {
		// sprawdzanie w enumach
		if (typeName == null) {
			for (TypedGraphCreators creator : TypedGraphCreators.values()) {
				if (creator.getCreator() == this) {
					typeName = creator.name();
					break;
				}
			}
			// jesli sie nie znalazlo
			if (typeName == null) {
				typeName = this.getClass().getSimpleName();
			}
		}
		return typeName;
	}

	/**
	 * Zwraca ilosc wymaganych parametrow
	 * @return
	 */
	public int getRequiredParamsNumber() {
		return requiredParamsNumber;
	}

	/**
	 * Zwraca liste poprawnych ilosci wezlow
	 * @param rangeFrom
	 * @param rangeTo
	 * @return
	 */
	public List<Integer> getValidNodeNumber(int rangeFrom, int rangeTo) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = rangeFrom; i <= rangeTo; i++) {
			if (isValidNodeNumber(i)) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * Zwraca zestawy pasujacych parametrow
	 * @param nodeNumber
	 * @param optimalize optymalizacja
	 * @return
	 */
	public List<int[]> getValidParams(int nodeNumber, boolean optimalize) {
		List<int[]> list = new ArrayList<int[]>();
		int[] params = new int[getRequiredParamsNumber()];
		// wchodzenie do petli
		if (params.length == 0) {
			list.add(params);
		} else {
			internalLoop(nodeNumber, list, params, 0, optimalize);
		}
		return list;
	}

	/**
	 * Uzupelnia liste o kolejne iteracje
	 * @param nodeNumber ilosc wezlow
	 * @param validList lista poprawnych parametrow
	 * @param params parametry
	 * @param deph glebokosc
	 */
	private void internalLoop(int nodeNumber, List<int[]> validList, int[] params, int deph, boolean optimalize) {
		// petla
		for (int i = 0; i <= nodeNumber / 2; i++) {
			// umieszczanie pozycji w tablicy parametrow
			params[deph] = i;
			// jesli jestesmy wystarczajaco gleboko
			if (deph == getRequiredParamsNumber() - 1) {
				if (isValidParams(nodeNumber, params)) {
					// czy optymalizowac
					if (optimalize) {
						if (isOptimalParams(nodeNumber, params)) {
							validList.add(Arrays.copyOf(params, params.length));
						}
					} else {
						validList.add(Arrays.copyOf(params, params.length));
					}
				}
			} else {
				// trzeba zejsc glebiej
				internalLoop(nodeNumber, validList, params, deph + 1, optimalize);
			}
		}

	}

	/**
	 * True jesli to NDR
	 * @return 
	 */
	public boolean isNdr() {
		return ndr;
	}
	
	
	
}
