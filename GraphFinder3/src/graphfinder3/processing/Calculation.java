/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.Graph;
import graphfinder3.data.Task;
import java.util.HashMap;
import java.util.Map;

/**
 * Obiekt obliczen
 *
 * @author damian
 */
public class Calculation {

	// graf dla ktorego liczymy
	private final Graph graph;
	// mapa promieni
	private final Map<Integer, Integer> radiuses = new HashMap<Integer, Integer>();
	// mapa srednich dlugosci sciezek
	private final Map<Integer, Double> averagePathLengths = new HashMap<Integer, Double>();

	/**
	 * Tworzy obiekt obliczen
	 *
	 * @param graph
	 */
	public Calculation(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Zwraca promien liczony z okreslonego wezla
	 *
	 * @param index
	 * @return
	 */
	public int getRadiusFrom(int index) {
		if (radiuses.get(index) != null) {
			return radiuses.get(index);
		} else {
			SingleCalculation sc = new SingleCalculation(graph, index);
			radiuses.put(index, sc.getRadius());
			averagePathLengths.put(index, sc.getAveragePathLength());
			return sc.getRadius();
		}
	}

	/**
	 * Zwraca srednia dlugosc sciezki liczony z okreslonego wezla
	 *
	 * @param index
	 * @return
	 */
	public double getAveragePathLengthFrom(int index) {
		if (averagePathLengths.get(index) != null) {
			return averagePathLengths.get(index);
		} else {
			SingleCalculation sc = new SingleCalculation(graph, index);
			radiuses.put(index, sc.getRadius());
			averagePathLengths.put(index, sc.getAveragePathLength());
			return sc.getAveragePathLength();
		}
	}

	/**
	 * Zwraca srednice dla okreslonej iloci wezlow (poczowszy od wezla 0)
	 *
	 * @param nodeNumber
	 * @return
	 */
	public int getDiameterFor(int nodeNumber) {
		int diameter = 0;
		for (int i = 0; i < nodeNumber; i++) {
			diameter = Math.max(diameter, getRadiusFrom(i));
		}
		return diameter;
	}

	/**
	 * Zwraca srednia dlugosc sciezki liczona od wezla
	 *
	 * @param nodeNumber
	 * @return
	 */
	public double getAveragePathLengthFor(int nodeNumber) {
		double sum = 0;
		for (int i = 0; i < nodeNumber; i++) {
			sum += getAveragePathLengthFrom(i);
		}
		return sum / nodeNumber;
	}

	/**
	 * Zwraca srednice liczona ze wszystkich wezlow
	 *
	 * @return
	 */
	public int getDiameterFromAll() {
		return getDiameterFor(graph.getNodeNumber());
	}

	/**
	 * Zwraca srednia dlugosc sciezek liczona ze wszystkich wezlow
	 *
	 * @return
	 */
	public double getAveragePathLengthFromAll() {
		return getAveragePathLengthFor(graph.getNodeNumber());
	}

	/**
	 * Zwraca najlepszy promien
	 *
	 * @return
	 */
	public int getBestRadius() {
		int bestRadius = Integer.MAX_VALUE;
		for (int i = 0; i < graph.getNodeNumber(); i++) {
			bestRadius = Math.min(bestRadius, getRadiusFrom(i));
		}
		return bestRadius;
	}

	/**
	 * Zwraca najlepsza (najkrutsza) srednia dlugosc sciezki z jednego wezla
	 *
	 * @return
	 */
	public double getBestAveragePathLength() {
		double bestAverage = Double.MAX_VALUE;
		for (int i = 0; i < graph.getNodeNumber(); i++) {
			bestAverage = Math.min(bestAverage, getAveragePathLengthFrom(i));
		}
		return bestAverage;
	}

	/**
	 * Zwraca wartosc wyniku konkretnego zadania
	 *
	 * @param task
	 * @return
	 */
	public double getResultValue(Task task) {
		switch (task) {
			case BEST_RADIUS:
				return getBestRadius();
			case RADIUS_FROM_0:
				return getRadiusFrom(0);
			case DIAMETER_FROM_ALL:
				return getDiameterFromAll();
			case DIAMETER_FROM_0_TO_1:
				return getDiameterFor(2);
			case DIAMETER_FROM_0_TO_3:
				return getDiameterFor(4);
			case BEST_AVERAGE:
				return getBestAveragePathLength();
			case AVERAGE_FROM_0:
				return getAveragePathLengthFrom(0);
			case AVERAGE_FROM_ALL:
				return getAveragePathLengthFromAll();
			case AVERAGE_FROM_0_TO_1:
				return getAveragePathLengthFor(2);
			case AVERAGE_FROM_0_TO_3:
				return getAveragePathLengthFor(4);
			case AVERAGE_FROM_0_TO_7:
				return getAveragePathLengthFor(8);
			default:
				throw new RuntimeException("Unsupported task: " + task);
		}
	}
}
