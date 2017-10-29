package org.chpr.chess.objects;

import org.chpr.chess.IBoard;

import java.util.ArrayList;
import java.util.List;

public class Figure {
	public static final int PAWN = 1;
	public static final int ROOK = 2;
	public static final int KNIGHT = 3;
	public static final int BISHOP = 4;
	public static final int QUEEN = 5;
	public static final int KING = 6;

	public static final String PAWN_STRING = "P";
	public static final String ROOK_STRING = "R";
	public static final String KNIGHT_STRING = "N";
	public static final String BISHOP_STRING = "B";
	public static final String QUEEN_STRING = "Q";
	public static final String KING_STRING = "K";

	public static final int WHITE_OFFSET = 0;
	public static final int BLACK_OFFSET = 10;

	public static final String WHITE_STRING = "w";
	public static final String BLACK_STRING = "b";

	public static int getType(int figureIndex){
		// TODO
		return 0;
	}

	public static int getColor(int figureIndex){
		// TODO
		return 0;
	}

	public static String toString(int figureIndex){
		// TODO
		return null;
	}

	public static String toString(int color, int type){
		// TODO
		return null;
	}

	public static short fromString(String str) {
		// TODO
		return 0;
	}

	static public List<Move> getValidMoves(IBoard board, int col, int row) {
		ArrayList<Move> moves = new ArrayList<Move>();
		short[][] figures = board.getFigures();
		short figureIndex = figures[col][row];

		if (figureIndex != 0) {
			// TODO
			int figureType = 0;
			if (figureType == PAWN) {
				// TODO
			}

			if (figureType == ROOK || figureType == QUEEN) {
				// TODO
			}

			if (figureType == KNIGHT) {
				// TODO
			}

			if(figureType == BISHOP || figureType == QUEEN){
				// TODO
			}

			if (figureType == KING) {
				// TODO
			}
		}
		return moves;
	}

	static private boolean isValidDestination(IBoard board, int color, int col, int row) {
		// TODO
		return false;
	}

	static private boolean isFree(IBoard board, int col, int row) {
		// TODO
		return false;
	}
}
