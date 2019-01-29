package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

/**
 * The {@code GET /sign-in} route handler; aka the Sign-In page.
 *
 * @author <a href='mailto:dl8006@g.rit.edu'>Diosdavi Lara</a>
 */
public class GetSignInRoute implements TemplateViewRoute {

  // View-Model attribute names
  static final String TITLE_ATTR = "title";
  static final String MESSAGE_ATTR = "message";
  // Constants
  static final String TITLE = "Sign-In";
  static final String VIEW_NAME = "sign-in.ftl";

  private final PlayerLobby playerLobby;

  /**
   * The constructor for the {@code GET /sign-in} route handler.
   *
   * @param playerLobby
   *    The {@link PlayerLobby} for the application.
   */
  public GetSignInRoute(PlayerLobby playerLobby) {
    this.playerLobby = playerLobby;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ModelAndView handle(Request request, Response response) {
    // retrieve the HTTP session
    final Session session = request.session();

    // checks if the user is signed-in
    final Player currentPlayer = playerLobby.getPlayer(session);
    if (currentPlayer != null) {
      // redirect the user to the Home page
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    // start building the View-Model
    final Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, TITLE);
    return new ModelAndView(vm, VIEW_NAME);
  }
}
