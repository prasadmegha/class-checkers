# Web-Checkers Design Documentation

## Executive Summary

The application must allow players to play checkers with other players who are currently signed-in. The game user interface (UI) will support a game experience using drag-and-drop browser capabilities for making moves.

Beyond this minimal set of features, we have grand vision for what we could do including watching live games, playing multiple games, playing games asynchronously, reviewing past games, running tournaments with timed moves, and offering AI players to compete against.

## Requirements

* **Player Sign-in:** Players can enter their name and start a gaming session.
* **Start a Game:** After signing in, players can pick an opponent from a list and start a checkers game with them.
* **Game Play:** The two players will be able to play a game of checkers based upon the American rules. The interface will support a game experience using drag-and-drop browser capabilities for making moves.
* **Player Resignation:** Any of the players can resign from a game in progress.
* **Player Sign-out:** Players can end their gaming session, freeing up their name for others to use.


### Definition of MVP

1. The minimal viable product, according to the Web-Checkers vision document, must include the following features:
2. Every player must sign-in before playing a game.
3. Two players must be able to play a game of checkers based upon the American rules.
Either player of a game may choose to resign, which ends the game.

### MVP Features

- Player Sign-in / Sign-out
- Epic: Set-up game
- Epic: Single Player Moves
- Epic: King Player Moves
- Player Resignation

## Architecture

The Web-Checkers application uses a Java based server. We have used the Spark web micro framework along with the FreeMarker template engine to handle HTTP requests and to generate HTML responses. We have used Java v8 for this project.
![Class Diagram](https://github.com/niranjana7/2171-swen-610-01-3-sylver/blob/master/docs/Class%20Diagram.jpeg)
![Sequence Diagram](https://github.com/niranjana7/2171-swen-610-01-3-sylver/blob/master/docs/Sequence%20Diagram%20for%20WebCheckers.png)


### Summary

1. The landing page is the home page (home.ftl) which welcomes the user to the Web-Checkers game. The user can then sign in.
2. The page consists of an input that can be submitted. This username must be unique and the user can only play once they are logged in. Once the user is logged in, a list of opponents that are online is displayed. The user can select an opponent and play against them once the “Let’s Play!” button is clicked.
3. The user is then redirected to the game page which displays a checkers board, the players’ pieces and links to submit a turn, reset a turn and undo. 
4. The user can also sign out using the link from the navigation pane if they wish to do so.

### UI Tier

This tier contains our code for the sign-in, home display, sign out and web server files. These are the files that the user interacts with. Following are the files with the description:

* **GetHomeRoute:** This file displays the opponent list and renders home.ftl
* **GetExitGameRoute:** This route exits from a game after the winner is decided.
* **GetSignOutRoute:** This file handles de-authentication and destroys session.
* **WebServer:** This file contains the routing information for the Web-Checkers application.
* **GetGameRoute:** This file contains the game controller to enable game play.
* **JsonUtils:** A Utility class for parsing and formatting JSON data.
* **PostBackupMoveRoute:** Placeholder for implementation in the next sprint.
* **PostCheckTurnRoute:** Checks if it the current player's turn.
* **PostSignInRoute:** The POST sign in page in charge of log-in user validation
* **GetSignInRoute** The sign-in page
* **PostSubmitTurnRoute:** Submits the current user's turn.
* **PostValidateMoveRoute:** Submits a single move for a player to be validated.


### Model Tier 

* **WebCheckers.java** orders to instantiate the model for the Web-Checkers application.
* **Board.java** class holds a complex data type that represents the checkers board.
* **Row.java** must hold an object data type with the following attributes.
* **Space.java** holds an object data type with the following attributes.
* **piece.java** must hold an object data type with the following attributes.
* **game.java** class randomly assigns a player order and contains method to get player names, colors and turns.
* **Message.java** contains the logic for displaying a message based on the moves made by the user. It contains a MessageType class enum.
* **Player.java** contains logic to construct a player object which contains username.
* **Position.java** contains the row and cell coordinates of th cell piece.
* **Type** is an enum which contains the logic for the type of piece be it single or king.



### Application Tier

This tier will contain the “business logic” for our WebCheckers application, that is, the logic to enable game play.

* **GameCenter**: Creates a new Game object when a Game is about to start.
* **PlayerLobby**: Holds a list with the state of the currently signed-in Players.

