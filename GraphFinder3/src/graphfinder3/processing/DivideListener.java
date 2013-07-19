/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.processing;

import graphfinder3.data.Problem;

/**
 * Interfejs sluchacza dividera, kiedy divider generuje problemy, wywoluje metode
 * @author damian
 */
public interface DivideListener {
	
	public void newSubProblem(Problem subProblem);
	
}
