package org.chpr;

import org.chpr.chess.Board;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.human.HumanPlayer;

import java.util.Date;
import java.util.Random;


public class HumanPlayerTest {
    private static Board board;
    private static int currentColor;

    public static void main(String[] args) {
		long seed = (new Date()).getTime();
		Random random = new Random(seed);

		currentColor = Figure.WHITE;
		board = new Board();

		Player humanPlayer = new HumanPlayer();
		int round = 1;
		int MAX_ROUNDS = 200;
		int MAX_TIME = 500;

		while (round < MAX_ROUNDS) {
			System.out.println(board.toString());
			Move move = humanPlayer.chooseMove(board, currentColor, MAX_TIME, random);
			board.executeMove(move);



			currentColor = BoardUtils.FlipColor(currentColor);
			round++;
		}

	}
}
