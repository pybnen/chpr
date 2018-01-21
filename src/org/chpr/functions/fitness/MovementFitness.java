package org.chpr.functions.fitness;

import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MovementFitness implements BiFunction<IBoard, Integer, Double> {

	@Override
	public Double apply(IBoard board, Integer color) {
		SimpleFitness simpleFitness = new SimpleFitness();
		double fitness = simpleFitness.apply(board, color);
		short[][] figures = board.getFigures();

		// compare movement of bishops, knights, rooks, queens
		int numFieldsToMove = 0;
		for (int col = 0; col < figures.length; col++) {
			for (int row = 0; row < figures[0].length; row++) {
				short fig = figures[col][row];
				if (fig > 0 && Figure.getColor(fig) == color) {
					int figureType = Figure.getType(fig);
					if (figureType == Figure.BISHOP || figureType == Figure.QUEEN) {
						int curCol = col - 1;
						int curRow = row - 1;
						while (curCol >= 0 && curRow >= 0) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol--;
							curRow--;
						}
						curCol = col - 1;
						curRow = row + 1;
						while (curCol >= 0 && curRow <= 7) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol--;
							curRow++;
						}
						curCol = col + 1;
						curRow = row - 1;
						while (curCol <= 7 && curRow >= 0) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol++;
							curRow--;
						}
						curCol = col + 1;
						curRow = row + 1;
						while (curCol <= 7 && curRow <= 7) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol++;
							curRow++;
						}
					}

					if (figureType == Figure.ROOK || figureType == Figure.QUEEN) {
						int curCol = col - 1;
						int curRow = row;
						while (curCol >= 0) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol--;
						}
						curCol = col + 1;
						while (curCol <= 7) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curCol++;
						}
						curCol = col;
						curRow = row - 1;
						while (curRow >= 0) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curRow--;
						}
						curRow = row + 1;
						while (curRow <= 7) {
							if (figures[curCol][curRow] == 0)
								numFieldsToMove++;
							else
								break;
							curRow++;
						}
					}

					if (figureType == Figure.KNIGHT) {
						int curCol = col + 1;
						int curRow = row + 2;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}
						curCol = col + 2;
						curRow = row + 1;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}

						curCol = col + 1;
						curRow = row - 2;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}
						curCol = col + 2;
						curRow = row - 1;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}

						curCol = col - 1;
						curRow = row + 2;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}
						curCol = col - 2;
						curRow = row + 1;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}

						curCol = col - 1;
						curRow = row - 2;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}
						curCol = col - 2;
						curRow = row - 1;
						if (curCol >= 0 && curCol <= 7 && curRow >= 0 && curRow <= 7) {
							numFieldsToMove++;
						}

					}
				}
			}
		}

		double moveFitness = numFieldsToMove / 50.0;
		return fitness + moveFitness;
	}
}
