package com.webcheckers.ui;

import static spark.Spark.halt;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

public class GetExitGameRoute implements TemplateViewRoute {

  private final GameCenter gameCenter;

  public GetExitGameRoute(GameCenter gameCenter) {
    // validation
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");

    //
    this.gameCenter = gameCenter;
  }

  /**
   * Invoked when a request is made on this route's corresponding path e.g. '/hello'
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return The content to be set in the response
   */
  @Override
  public ModelAndView handle(Request request, Response response) {
    // retrieve the HTTP session
    final Session httpSession = request.session();
    gameCenter.end(httpSession);

    // redirect to the home page
    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
