package com.webcheckers.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 *  The unit test suite for the {@link Game} component.
 *
 *  @author <a href="mailto:mfabbu@rit.edu">Matt Arlauckas</a>
 */
public class GameTest {

  @Test
  public void hasPlayerWon() throws Exception {
    final Game CuT = new Game(PLAYER_RED, PLAYER_WHITE);

    // No game should be declared as a winner yet
    assertFalse(CuT.hasPlayerWon(PLAYER_RED_NAME));
    assertFalse(CuT.hasPlayerWon(PLAYER_WHITE_NAME));

    // Clear the board and put a single red piece, to declare Red as the winner
    CuT.board.clearBoard();
    Space spaceRedPiece = CuT.board.getSpaceByPosition(new Position(3, 4));
    spaceRedPiece.setPiece(new Piece(Type.SINGLE, Color.RED));
    assertTrue(CuT.hasPlayerWon(CuT.getPlayerRedUsername()));
    assertFalse(CuT.hasPlayerWon(CuT.getPlayerWhiteUsername()));

    // Clear the board again and put a single white piece, to declare White as the winner
    CuT.board.clearBoard();
    Space spaceWhitePiece = CuT.board.getSpaceByPosition(new Position(3, 4));
    spaceWhitePiece.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    assertTrue(CuT.hasPlayerWon(CuT.getPlayerWhiteUsername()));
    assertFalse(CuT.hasPlayerWon(CuT.getPlayerRedUsername()));
  }

  /**
   * Test the {@link Game#toString()} method.
   */
  @Test
  public void testToString() throws Exception {
    final Game CuT = new Game(PLAYER_RED, PLAYER_WHITE);
    assertEquals(CuT.toString(), "{Game: " + CuT.getPlayerRedUsername() + " vs " + CuT.getPlayerWhiteUsername() + "}");
  }

  private static final String PLAYER_RED_NAME = "Bob";
    private static final String PLAYER_WHITE_NAME = "Sally";
    private static final Player PLAYER_RED = new Player(PLAYER_RED_NAME);
    private static final Player PLAYER_WHITE = new Player(PLAYER_WHITE_NAME);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPlayerRedUsername() throws Exception {
        final Game CuT = new Game(PLAYER_RED,PLAYER_WHITE);
        assertSame(PLAYER_RED_NAME, PLAYER_RED.getUsername());
    }

    @Test
    public void getPlayerWhiteUsername() throws Exception {
        final Game CuT = new Game(PLAYER_RED,PLAYER_WHITE);
        assertSame(PLAYER_WHITE_NAME, PLAYER_WHITE.getUsername());
    }
}