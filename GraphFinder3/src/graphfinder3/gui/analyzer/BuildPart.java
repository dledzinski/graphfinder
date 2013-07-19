/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.gui.analyzer;

import graphfinder3.data.BuildRule;
import graphfinder3.data.Graph;
import graphfinder3.graphbuilding.Builder;
import java.util.Collections;
import java.util.Set;

/**
 * Klasa reprezentuje kawalek budowy grafu
 *
 * @author damian
 */
public class BuildPart {

	private final Graph parrentGraph;
	private final BuildRule buildRule;
	private final Builder builder;
	private int buildParam = -1;
	private Graph currentGraph = null;

	/**
	 * Tworzy kawalek budowy
	 *
	 * @param parrentGraph graf od ktorego nalezy zaczynac
	 * @param buildRule regula budowy
	 */
	public BuildPart(Graph parrentGraph, BuildRule buildRule) {
		this.parrentGraph = parrentGraph;
		this.buildRule = buildRule;
		this.builder = Builder.getBuilder(buildRule, parrentGraph);
	}

	/**
	 * Zwraca liste dopuszczalnych parametrow budowy
	 *
	 * @return
	 */
	public Set<Integer> getValidParams() {
		if (builder != null) {
		return builder.getValidParams();
		}
		return Collections.EMPTY_SET;
	}

	/**
	 * Metoda ustawia parametr budowy
	 *
	 * @param buildParam
	 */
	public void setBuildParam(int buildParam) {
		if (builder != null && builder.isValidParam(buildParam)) {
			this.buildParam = buildParam;
			currentGraph = builder.getSubGraph(buildParam);
		} else {
			this.buildParam = -1;
			currentGraph = null;
		}
	}

	/**
	 * Metoda zwraca aktualny graf
	 *
	 * @return graf lub null jesli parametr jest nieprawidlowy
	 */
	public Graph getCurrentGraph() {
		return currentGraph;
	}

	/**
	 * Zwraca regule budowy
	 *
	 * @return
	 */
	public BuildRule getBuildRule() {
		return buildRule;
	}

	/**
	 * Zwraca parametr budowy
	 *
	 * @return
	 */
	public int getBuildParam() {
		return buildParam;
	}
}
