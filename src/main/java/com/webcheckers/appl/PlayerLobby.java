package com.webcheckers.appl;

import com.webcheckers.model.Player;
import spark.Session;

import java.util.*;

public class PlayerLobby {

  /**
   * The user session attribute name that points to the current player object.
   */
  public final static String PLAYER_ID = "player";

  public static Map<String, Player> onlinePlayers;

  public PlayerLobby() {
    onlinePlayers = new HashMap<>();
  }

  /**
   * Signs-in a Player into the lobby, and saves the Player object in the current browser session.
   *
   * @param username
   *          The Player's username.
   * @param session
   *   The HTTP session
   *
   * @return true if the username is unique and the login was successful, false otherwise
   */
  public boolean signIn(String username, final Session session){
    // validation
    Objects.requireNonNull(session, "session must not be null");
    //

    if (!isUsernameInUse(username)) {
      Player currentPlayer = new Player(username);
      onlinePlayers.put(username, currentPlayer);
      session.attribute(PLAYER_ID, currentPlayer);
      System.out.println("Player " + username + " has been added into the lobby.");
      return true;
    }
    return false;
  }

  /**
   * Signs-out a Player from the lobby, and deletes the Player object from the current browser session.
   *
   * @param username
   *          The Player's username.
   * @param session
   *   The HTTP session
   *
   */
  public void signOut(String username, final Session session){
    // validation
    Objects.requireNonNull(session, "session must not be null");
    //

    onlinePlayers.remove(username);
    session.removeAttribute(PLAYER_ID);
    System.out.println("Player " + username + " has been removed from the lobby.");
  }

  /**
   * Queries whether the username is already in use by someone else in the lobby.
   *
   * @param username
   *          The Player's username.
   *
   * @return true if there is someone online with that username, otherwise, false
   */
  public boolean isUsernameInUse(String username) {
    return (onlinePlayers.get(username) != null);
  }

  /**
   * Get the {@linkplain Player player} object for the current user
   * (identified by a browser session).
   *
   * @param session
   *   The HTTP session
   *
   * @return
   *   A existing {@link Player}, or null
   */
  public Player getPlayer(Session session) {
    // validation
    Objects.requireNonNull(session, "session must not be null");
    return session.attribute(PLAYER_ID);
  }

  /**
   * Get the {@linkplain Player player} object for the username provided.
   *
   * @param username
   *   The username of the {@link Player}
   *
   * @return
   *   A existing {@link Player} from the lobby, or null
   */
  public Player getPlayer(String username) {
    return onlinePlayers.get(username);
  }

  /**
   * Queries the list of Players who are signed-in to the Lobby.
   *
   * @return list of string with the user names of the online Players
   */
  public List<String> getUsersList() {
    List<Player> playerList = new ArrayList<>(onlinePlayers.values());
    List<String> usernameList = new ArrayList<>();

    for (Player item : playerList) {
      usernameList.add(item.getUsername());
    }
    return usernameList;
  }
}
