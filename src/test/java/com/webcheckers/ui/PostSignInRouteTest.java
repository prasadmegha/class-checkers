package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSignInRouteTest {
    private PlayerLobby playerLobby = new PlayerLobby();

    Session session;
    Request request;
    Response response;
    Game game;
    String GAME_ID;
    final String USERNAME_PARAM = "username";
    @Before

    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        playerLobby = mock(PlayerLobby.class);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void handle() throws Exception {
        final PostSignInRoute CuT = new PostSignInRoute(playerLobby);

        final ModelAndView result = CuT.handle(request, response);
        assertNotNull(playerLobby);
        final Object model = result.getModel();
        session.attribute("Pepito", PlayerLobby.PLAYER_ID);
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertNotNull(model);
        assertTrue(model instanceof Map);
        Assert.assertEquals(GetSignInRoute.VIEW_NAME, result.getViewName());
        when(session.attribute(PlayerLobby.PLAYER_ID) != null).thenReturn(true);






    }

}