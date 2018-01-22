package org.chpr.players.artificial;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;
import org.chpr.players.Player;
import org.chpr.players.Thinker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EvalMovementThinker implements Thinker {

	private Player player;
	private IBoard board;
	private int color;
	private Random random;
	private List<Move> bestMoves;
	private double bestFitness;

	private static final double REAL_LOW_VALUE = -5000.0;

	public EvalMovementThinker(Player player, IBoard board, Integer color, Random random) {
		this.player = player;
		this.board = board;
		this.color = color;
		this.random = random;
		this.bestFitness = REAL_LOW_VALUE;
		bestMoves = new ArrayList<>();
	}

	@Override
	public void run() {
		int level = 1;
		while (true) {
			System.out.println("current level of evalMovementPlayer: " + level);
			List<Move> curBestMoves = new ArrayList<>();
			double curBestFitness = REAL_LOW_VALUE;
			List<Move> moves = board.getValidMoves(color);

			for (Move m : moves) {
				IBoard b = board.cloneIncompletely();
				b.executeMove(m);
				double fitness = evaluate(b, BoardUtils.FlipColor(color), level - 1);
				if (fitness >= curBestFitness) {
					if (fitness > curBestFitness) {
						curBestMoves = new ArrayList<>();
						curBestFitness = fitness;
					}
					if (!curBestMoves.contains(m)) {
						curBestMoves.add(m);
					}
				}
			}
			bestMoves = curBestMoves;
			bestFitness = curBestFitness;
			level++;
		}
	}

	public double evaluate(IBoard b, int color, int level) {
		if (level == 0) {
			return player.getFitness(b, color) * -1;
		} else {
			List<Move> moves = b.getValidMoves(color);
			if (moves.isEmpty() || b.isMat(color)) {
				return player.getFitness(b, color) * -1;
			}

			double max = REAL_LOW_VALUE;
			for (Move m : moves) {
				IBoard tmp = b.cloneIncompletely();
				tmp.executeMove(m);
				double d = evaluate(tmp, BoardUtils.FlipColor(color), level - 1);

				if (d > max) {
					max = d;
				}
			}
			return max * -1;
		}
	}

	public Move getBestMove() {
		List<Move> bestEval0Moves = new ArrayList<>();
		double maxEval0 = REAL_LOW_VALUE;
		for (Move move : bestMoves) {
			IBoard tmp = board.cloneIncompletely();
			tmp.executeMove(move);
			double eval0 = player.getFitness(tmp, color);
			if (eval0 >= maxEval0) {
				if (eval0 > maxEval0) {
					bestEval0Moves = new ArrayList<>();
					maxEval0 = eval0;
				}
				bestEval0Moves.add(move);
			}
		}
		return bestEval0Moves.get(random.nextInt(bestEval0Moves.size()));
	}
}
