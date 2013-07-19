/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.typedGraph;

import graphfinder2.typedGraph.degree3.*;
import graphfinder2.typedGraph.degree4.*;
import graphfinder2.typedGraph.degree5.*;
import graphfinder2.typedGraph.degree6.*;


/**
 *
 * @author damian
 */
public enum TypedGraphCreators {

	// stopien 3
	CHR3_DIAMETER(Chr3Diameter.getInstance(), Degrees.DEGREE3),
	CHR3_CHORD(Chr3Chord.getInstance(), Degrees.DEGREE3),
	CHR3_ECHOCH(Chr3EchOch.getInstance(), Degrees.DEGREE3),
	CHR3_CH1CH2CH3(Chr3Ch1Ch2Ch3.getInstance(), Degrees.DEGREE3),
	CHR3_CH1CH2CH3CH4(Chr3Ch1Ch2Ch3Ch4.getInstance(), Degrees.DEGREE3),
	CHR3_ECHOCHALTERNATELY(Chr3EchOchAlternately.getInstance(), Degrees.DEGREE3),
	NDR_DIAMETER_CHORD(NdrDiameterChord.getInstance(), Degrees.DEGREE3),
	NDR_DIAMETER_ECHOCH(NdrDiameterEchOch.getInstance(), Degrees.DEGREE3),
	NDR_CHORD_CHORD(NdrChordChord.getInstance(), Degrees.DEGREE3),
	NDR_CHORD_ECHOCH(NdrChordEchOch.getInstance(), Degrees.DEGREE3),
	NDR_ECHOCH_ECHOCH(NdrEchOchEchOch.getInstance(), Degrees.DEGREE3),
	NDR_HAMILTON(NdrHamIlton.getInstance(), Degrees.DEGREE3),
	NDR_EHAMOHAM(NdrEhamOham.getInstance(), Degrees.DEGREE3),
	NDR_EHAMODIV(NdrEhamOdiv.getInstance(), Degrees.DEGREE3),
	NDR_DIVISIBLE(NdrDivisible.getInstance(), Degrees.DEGREE3),
	NDR_EDIVODIV(NdrEdivOdiv.getInstance(), Degrees.DEGREE3),
	// stopien 4
	CHR4_DIAMETER_CHORD(Chr4DiameterChord.getInstance(), Degrees.DEGREE4),
	CHR4_DIAMETER_ECHOCH(Chr4DiameterEchOch.getInstance(), Degrees.DEGREE4),
	CHR4_CHORD_CHORD(Chr4ChordChord.getInstance(), Degrees.DEGREE4),
	CHR4_CHORD_ECHOCH(Chr4ChordEchOch.getInstance(), Degrees.DEGREE4),
	CHR4_ECHOCH_ECHOCH(Chr4EchOchEchOch.getInstance(), Degrees.DEGREE4),
	CHR4_HAMILTON(Chr4Hamolton.getInstance(), Degrees.DEGREE4),
	CHR4_EHAMOHAM(Chr4EhamOham.getInstance(), Degrees.DEGREE4),
	CHR4_H1H2H3(Chr4H1H2H3.getInstance(), Degrees.DEGREE4),
	CHR4_H1H2H3H4(Chr4H1H2H3H4.getInstance(), Degrees.DEGREE4),
	CHR4_EHAMODIV(Chr4EhamOdiv.getInstance(), Degrees.DEGREE4),
	CHR4_DIVISIBLE(Chr4Divisible.getInstance(), Degrees.DEGREE4),
	CHR4_EDIVODIV(Chr4EdivOdiv.getInstance(), Degrees.DEGREE4),
	CHR4_D1D2D3(Chr4D1D2D3.getInstance(), Degrees.DEGREE4),
	CHR4_D1D2D3D4(Chr4D1D2D3D4.getInstance(), Degrees.DEGREE4),
	// stopien 5
	CHR5_DIAMETER_CHORD_CHORD(Chr5DiameterChordChord.getInstance(), Degrees.DEGREE5),
	CHR5_DIAMETER_CHORD_ECHOCH(Chr5DiameterChordEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_DIAMETER_ECHOCH_ECHOCH(Chr5DiameterEchOchEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_CHORD_CHORD_CHORD(Chr5ChordChordChord.getInstance(), Degrees.DEGREE5),
	CHR5_CHORD_CHORD_ECHOCH(Chr5ChordChordEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_HAMILTON_DIAMETER(Chr5HamiltonDiameter.getInstance(), Degrees.DEGREE5),
	CHR5_HAMILTON_CHORD(Chr5HamiltonChord.getInstance(), Degrees.DEGREE5),
	CHR5_HAMILTON_ECHOCH(Chr5HamiltonEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMOHAM_DIAMETER(Chr5EhamOhamDiameter.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMOHAM_CHORD(Chr5EhamOhamChord.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMOHAM_ECHOCH(Chr5EhamOhamEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMODIV_DIAMETER(Chr5EhamOdivDiameter.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMODIV_CHORD(Chr5EhamOdivChord.getInstance(), Degrees.DEGREE5),
	CHR5_EHAMODIV_ECHOCH(Chr5EhamOdivEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_DIVISIBLE_DIAMETER(Chr5DivisibleDiameter.getInstance(), Degrees.DEGREE5),
	CHR5_DIVISIBLE_CHORD(Chr5DivisibleChord.getInstance(), Degrees.DEGREE5),
	CHR5_DIVISIBLE_ECHOCH(Chr5DivisibleEchOch.getInstance(), Degrees.DEGREE5),
	CHR5_EDIVODIV_DIAMETER(Chr5EdivOdivDiameter.getInstance(), Degrees.DEGREE5),
	CHR5_EDIVODIV_CHORD(Chr5EdivOdivChord.getInstance(), Degrees.DEGREE5),
	CHR5_EDIVODIV_ECHOCH(Chr5EdivOdivEchOch.getInstance(), Degrees.DEGREE5),
	// stopien 6
	CHR6_HAMILTON_DIAMETER_CHORD(Chr6HamoltonDiameterChord.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_DIAMETER_ECHOCH(Chr6HamoltonDiameterEchOch.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_CHORD_CHORD(Chr6HamoltonChordChord.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_HAMILTON(Chr6HamoltonHamilton.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_EHAMOHAM(Chr6HamoltonEhamOham.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_DIVISIBLE(Chr6HamoltonDivisible.getInstance(), Degrees.DEGREE6),
	CHR6_HAMILTON_EDIVODIV(Chr6HamoltonEdivOdiv.getInstance(), Degrees.DEGREE6),
	CHR6_EHAMOHAM_DIAMETER_CHORD(Chr6EhamOhamDiameterChord.getInstance(), Degrees.DEGREE6),
	CHR6_EHAMOHAM_DIAMETER_ECHOCH(Chr6EhamOhamDiameterEchOch.getInstance(), Degrees.DEGREE6),
	CHR6_EHAMOHAM_CHORD_CHORD(Chr6EhamOhamChordChord.getInstance(), Degrees.DEGREE6),
	CHR6_EHAMOHAM_EHAMOHAM(Chr6EhamOhamEhamOham.getInstance(), Degrees.DEGREE6),
	CHR6_EHAMOHAM_EDIVODIV(Chr6EhamOhamEdivOdiv.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_DIAMETER_CHORD(Chr6DivisibleDiameterChord.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_DIAMETER_ECHOCH(Chr6DivisibleDiameterEchOch.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_CHORD_CHORD(Chr6DivisibleChordChord.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_EHAMOHAM(Chr6DivisibleEhamOham.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_DIVISIBLE(Chr6DivisibleDivisible.getInstance(), Degrees.DEGREE6),
	CHR6_DIVISIBLE_EDIVODIV(Chr6DivisibleEdivOdiv.getInstance(), Degrees.DEGREE6),
	CHR6_EDIVODIV_DIAMETER_CHORD(Chr6EdivOdivDiameterChord.getInstance(), Degrees.DEGREE6),
	CHR6_EDIVODIV_DIAMETER_ECHOCH(Chr6EdivOdivDiameterEchOch.getInstance(), Degrees.DEGREE6),
	CHR6_EDIVODIV_CHORD_CHORD(Chr6EdivOdivChordChord.getInstance(), Degrees.DEGREE6),
	CHR6_EDIVODIV_EDIVODIV(Chr6EdivOdivEdivOdiv.getInstance(), Degrees.DEGREE6),
	;
	// kreator
	private final TypedGraphCreator creator;
	// stopien
	private final Degrees stage;

	private TypedGraphCreators(TypedGraphCreator creator, Degrees stage) {
		this.creator = creator;
		this.stage = stage;
	}

	public TypedGraphCreator getCreator() {
		return creator;
	}

	public Degrees getStage() {
		return stage;
	}
}
