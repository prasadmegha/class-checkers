package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageType;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostBackupMoveRouteTest {
   Session session;
   Request request;
   GameCenter gameCenter;
   PlayerLobby playerLobby;
   Game game;

  @Before
  public void setUp() throws Exception {
    request = mock(Request.class);
    session = mock(Session.class);
    gameCenter = new GameCenter();
    game = new Game(new Player("A"), new Player("B"));

    List<Move> tempMove = new ArrayList<>();
    //tempMove.add(new Move(new Position(7, 0), new Position(6, 1)));
    game.validatedMoves = (tempMove);
    when(request.session()).thenReturn(session);
    when(gameCenter.get(session)).thenReturn(game);
  }


  @Test
  public void handle() throws Exception {
    final PostBackupMoveRoute CuT = new PostBackupMoveRoute(gameCenter);
    assertNotNull(gameCenter);
    when(session.attribute(GameCenter.GAME_ID)).thenReturn(game);

    final Response response = mock(Response.class);

    // Invoke the test
    final Message result = (Message) CuT.handle(request, response);
    assertNotNull(result);
  }

}