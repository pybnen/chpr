package org.chpr.chess.utils;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.artificial.UberPlayer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardUtils {

	private static final int MOVELIST_ROWS = 4;
	private static Map<Integer, Double> figureValuesStat;
	private static final double KING_VALUE = 10000.0;

	public static int FlipColor(int color) {
		return color == 0 ? 1 : 0;
	}

	public static String ColorToString(int color) {
		return color == 0 ? "white" : "black";
	}

	public static String ColumnName(int index) {
		return null;
	}

	public static String formatMovesList(List<Move> moves) {
		StringBuilder sb = new StringBuilder();

		int size = moves.size();
		int movelist_cols = (int)Math.ceil((float)size / MOVELIST_ROWS);
		for (int row = 0; row < MOVELIST_ROWS; row++) {
			int moveIdx = row;
			for (int col = 0; col < movelist_cols; col++) {
				if (moveIdx < size) {
					sb.append(String.format("%02d. %s\t\t", moveIdx + 1, moves.get(moveIdx)));
				}
				moveIdx += MOVELIST_ROWS;
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static boolean isIndex(String s) {
		return s != null && s.matches("\\d+");
	}
	
	public static Comparator<Move> moveComp = new Comparator<Move>() {

		@Override
		public int compare(Move o1, Move o2) {
			IBoard b1 = o1.getBoard().cloneIncompletely();
			IBoard b2 = o2.getBoard().cloneIncompletely();
			b1.executeMove(o1);
			b2.executeMove(o2);
			double eval1 = eval0(b1, o1.getColor());
			double eval2 = eval0(b2, o2.getColor());
			return Double.compare(eval1, eval2);
			
		}
		
	};


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
}
