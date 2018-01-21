package org.chpr.players.artificial;

import org.chpr.functions.fitness.SimpleFitness;
import org.chpr.players.AbstractPlayer;


public class AlphaBetaPlayer extends AbstractPlayer {

	public AlphaBetaPlayer() {
		thinkerClazz = AlphaBetaThinker.class;
		fitnessFn = new SimpleFitness();
	}
}
