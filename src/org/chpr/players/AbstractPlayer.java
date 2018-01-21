package org.chpr.players;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;

import java.util.Random;
import java.util.function.BiFunction;

import static java.lang.Thread.sleep;

public abstract class AbstractPlayer implements Player {
	protected Class<?> thinkerClazz;
	protected BiFunction<IBoard, Integer, Double> fitnessFn;

	@Override
	public double getFitness(IBoard board, int color) {
		return fitnessFn.apply(board, color);
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		try {
			Thinker thinker = (Thinker)thinkerClazz.getDeclaredConstructor(Player.class, IBoard.class, Integer.class, Random.class).newInstance(this, board, color, random);
			Thread t = new Thread(thinker);
			t.start();
			try {
				sleep(milliSeconds);
			} catch (InterruptedException ignored) {
			}
			t.stop();
			return thinker.getBestMove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
