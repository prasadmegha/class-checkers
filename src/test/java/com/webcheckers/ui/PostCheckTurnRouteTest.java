package com.webcheckers.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class PostCheckTurnRouteTest {

  private static final String PLAYER_RED_NAME = "Bob";
  private static final String PLAYER_WHITE_NAME = "Sally";
  private static final Player PLAYER_RED = new Player(PLAYER_RED_NAME);
  private static final Player PLAYER_WHITE = new Player(PLAYER_WHITE_NAME);

  private PostCheckTurnRoute CuT;
  private Session session;
  private Request request;
  private PlayerLobby playerLobby;
  private GameCenter gameCenter;

  @Before
  public void setUp() throws Exception {
    request = mock(Request.class);
    session = mock(Session.class);
    playerLobby = mock(PlayerLobby.class);
    gameCenter = mock(GameCenter.class);

    when(request.session()).thenReturn(session);
    CuT = new PostCheckTurnRoute(gameCenter, playerLobby);
  }

  @Test
  public void handle() throws Exception {
    // Arrange the test scenario
    final Game currentGame = new Game(PLAYER_RED, PLAYER_WHITE);
    currentGame.setCurrentTurn(Color.RED);

    when(playerLobby.getPlayer(session)).thenReturn(PLAYER_RED);
    when(gameCenter.get(session)).thenReturn(currentGame);
    final Response response = mock(Response.class);

    // Invoke the test
    final Object result = CuT.handle(request, response);

    // Analyze the results
    assertNotNull(result);
  }
}