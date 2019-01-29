package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

/**
 * The {@code POST /sign-in} route handler.
 *
 * @author <a href='mailto:dl8006@g.rit.edu'>Diosdavi Lara</a>
 */
public class PostSignInRoute implements TemplateViewRoute {

  // Constants
  static final String USERNAME_PARAM = "username";
  static final String USERNAME_TAKEN_ERROR_MESSAGE = "Username already taken.";
  static final String SIGN_IN_SUCCESSFUL_MESSAGE = "Sign-in successful!";

  private final PlayerLobby playerLobby;

  /**
   * The constructor for the {@code POST /sign-in} route handler.
   *
   * @param playerLobby
   *    The {@link PlayerLobby} for the application.
   */
  PostSignInRoute(final PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    //
    this.playerLobby = playerLobby;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    final String username = request.queryParams(USERNAME_PARAM);

    // retrieve the HTTP session
    final Session httpSession = request.session();
    final boolean signInSuccessful = playerLobby.signIn(username, httpSession);

    // start building the View-Model
    final Map<String, Object> vm = new HashMap<>();
    if (signInSuccessful) {
      // move to the home page
      vm.put(GetHomeRoute.MESSAGE_ATTR, SIGN_IN_SUCCESSFUL_MESSAGE);
      vm.put(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
      vm.put(GetHomeRoute.PLAYER_NAME_ATTR, ((Player)httpSession.attribute(PlayerLobby.PLAYER_ID)).getUsername());
      vm.put(GetHomeRoute.IS_LOGGED_IN_ATTR, true);
      vm.put(GetHomeRoute.PLAYER_LIST_ATTR, playerLobby.getUsersList());
      //
      return new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
    } else {
      // re-display the sign-in page
      vm.put(GetSignInRoute.MESSAGE_ATTR, USERNAME_TAKEN_ERROR_MESSAGE);
      vm.put(GetSignInRoute.TITLE_ATTR, GetSignInRoute.TITLE);
      return new ModelAndView(vm, GetSignInRoute.VIEW_NAME);
    }
  }
}
