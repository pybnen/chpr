package org.chpr.chess.utils;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.artificial.UberPlayer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BoardUtils {

	private static final int MOVELIST_ROWS = 4;

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
			double eval1 = UberPlayer.eval0(b1, o1.getColor());
			double eval2 = UberPlayer.eval0(b2, o2.getColor());
			return Double.compare(eval1, eval2);
			
		}
		
	};
}
