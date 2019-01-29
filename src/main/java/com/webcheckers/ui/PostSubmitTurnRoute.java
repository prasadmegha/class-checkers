package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.*;

import java.util.List;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSubmitTurnRoute implements TemplateViewRoute {

  private final GameCenter gameCenter;
  private final PlayerLobby playerLobby;

  /**
   * The constructor for the {@code POST /submitTurn} route handler.
   *
   * @param gameCenter
   *    The {@link GameCenter} for the application.
   * @param playerLobby
   *    The {@Link PlayerLobby} for the application.
   */
  public PostSubmitTurnRoute(GameCenter gameCenter, PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(gameCenter, "playerLobby must not be null");
    //
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    // retrieve the HTTP session
    final Session httpSession = request.session();

    // checks if the user is signed-in
    final Player currentPlayer = playerLobby.getPlayer(httpSession);

    // retrieve the game from the session
    final Game currentGame = gameCenter.get(httpSession);
    final List<Move> validatedMove = currentGame.validatedMoves;

    final boolean madeValidMoves = currentGame.makeMoves(validatedMove);
    // flips the color to change the turn,
    // and clear the list of moves
    if (madeValidMoves) {
      currentGame.setCurrentTurn((currentGame.getCurrentTurn() == Color.RED ? Color.WHITE : Color.RED));
      currentGame.validatedMoves.clear();
    }

    // Redirect the user to the Game view
    String opponentUsername = Objects.equals(currentPlayer.getUsername(), currentGame.getPlayerRedUsername()) ? currentGame.getPlayerWhiteUsername() : currentGame.getPlayerRedUsername();
    response.redirect(WebServer.GAME_URL + "?" + GetGameRoute.OPPONENT_PARAM + "=" + opponentUsername);
    halt();
    return null;
  }
}
