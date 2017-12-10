package org.chpr.players;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;

public interface Player {

	/**
	 * Evaluate position of board for given color
	 *
	 * @param board board to evaluate
	 * @param color color for which to evaluate
	 * @return fitness value of position
	 */
	double getFitness(IBoard board, int color);

	/**
	 * Choose a move for current position and color
	 *
	 * @param board current position
	 * @param color color that should move
	 * @param milliSeconds
	 * @param random
	 * @return chosen move
	 */
	Move chooseMove(IBoard board, int color, int milliSeconds, java.util.Random random);
}
