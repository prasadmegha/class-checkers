package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSignInRouteTest {
    private GetSignInRoute CuT;
    // mock objects
    private Request request;
    private Session session;
    private PlayerLobby playerLobby;
    @Before
    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        playerLobby = mock(PlayerLobby.class);
        when(playerLobby.getUsersList()).thenReturn(new ArrayList<String>());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void handle() throws Exception {
        CuT=new GetSignInRoute(playerLobby);

       final Response response=mock(Response.class);

       final ModelAndView result=CuT.handle(request,response);
       assertNotNull(result);

//        final ModelAndView result = CuT.handle();
        final Object model=result.getModel();

        final Map<String, Object> vm = (Map<String, Object>) model;

        assertNotNull(model);
        assertTrue(model instanceof Map);
        assertEquals(GetSignInRoute.TITLE,vm.get(GetSignInRoute.TITLE_ATTR));
        assertEquals(GetSignInRoute.VIEW_NAME, result.getViewName());

    }

}