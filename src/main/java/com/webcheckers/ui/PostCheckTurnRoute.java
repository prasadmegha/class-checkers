package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route {

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;

  public PostCheckTurnRoute(GameCenter gameCenter, PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    //
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // retrieve the HTTP session
    final Session httpSession = request.session();
    // retrieve the game from the session
    final Game currentGame = gameCenter.get(httpSession);
    final Player currentPlayer = playerLobby.getPlayer(httpSession);
    if (currentPlayer == currentGame.getPlayerCurrentTurn()) {
      return true;
    } else {
      return false;
    }
  }
}
