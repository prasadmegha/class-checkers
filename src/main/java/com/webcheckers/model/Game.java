package com.webcheckers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;

/**
 * A single checkers game.
 *
 * @author <a href='mailto:dl8006@g.rit.edu'>Diosdavi Lara</a>
 */
public class Game {

  //
  // Constants
  //
  private static final Random RANDOM = new Random();

  //
  // Attributes
  //
  public Board board;
  public List<Move> validatedMoves;
  private static Map<Color, Player> players;
  public Player winner;

  //
  // Constructors
  //

  /**
   * Create a checkers game with two known players.
   *
   * @param playerOne
   *          The player with the red pieces.
   * @param playerTwo
   *          The player with the white pieces.
   *
   */
  public Game(Player playerOne, Player playerTwo) {
    // validation
    Objects.requireNonNull(playerOne, "red player must not be null");
    Objects.requireNonNull(playerTwo, "white player must not be null");

    this.winner = null;
    this.board = new Board();
    this.board.currentTurn = Color.RED;
    this.players = new HashMap<>();
    this.validatedMoves = new ArrayList<>();
    double playerOrder = RANDOM.nextInt();

    if (playerOrder % 2 == 0) {
      this.players.put(Color.RED, playerOne);
      this.players.put(Color.WHITE, playerTwo);
    } else {
      this.players.put(Color.WHITE, playerOne);
      this.players.put(Color.RED, playerTwo);
    }
  }

  /**
   * Get the first player's username.
   *
   * @return
   *   The first player's username.
   */
  public String getPlayerRedUsername() {
    return players.get(Color.RED).getUsername();
  }

  /**
   * Get the second player's username.
   *
   * @return
   *   The second player's username.
   */
  public String getPlayerWhiteUsername() {
    return players.get(Color.WHITE).getUsername();
  }

  /**
   * Get the Color associated with the player's username.
   *
   * @return
   *   The player's username.
   */
  public Color getPlayerColor(String username) {
    for (Entry<Color, Player> entry : players.entrySet()) {
      if (Objects.equals(entry.getValue().getUsername(), username)) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("Invalid username, that Player is not part of the game.");
  }

  /**
   * Gets the Player that corresponds to the current turn.
   *
   * @return
   *   The player's object associated with the current turn.
   */
  public Player getPlayerCurrentTurn() {
    return players.get(this.board.currentTurn);
  }

  public Color getCurrentTurn() {
    return this.board.currentTurn;
  }

  public void setCurrentTurn(Color color) {
    this.board.currentTurn = color;
  }

  public boolean hasPlayerWon(String username) {
    Color color = getPlayerColor(username);
    if (color == Color.RED) {
      return this.board.isColorBlocked(Color.WHITE);
    } else if (color == Color.WHITE) {
      return this.board.isColorBlocked(Color.RED);
    } else {
      return false;
    }
  }

  public void backupMove() {
    if (!(validatedMoves == null || validatedMoves.isEmpty())) {
      validatedMoves.remove(validatedMoves.size() - 1);
    }
  }

  /**
   * Makes a list of Moves inside the Board.
   *
   * @param moves
   *    The list of {@link Move}s from the user.
   * @return true if at least one move was made, false otherwise.
   */
  public boolean makeMoves(List<Move> moves) {
    int totalValidMoves = 0;
    for (Move moveItem : moves) {
      this.board.makeMove(moveItem);
      totalValidMoves++;
    }
    if (totalValidMoves > 0) { return true; }
    else { return false; }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized String toString() {
    Player playerOne = players.get(Color.RED);
    Player playerTwo = players.get(Color.WHITE);
    return "{Game: " + playerOne.getUsername() + " vs " + playerTwo.getUsername() + "}";
  }
}
