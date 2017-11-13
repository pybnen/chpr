package org.chpr.tests;

import org.chpr.chess.Board;
import org.chpr.chess.IBoard;
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

	private IBoard board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@After
	public void tearDown() throws Exception {
		board = null;
	}

	@Test
	public void getFigures() throws Exception {
	}

	@Test
	public void setFigure() throws Exception {
	}

	@Test
	public void reset() throws Exception {
	}

	@Test
	public void cloneIncompletely() throws Exception {
	}

	@Test
	public void getValidMoves() throws Exception {
	}

	@Test
	public void getValidMoves1() throws Exception {
	}

	@Test
	public void getHistory() throws Exception {
	}

	@Test
	public void executeMove() throws Exception {
	}

	@Test
	public void canWhiteCastle() throws Exception {
	}

	@Test
	public void canBlackCastle() throws Exception {
	}

	@Test
	public void isMat() throws Exception {
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(org.chpr.chess.IBoard.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

}
