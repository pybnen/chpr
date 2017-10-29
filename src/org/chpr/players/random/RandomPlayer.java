package org.chpr.players.random;


import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;
import org.chpr.players.Player;

import java.util.Random;

public class RandomPlayer implements Player {


	@Override
	public double getFitness(IBoard board, int color) {
		return 0;
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		return null;
	}
}
