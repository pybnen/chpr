package org.chpr.gui;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;
import org.chpr.chess.utils.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;
import java.util.List;

public class PlayerGUI extends JFrame {
	private static final int CELLSIZE = 72;
	private static final int LEFT_OFFSET = 8;
	private static final int RIGHT_OFFSET = 8;
	private static final int TOP_OFFSET = 32;
	private static final int BOTTOM_OFFSET = 8;
	private static final String REL_IMG_DIR = "../../../img/";

	private IBoard board;
	int myColor = -1;
	public Move move;
	private static String imgPath;
	private boolean figureSelected;
	int selectedRow = 0;
	int selectedCol = 0;

	public PlayerGUI(IBoard board, int color) {
		this.board = board;
		this.myColor = color;
		this.move = null;
		imgPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + REL_IMG_DIR;

		getContentPane().setLayout(null);
		initWindow();
	}

	private void initWindow() {
		setTitle("My Chess v0.1 its your turn " + BoardUtils.ColorToString(myColor) + "!");
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				reactOnClick(e.getX(), e.getY());
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
	}

	private void reactOnClick(int x, int y) {
		int col = (x - LEFT_OFFSET) / CELLSIZE;
		int row = 7 - (y - TOP_OFFSET) / CELLSIZE;

		if (!(col >= 0 && col < 8 && row >= 0 && row < 8)) {
			figureSelected = false;
			this.repaint();
			return;
		}

		if (!figureSelected) {
			short figureIndex = board.getFigures()[col][row];
			if (figureIndex > 0 && Figure.getColor(figureIndex) == myColor) {
				selectedCol = col;
				selectedRow = row;
				figureSelected = true;
			}
		} else {
			short figureIndex = board.getFigures()[selectedCol][selectedRow];
			int figure = Figure.getType(figureIndex);
			boolean newType = false;
			if (figure == Figure.PAWN) {
				if ((myColor == Figure.BLACK && row == 0) || (myColor == Figure.WHITE && row == 7)) {
					// promote
					figure = Figure.QUEEN;
					newType = true;
				}
			}
			List<Move> moves = board.getValidMoves(myColor);
			// for the human player only allow moves that leaves the king save
			moves = Figure.getSafeMoves(board, moves);

			// just try none hit/hit version of move, otherwise we would have to check for en passant again
			Move moveNoHit = new Move(board, myColor, figure, selectedCol, selectedRow, col, row, newType, false);
			Move moveHit = new Move(board, myColor, figure, selectedCol, selectedRow, col, row, newType, true);


			if (moves.contains(moveNoHit)) {
				move = moveNoHit;
			} else if(moves.contains(moveHit)) {
				move = moveHit;
			} else {
				figureSelected = false;
			}
		}
		this.repaint();
	}

	private void closeWindow() {
		System.exit(-1);
	}

	public void paint(Graphics g) {
		setSize(CELLSIZE * 8 + RIGHT_OFFSET + LEFT_OFFSET,
				CELLSIZE * 8 + TOP_OFFSET + BOTTOM_OFFSET);
		int width = getWidth();
		int height = getHeight();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		for (int i = 0; i < 8; i += 2) {
			for (int j = 0; j < 8; j += 2) {
				g.fillRect(LEFT_OFFSET + j * CELLSIZE, TOP_OFFSET + i * CELLSIZE, CELLSIZE, CELLSIZE);
				g.fillRect(LEFT_OFFSET + (j + 1) * CELLSIZE, TOP_OFFSET + (i + 1) * CELLSIZE, CELLSIZE, CELLSIZE);
			}
		}
		short[][] figures = board.getFigures();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				short figureIndex = figures[col][row];
				if (figureIndex > 0) {
					int figureType = Figure.getType(figureIndex);
					int figureColor = Figure.getColor(figureIndex);
					int cellColor = (row + col) % 2 == 0 ? Figure.BLACK : Figure.WHITE;
					String filename = imgPath;

					if (figureType == Figure.PAWN) filename += "pawn";
					if (figureType == Figure.ROOK) filename += "rook";
					if (figureType == Figure.BISHOP) filename += "bishop";
					if (figureType == Figure.KNIGHT) filename += "knight";
					if (figureType == Figure.QUEEN) filename += "queen";
					if (figureType == Figure.KING) filename += "king";

					filename += figureColor == Figure.WHITE ? "_white" : "_black";
					filename += cellColor == Figure.WHITE ? "_white" : "_black";
					filename += ".gif";

					Image img = getToolkit().getImage(filename);
					g.drawImage(img, LEFT_OFFSET + col * CELLSIZE, TOP_OFFSET + (7 - row) * CELLSIZE, this);
				}
			}
		}

		if (figureSelected) {
			g.setColor(Color.RED);
			Graphics2D g2 = (Graphics2D) g;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(2));
			g.drawRect(LEFT_OFFSET + selectedCol * CELLSIZE, TOP_OFFSET + (7 - selectedRow) * CELLSIZE, CELLSIZE, CELLSIZE);
			g2.setStroke(oldStroke);
		}
	}
}
