package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostValidateMoveRouteTest {

    Session session;
    Request request;
    Response response;
    GameCenter gameCenter;
    PlayerLobby playerLobby;

    private static final String PLAYER_RED_NAME = "Bob";
    private static final String PLAYER_WHITE_NAME = "Sally";
    private static final Player PLAYER_RED = new Player(PLAYER_RED_NAME);
    private static final Player PLAYER_WHITE = new Player(PLAYER_WHITE_NAME);

    private PostValidateMoveRoute CuT;

    @Before
    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);

        when(request.session()).thenReturn(session);
        CuT = new PostValidateMoveRoute(gameCenter, playerLobby);
    }

    @Test
    public void handle() throws Exception {
        final Game currentGame = new Game(PLAYER_RED, PLAYER_WHITE);
        when(playerLobby.getPlayer(session)).thenReturn(PLAYER_RED);
        when(gameCenter.get(session)).thenReturn(currentGame);

        Position redPieceStart = new Position(2, 1);
        Position redPieceEnd = new Position(3, 0);
        Move testMove = new Move(redPieceStart, redPieceEnd);

        when(request.body()).thenReturn(JsonUtils.toJson(testMove));

        // Invoke the test
        final Object result = CuT.handle(request, response);

        // Analyze the results
        assertNotNull(result);
        assertTrue(result instanceof Message);
    }

}