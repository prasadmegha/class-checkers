package com.webcheckers.appl;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Session;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerLobbyTest {


    // mock objects
    private Request request;
    private Session session;

    private static final String NAME = "Peggy";
    private List<String> usersList = new ArrayList<String>();
    final PlayerLobby playerLobby = new PlayerLobby();

    @Before
    public void setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
    }


    @Test
    public void signIn() throws Exception {


        playerLobby.signIn("Peggy", session);


        boolean player11;
        if (playerLobby.isUsernameInUse("Peggy")) {
            assertFalse("Not unique", false);

        } else {
            assertTrue("Name unique", true);

        }

        if (playerLobby.isUsernameInUse("Fisher")) {
            assertFalse("Not unique", false);

        } else {
            assertTrue("Name unique, signed in", true);

        }


    }

    @Test
    public void signOut() throws Exception {


        //assume current signed in user is Peggy

        playerLobby.signOut("Peggy", session);

        if (playerLobby.isUsernameInUse("Peggy")) {
            assertFalse("Not signed out", false);

        } else {
            assertTrue("Player has been signed out", true);

        }
    }
}



