package org.chpr.players.artificial;

import org.chpr.functions.fitness.MovementFitness;
import org.chpr.players.AbstractPlayer;


public class EvalMovementPlayer extends AbstractPlayer {

	public EvalMovementPlayer() {
		thinkerClazz = EvalMovementThinker.class;
		fitnessFn = new MovementFitness();
	}
}
