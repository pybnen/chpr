package org.chpr.players.human;


import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;
import org.chpr.gui.PlayerGUI;
import org.chpr.players.Player;

import java.util.Random;

public class HumanPlayerGUI implements Player {
	private PlayerGUI gui;

	public HumanPlayerGUI(PlayerGUI gui) {
		this.gui = gui;
	}

	@Override
	public double getFitness(IBoard board, int color) {
		return 0.0;
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		gui.allowMove(color);

		Move playerMove = gui.getMoveAndReset();
		while (playerMove == null) {
			try {
				Thread.sleep(50);
			} catch (Exception ignored) {
			}
			playerMove = gui.getMoveAndReset();
		}
		return playerMove;
	}
}
