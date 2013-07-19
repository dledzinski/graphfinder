/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3;

import java.util.List;
import org.uncommons.maths.combinatorics.PermutationGenerator;

/**
 *
 * @author damian
 */
public class PermutationTest {
	
	public static void main(String[] args) {
		
		PermutationGenerator<Integer> generator = new PermutationGenerator<Integer>(new Integer[] {1,2,3,4});
		for (List<Integer> list : generator) {
			System.out.println(list);
		}
		
	}
	
}
