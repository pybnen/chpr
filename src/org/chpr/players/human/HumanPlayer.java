package org.chpr.players.human;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;


public class HumanPlayer implements Player {

	private BufferedReader br;

	public HumanPlayer() {
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public double getFitness(IBoard board, int color) {
		// not needed for human player
		return 0;
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		List<Move> moves = board.getValidMoves(color);
		System.out.println();
		System.out.println(BoardUtils.formatMovesList(moves));

		Move m = null;
		while (m == null) {
			try {
				System.out.print("Please enter your move: ");
				String s = br.readLine();
				m = Move.Import(s, board, color);
				if (!moves.contains(m)) {
					System.out.println("Invalid move!");
					m = null;
				}
			} catch (Exception e) {
				System.out.println("Move not recognised!");
			}
		}
		return m;
	}
}
