package com.webcheckers.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

  private static final String USERNAME = "Shinji";
  private static final String TO_STRING_OUTPUT = "{Player " + USERNAME + "}";

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test the {@link Player#getUsername()} method.
   */
  @Test
  public void getUsername() throws Exception {
    final Player CuT = new Player(USERNAME);
    assertEquals(USERNAME, CuT.getUsername());
  }

  /**
   * Test the {@link Player#toString()} method.
   */
  @Test
  public void testToString() throws Exception {
    final Player CuT = new Player(USERNAME);
    assertEquals(TO_STRING_OUTPUT, CuT.toString());
  }

  /**
   * Test the {@link Player()} equals() method.
   */
  @Test
  public void testEquals() throws Exception {
    Player CuTx = new Player(USERNAME);
    Player CuTY = new Player(USERNAME);
    assertTrue(CuTx.equals(CuTY) && CuTY.equals(CuTx));
  }

  /**
   * Test the {@link Player()} hashCode() method.
   */
  @Test
  public void testHashCode() throws Exception {
    Player CuTx = new Player(USERNAME);
    Player CuTY = new Player(USERNAME);
    assertTrue(CuTx.hashCode() == CuTY.hashCode());
  }

}