package org.chpr.players;

import org.chpr.chess.objects.Move;

public interface Thinker extends Runnable {
	Move getBestMove();
}
