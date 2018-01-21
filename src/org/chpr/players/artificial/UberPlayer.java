package org.chpr.players.artificial;

import org.chpr.functions.fitness.MovementFitness;
import org.chpr.players.AbstractPlayer;


public class UberPlayer extends AbstractPlayer {

	public UberPlayer() {
		thinkerClazz = UberThinker.class;
		fitnessFn = new MovementFitness();
	}
}
