package com.webcheckers.ui;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetHomeRouteTest {

    private final GetHomeRoute CuT = new GetHomeRoute(new PlayerLobby(), new GameCenter());

    // mock objects
    private Request request;
    private Session session;
    private Response response;

    @Before
    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
    }

    @Test
    public void handle() throws Exception {

        final ModelAndView result = CuT.handle(request, response);
        final Object model = result.getModel();

        System.out.println(model);
        assertNotNull(model);
        assertTrue(model instanceof Map);
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(GetHomeRoute.TITLE, vm.get(GetHomeRoute.TITLE_ATTR));
        assertEquals(GetHomeRoute.VIEW_NAME, result.getViewName());
        session.attribute("Fisher", PlayerLobby.PLAYER_ID);
        //player is logged in
        when(session.attribute(PlayerLobby.PLAYER_ID) != null).thenReturn(true);
    }
}

