/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.graph;

/**
 * Klasa do modyfikaci grafu opartego na pierscieniu
 *
 * @author damian
 */
public class RingGraph extends Graph {

	/**
	 *
	 * @param graph
	 */
	public RingGraph(int nodeNumber) {
		super(nodeNumber);
		createRing();
	}

	/**
	 * Zwraca numer poprzedniego wezla
	 *
	 * @param index
	 * @return
	 */
	public int previous(int index) {
		return skip(index, -1);
	}

	/**
	 * Zwraca numer nastepnego wezla
	 *
	 * @param index
	 * @return
	 */
	public int next(int index) {
		return skip(index, 1);
	}

	/**
	 * Metoda zwraca numer wezla po skoku o okreslona dlugosc
	 *
	 * @param index
	 * @param skipLength dlugosc skoku
	 * @return
	 */
	public int skip(int index, int skipLength) {
		return (index + nodes.length + skipLength) % nodes.length;
	}

	/**
	 * Tworzy polaczenia ringu
	 */
	public void createRing() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].addNeighbour(previous(i));
			nodes[i].addNeighbour(next(i));
		}
	}

	/**
	 * Metoda tworzy cieciwy
	 *
	 * @param skipLength dlugosc cieciwy
	 * @param graphComplexity zlozonosc - z ilu miejsc zaczynamy niezaleznie
	 * @param startNodeIndex poczatek
	 */
	public void createChord(int skipLength, int graphComplexity, int startNodeIndex) {
		// petla co drugi wezel
		for (int i = startNodeIndex; i < nodes.length; i += (2 * graphComplexity)) {
			// wyluskiwanie sasiada
			int neighbour = skip(i, skipLength);
			nodes[i].addNeighbour(neighbour);
			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Tworzy srednice
	 */
	public void createDiameter() {
		//
		for (int i = 0; i < nodes.length / 2; i++) {
			// wyluskiwanie sasiada
			int neighbour = skip(i, nodes.length / 2);
			nodes[i].addNeighbour(neighbour);
			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Metoda tworzy cykl hamiltona
	 *
	 * @param skipLength
	 * @param graphComplexity
	 * @param startNodeIndex
	 */
	public void createHamilton(int skipLength, int graphComplexity, int startNodeIndex) {
		// petla po wszystkich z danej podgrupy
		for (int i = startNodeIndex; i < nodes.length; i += graphComplexity) {
			// wyluskiwanie nastepnego wezla
			int neighbour = skip(i, skipLength);
			nodes[i].addNeighbour(neighbour);
			nodes[neighbour].addNeighbour(i);
		}
	}

	/**
	 * Metoda tworzy podcylke
	 *
	 * @param skipLength
	 * @param graphComplexity
	 * @param startNodeIndex
	 */
	public void createDivisible(int skipLength, int graphComplexity, int startNodeIndex) {
		// petla po punktacg startowych
		for (int i = startNodeIndex; i < skipLength; i += graphComplexity) {
			// i jest nowym punktem startowym
			// petla danego okrazenia
			for (int j = i; j < nodes.length; j += skipLength) {
				// wyluskiwanie nastepnego wezla
				int neighbour = skip(j, skipLength);
				nodes[j].addNeighbour(neighbour);
				nodes[neighbour].addNeighbour(i);
			}
		}
	}

	/**
	 * Okresla czy to prawidlowa dlugosc cieciwy dla danego typu grafu
	 *
	 * @param nodeNumber
	 * @param skipLength
	 * @param graphComplexity
	 * @return
	 */
	public static boolean isValidChordLength(int nodeNumber, int skipLength, int graphComplexity) {
		if (skipLength > 2 && skipLength <= nodeNumber / 2) {
			if (skipLength % graphComplexity == 0 && (skipLength / graphComplexity) % 2 == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Okresla czy to prawidlowa dlugosc cieciwy dla danego typu grafu
	 *
	 * @param nodeNumber
	 * @param skipLength
	 * @param graphComplexity
	 * @return
	 */
	public static boolean isValidAlternatelyChordLength(int nodeNumber, int skipLength, int graphComplexity, int startNodeIndex) {
		if (skipLength > 2 && skipLength <= nodeNumber / 2) {
			if (skipLength % graphComplexity == 1) {
				// rozny wynik w zaleznosci od wezla poczatkowego
				if (((skipLength + startNodeIndex) / graphComplexity) % 2 == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Okresla czy to prawidlowa dlugosc skoku dla cyklu hamiltona
	 *
	 * @param nodeNumber
	 * @param skipLength
	 * @param graphComplexity
	 * @return
	 */
	public static boolean isValidHamiltonLength(int nodeNumber, int skipLength, int graphComplexity) {
		// sprawdzanie wstepne
		if (skipLength > 1 && skipLength <= nodeNumber / 2) {
			// sprawdzanie czy NWD ilosci wezlow i skoku to maksymalnie 1
			if (skipLength % graphComplexity == 0 && gcd(skipLength / graphComplexity, nodeNumber / graphComplexity) == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Okresla czy to prawidlowa dlugosc skoku dla podcykli
	 *
	 * @param nodeNumber
	 * @param skipLength
	 * @param graphComplexity
	 * @return
	 */
	public static boolean isValidDivisibleLength(int nodeNumber, int skipLength, int graphComplexity) {
		// sprawdzanie wstepne
		if (skipLength > 1 && skipLength <= nodeNumber / 2) {
			if (skipLength % graphComplexity == 0) {
				return true;
			}
		}
		return false;
	}
}
