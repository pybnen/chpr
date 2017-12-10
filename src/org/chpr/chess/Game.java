package org.chpr.chess;

import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.artificial.MyPlayer2;
import org.chpr.players.human.HumanPlayer;
import org.chpr.players.random.RandomPlayer;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Game {
	public static void main(String[] args) {
		long seed = (new Date()).getTime();
		Random random = new Random(seed);

		Board board = new Board();
		short[][] figures = new short[8][8];

		//figures[2][1] = Figure.PAWN + Figure.BLACK_OFFSET;
//		figures[2][1] = Figure.BISHOP + Figure.BLACK_OFFSET;
//		figures[3][7] = Figure.KING + Figure.BLACK_OFFSET;
//
//		figures[6][0] = Figure.KING + Figure.WHITE_OFFSET;
//		//figures[7][0] = Figure.QUEEN + Figure.WHITE_OFFSET;
//
//		board.setFigures(figures);

		//Player whitePlayer = new HumanPlayer();Player blackPlayer = new HumanPlayer();
		Player whitePlayer = new HumanPlayer();Player blackPlayer = new MyPlayer();
		//Player whitePlayer = new MyPlayer2();Player blackPlayer = new HumanPlayer();


		boolean whiteMat = false;
		boolean blackMat = false;
		boolean remis = false;

		int round = 1;
		int MAX_ROUNDS = 200;
		int MAX_TIME = 500;


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
			board.executeMove(move);

			currentColor = BoardUtils.FlipColor(currentColor);
			if (board.isMat(currentColor)) {
				if (currentColor == Figure.WHITE) {
					whiteMat = true;
				} else {
					blackMat = true;
				}
			} else {
				List<Move> validMoves = board.getValidMoves(currentColor);
				if (Figure.getSafeMoves(board, validMoves).size() == 0) {
					remis = true;
				}
			}
			round++;
		}

		if (whiteMat)
			System.out.println("Result: BLACK wins");
		if (blackMat)
			System.out.println("Result: WHITE wins");
		if (remis || round == MAX_ROUNDS)
			System.out.println("Result: REMIS");

	}
}
