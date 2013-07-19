/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph;

import graphfinder2.graph.Graph;
import graphfinder2.graph.Result;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Klasa zawiera metody o wspolnym kodzie dla wszystkich grafow
 *
 * @author damian
 */
public abstract class AbstractTypedGraph implements TypedGraph {

	// kreator
	protected final TypedGraphCreator creator;
	// ilosc wezlow
	protected final int nodeNumber;
	// parametry
	protected final int[] params;
	// graf
	protected final Graph graph;
	// srednie dlugosci sciezek z danych wezlow
	protected final double averages[];
	// srednice
	protected final int diameters[];
	// rozklad
	protected final Map<Integer, Integer> distributions[];

	/**
	 * Tworzy suchy graf bez polaczen
	 *
	 * @param nodeNumber ilosc wezlow
	 * @param complexity zlozonosc (z ilu pierwszych wezlow trzeba graf
	 * sprawdzac)
	 */
	public AbstractTypedGraph(TypedGraphCreator creator, int nodeNUmber, int[] params) {
		// podstawowe parametry
		this.creator = creator;
		this.nodeNumber = nodeNUmber;
		this.params = params;
		// walidacja
		validate();
		// tworzenie grafu
		graph = createGraph(nodeNUmber, params);
		// parametry
		this.averages = new double[creator.getComplexity()];
		this.diameters = new int[creator.getComplexity()];
		this.distributions = new Map[creator.getComplexity()];
		// obliczanie
		calculate();
	}

	/**
	 * Okresla poprawnosc parametrow do tworzenia grafu
	 */
	protected void validate() {
		if (!creator.isValidNodeNumber(nodeNumber)) {
			throw new RuntimeException("Nieprawidlowa ilosc wezlow: " + nodeNumber + " dla grafu typu: " + getTypeName());
		}
		if (params.length < creator.getRequiredParamsNumber()) {
			throw new RuntimeException("Za mala ilosc parametrow dla grafu typu: " + getTypeName());
		}
		if (!creator.isValidParams(nodeNumber, params)) {
			throw new RuntimeException("Nieprawidlowe parametry: " + Arrays.toString(params) + " dla grafu typu: " + getTypeName());
		}
	}

	/**
	 * Zwraca srednia dlugosc sciezek
	 *
	 * @return
	 */
	public double getAverage() {
		double sum = 0;
		for (int i = 0; i < averages.length; i++) {
			sum += averages[i];
		}
		return sum / averages.length;
	}

	/**
	 * Zwraca najwieksza srednice
	 *
	 * @return
	 */
	public int getDiameter() {
		int max = 0;
		for (int i = 0; i < diameters.length; i++) {
			if (diameters[i] > max) {
				max = diameters[i];
			}
		}
		return max;
	}

	/**
	 * Zwraca preambule wynikow
	 *
	 * @return
	 */
	public String getResultPreamble() {
		String s = "";
		// ilosc wezlow
		s += "Nodes";
		// srednia dlugosc sciezek
		s += ";" + "Avg";
		// srednica
		s += ";" + "Diam";
		// srednia i srednica od roznych wezlow
		if (creator.getComplexity() > 1) {
			for (int i = 0; i < creator.getComplexity(); i++) {
				s += ";" + "AvgFrom" + i;
			}
			for (int i = 0; i < creator.getComplexity(); i++) {
				s += ";" + "DiamFrom" + i;
			}
		}
		// parametry
		for (int i = 0; i < getParams().length; i++) {
			s += ";" + "Param" + i;
		}
		// rozklad
		for (int i = 0; i < creator.getComplexity(); i++) {
			s += ";" + "AvgFrom" + i;
		}
		return s;
	}

	/**
	 * Zwraca preambule wynikow
	 *
	 * @return
	 */
	public String getResultLine() {
		String s = "";
		s += getGraph().getNodes().length;
		// srednia dlugosc sciezek
		s += ";" + getAverage();
		// srednica
		s += ";" + getDiameter();
		// srednia i srednica od roznych wezlow
		if (creator.getComplexity() > 1) {
			for (int i = 0; i < creator.getComplexity(); i++) {
				s += ";" + averages[i];
			}
			for (int i = 0; i < creator.getComplexity(); i++) {
				s += ";" + diameters[i];
			}
		}
		// parametry
		for (int i = 0; i < getParams().length; i++) {
			s += ";" + getParams()[i];
		}
		// rozkald
		for (int i = 0; i < creator.getComplexity(); i++) {
			s += ";" + distributions[i].toString().replaceAll("=", ":");
		}
		// zamienianie kropek na przecinki
		s = s.replaceAll("\\.", ",");
		return s;
	}

	/**
	 * Metoda wywolywana w celu wyliczenia grafu
	 */
	protected void calculate() {
		// zbieranie wynikow dla kazdego z wezlow poczatkowych
		if (creator.isNdr()) {
			// zbieranie wynikow dla wezlow pocztakowych
			for (int i = 0; i < creator.getComplexity(); i++) {
				// w zaleznosci czy wewnetrzny czy zewnetrzny ring
				int nn = (i < creator.getComplexity() / 2) ? i : i + nodeNumber / 2;
				Result result = new Result(getGraph(), nn);
				averages[i] = result.getAverage();
				diameters[i] = result.getDiameter();
				distributions[i] = result.getDistribution();
			}

		} else {
			// zbieranie wynikow dla kazdego z wezlow poczatkowych
			for (int i = 0; i < creator.getComplexity(); i++) {
				Result result = new Result(getGraph(), i);
				averages[i] = result.getAverage();
				diameters[i] = result.getDiameter();
				distributions[i] = result.getDistribution();
			}
		}
	}

	/**
	 * Zwraca nazwe typu grafu - nazwe klasy
	 *
	 * @return
	 */
	public String getTypeName() {
		return creator.getTypeName();
	}

	public Graph getGraph() {
		return graph;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public int[] getParams() {
		return params;
	}

	/**
	 * Metoda do tworzenia grafu
	 *
	 * @param nodeNUmber
	 * @param params
	 * @return
	 */
	protected abstract Graph createGraph(int nodeNUmber, int[] params);
}
