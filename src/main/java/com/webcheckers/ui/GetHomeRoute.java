package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetHomeRoute implements TemplateViewRoute {

  // View-Model attribute names
  static final String TITLE_ATTR = "title";
  static final String IS_LOGGED_IN_ATTR = "isLoggedIn";
  static final String PLAYER_NAME_ATTR = "playerName";
  static final String PLAYER_LIST_ATTR = "playerList";
  static final String MESSAGE_ATTR = "message";
  // Constants
  static final String TITLE = "Welcome";
  static final String VIEW_NAME = "home.ftl";

  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;

  /**
   * The constructor for the {@code GET /home} route handler.
   *
   * @param playerLobby
   *    The {@link PlayerLobby} for the application.
   * @param gameCenter
   */
  GetHomeRoute(final PlayerLobby playerLobby, GameCenter gameCenter) {
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ModelAndView handle(Request request, Response response) {
    // retrieve the HTTP session
    final Session session = request.session();

    // start building the View-Model
    final Map<String, Object> vm = new HashMap<>();
    // checks if the user is signed-in
    final Player currentPlayer = playerLobby.getPlayer(session);
    if (currentPlayer != null) {
      // check if the player is currently on a game with someone else
      boolean isCurrentlyPlaying = gameCenter.isUserPlaying(currentPlayer.getUsername());
      if (isCurrentlyPlaying) {
        // there is a game already being played
        // so redirect the user to the Game view
        Game game = gameCenter.get(currentPlayer.getUsername());
        session.attribute(GameCenter.GAME_ID, game);
        String opponentUsername = Objects.equals(currentPlayer.getUsername(), game.getPlayerRedUsername()) ? game.getPlayerWhiteUsername() : game.getPlayerRedUsername();
        response.redirect(WebServer.GAME_URL + "?" + GetGameRoute.OPPONENT_PARAM + "=" + opponentUsername);
        halt();
        return null;
      }
      vm.put(IS_LOGGED_IN_ATTR, true);
      vm.put(PLAYER_NAME_ATTR, currentPlayer.getUsername());
    } else {
      vm.put(IS_LOGGED_IN_ATTR, false);
      vm.put(PLAYER_NAME_ATTR, "");
    }
    vm.put(TITLE_ATTR, TITLE);
    vm.put(PLAYER_LIST_ATTR, playerLobby.getUsersList());

    return new ModelAndView(vm, VIEW_NAME);
  }
}
