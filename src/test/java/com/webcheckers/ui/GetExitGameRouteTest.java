package com.webcheckers.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.GameCenter;
import org.junit.Test;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class GetExitGameRouteTest {

  /**
   * The component-under-test (CuT).
   *
   * <p>
   * This is a stateless component so we only need one.
   */
  private GetExitGameRoute CuT;

  private Request request;
  private Session session;
  private GameCenter gameCenter;

  @Test(expected = HaltException.class)
  public void handle() throws Exception {
    request = mock(Request.class);
    session = mock(Session.class);
    gameCenter = mock(GameCenter.class);
    when(request.session()).thenReturn(session);

    CuT = new GetExitGameRoute(gameCenter);

    final Response response = mock(Response.class);

    // Invoke the test
    final ModelAndView result = CuT.handle(request, response);

    // Analyze the results:
    //   * result is null
    assertNull(result);
  }

}