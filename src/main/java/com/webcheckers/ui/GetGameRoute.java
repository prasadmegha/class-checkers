package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Player;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetGameRoute implements TemplateViewRoute {
    // View-Model attribute names
    static final String TITLE_ATTR = "title";
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String PLAYER_NAME_ATTR = "playerName";
    static final String PLAYER_COLOR_ATTR = "playerColor";
    static final String OPPONENT_NAME_ATTR = "opponentName";
    static final String OPPONENT_COLOR_ATTR = "opponentColor";
    static final String IS_MY_TURN_ATTR = "isMyTurn";
    static final String MESSAGE_ATTR = "message";
    static final String BOARD_ATTR = "board";
    static final String IS_GAME_ENDED_ATTR = "isGameEnded";

    // Constants
    static final String TITLE = "Game Play!";
    static final String OPPONENT_PARAM = "opponentRadio";
    static final String RESIGN_PARAM = "resign";
    static final String VIEW_NAME = "game.ftl";

    private final GameCenter gameCenter;
    private final PlayerLobby playerLobby;

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     * @param playerLobby
     *    The {@link PlayerLobby} for the application.
     */
    GetGameRoute(final GameCenter gameCenter, final PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        //
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        // retrieve the HTTP session and query params
        final Session httpSession = request.session();
        String opponent = request.queryParams(OPPONENT_PARAM);

        final Player currentPlayer;
        final Player opponentPlayer;
        final Game game;
        Message message;

        // if no parameters are given, pull the data from the session
        if (opponent != null) {
            // retrieve the two player objects
            currentPlayer = playerLobby.getPlayer(httpSession);
            opponentPlayer = playerLobby.getPlayer(opponent);

            //check if the opponent is already playing a Game with someone else
            boolean isOpponentPlaying = gameCenter.isUserPlaying(opponent);
            if (isOpponentPlaying) {
                Game ongoingGame = gameCenter.get(opponent);
                boolean isNotPlayerRed = !currentPlayer.getUsername().equals(ongoingGame.getPlayerRedUsername());
                boolean isNotPlayerWhite = !currentPlayer.getUsername().equals(ongoingGame.getPlayerWhiteUsername());
                boolean isCurrentPlayerNotOnThatGame = isNotPlayerRed && isNotPlayerWhite;
                if (isCurrentPlayerNotOnThatGame) {
                    // redirect to the home page, that player is already with someone else
                    response.redirect(WebServer.HOME_URL);
                    halt();
                    return null;
                }
            }

            //if not, retrieve the game object
            game = gameCenter.get(httpSession, currentPlayer, opponentPlayer);
        } else {
            currentPlayer = playerLobby.getPlayer(httpSession);
            game = gameCenter.get(httpSession);
            Color currentColor = game.getPlayerColor(currentPlayer.getUsername());
            opponent = (currentColor == Color.WHITE) ? game.getPlayerRedUsername() : game.getPlayerWhiteUsername();
            opponentPlayer = playerLobby.getPlayer(opponent);
        }

        // check if the current player's turn
        boolean isMyTurn = game.getPlayerColor(currentPlayer.getUsername()) == game.getCurrentTurn();

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // Resignation logic
        String user = request.queryParams(RESIGN_PARAM);
        if (user != null) {
            game.winner = opponentPlayer;
        }

        // Check if any of the players has won the game, and update the winner property
        if (game.winner == null) {
            if (game.hasPlayerWon(currentPlayer.getUsername())) {
                game.winner = currentPlayer;
            } else if (game.hasPlayerWon(opponentPlayer.getUsername())) {
                game.winner = opponentPlayer;
            } else {
                game.winner = null;
            }
        }

        // If someone won, display the message
        if (game.winner != null) {
            message = new Message("Player " + game.winner.getUsername() + " has won the game!", MessageType.INFO);
            vm.put(MESSAGE_ATTR, message);
        } else {
            vm.put(MESSAGE_ATTR, null);
        }

        boolean isGameEnded = (game.winner != null) ? true : false;
        vm.put(IS_GAME_ENDED_ATTR, isGameEnded);
        vm.put(TITLE_ATTR, TITLE);
        vm.put(CURRENT_PLAYER_ATTR, currentPlayer);
        vm.put(PLAYER_NAME_ATTR, currentPlayer.getUsername());
        vm.put(PLAYER_COLOR_ATTR, game.getPlayerColor(currentPlayer.getUsername()).name());
        vm.put(OPPONENT_NAME_ATTR, opponentPlayer.getUsername());
        vm.put(OPPONENT_COLOR_ATTR, game.getPlayerColor(opponentPlayer.getUsername()).name());
        vm.put(IS_MY_TURN_ATTR, isMyTurn);
        vm.put(BOARD_ATTR, game.board);
        return new ModelAndView(vm, VIEW_NAME);
    }
}
