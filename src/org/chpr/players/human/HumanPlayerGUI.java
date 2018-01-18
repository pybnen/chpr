package org.chpr.players.human;


import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Move;
import org.chpr.gui.PlayerGUI;
import org.chpr.players.Player;

import java.util.Random;

public class HumanPlayerGUI implements Player {
	@Override
	public double getFitness(IBoard board, int color) {
		return 0.0;
	}

	@Override
	public Move chooseMove(IBoard board, int color, int milliSeconds, Random random) {
		PlayerGUI gui = new PlayerGUI(board, color);
		gui.setAlwaysOnTop(true);
		Thread thread = new Thread(() -> {
			gui.setBounds(10, 10, 420, 180);
			gui.show();
		});
		thread.start();
		while (gui.move == null) {
			try {
				Thread.sleep(50);
			} catch (Exception ignored) {
			}
		}
		gui.dispose();
		return gui.move;
	}
}
