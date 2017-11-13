// e2-e4 pawn moves from e2 to e4
// e2xf3 pawn moves from e2 to f3 and hits
// Ra1-a8 rook moves from a1 to a8
// a7xb8N pawn hits b8 and gets promoted to knight
// a7-a8Q pawn moves to a8 and gets promoted to queen
// 0-0 king side castle
// 0-0-0 queen side castle
//

package org.chpr.chess.objects;

import org.chpr.chess.IBoard;

import java.util.List;

public class Move {

	private int color;
	private final int type;
	private final int sourceCol;
	private final int sourceRow;
	private final int destCol;
	private final int destRow;
	private int fig;
	private IBoard board;
	private boolean hit;
	private boolean prom;

	public Move(IBoard board, int color, int type, int sourceCol, int sourceRow, int destCol, int destRow, boolean newType) {
		this.color = color;
		this.type = type;
		this.sourceCol = sourceCol;
		this.sourceRow = sourceRow;
		this.destCol = destCol;
		this.destRow = destRow;
		this.board = board;
		this.fig = type + (color == Figure.WHITE ? Figure.WHITE_OFFSET : Figure.BLACK_OFFSET);
		this.hit = false; // will be handled by setHit()
		this.prom = newType;
	}

	public IBoard getBoard() {
		return board;
	}

	public int getFigureIndex() {
		return fig;
	}

	public int getColor() {
		return color;
	}

	public int getType() {
		return type;
	}

	public int getSourceCol() {
		return sourceCol;
	}

	public int getSourceRow() {
		return sourceRow;
	}

	public int getDestCol() {
		return destCol;
	}

	public int getDestRow() {
		return destRow;
	}

	public void setColor(int color) {
		if (this.color != color) {
			if (color == Figure.WHITE)
				fig -= 10;
			else if (color == Figure.BLACK)
				fig += 10;
		}
		this.color = color;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit() {
		hit = true;
	}

	public boolean isProm() {
		return prom;
	}

	@Override
	public String toString() {
		String ret = "";
		if (Figure.getType(fig) == Figure.KING) {
			if (sourceCol == 4 && destCol == 6)
				return "0-0";
			if (sourceCol == 4 && destCol == 2)
				return "0-0-0";
		}
		if (Figure.getType(fig) != Figure.PAWN && !prom) {
			ret = ret.concat(Figure.toString(fig));
		}
		char[] col = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		ret = ret.concat(Character.toString(col[sourceCol]));
		ret = ret.concat(Integer.toString(sourceRow + 1).concat(hit ? "x" : "-"));
		ret = ret.concat(Character.toString(col[destCol]));
		ret = ret.concat(Integer.toString(destRow + 1));
		if (prom)
			ret = ret.concat(Figure.toString(fig));

//		short[][] b = board.getFigures();
//		for (int column = 0; column < b.length; column++) {
//			for (int row = 0; row < b[0].length; row++) {
//				if (b[column][row] == Figure.KING + (color == Figure.WHITE ? Figure.BLACK_OFFSET : Figure.WHITE_OFFSET)) {
//					// TODO check for check, need valid moves for this
//				}
//			}
//		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != Move.class)
			return false;
		Move m = (Move)obj;
		if (color != m.getColor())
			return false;
		if (!board.equals(m.getBoard()))
			return false;
		if (sourceCol != m.getSourceCol())
			return false;
		if (sourceRow != m.getSourceRow())
			return false;
		if (destCol != m.getDestCol())
			return false;
		if (destRow != m.getDestRow())
			return false;
		if (fig != m.getFigureIndex())
			return false;
		// no need to check for hit, because when all is equal, then hit is also equal (when setHit() is used correctly)
		// no need to check for color, its in figureIndex
		// no need to check for type, its in figureIndex (fig)
		// no need to check for promotion, because when all is equal, then promotion is also equal
		return true;
	}

	public static Move Import(String str, IBoard board, int color) {
		if (str.equals("0-0")) {
			int row = (color == Figure.WHITE ? 0 : 7);
			return new Move(board, color, Figure.KING, 4, row, 6, row, false);
		}
		if (str.equals("0-0-0")) {
			int row = (color == Figure.WHITE ? 0 : 7);
			return new Move(board, color, Figure.KING, 4, row, 2, row, false);
		}
		char[] col = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		int figureIndex = 0;
		if (color == Figure.BLACK)
			figureIndex += Figure.BLACK_OFFSET;
		// if str starts with a-h, then its a pawn
		for (char c : col) {
			if (str.startsWith(Character.toString(c))) {
				figureIndex += Figure.PAWN;
				// concat for equal position of cols, rows, ...
				str = "P".concat(str);
				break;
			}
		}
		if (str.startsWith(Figure.ROOK_STRING))
			figureIndex += Figure.ROOK;
		else if (str.startsWith(Figure.KNIGHT_STRING))
			figureIndex += Figure.KNIGHT;
		else if (str.startsWith(Figure.BISHOP_STRING))
			figureIndex += Figure.BISHOP;
		else if (str.startsWith(Figure.QUEEN_STRING))
			figureIndex += Figure.QUEEN;
		else if (str.startsWith(Figure.KING_STRING))
			figureIndex += Figure.KING;
		int sourceCol = str.charAt(1) - 'a';
		int sourceRow = Integer.parseInt(Character.toString(str.charAt(2))) - 1;
		boolean hit = Character.toString(str.charAt(3)).equals("x") ? true : false;
		int destCol = str.charAt(4) - 'a';
		int destRow = Integer.parseInt(Character.toString(str.charAt(5))) - 1;
		// if str.length() is 7, then it must be a promotion with the additional newTypeStr at the end
		if (str.length() == 7) {
			String newTypeStr = Character.toString(str.charAt(6));
			int newType = Figure.fromString(newTypeStr);
			Move m = new Move(board, color, newType, sourceCol, sourceRow, destCol, destRow, true);
			if (hit)
				m.setHit();
			return m;
		}
		Move m = new Move(board, color, Figure.getType(figureIndex), sourceCol, sourceRow, destCol, destRow, false);
		if (hit)
			m.setHit();
		return m;
	}

	public static boolean MovesListIncludesMove(List<Move> moves, Move move) {
		for (Move m : moves)
			if (move.equals(m))
				return true;
		return false;
	}
}
