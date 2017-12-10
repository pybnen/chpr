package org.chpr.tests;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
import org.chpr.chess.objects.Figure;
import org.chpr.players.Player;
import org.chpr.players.artificial.MyPlayer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class BoardTest {
	private double delta = 0.00001;
	private Board board;
	private int currentColor;

	@Before
	public void setUp() throws Exception {
		currentColor = Figure.WHITE;
		board = new Board();
	}

	@After
	public void tearDown() throws Exception {
		board = null;
	}

	@Test
	public void testMyPlayer() throws Exception {
		short[][] figures = new short[8][8];

		figures[2][4] = Figure.PAWN + Figure.BLACK_OFFSET;
		figures[7][4] = Figure.ROOK + Figure.BLACK_OFFSET;
		figures[4][4] = Figure.ROOK + Figure.WHITE_OFFSET;
		board.setFigures(figures);

		Player p = new MyPlayer();
		double fitness = p.getFitness(board, currentColor);
		assertEquals(fitness, -1.0, delta);


	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(org.chpr.chess.IBoard.class)
				.addClass(org.chpr.chess.Board.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

}
