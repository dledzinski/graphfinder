/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.graph;

import java.util.Arrays;

/**
 *
 * @author damian
 */
public class NdrGraph extends Graph {

	/**
	 *
	 * @param graph
	 */
	public NdrGraph(int nodeNumber) {
		super(nodeNumber);
		createRings();
	}

	/**
	 * Metoda zwraca numer wezla po skoku o okreslonej dlugosc na zewnetrznym ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
//	public int skipExternal(int index, int skipLength) {
//		return (index + nodes.length + skipLength * 2) % nodes.length;
//	}
	/**
	 * Metoda zwraca numer wezla po skoku o okreslonej dlugosc na zewnetrznym ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
	public int skipExternal(int index, int skipLength) {
		return (index + skipLength + nodes.length) % (nodes.length / 2);
	}

	/**
	 * Metoda zwraca numer wezla po skoku o okreslonej dlugosc na wewnwtrznym ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
//	public int skipInternal(int index, int skipLength) {
//		return (index + nodes.length + skipLength * 2) % nodes.length;
//	}
	/**
	 * Metoda zwraca numer wezla po skoku o okreslonej dlugosc na wewnwtrznym ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
	public int skipInternal(int index, int skipLength) {
		return (index + skipLength + nodes.length) % (nodes.length / 2) + (nodes.length / 2);
	}

	/**
	 * Metoda zwraca numer wezla po skoku do drugiego ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
//	public int skipBetween(int index) {
//		// jesli parzysty to zewnetrzny
//		if (index % 2 == 0) {
//			return (index + nodes.length + 1) % nodes.length;
//		} else {
//			return (index + nodes.length - 1) % nodes.length;
//		}
//	}
	/**
	 * Metoda zwraca numer wezla po skoku do drugiego ringu
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
	public int skipBetween(int index) {
		// jesli parzysty to zewnetrzny
		return (index + (nodes.length / 2)) % nodes.length;
	}

	/**
	 * Tworzy polaczenia ringu
	 */
//	public void createRings() {
//		for (int i = 0; i < nodes.length; i += 2) {
//			nodes[i].addNeighbour(skipExternal(i, -1));
//			nodes[i].addNeighbour(skipExternal(i, 1));
//			int internalNodeIndex = skipBetween(i);
//			nodes[i].addNeighbour(internalNodeIndex);
//			nodes[internalNodeIndex].addNeighbour(i);
//		}
//	}
	/**
	 * Tworzy polaczenia ringu
	 */
	public void createRings() {
		for (int i = 0; i < (nodes.length / 2); i++) {
			nodes[i].addNeighbour(skipExternal(i, -1));
			nodes[i].addNeighbour(skipExternal(i, 1));
			int internalNodeIndex = skipBetween(i);
			nodes[i].addNeighbour(internalNodeIndex);
			nodes[internalNodeIndex].addNeighbour(i);
		}
	}

	/**
	 * Metoda tworzy cieciwy
	 * @param skipLength dlugosc cieciwy
	 * @param graphComplexity zlozonosc - z ilu miejsc zaczynamy niezaleznie
	 * @param startNodeIndex poczatek
	 */
//	public void createChord(int skipLength, int graphComplexity, int startNodeIndex) {
//		//
//		for (int i = startNodeIndex; i < nodes.length; i += (2 * graphComplexity * 2)) {
//			// wyluskiwanie sasiada
//			int neighbour = skipInternal(i, skipLength);
//			nodes[i].addNeighbour(neighbour);
//			nodes[neighbour].addNeighbour(i);
//		}
//	}
	/**
	 * Metoda tworzy cieciwy
	 * @param skipLength dlugosc cieciwy
	 * @param graphComplexity zlozonosc - z ilu miejsc zaczynamy niezaleznie
	 * @param startNodeIndex poczatek
	 */
	public void createChord(int skipLength, int graphComplexity, int startNodeIndex) {
		// zrownywanie
		startNodeIndex = startNodeIndex % (nodes.length / 2) + (nodes.length / 2);
		//
		for (int i = startNodeIndex; i < nodes.length; i += (2 * graphComplexity)) {
			// wyluskiwanie sasiada
			int neighbour = skipInternal(i, skipLength);
			nodes[i].addNeighbour(neighbour);
			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Tworzy srednice
	 */
//	public void createDiameter() {
//		//
//		for (int i = 1; i < nodes.length / 2; i += 2) {
//			// wyluskiwanie sasiada
//			int neighbour = skipInternal(i, nodes.length / 4);
//			nodes[i].addNeighbour(neighbour);
//			nodes[neighbour].addNeighbour(i);
//		}
//	}
	/**
	 * Tworzy srednice
	 */
	public void createDiameter() {
		//
		for (int i = nodes.length / 2; i < nodes.length; i++) {
			// wyluskiwanie sasiada
			int neighbour = skipInternal(i, nodes.length / 4);
			nodes[i].addNeighbour(neighbour);
//			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Metoda tworzy cykl hamiltona
	 * @param skipLength
	 * @param graphComplexity
	 * @param startNodeIndex
	 */
//	public void createHamilton(int skipLength, int graphComplexity, int startNodeIndex) {
//		// petla po wszystkich z danej podgrupy
//		for (int i = startNodeIndex; i < nodes.length; i += (2 * graphComplexity)) {
//			// wyluskiwanie nastepnego wezla
//			int neighbour = skipInternal(i, skipLength);
//			nodes[i].addNeighbour(neighbour);
//			nodes[neighbour].addNeighbour(i);
//		}
//	}
	/**
	 * Metoda tworzy cykl hamiltona
	 * @param skipLength
	 * @param graphComplexity
	 * @param startNodeIndex
	 */
	public void createHamilton(int skipLength, int graphComplexity, int startNodeIndex) {
		// zrownywanie
		startNodeIndex = startNodeIndex % (nodes.length / 2) + (nodes.length / 2);
		// petla po wszystkich z danej podgrupy
		for (int i = startNodeIndex; i < nodes.length; i += graphComplexity) {
			// wyluskiwanie nastepnego wezla
			int neighbour = skipInternal(i, skipLength);
			nodes[i].addNeighbour(neighbour);
			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Metoda tworzy podcylke
	 * @param skipLength
	 * @param graphComplexity
	 * @param startNodeIndex
	 */
	public void createDivisible(int skipLength, int graphComplexity, int startNodeIndex) {
		// zrownywanie
		startNodeIndex = startNodeIndex % (nodes.length / 2) + (nodes.length / 2);
		// petla po punktacg startowych
		for (int i = startNodeIndex; i < (nodes.length / 2) + skipLength; i += graphComplexity) {
			// i jest nowym punktem startowym
			// petla danego okrazenia
			for (int j = i; j < nodes.length; j += skipLength) {
				// wyluskiwanie nastepnego wezla
				int neighbour = skipInternal(j, skipLength);
				nodes[j].addNeighbour(neighbour);
				nodes[neighbour].addNeighbour(j);
			}
		}
	}

	public static boolean isValidChordLength(int nodeNumber, int skipLength, int graphComplexity) {
		if (skipLength > 2 && skipLength <= nodeNumber / 4) {
			if (skipLength % graphComplexity == 0 && (skipLength / graphComplexity) % 2 == 1) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidHamiltonLength(int nodeNumber, int skipLength, int graphComplexity) {
		// sprawdzanie wstepne
		if (skipLength > 1 && skipLength <= nodeNumber / 4) {
			// sprawdzanie czy NWD ilosci wezlow i skoku to maksymalnie 1
			if (skipLength % graphComplexity == 0 && gcd(skipLength / graphComplexity, nodeNumber / (2 * graphComplexity)) == 1) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidDivisibleLength(int nodeNumber, int skipLength, int graphComplexity) {
		// sprawdzanie wstepne
		if (skipLength > 1 && skipLength <= nodeNumber / 4) {
			if (skipLength % graphComplexity == 0) {
				return true;
			}
		}
		return false;
	}
}
