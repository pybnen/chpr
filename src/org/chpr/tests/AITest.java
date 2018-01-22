package org.chpr.tests;

import org.chpr.chess.Board;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.gui.PlayerGUI;
import org.chpr.players.Player;
import org.chpr.players.artificial.AlphaBetaPlayer;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.human.HumanPlayer;
import org.chpr.players.human.HumanPlayerGUI;
import org.chpr.players.smart.HAlphaBetaPlayer;
import org.chpr.players.smart.HPlayer;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class AITest {
	public static void main(String[] args) {
		Random random = new Random(123123);

		Board board = new Board();

		PlayerGUI gui = new PlayerGUI(board);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		gui.setSize(420, 180);

		Player whitePlayer = new HumanPlayerGUI(gui);
		Player blackPlayer = new AlphaBetaPlayer();

		boolean whiteMat = false;
		boolean blackMat = false;
		boolean remis = false;

		int round = 1;
		int MAX_ROUNDS = 200;
		int MAX_TIME = 5000;

		short[][] figures = new short[8][8];
//		figures[0][0] = Figure.KING + Figure.WHITE_OFFSET;
//		figures[3][3] = Figure.PAWN + Figure.WHITE_OFFSET;
//		figures[6][1] = Figure.PAWN + Figure.WHITE_OFFSET;
//
//		figures[7][3] = Figure.KING + Figure.BLACK_OFFSET;
//		figures[2][1] = Figure.PAWN + Figure.BLACK_OFFSET;
//		figures[2][5] = Figure.PAWN + Figure.BLACK_OFFSET;

		figures[3][1] = Figure.KING + Figure.WHITE_OFFSET;
		figures[3][3] = Figure.PAWN + Figure.WHITE_OFFSET;
		figures[6][1] = Figure.PAWN + Figure.WHITE_OFFSET;

		figures[5][3] = Figure.KING + Figure.BLACK_OFFSET;
		figures[2][5] = Figure.PAWN + Figure.BLACK_OFFSET;

		board.setFigures(figures);

		int currentColor = Figure.WHITE;


		while (!whiteMat && !blackMat && !remis && round < MAX_ROUNDS) {
			System.out.println(BoardUtils.ColorToString(currentColor) + " to move");
			System.out.println(board.toString());
			System.out.println();

			Move move;
			if (currentColor == Figure.WHITE) {
				move = whitePlayer.chooseMove(board, currentColor, MAX_TIME, random);
			} else {
				move = blackPlayer.chooseMove(board, currentColor, MAX_TIME, random);
			}
			System.out.println(BoardUtils.ColorToString(currentColor) + " move: " + move.toString());
			System.out.println();
			board.executeMove(move);
			gui.repaint();
			currentColor = BoardUtils.FlipColor(currentColor);
			round++;
		}
	}
}
