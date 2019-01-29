package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Web Controller for the Sign-Out page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSignOutRoute implements TemplateViewRoute {

    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code GET /sign-out} route handler.
     *
     * @param playerLobby
     *    The {@link PlayerLobby} for the application.
     * @param gameCenter
     */
    GetSignOutRoute(final PlayerLobby playerLobby, GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        //
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;

    }

    @Override
    public ModelAndView handle(Request request, Response response) {

        // retrieve the HTTP session
        final Session httpSession = request.session();
        final String username = ((Player)httpSession.attribute(PlayerLobby.PLAYER_ID)).getUsername();
        playerLobby.signOut(username, httpSession);
        gameCenter.end(httpSession);

        // redirect to the home page
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}
