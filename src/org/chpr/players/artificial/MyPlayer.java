package org.chpr.players.artificial;

import org.chpr.functions.fitness.SimpleFitness;
import org.chpr.players.AbstractPlayer;


public class MyPlayer extends AbstractPlayer {

	public MyPlayer() {
		thinkerClazz = MyThinker.class;
		fitnessFn = new SimpleFitness();
	}
}
