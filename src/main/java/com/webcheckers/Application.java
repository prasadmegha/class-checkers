package com.webcheckers;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.ui.WebServer;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.logging.Logger;


/**
 * The entry point for the WebCheckers web application.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public final class  Application {
  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  //
  // Application Launch method
  //

  /**
   * Entry point for the WebCheckers web application.
   *
   * <p>
   * It wires the application components together.  This is an example
   * of <a href='https://en.wikipedia.org/wiki/Dependency_injection'>Dependency Injection</a>
   * </p>
   *
   * @param args
   *    Command line arguments; none expected.
   */
  public static void main(String[] args) {


    // create the one and only game center
    final GameCenter gameCenter = new GameCenter();
    // create the player lobby to handle online players
    final PlayerLobby playerLobby = new PlayerLobby();

    // The application uses FreeMarker templates to generate the HTML
    // responses sent back to the client. This will be the engine processing
    // the templates and associated data.
    final TemplateEngine templateEngine = new FreeMarkerEngine();

    // inject the player lobby, the game center and the freemarker engine into web server
    final WebServer webServer = new WebServer(playerLobby, gameCenter, templateEngine);

    // inject web server into application
    final Application app = new Application(webServer);

    // start the application up
    app.initialize();
  }

  //
  // Attributes
  //

  private final WebServer webServer;

  //
  // Constructor
  //

  private Application(final WebServer webServer) {
    this.webServer = webServer;
  }

  //
  // Private methods
  //

  private void initialize() {
    LOG.fine("WebCheckers is initializing.");
    // configure Spark and startup the Jetty web server
    webServer.initialize();

    // other applications might have additional services to configure

    LOG.fine("WebCheckers initialization complete.");
  }

}