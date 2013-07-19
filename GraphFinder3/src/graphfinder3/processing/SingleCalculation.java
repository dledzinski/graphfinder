/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.Graph;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Obiekt obliczen z pojedynczego wezla
 *
 * @author damian
 */
public class SingleCalculation {

	// promien grafu
	private int radius;
	// srednia dlugosc sciezek
	private double averagePathLength;
	// wezel zrodlowy
	private final int sourceIndex;
	// dlugosci sciezek
	private final int[] pathLengths;

	public SingleCalculation(Graph graph, int sourceIndex) {
		this.sourceIndex = sourceIndex;
		this.pathLengths = new int[graph.getNodeNumber()];
		// obliczanie
		calculate(graph, sourceIndex);
	}

	/**
	 * Obliczanie promienia i sredniej dlugosci sciezki
	 *
	 * @param graph
	 * @param sourceIndex
	 */
	private void calculate(Graph graph, int sourceIndex) {
		// polaczenia w grafie
		final int[][] connections = graph.getConnections();
		final int degree = graph.getDegree();

		// oznaczanie wezlow jako nieodwiedzonych
		for (int i = 0; i < pathLengths.length; i++) {
			pathLengths[i] = -1;
		}
		// oznaczanie zrodla
		pathLengths[sourceIndex] = 0;
		// kolejka wezlow do przebadania
		final Queue<Integer> fifo = new LinkedList<Integer>();
		fifo.add(sourceIndex);

		// petla wykonywana do czasu az kolejka bedzie pusta
		while (!fifo.isEmpty()) {
			int currentIndex = fifo.poll();
			// przegladanie sasiadow
			for (int i = 0; i < degree; i++) {
				final int neighbourIndex = connections[currentIndex][i];
				// jesli sasiad istnieje
				if (neighbourIndex >= 0) {
					// jesli sasiad jeszcze nie byl obslugiwany
					if (pathLengths[neighbourIndex] < 0) {
						// ustalanie dlugosci sciezki
						pathLengths[neighbourIndex] = pathLengths[currentIndex] + 1;
						// dodawanie wezla do kolejki
						fifo.add(neighbourIndex);
					}
				}
			}
		}

		radius = 0;
		double sum = 0;
		// wyluskiwanie wynikow
		for (int i = 0; i < pathLengths.length; i++) {
//			if (pathLengths[i] < 0) {
//				throw new RuntimeException("Node out of range: " + i + " in graph: " + graph);
//			}
			radius = Math.max(radius, pathLengths[i]);
			sum += pathLengths[i];
		}
		averagePathLength = sum / (pathLengths.length - 1);
	}

	/**
	 * Zwraca srednia dlugosc sciezki
	 *
	 * @return
	 */
	public double getAveragePathLength() {
		return averagePathLength;
	}

	/**
	 * Zwraca promien
	 *
	 * @return
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Zwraca wezel poczatkowy
	 *
	 * @return
	 */
	public int getSourceIndex() {
		return sourceIndex;
	}

	/**
	 * Zwraca dlugosci sciezek
	 *
	 * @return
	 */
	public int[] getPathLengths() {
		return pathLengths;
	}

	/**
	 * Zwraca rozklad
	 *
	 * @return mapa, gdzie klucz to ilosc krokow a wartosc to ilosc wezlow
	 */
	public Map<Integer, Integer> getDistribution() {
		Map<Integer, Integer> distribution = new TreeMap<Integer, Integer>();

		// przejscie po dlugosciach sciezek
		for (int i : pathLengths) {
			// okreslenie czy taka odleglosc juz sie znajduje
			if (distribution.containsKey(i)) {
				// zwiekszenie o 1
				distribution.put(i, distribution.get(i) + 1);
			} else {
				distribution.put(i, 1);
			}
		}
		// usuniecie poczatkowego
		distribution.remove(0);

		return distribution;
	}
}
