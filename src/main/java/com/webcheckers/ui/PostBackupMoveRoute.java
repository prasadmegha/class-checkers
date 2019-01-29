package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import spark.Session;

public class PostBackupMoveRoute implements Route {

  /**
   * {@inheritDoc}
   */
  private final GameCenter gameCenter;

  public PostBackupMoveRoute(final GameCenter gameCenter) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    //
    this.gameCenter = gameCenter;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    // retrieve the HTTP session
    final Session httpSession = request.session();
    // retrieve the game from the session
    final Game currentGame = gameCenter.get(httpSession);
    currentGame.backupMove();
    Message message = currentGame.board.validateMove(currentGame.validatedMoves);
    message.setType(MessageType.INFO);
    message.setText("The last move has been removed. Please refresh the page to see the changes.");
    return message;
  }
}