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

	public static final int WHITE = 0;
	public static final int BLACK = 1;

	public static final String[] ARR_TYPE_STRING = {PAWN_STRING, ROOK_STRING, KNIGHT_STRING, BISHOP_STRING, QUEEN_STRING, KING_STRING};

	public static int getType(int figureIndex) {
		return figureIndex % 10;
	}

	public static int getColor(int figureIndex) {
		return figureIndex / 10;
	}

	public static String toString(int figureIndex) {
		return toString(getColor(figureIndex), getType(figureIndex));
	}

	public static String toString(int color, int type) {
		if (type == 0) {
			return "  ";
		}
		String ret = "";
		if (color == WHITE)
			ret = ret.concat(WHITE_STRING);
		else if (color == BLACK)
			ret = ret.concat(BLACK_STRING);
		ret = ret.concat(ARR_TYPE_STRING[type - 1]);
		return ret;
	}

	public static short fromString(String str) {
		short ret = 0;
		if (str.startsWith(WHITE_STRING)) {
			ret += WHITE_OFFSET;
			str = str.substring(1);
		}
		else if (str.startsWith(BLACK_STRING)) {
			ret += BLACK_OFFSET;
			str = str.substring(1);
		}
		for (int i = 0; i < ARR_TYPE_STRING.length; i++)
			if (str.equals(ARR_TYPE_STRING[i]))
				return (short)(ret + i + 1);
		return -1;
	}

	static public List<Move> getValidMoves(IBoard board, int col, int row) {
		//TODO: Implement in exercise 2
		return null;
	}

	static private boolean isValidDestination(IBoard board, int color, int col, int row) {
		if (col < 0 || col > 7)
			return false;
		if (row < 0 || row > 7)
			return false;
		short[][] figures = board.getFigures();
		short fig = figures[col][row];
		if (color == WHITE && fig > 0 && fig < BLACK_OFFSET)
			return false;
		if (color == BLACK && fig > BLACK_OFFSET)
			return false;
		return true;
	}

	static private boolean isFree(IBoard board, int col, int row) {
		if (col < 0 || col > 7)
			return false;
		if (row < 0 || row > 7)
			return false;
		short[][] figures = board.getFigures();
		short fig = figures[col][row];
		if (fig == 0)
			return true;
		return false;
	}
}
