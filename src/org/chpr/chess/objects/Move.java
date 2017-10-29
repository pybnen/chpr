package org.chpr.chess.objects;

import org.chpr.chess.IBoard;

import java.util.List;

public class Move {

	public Move(IBoard board, int color, int type, int sourceCol,
				int sourceRow, int destCol, int destRow) {

	}

	public int getColor() {

		return 0;
	}

	public int getType() {

		return 0;
	}

	public int getSourceCol() {

		return 0;
	}

	public int getSourceRow() {

		return 0;
	}

	public int getDestCol() {

		return 0;
	}

	public int getDestRow() {

		return 0;
	}

	public void setColor(int color) {

	}

	public boolean isHit() {

		return false;
	}

	public void setHit() {

	}

	@Override
	public String toString() {
		// TODO
		return super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO
		return super.equals(obj);
	}

	public static Move Import(String str, IBoard board, int color) {

		return null;
	}

	public static boolean MovesListIncludesMove(List<Move> moves, Move move) {

		return false;
	}
}
