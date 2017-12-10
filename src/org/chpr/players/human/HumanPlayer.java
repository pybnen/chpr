package org.chpr.players.human;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
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
		// for the human player only allow moves that leaves the king save
		moves = Figure.getSafeMoves(board, moves);
		System.out.println("Safe:");
		System.out.println(BoardUtils.formatMovesList(moves));

		Move m = null;
		while (m == null) {
			try {
				System.out.print("Please enter your move: ");
				String s = br.readLine();

				if (BoardUtils.isIndex(s)) {
					int idx = Integer.parseInt(s) - 1;
					if (idx < 0 || idx > moves.size()) {
						System.out.println("Invalid index!");
					} else {
						m = moves.get(idx);
					}
				} else {
					m = Move.Import(s, board, color);
					if (!moves.contains(m)) {
						System.out.println("Invalid move!");
						m = null;
					}
				}
			} catch (Exception e) {
				System.out.println("Move not recognised!");
			}
		}
		System.out.println();
		return m;
	}
}
