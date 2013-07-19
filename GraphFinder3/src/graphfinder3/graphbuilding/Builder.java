/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.graphbuilding;

import graphfinder3.data.BuildRule;
import graphfinder3.data.Graph;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Interfejs klasy budujacej
 *
 * @author damian
 */
public abstract class Builder {

	/**
	 * Graf rodzic
	 */
	protected final Graph parrentGraph;
	/**
	 * Mapa podgrafow, gdzie kluczem jest wartosc parametru wykorzystanego przy
	 * budowie
	 */
	protected final Map<Integer, Graph> subGraphs = new HashMap<Integer, Graph>();

	/**
	 * Tworzy podgrafy
	 *
	 * @param parrentGraph
	 */
	public Builder(Graph parrentGraph) {
		this.parrentGraph = parrentGraph;
	}

	/**
	 * Zwraca podgrafy
	 *
	 * @return mapa gdzie kluczem jest parametr a wartosic graf
	 */
	public Map<Integer, Graph> getSubGraphs() {
		build();
		return subGraphs;
	}

	/**
	 * Zwraca graf utworzony z konkretnego parametru
	 *
	 * @param param
	 * @return
	 */
	public Graph getSubGraph(int param) {
		if (!subGraphs.containsKey(param)) {
			// taki graf nie jest jeszcez utworzony
			if (isValidParam(param)) {
				build(param);
			} else {
				// paramwtr jest nieprawidlowy
				return null;
			}
		}
		return subGraphs.get(param);
	}

	/**
	 * Metoda zwraca dopuszczalne parametry budowy
	 *
	 * @return
	 */
	public abstract Set<Integer> getValidParams();

	/**
	 * Okresla czy dany parametr jest prawidlowy
	 *
	 * @param param parametr
	 * @return
	 */
	public abstract boolean isValidParam(int param);

	/**
	 * Tworzy graf dla okreslonego parametru
	 *
	 * @param param
	 */
	protected abstract void build(int param);

	/**
	 * Tworzy wszystkie grafy
	 */
	protected abstract void build();
	
	/**
	 * Metoda statyczna ktora tworzy builder na podstawie reguly budowy
	 * @param buildRule regula budowy
	 * @param graph graf
	 * @return 
	 */
	public static Builder getBuilder(BuildRule buildRule, Graph graph) {
		Builder builder = null;
		switch (buildRule) {
			case CHAIN:
				builder = new ChainBuilder(graph);
				break;
			case RING:
				builder = new RingBuilder(graph);
				break;
			case NDR:
				builder = new NDRBuilder(graph);
				break;
			case TREE:
				builder = new TreeBuilder(graph);
				break;
			case FULL_TREE:
				builder = new FullTreeBuilder(graph);
				break;
			case FREE:
				builder = new FreeBuilder(graph);
				break;
			case NRFREE:
				builder = new NRFreeBuilder(graph);
				break;
			case LONGESTS:
				builder = new LongestsBuilder(graph);
				break;
			case NDR_CHORD:
				builder = new NdrChordBuilder(graph);
				break;
			case NDR_ECH:
				builder = new NdrEchBuilder(graph);
				break;
			case NDR_OCH:
				builder = new NdrOchBuilder(graph);
				break;

		}
		return builder;
	}
}
