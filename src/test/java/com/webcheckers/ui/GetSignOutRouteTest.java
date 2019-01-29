package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetSignOutRoute} component.
 *
 * @author <a href='mailto:dl8006@g.rit.edu'>Diosdavi Lara</a>
 */
public class GetSignOutRouteTest {

  /**
   * The component-under-test (CuT).
   *
   * <p>
   * This is a stateless component so we only need one.
   */
  private GetSignOutRoute CuT;

  private static final String USERNAME = "Pepito";
  // mock objects
  private Request request;
  private Session session;
  private PlayerLobby playerLobby;
  private GameCenter gameCenter;

  /**
   * Setup new mock objects for each test.
   */
  @Before
  public void setUp() {
    request = mock(Request.class);
    session = mock(Session.class);
    playerLobby = mock(PlayerLobby.class);
    gameCenter = mock(GameCenter.class);
    when(request.session()).thenReturn(session);
    when(playerLobby.getUsersList()).thenReturn(new ArrayList<String>());
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test that CuT shows the Home view when the user has signed-out.
   */
  @Test(expected = HaltException.class)
  public void handle() throws Exception {
    CuT = new GetSignOutRoute(playerLobby, gameCenter);

    // Arrange the test scenario: There is an existing session with a logged in user.
    when(session.attribute(PlayerLobby.PLAYER_ID)).thenReturn(new Player(USERNAME));
    final Response response = mock(Response.class);

    // Invoke the test
    final ModelAndView result = CuT.handle(request, response);

    // Analyze the results:
    //   * result is null
    assertNull(result);
  }
}