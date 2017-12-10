package org.chpr.chess;

import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.artificial.MyPlayer;
import org.chpr.players.human.HumanPlayer;
import org.chpr.players.random.RandomPlayer;

import java.util.Date;
import java.util.Random;

public class Game {
	public static void main(String[] args) {
		long seed = (new Date()).getTime();
		Random random = new Random(seed);

		Board board = new Board();

		Player whitePlayer = new HumanPlayer();
		Player blackPlayer = new RandomPlayer();

		// Player whitePlayer = new HumanPlayer();
		// Player blackPlayer = new MyPlayer();

		// Player whitePlayer = new MyPlayer();
		// Player blackPlayer = new HumanPlayerGUI();

		boolean whiteMat = false;
		boolean blackMat = false;
		boolean remis = false;

		int round = 1;
		int MAX_ROUNDS = 200;
		int MAX_TIME = 500;

		System.out.println(board.toString());
		int currentColor = Figure.WHITE;

		while (!whiteMat && !blackMat && !remis && round < MAX_ROUNDS) {
			Move move;

			if (currentColor == Figure.WHITE) {
				move = whitePlayer.chooseMove(board, currentColor, MAX_TIME, random);
			} else {
				move = blackPlayer.chooseMove(board, currentColor, MAX_TIME, random);
			}
			board.executeMove(move);
			// check if matt or remis
			currentColor = BoardUtils.FlipColor(currentColor);

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
