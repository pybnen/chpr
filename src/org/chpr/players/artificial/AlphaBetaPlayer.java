package org.chpr.players.artificial;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;


public class AlphaBetaPlayer implements Player {

	private Map<Integer, Double> figureValues;
	private static Map<Integer, Double> figureValuesStat;
	private static final double KING_VALUE = 10000.0;

	public AlphaBetaPlayer() {
		figureValues = new HashMap<>();
		figureValues.put(Figure.PAWN, 1.0);
		figureValues.put(Figure.KNIGHT, 3.3);
		figureValues.put(Figure.BISHOP, 3.3);
		figureValues.put(Figure.ROOK, 5.0);
		figureValues.put(Figure.QUEEN, 9.0);
		figureValues.put(Figure.KING, KING_VALUE);
	}
	
	public static double eval0(IBoard board, int color) {
		if (figureValuesStat == null) {
			figureValuesStat = new HashMap<>();
			figureValuesStat.put(Figure.PAWN, 1.0);
			figureValuesStat.put(Figure.KNIGHT, 3.3);
			figureValuesStat.put(Figure.BISHOP, 3.3);
			figureValuesStat.put(Figure.ROOK, 5.0);
			figureValuesStat.put(Figure.QUEEN, 9.0);
			figureValuesStat.put(Figure.KING, KING_VALUE);
			
		}
		double fitness = 0.0;
		short[][] figures = board.getFigures();
		for (int col = 0; col < figures.length; col++) {
			for (int row = 0; row < figures[0].length; row++) {
				short fig = figures[col][row];
				if (fig > 0) {
					int figureType = Figure.getType(fig);
					double value = figureValuesStat.get(figureType);
					if (Figure.getColor(fig) == color) {
						fitness += value;
					} else {
						fitness -= value;
					}
				}
			}
		}
		return fitness;
	}

	@Override
	public double getFitness(IBoard board, int color) {
		double fitness = 0.0;
		short[][] figures = board.getFigures();
		// compare piece values
		for (int col = 0; col < figures.length; col++) {
			for (int row = 0; row < figures[0].length; row++) {
				short fig = figures[col][row];
				if (fig > 0) {
					int figureType = Figure.getType(fig);
					double value = figureValues.get(figureType);
					if (Figure.getColor(fig) == color) {
						fitness += value;
					} else {
						fitness -= value;
					}
				}
			}
		}
		return fitness;
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		AlphaBetaThinker AlphaBetaThinker = new AlphaBetaThinker(this, board, color, random);
		Thread t = new Thread(AlphaBetaThinker);
		t.start();
		try {
			sleep(milliSeconds);
		} catch (InterruptedException ignored) {
		}
		t.stop();
		return AlphaBetaThinker.getBestMove();
	}
}
