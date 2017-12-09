package org.chpr;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;

import java.util.List;


public class Main {
    private static Board board;
    private static int currentColor;

    public static void main(String[] args) {
		//testExercise1();
		testExercise2();
    }

    private static void testExercise2() {
		currentColor = Figure.WHITE;
		board = new Board();
		short[][] figures = new short[8][8];
		figures[4][3] = Figure.ROOK + Figure.WHITE_OFFSET;

		figures[2][3] = Figure.PAWN + Figure.WHITE_OFFSET;
		figures[4][6] = Figure.QUEEN + Figure.BLACK_OFFSET;
		board.setFigures(figures);
		printBoard();
		List<Move> moves = Figure.getValidMoves(board, 4, 3);
		executeMoveList(moves);
	}


	private static void executeMoveList(List<Move> moves) {
		for (Move m : moves) {
			IBoard b = board.cloneIncompletely();
			b.executeMove(m);
			System.out.println(m);
			System.out.println(b);
			System.out.println("\n");
		}
	}

    private static void testExercise1() {
		currentColor = Figure.WHITE;
		board = new Board();

		testFewMoves();
		testEnPassant();
		testPromotion();
		testCastle();
	}

    private static void testFewMoves() {
        printBoard("Try a few moves:");
        executeAndPrint("e2-e4");
        executeAndPrint("b7-b6");
        executeAndPrint("Ng1-f3");
        executeAndPrint("Bc8-b7");
        executeAndPrint("d2-d3");
        executeAndPrint("Bb7xe4");
        executeAndPrint("d3xe4");

        resetBoard(true);
        System.out.println("----------------------\n");
    }

    private static void testEnPassant() {
        short[][] figures = new short[8][8];
        figures[0][3] = Figure.PAWN + Figure.WHITE_OFFSET;
        figures[1][3] = Figure.PAWN + Figure.BLACK_OFFSET;
        board.setFigures(figures);
        currentColor = Figure.BLACK;

        printBoard("Test En Passant");
        executeAndPrint("b4xa3");
        resetBoard(false);
    }

    private static void testPromotion() {
        short[][] figures = new short[8][8];
        figures[1][6] = Figure.PAWN + Figure.WHITE_OFFSET;
        board.setFigures(figures);
        currentColor = Figure.WHITE;

        printBoard("Test promotion");
        executeAndPrint("b7-b8Q");
        resetBoard(false);
        System.out.println("----------------------\n");
    }

    private static void testCastle() {
        setUpCastle();
        printBoard("Test Castle: 0-0");
        executeAndPrint("0-0");
        printCastleStatus();
        executeAndPrint("0-0");
        printCastleStatus();

        setUpCastle();
        printBoard("Test Castle: 0-0-0");
        executeAndPrint("0-0-0");
        printCastleStatus();
        executeAndPrint("0-0-0");
        printCastleStatus();

        setUpCastle();
        printBoard("Test move with king");
        executeAndPrint("Ke1-e2");
        printCastleStatus();

        setUpCastle();
        printBoard("Test move with rooks");
        executeAndPrint("Ra1xa8");
        printCastleStatus();
        executeAndPrint("Ke8-f8");
        printCastleStatus();
        executeAndPrint("Rh1-h4");
        printCastleStatus();

        resetBoard(false);
        System.out.println("----------------------\n");
    }

    private static void setUpCastle() {
        resetBoard(false);
        short[][] figures = new short[8][8];
        figures[0][0] = Figure.ROOK + Figure.WHITE_OFFSET;
        figures[7][0] = Figure.ROOK + Figure.WHITE_OFFSET;
        figures[4][0] = Figure.KING+ Figure.WHITE_OFFSET;

        figures[0][7] = Figure.ROOK + Figure.BLACK_OFFSET;
        figures[7][7] = Figure.ROOK + Figure.BLACK_OFFSET;
        figures[4][7] = Figure.KING + Figure.BLACK_OFFSET;

        board.setFigures(figures);
        currentColor = Figure.WHITE;
    }

    private static void executeAndPrint(String moveString) {
        Move m = Move.Import(moveString, board, currentColor);
        board.executeMove(m);
        System.out.println(m);
        System.out.println(board);
        System.out.println("\n");

        currentColor = (currentColor == Figure.WHITE ? Figure.BLACK : Figure.WHITE);
    }



    private static void printBoard(String title) {
		printBoard(title, board);
    }

	private static void printBoard(String title, Board b) {
		System.out.println(title);
		printBoard(b);
	}

    private static void printBoard() {
		printBoard(board);
    }

	private static void printBoard(Board b) {
		System.out.println(b);
		System.out.println("\n");
	}

    private static void printCastleStatus() {
        System.out.println("Can white castle: " + board.canWhiteCastle());
        System.out.println("Can black castle: " + board.canBlackCastle());
        System.out.println("\n");
    }

    private static void resetBoard(boolean print) {
        board.reset();
        currentColor = Figure.WHITE;
        if (print) {
            System.out.println("Reset board");
            System.out.println(board);
            System.out.println("\n");
        }
    }
}
