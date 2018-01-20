package org.chpr.tests;

import org.chpr.chess.Board;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.artificial.AlphaBetaPlayer;
import org.chpr.players.artificial.EvalMovementPlayer;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.artificial.UberPlayer;

import java.util.Random;

public class AdvancedTest {
    private static Board board;
    private static int currentColor;

    public static void main(String[] args) {
		test1();
    }

	public static void test1() {
		currentColor = Figure.WHITE;
		board = new Board();

		short[][] figures = new short[8][8];

		figures[4][1] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[7][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		
		board.setFigures(figures);

		Player p = new UberPlayer();

		System.out.println(p.getFitness(board, Figure.WHITE));
		TestHelper.printBoard(board);

		for (int i = 0; i < 15; i++) {

			Move move = p.chooseMove(board, currentColor, 1500, new Random());
			if (move == null) {
				System.out.println("...");
				break;
			}
			board.executeMove(move);
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.WHITE));
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.BLACK));
			System.out.println(move.toString() + " " + board.isCheck(Figure.WHITE));
			System.out.println(move.toString() + " " + board.isCheck(Figure.BLACK));
			TestHelper.printBoard(board);

			currentColor = BoardUtils.FlipColor(currentColor);

		}
	}
	
	public static void test2() {
		currentColor = Figure.WHITE;
		board = new Board();

		short[][] figures = new short[8][8];

		figures[4][1] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[7][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		
		board.setFigures(figures);

		Player p = new AlphaBetaPlayer();

		System.out.println(p.getFitness(board, Figure.WHITE));
		TestHelper.printBoard(board);

		for (int i = 0; i < 15; i++) {

			Move move = p.chooseMove(board, currentColor, 1500, new Random());
			if (move == null) {
				System.out.println("...");
				break;
			}
			board.executeMove(move);
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.WHITE));
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.BLACK));
			System.out.println(move.toString() + " " + board.isCheck(Figure.WHITE));
			System.out.println(move.toString() + " " + board.isCheck(Figure.BLACK));
			TestHelper.printBoard(board);

			currentColor = BoardUtils.FlipColor(currentColor);

		}
	}
	
	public static void test3() {
		currentColor = Figure.WHITE;
		board = new Board();

		short[][] figures = new short[8][8];

		figures[4][1] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[7][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		
		board.setFigures(figures);

		Player p = new EvalMovementPlayer();

		System.out.println(p.getFitness(board, Figure.WHITE));
		TestHelper.printBoard(board);

		for (int i = 0; i < 15; i++) {

			Move move = p.chooseMove(board, currentColor, 1500, new Random());
			if (move == null) {
				System.out.println("...");
				break;
			}
			board.executeMove(move);
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.WHITE));
			System.out.println(move.toString() + " " + p.getFitness(board, Figure.BLACK));
			System.out.println(move.toString() + " " + board.isCheck(Figure.WHITE));
			System.out.println(move.toString() + " " + board.isCheck(Figure.BLACK));
			TestHelper.printBoard(board);

			currentColor = BoardUtils.FlipColor(currentColor);

		}
	}
}
