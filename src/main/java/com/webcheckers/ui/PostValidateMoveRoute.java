package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import java.util.Objects;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostValidateMoveRoute implements Route {

  private final GameCenter gameCenter;

  public PostValidateMoveRoute(GameCenter gameCenter, PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    //
    this.gameCenter = gameCenter;
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
    final Move move = JsonUtils.fromJson(request.body(), Move.class);
    currentGame.validatedMoves.add(move);
    Message message = currentGame.board.validateMove(currentGame.validatedMoves);
    return message;
  }
}
