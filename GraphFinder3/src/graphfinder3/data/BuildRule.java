/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder3.data;

/**
 *
 * @author damian
 */
public enum BuildRule {
	/**
	 * Lancuch - laczy kolejne wezly, nie tworzy pierscienia.
	 * Regula powinna wystepowac jako pierwsza
	 */
	CHAIN,
	/**
	 * Pierscien
	 * Regula powinna wystepowac jako pierwsza
	 */
	RING,
	/**
	 * Podwojny pierscien
	 * Regula powinna wystepowac jako pierwsza
	 */
	NDR,
	/**
	 * Drzewo, obejmujace pelne pietrz
	 * Regula powinna wystepowac jako pierwsza
	 */
	TREE,
	/**
	 * Drzewo, obejmujace wszystkie wezly
	 * Regula powinna wystepowac jako pierwsza
	 */
	FULL_TREE,
	HAMILTON,
	CHORD,
	/**
	 * Uzupelnia brakujace polaczenia na zasadzie kazdy z kazdym
	 */
	FREE,
	/**
	 * Uzupelnia brakujace polaczenia na zasadzie kazdy z kazdym bez powtarzania
	 */
	NRFREE,
	/**
	 * Dodaje najdluzsze mozliwe polaczenia
	 */
	LONGESTS,
	// inne
	NDR_CHORD,
	NDR_ECH,
	NDR_OCH,
	/**
	 * Nic nie robi, na potrzeby GUI
	 */
	NONE,		
}
