package org.chpr.chess;

import org.chpr.chess.objects.Move;

import java.util.List;

public class Board implements IBoard {
	@Override
	public short[][] getFigures() {
		return new short[0][];
	}

	@Override
	public void setFigure(int row, int column, short figure) {

	}

	@Override
	public void reset() {

	}

	@Override
	public IBoard cloneIncompletely() {
		return null;
	}

	@Override
	public List<Move> getValidMoves() {
		return null;
	}

	@Override
	public List<Move> getValidMoves(int color) {
		return null;
	}

	@Override
	public List<Move> getHistory() {
		return null;
	}

	@Override
	public void executeMove(Move move) {

	}

	@Override
	public boolean canWhiteCastle() {
		return false;
	}

	@Override
	public boolean canBlackCastle() {
		return false;
	}

	@Override
	public boolean isMat(int color) {
		return false;
	}

	@Override
	public String toString() {
		//TODO implement
		return "Board{}";
	}
}
