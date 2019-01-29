package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.Map;
import javax.swing.text.DefaultEditorKit.CutAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *  The unit test suite for the {@link GetGameRoute} component.
 *
 *  @author <a href="mailto:mfabbu@rit.edu">Matt Arlauckas</a>
 */
public class GetGameRouteTest {

    private static final String PLAYER_RED_NAME = "Bob";
    private static final String PLAYER_WHITE_NAME = "Sally";
    private static final Player PLAYER_RED = new Player(PLAYER_RED_NAME);
    private static final Player PLAYER_WHITE = new Player(PLAYER_WHITE_NAME);
    private static final String RED_COLOR = "RED";
    private static final String WHITE_COLOR = "WHITE";
    private static final String OPPONENT_PARAM = "opponentRadio";

    /**
     *  Component-under-test CuT
     */
    private GetGameRoute CuT;

    // Mock objects
    private Request request;
    private Session session;
    private Response response;
    private PlayerLobby playerLobby;

    /**
     *  Set up mock objects for each test
     */
    @Before
    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = new PlayerLobby();
        playerLobby.signIn(PLAYER_RED_NAME, session);
        playerLobby.signIn(PLAYER_WHITE_NAME, session);
        CuT = new GetGameRoute(new GameCenter(), playerLobby);
    }

    @Test
    public void handle() throws Exception {
        // Arrange the test scenario: There is an existing web session and a PlayerLobby with two logged in users.
        when(session.attribute(PlayerLobby.PLAYER_ID)).thenReturn(new Player(PLAYER_RED_NAME));
        when(request.queryParams(OPPONENT_PARAM)).thenReturn(PLAYER_WHITE_NAME);
        final ModelAndView result = CuT.handle(request, response);
        final Object model = result.getModel();

        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(GetGameRoute.TITLE, vm.get(GetGameRoute.TITLE_ATTR));
        assertEquals(GetGameRoute.VIEW_NAME, result.getViewName());
        assertEquals(PLAYER_RED, vm.get(GetGameRoute.CURRENT_PLAYER_ATTR));
        assertEquals(PLAYER_RED_NAME, vm.get(GetGameRoute.PLAYER_NAME_ATTR));
        //assertEquals(RED_COLOR, vm.get(GetGameRoute.PLAYER_COLOR_ATTR));
        assertEquals(PLAYER_WHITE_NAME, vm.get(GetGameRoute.OPPONENT_NAME_ATTR));
        //assertEquals(WHITE_COLOR, vm.get(GetGameRoute.OPPONENT_COLOR_ATTR));
        //assertTrue((boolean)vm.get(GetGameRoute.IS_MY_TURN_ATTR));
        assertEquals(null, vm.get(GetGameRoute.MESSAGE_ATTR));
        assertTrue(vm.get(GetGameRoute.BOARD_ATTR) instanceof Board);
    }
}