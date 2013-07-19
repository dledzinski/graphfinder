/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Obiekt reprezentujacy wyniki
 * @author damian
 */
public class Result {

	// srednica
	private int diameter;
	// srednia dlugosc sciezki
	private double average;
	// rozklad
	private final Map<Integer, Integer> distribution = new TreeMap<Integer, Integer>();
	// wezel zrodlowy
	private final int sourceNodeIndex;
	// badany graf
	private final Graph graph;
	// wyniki posrednie dla kolejnych wezlow
	private final int[] pathLengths;
	// kolejka zadan
	private final Queue<Integer> fifo = new LinkedList<Integer>();

	/**
	 * Tworzy obiekt wynikow, badajac graf
	 * @param sourceNodeIndex
	 * @param graph
	 */
	public Result(Graph graph, int sourceNodeIndex) {
		this.sourceNodeIndex = sourceNodeIndex;
		this.graph = graph;
		// tablica z wynikami posrednimi
		pathLengths = new int[graph.getNodes().length];
		// obliczanie
		calculate();
	}

	/**
	 * Obliczenia
	 */
	private void calculate() {
		// oznaczanie  wezlow jako nieodwiedzonych
		for (int i = 0; i < pathLengths.length; i++) {
			pathLengths[i] = -1;
		}
		// oznaczanie zrodla
		pathLengths[sourceNodeIndex] = 0;
		// wrzucanie do kolejki
		fifo.add(sourceNodeIndex);
		// petla wykonywana do czasu az kolejka bedzie pusta
		while (!fifo.isEmpty()) {
			int currentNodeIndex = fifo.poll();
			// pobieranie sasiadow
			for (int neighbourIndex : graph.getNodes()[currentNodeIndex].getNeighbours()) {
				// jesli jeszcze nie byl obslugiwany
				if (pathLengths[neighbourIndex] < 0) {
					// ustalanie dlugosci sciezki
					pathLengths[neighbourIndex] = pathLengths[currentNodeIndex] + 1;
					// dodawanie wezla do kolejki
					fifo.add(neighbourIndex);
				}
			}
		}
		// ustalanie wynikow
		diameter = 0;
		double sum = 0;
		for (int i = 0; i < pathLengths.length; i++) {
			int currentPathLength = pathLengths[i];
			// na wszelki wypadek
			if (currentPathLength < 0) {
				throw new RuntimeException("Wezel bez wyznaczonej dlugosci sciezli: " + i);
			}
			// srednica
			diameter = Math.max(diameter, currentPathLength);
			// suma
			sum += currentPathLength;
			// rozklad
			if (distribution.get(currentPathLength) != null) {
				// jesli juz byl
				distribution.put(currentPathLength, distribution.get(currentPathLength) + 1);
			} else {
				distribution.put(currentPathLength, 1);
			}
		}
		average = sum / (graph.getNodes().length - 1);
	}

	/**
	 * Zwraca srednice
	 * @return
	 */
	public int getDiameter() {
		return diameter;
	}

	/**
	 * Zwraca srednia dlugosc sciezki
	 * @return
	 */
	public double getAverage() {
		return average;
	}

	/**
	 * Zwraca rozklad
	 * @return
	 */
	public Map<Integer, Integer> getDistribution() {
		return distribution;
	}

	@Override
	public String toString() {System.out.println(Arrays.toString(pathLengths));
		return average + ";" + diameter;
	}

	

}
