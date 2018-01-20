package org.chpr.chess;

import org.chpr.chess.objects.Move;

import java.util.List;

/**
 * Interface for public methods of our chess board
 */
public interface IBoard {
	/**
	 * Return all figures that are on the board
	 *
	 * @return array of figures
	 */
	short[][] getFigures();

	/**
	 * Set given figure at given position
	 *
	 * @param row the row where the figure should be positioned
	 * @param column the column where the figure should be positioned
	 * @param figure the figure that should be positioned
	 */
	void setFigure(int row, int column, short figure);

	/**
	 * Reset the board, i.e. set board to start position
	 */
	void reset();

	/**
	 * Copy board except with empty history
	 *
	 * @return incomplete copy of the board
	 */
	IBoard cloneIncompletely();

	/**
	 * Return list of all valid moves on the current board
	 *
	 * @return list of all valid moves
	 */
	List<Move> getValidMoves();

	/**
	 * Return list of all valid moves for a given color on the current board
	 *
	 * @param color color for which valid moves should be returned
	 * @return list of all valid moves for given color
	 */
	List<Move> getValidMoves(int color);

	/**
	 * Return List of previously executed moves
	 *
	 * @return list of previously executed moves
	 */
	List<Move> getHistory();

	/**
	 * Execute given move on current position
	 *
	 * @param move move to execute
	 */
	void executeMove(Move move);

	/**
	 * Check if white can still castle
	 *
	 * @return true, if white can still castle
	 */
	boolean canWhiteCastle();

	/**
	 * Check if white can still castle on queenside
	 *
	 * @return true, if white can still castle on queenside
	 */
	boolean canWhiteCastleQueenside();

	/**
	 * Check if white can still castle on kingside
	 *
	 * @return true, if white can still castle on kingside
	 */
	boolean canWhiteCastleKingside();

	/**
	 * Check if black can still castle
	 *
	 * @return true, if black can still castle
	 */
	boolean canBlackCastle();

	/**
	 * Check if black can still castle on queenside
	 *
	 * @return true, if black can still castle on queenside
	 */
	boolean canBlackCastleQueenside();

	/**
	 * Check if black can still castle on kingside
	 *
	 * @return true, if black can still castle on kingside
	 */
	boolean canBlackCastleKingside();
	
	/**
	 * Check if color is in check
	 *
	 * @param color color which should be checked for
	 * @return true, if color is in check
	 */
	boolean isCheck(int color);
	
	/**
	 * Check if color is in mat
	 *
	 * @param color color which should be checked for
	 * @return true, if color is in mat
	 */
	boolean isMat(int color);

	
}
