package org.chpr;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.chess.objects.Move;

import java.util.List;


public class TestHelper {

	public static void printBoard(Board b, String title) {
		System.out.println(title);
		printBoard(b);
	}

	public static void printBoard(Board b) {
		System.out.println(b);
		System.out.println("\n");
	}
}
