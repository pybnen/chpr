package org.chpr;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;

import java.util.List;


public class ValidMovesTest {
    private static Board board;
    private static int currentColor;

    public static void main(String[] args) {
		currentColor = Figure.WHITE;
		board = new Board();
		short[][] figures = new short[8][8];
		figures[4][3] = Figure.ROOK + Figure.WHITE_OFFSET;

		figures[2][3] = Figure.PAWN + Figure.WHITE_OFFSET;
		figures[4][6] = Figure.QUEEN + Figure.BLACK_OFFSET;
		board.setFigures(figures);
		TestHelper.printBoard(board);
		List<Move> moves = Figure.getValidMoves(board, 4, 3);
		executeMoveList(moves);
    }


	private static void executeMoveList(List<Move> moves) {
		for (Move m : moves) {
			IBoard b = board.cloneIncompletely();
			b.executeMove(m);
			System.out.println(m);
			System.out.println(b);
			System.out.println("\n");
		}
	}
}
