package org.chpr.chess;

import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board implements IBoard {
	static private int COLS = 8;
	static private int ROWS = 8;

	private short[][] figures;
	private boolean canWhiteCastleKingside = true;
	private boolean canWhiteCastleQueenside = true;
	private boolean canBlackCastleKingside = true;
	private boolean canBlackCastleQueenside = true;
	private List<Move> history;


	public Board() {
		this.reset();
	}

	@Override
	public short[][] getFigures() {
		return figures;
	}

	@Override
	public void setFigure(int row, int column, short figure) {
		figures[column][row] = figure;
	}

	/**
	 * Set figures of board for better testing
	 *
	 * @param figures new position of board
	 */
	public void setFigures(short[][] figures) {
		this.figures = figures;
	}

	@Override
	public void reset() {
		figures = new short[COLS][ROWS];
		// set white pieces
		figures[0][0] = Figure.ROOK + Figure.WHITE_OFFSET;
		figures[1][0] = Figure.KNIGHT + Figure.WHITE_OFFSET;
		figures[2][0] = Figure.BISHOP + Figure.WHITE_OFFSET;
		figures[3][0] = Figure.QUEEN + Figure.WHITE_OFFSET;
		figures[4][0] = Figure.KING+ Figure.WHITE_OFFSET;
		figures[5][0] = Figure.BISHOP + Figure.WHITE_OFFSET;
		figures[6][0] = Figure.KNIGHT+ Figure.WHITE_OFFSET;
		figures[7][0] = Figure.ROOK + Figure.WHITE_OFFSET;
		for (int col = 0; col < COLS; col++) {
			figures[col][1] = Figure.PAWN + Figure.WHITE_OFFSET;
		}
		// set black pieces
		int lastRow = ROWS - 1;
		figures[0][lastRow] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[1][lastRow] = Figure.KNIGHT + Figure.BLACK_OFFSET;
		figures[2][lastRow] = Figure.BISHOP + Figure.BLACK_OFFSET;
		figures[3][lastRow] = Figure.QUEEN + Figure.BLACK_OFFSET;
		figures[4][lastRow] = Figure.KING+ Figure.BLACK_OFFSET;
		figures[5][lastRow] = Figure.BISHOP + Figure.BLACK_OFFSET;
		figures[6][lastRow] = Figure.KNIGHT+ Figure.BLACK_OFFSET;
		figures[7][lastRow] = Figure.ROOK + Figure.BLACK_OFFSET;
		for (int col = 0; col < COLS; col++) {
			figures[col][lastRow - 1] = Figure.PAWN + Figure.BLACK_OFFSET;
		}
		// reset other properties
		canWhiteCastleKingside = true;
		canWhiteCastleQueenside = true;
		canBlackCastleKingside = true;
		canBlackCastleQueenside = true;
		history = new LinkedList<>();
	}

	@Override
	public IBoard cloneIncompletely() {
		Board clonedBoard = new Board();
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				clonedBoard.figures[col][row] = figures[col][row];
			}
		}
		clonedBoard.canWhiteCastleKingside = canWhiteCastleKingside;
		clonedBoard.canWhiteCastleQueenside = canWhiteCastleQueenside;
		clonedBoard.canBlackCastleKingside = canBlackCastleKingside;
		clonedBoard.canBlackCastleQueenside = canBlackCastleQueenside;
		int historySize = history.size();
		if (historySize > 0) {
			clonedBoard.history.add(history.get(historySize - 1));
		}
		return clonedBoard;
	}

	@Override
	public List<Move> getValidMoves() {
		List<Move> moves = new ArrayList<>();
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				// iterate over every field
				short figure = figures[col][row];
				moves.addAll(Figure.getValidMoves(this, col, row));
			}
		}
		return moves;
	}

	@Override
	public List<Move> getValidMoves(int color) {
		List<Move> moves = new ArrayList<>();
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				// iterate over every field
				short figure = figures[col][row];
				if (Figure.getColor(figure) == color) {
					moves.addAll(Figure.getValidMoves(this, col, row));
				}
			}
		}
		return moves;
	}

	@Override
	public List<Move> getHistory() {
		return history;
	}

	@Override
	public void executeMove(Move move) {
		int srcCol = move.getSourceCol();
		int srcRow = move.getSourceRow();
		int destCol = move.getDestCol();
		int destRow = move.getDestRow();
		short srcFigure = figures[srcCol][srcRow];
		short destFigure = figures[destCol][destRow];
		boolean whiteMove = move.getColor() == Figure.WHITE;

		figures[destCol][destRow] = srcFigure;
		figures[srcCol][srcRow] = 0;

		if (move.isProm()) {
			// promotion
			short newFigure = (short)(move.getType() + (whiteMove ? Figure.WHITE_OFFSET : Figure.BLACK_OFFSET));
			figures[destCol][destRow] = newFigure;
		}
		if (move.getType() == Figure.PAWN && move.isHit() && destFigure == 0) {
			// En passant
			figures[destCol][destRow + (whiteMove ? -1 : 1)] = 0;
		}
		if (move.getType() == Figure.KING ) {
			if (Math.abs(destCol - srcCol) == 2) {
				// castle
				if (whiteMove) {
					if (destCol == 2) {
						figures[0][0] = 0;
						figures[3][0] = Figure.ROOK + Figure.WHITE_OFFSET;
					} else {
						figures[7][0] = 0;
						figures[5][0] = Figure.ROOK + Figure.WHITE_OFFSET;
					}
				} else {
					if (destCol == 2) {
						figures[0][7] = 0;
						figures[3][7] = Figure.ROOK + Figure.BLACK_OFFSET;
					} else {
						figures[7][7] = 0;
						figures[5][7] = Figure.ROOK + Figure.BLACK_OFFSET;
					}
				}
			}
			if (whiteMove) {
				canWhiteCastleQueenside = canWhiteCastleKingside = false;
			} else {
				canBlackCastleQueenside = canBlackCastleKingside = false;
			}
		}

		if (move.getType()== Figure.ROOK) {
			// if rook was moved set can castle options
			if (whiteMove) {
				if (srcCol == 0 && srcRow == 0) canWhiteCastleQueenside = false;
				if (srcCol == 7 && srcRow == 0) canWhiteCastleKingside = false;
			} else {
				if (srcCol == 0 && srcRow == 7) canBlackCastleQueenside = false;
				if (srcCol == 7 && srcRow == 7) canBlackCastleKingside = false;
			}
		}
		history.add(move);
	}

	@Override
	public boolean canWhiteCastle() {
		return canWhiteCastleKingside || canWhiteCastleQueenside;
	}

	@Override
	public boolean canWhiteCastleQueenside() {
		return canWhiteCastleQueenside;
	}

	@Override
	public boolean canWhiteCastleKingside() {
		return canWhiteCastleKingside;
	}

	@Override
	public boolean canBlackCastle() {
		return canBlackCastleKingside || canBlackCastleQueenside;
	}

	@Override
	public boolean canBlackCastleQueenside() {
		return canBlackCastleQueenside;
	}

	@Override
	public boolean canBlackCastleKingside() {
		return canBlackCastleKingside;
	}
	
	@Override
	public boolean isCheck(int color) {
		// look for king
		int col = -1;
		int row = -1;
		for (int i = 0; i < figures.length; i++) {
			for (int j = 0; j < figures[i].length; j++) {
				if (figures[i][j] == (Figure.KING + (color == Figure.WHITE ? Figure.WHITE_OFFSET : Figure.BLACK_OFFSET))) {
					col = i;
					row = j;
				}
			}	
		}
		if (col == -1)
			return false;
		// check if under attack
		// by pawn
		int oppCol = 0;
		int oppRow = 0;
		if (color == Figure.WHITE) {
			oppCol = col + 1;
			oppRow = row + 1;
			if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
				if (figures[oppCol][oppRow] == Figure.PAWN + Figure.BLACK_OFFSET)
					return true;
			}
			oppCol = col - 1;
			oppRow = row + 1;
			if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
				if (figures[oppCol][oppRow] == Figure.PAWN + Figure.BLACK_OFFSET)
					return true;
			}
		}
		else {
			oppCol = col + 1;
			oppRow = row - 1;
			if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
				if (figures[oppCol][oppRow] == Figure.PAWN + Figure.WHITE_OFFSET)
					return true;
			}
			oppCol = col - 1;
			oppRow = row - 1;
			if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
				if (figures[oppCol][oppRow] == Figure.PAWN + Figure.WHITE_OFFSET)
					return true;
			}
		}
		// by knight
		oppCol = col + 1;
		oppRow = row + 2;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col + 2;
		oppRow = row + 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		
		oppCol = col + 1;
		oppRow = row - 2;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col + 2;
		oppRow = row - 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		
		oppCol = col - 1;
		oppRow = row + 2;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col - 2;
		oppRow = row + 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		
		oppCol = col - 1;
		oppRow = row - 2;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col - 2;
		oppRow = row - 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KNIGHT + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		// by bishop or queen
		int curCol = col - 1;
		int curRow = row - 1;
		while (curCol >= 0 && curRow >= 0) {
			if (figures[curCol][curRow] == 0) {
				curCol--;
				curRow--;
				continue;
			}
			if (figures[curCol][curRow] == Figure.BISHOP + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
			
		}
		curCol = col - 1;
		curRow = row + 1;
		while (curCol >= 0 && curRow <= 7) {
			if (figures[curCol][curRow] == 0) {
				curCol--;
				curRow++;
				continue;
			}
			if (figures[curCol][curRow] == Figure.BISHOP + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		curCol = col + 1;
		curRow = row - 1;
		while (curCol <= 7 && curRow >= 0) {
			if (figures[curCol][curRow] == 0) {
				curCol++;
				curRow--;
				continue;
			}
			if (figures[curCol][curRow] == Figure.BISHOP + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		curCol = col + 1;
		curRow = row + 1;
		while (curCol <= 7 && curRow <= 7) {
			if (figures[curCol][curRow] == 0) {
				curCol++;
				curRow++;
				continue;
			}
			if (figures[curCol][curRow] == Figure.BISHOP + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		
		// by rook or queen
		curCol = col - 1;
		curRow = row;
		while (curCol >= 0) {
			if (figures[curCol][curRow] == 0) {
				curCol--;
				continue;
			}
			if (figures[curCol][curRow] == Figure.ROOK + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
			
		}
		curCol = col + 1;
		curRow = row;
		while (curCol <= 7) {
			if (figures[curCol][curRow] == 0) {
				curCol++;
				continue;
			}
			if (figures[curCol][curRow] == Figure.ROOK + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		curCol = col;
		curRow = row - 1;
		while (curRow >= 0) {
			if (figures[curCol][curRow] == 0) {
				curRow--;
				continue;
			}
			if (figures[curCol][curRow] == Figure.ROOK + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		curRow = row + 1;
		while (curRow <= 7) {
			if (figures[curCol][curRow] == 0) {
				curRow++;
				continue;
			}
			if (figures[curCol][curRow] == Figure.ROOK + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET) || figures[curCol][curRow] == Figure.QUEEN + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
			else
				break;
		}
		
		// by king
		oppCol = col + 1;
		oppRow = row;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col + 1;
		oppRow = row + 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		
		oppCol = col + 1;
		oppRow = row - 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col - 1;
		oppRow = row;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col - 1;
		oppRow = row + 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col;
		oppRow = row + 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		oppCol = col;
		oppRow = row - 1;
		if (oppCol >= 0 && oppCol <= 7 && oppRow >= 0 && oppRow <= 7) {
			if (figures[oppCol][oppRow] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET))
				return true;
		}
		return false;
	}

	@Override
	public boolean isMat(int color) {
		return getValidMoves(color).isEmpty() && isCheck(color);
	}

	@Override
	public String toString() {
		String frame = "  +----+----+----+----+----+----+----+----+\n";
		StringBuilder sb = new StringBuilder();
		sb.append("    a    b    c    d    e    f    g    h\n");
		for (int row = ROWS - 1; row >= 0; row--) {
			sb.append(frame);
			sb.append(row + 1);sb.append(" ");
			for (int col = 0; col < COLS; col++) {
				sb.append("| " + Figure.toString(figures[col][row]) + " ");
			}
			sb.append("| " + (row + 1) + "\n");
		}
		sb.append(frame);
		sb.append("    a    b    c    d    e    f    g    h");
		return sb.toString();
	}
}
