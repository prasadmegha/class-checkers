package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The object to coordinate the state of the Web Application.
 *
 * @author <a href='mailto:dl8006@g.rit.edu'>Diosdavi Lara</a>
 */
public class GameCenter {

  /**
   * The user session attribute name that points to a game object.
   */
  public final static String GAME_ID = "game";
  private List<Game> ongoingGames;

  public GameCenter() {
    ongoingGames = new ArrayList<>();
  }

  //
  // Public methods
  //

  /**
   * Get the {@linkplain Game game} for the current user
   * (identified by a browser session).
   *
   * @param session
   *   The HTTP session
   * @param playerRed
   *   The Player class for the red pieces
   * @param playerWhite
   *   The Player class for the white pieces
   *
   * @return
   *   A existing or new {@link Game}
   */
  public Game get(final Session session, final Player playerRed, final Player playerWhite) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    Objects.requireNonNull(session, "playerRed must not be null");
    Objects.requireNonNull(session, "playerWhite must not be null");
    //
    Game game = session.attribute(GAME_ID);
    if (game == null) {
      // create new game
      game = new Game(playerRed, playerWhite);
      session.attribute(GAME_ID, game);
      ongoingGames.add(game);
      System.out.println("New game created: " + game);
    }
    return game;
  }

  /**
   * Get the {@linkplain Game game} for the current user
   * (identified by a browser session).
   *
   * @param session
   *   The HTTP session
   *
   * @return
   *   A existing or new {@link Game}
   */
  public Game get(final Session session) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    //
    Game game = session.attribute(GAME_ID);
    return game;
  }

  /**
   * Get the {@linkplain Game game} that is currently ongoing with that username.
   *
   * @param username
   *   The Player's username.
   *
   * @return
   *   A existing {@link Game}
   */
  public Game get(final String username) {
    Predicate<Game> predicate = x -> x.getPlayerRedUsername().equals(username) || x.getPlayerWhiteUsername().equals(username);
    return ongoingGames.stream().filter(predicate).findFirst().orElse(null);
  }
  /**
   * End the user's current {@linkplain Game game}
   * and remove it from the session and the ongoingGames list
   *
   * @param session
   *   The HTTP session
   */
  public void end(Session session) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    Game game = session.attribute(GAME_ID);
    // remove the game from the user's session and the ongoingGames list
    session.removeAttribute(GAME_ID);
    ongoingGames.remove(game);
  }

  /**
   * Queries whether the Player associated with the username is already playing checkers with someone else
   *
   * @param username
   *          The Player's username.
   *
   * @return true if the player is already on a Game with someone else, false otherwise
   */
  public boolean isUserPlaying(String username){
    for (Game item : ongoingGames) {
      // loop through every game of the list, and see if the players match
      String playerRedUsername = item.getPlayerRedUsername();
      String playerWhiteUsername = item.getPlayerWhiteUsername();

      if (playerRedUsername.equals(username)) { return true; }
      if (playerWhiteUsername.equals(username)) { return true; }
    }
    return false;
  }
}
