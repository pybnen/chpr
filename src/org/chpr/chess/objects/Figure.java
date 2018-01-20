package org.chpr.chess.objects;

import org.chpr.chess.IBoard;
import org.chpr.chess.utils.BoardUtils;

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
		List<Move> moves = new ArrayList<>();
		short[][] figures = board.getFigures();
		short figureIndex = figures[col][row];
		int color = getColor(figureIndex);
		
		if (figureIndex != 0) {
			int figureType = getType(figureIndex);
			int figureColor = getColor(figureIndex);

			if (figureType == PAWN) {
				int dir = (figureColor == WHITE ? 1 : -1);
				int targetRow = row + dir;
				if (isFree(board, col, targetRow)) {
					if (targetRow == rankRow(8, figureColor)) {
						// add promotions
						moves.add(new Move(board, figureColor, QUEEN, col, row, col, targetRow, true, false));
						moves.add(new Move(board, figureColor, ROOK, col, row, col, targetRow, true, false));
						moves.add(new Move(board, figureColor, BISHOP, col, row, col, targetRow, true, false));
						moves.add(new Move(board, figureColor, KNIGHT, col, row, col, targetRow, true, false));
					} else {
						moves.add(new Move(board, figureColor, figureType, col, row, col, targetRow, false, false));

						if (row == rankRow(2, figureColor) && isFree(board, col, targetRow + dir)) {
							moves.add(new Move(board, figureColor, figureType, col, row, col, targetRow + dir, false, false));
						}
					}
				}
				// check for hit
				int[] colDirs = {-1, 1};
				for (int colDir : colDirs) {
					int targetCol = col + colDir;
					if (isValidDestination(board, figureColor, targetCol, targetRow) && !isFree(board, targetCol, targetRow)) {
						if (targetRow == rankRow(8, figureColor)) {
							moves.add(new Move(board, figureColor, QUEEN, col, row, targetCol, targetRow, true, true));
							moves.add(new Move(board, figureColor, ROOK, col, row, targetCol, targetRow, true, true));
							moves.add(new Move(board, figureColor, BISHOP, col, row, targetCol, targetRow, true, true));
							moves.add(new Move(board, figureColor, KNIGHT, col, row, targetCol, targetRow, true, true));
						} else {
							moves.add(new Move(board, figureColor, figureType, col, row, targetCol, targetRow, false, true));
						}
					}
				}
				// check en passant
				if (board.getHistory().size() > 0) {
					Move prevMove = board.getHistory().get(board.getHistory().size() - 1);
					if (prevMove.getType() == PAWN &&
							Math.abs(prevMove.getDestRow() - prevMove.getSourceRow()) == 2 &&
							(prevMove.getDestCol() == col - 1 || prevMove.getDestCol() == col + 1) &&
							row == rankRow(5, figureColor)) {
						moves.add(new Move(board, figureColor, figureType, col, row, prevMove.getDestCol(), targetRow, false, true));
					}
				}
			}

			if (figureType == ROOK || figureType == QUEEN) {
				int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
				moves.addAll(searchValidMoves(board, col, row, figureType, figureColor, directions));
			}

			if (figureType == KNIGHT) {
				int[][] deltas = {{-1, 2}, {1, 2},
						{-1, -2}, {1, -2},
						{-2, -1}, {-2, 1},
						{2, -1}, {2, 1}};
				for (int[] delta : deltas) {
					int colDelta = delta[0];
					int rowDelta = delta[1];
					if (isValidDestination(board, figureColor, col + colDelta, row + rowDelta)) {
						boolean hit = !isFree(board, col + colDelta, row + rowDelta);
						moves.add(new Move(board, figureColor, figureType, col, row, col + colDelta, row + rowDelta, false, hit));
					}
				}
			}

			if (figureType == BISHOP || figureType == QUEEN){
				int[][] directions = {{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
				moves.addAll(searchValidMoves(board, col, row, figureType, figureColor, directions));
			}

			if (figureType == KING) {
				int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
				for (int[] direction : directions) {
					int destCol = col + direction[0];
					int destRow = row + direction[1];
					if (isValidDestination(board, figureColor, destCol, destRow)) {
						boolean hit = !isFree(board, destCol, destRow);
						moves.add(new Move(board, figureColor, figureType, col, row, destCol, destRow, false, hit));
					}
				}
				if (figureColor == WHITE && board.canWhiteCastle() && col == 4 && row == 0) {
					short kingsideRook = figures[7][0];
					short queensideRook = figures[0][0];
					if (board.canWhiteCastleKingside()) {
						if (isFree(board, 5, 0) && isFree(board, 6, 0) && getType(kingsideRook) == ROOK && getColor(kingsideRook) == WHITE) {
							moves.add(new Move(board, figureColor, figureType, col, row, 6, 0, false, false));
						}
					}
					if (board.canWhiteCastleQueenside()) {
						if (isFree(board, 2, 0) && isFree(board, 3, 0) && getType(queensideRook) == ROOK && getColor(queensideRook) == WHITE) {
							moves.add(new Move(board, figureColor, figureType, col, row, 2, 0, false, false));
						}
					}
				}
				if (figureColor == BLACK && board.canBlackCastle() && col == 4 && row == 7) {
					short kingsideRook = figures[7][7];
					short queensideRook = figures[0][7];
					if (board.canBlackCastleKingside()) {
						if (isFree(board, 5, 7) && isFree(board, 6, 7) && getType(kingsideRook) == ROOK && getColor(kingsideRook) == BLACK) {
							moves.add(new Move(board, figureColor, figureType, col, row, 6, 7, false, false));
						}
					}
					if (board.canBlackCastleQueenside()) {
						if (isFree(board, 2, 7) && isFree(board, 3, 7) && getType(queensideRook) == ROOK && getColor(queensideRook) == BLACK) {
							moves.add(new Move(board, figureColor, figureType, col, row, 2, 7, false, false));
						}
					}
				}
			}
		}
		// remove moves that leave the color in check
		List<Move> movesWithoutLosingKing = new ArrayList<Move>();
		for (Move move : moves) {
			IBoard tmp = board.cloneIncompletely();
			tmp.executeMove(move);
			if (!tmp.isCheck(color))
				movesWithoutLosingKing.add(move);
		}
		
		return movesWithoutLosingKing;
	}

	private static List<Move> searchValidMoves(IBoard board, int col, int row, int figureType, int figureColor, int[][] directions) {
		List<Move> moves = new ArrayList<>();
		for (int[] direction : directions) {
			int colDir = direction[0];
			int rowDir = direction[1];

			int c = col;
			int r = row;
			boolean goOn = true;
			while (goOn) {
				c += colDir;
				r += rowDir;
				if (isValidDestination(board, figureColor, c, r)) {
					boolean hit = false;
					if (!isFree(board, c, r)) {
						hit = true;
						goOn = false;
					}
					moves.add(new Move(board, figureColor, figureType, col, row, c, r, false, hit));
				} else {
					goOn = false;
				}
			}
		}
		return moves;
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
		return fig == 0;
	}

	static public List<Move> getSafeMoves(IBoard board, List<Move> moves) {
		List<Move> safeMoves = new ArrayList<>();
		for (Move move : moves) {
			IBoard b = board.cloneIncompletely();
			b.executeMove(move);
			List<Move> nextMoves = b.getValidMoves(BoardUtils.FlipColor(move.getColor()));
			boolean isSafe = true;
			for (Move nextMove : nextMoves) {
				if (hitsKing(b, nextMove)) {
					isSafe = false;
					break;
				}
			}
			if (isSafe) {
				safeMoves.add(move);
			}
		}
		return safeMoves;
	}

	static public boolean hitsKing(IBoard board, Move m) {
		if (m.isHit()) {
			return getType(board.getFigures()[m.getDestCol()][m.getDestRow()]) == KING;
		}
		return false;
	}

	/**
	 * Return row index of rank from player (=color) perspective,
	 * from https://en.wikipedia.org/wiki/Glossary_of_chess#rank:
	 * > A row of the chessboard. In algebraic notation,
	 * > ranks are numbered 1–8 starting from White's side of the board;
	 * > however, players customarily refer to ranks from their own perspectives.
	 * > For example: White's king and other pieces start on his or her first (or "back") rank,
	 * > whereas Black calls the same rank the eighth rank; White's seventh rank is Black's second;
	 * > and so on.
	 * > If neither perspective is given, White's view is assumed.
	 *
	 * @param rank rank for which the row index should be returned
	 * @param color player perspective
	 * @return row index for rank from player perspective
	 */
	static private int rankRow(int rank, int color) {
		int row = rank - 1;
		if (color == BLACK) {
			return 7 - row;
		}
		return row;
	}
}
