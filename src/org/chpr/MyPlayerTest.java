package org.chpr;

import org.chpr.chess.Board;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.artificial.MyPlayer;

import java.util.Random;

public class MyPlayerTest {
    private static Board board;
    private static int currentColor;

    public static void main(String[] args) {
		test2();
    }

    public static void test1() {
		currentColor = Figure.WHITE;
		board = new Board();

		short[][] figures = new short[8][8];

		figures[2][4] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[7][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		board.setFigures(figures);

		Player p = new MyPlayer();
		double fitness = p.getFitness(board, currentColor);
		System.out.println(fitness);
		Move move = p.chooseMove(board, currentColor, 500, new Random());
		System.out.println(move);
	}


	public static void test2() {
		currentColor = Figure.WHITE;
		board = new Board();

		short[][] figures = new short[8][8];

		figures[2][1] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[2][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		board.setFigures(figures);

		Player p = new MyPlayer();

		System.out.println(p.getFitness(board, Figure.WHITE));
		TestHelper.printBoard(board);

		for (int i = 0; i < 10; i++) {

			Move move = p.chooseMove(board, currentColor, 500, new Random());
			board.executeMove(move);
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.WHITE));
			TestHelper.printBoard(board);

			currentColor = BoardUtils.FlipColor(currentColor);

		}
	}



}
