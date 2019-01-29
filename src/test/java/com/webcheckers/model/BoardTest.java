package com.webcheckers.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DefaultEditorKit.CutAction;
import org.junit.Test;

/**
 *  The unit test suite for the {@link Board} component.
 *
 *  @author <a href="mailto:dl8006@g.rit.edu">Diosdavi Lara</a>
 */
public class BoardTest {

  @Test
  public void canMove() throws Exception {
    final Board CuT = new Board();
    CuT.clearBoard();

    final boolean result = CuT.canMove(new Position(5, 2));
    // It should return false on empty spaces
    assertFalse(result);
  }

  @Test
  public void isColorBlocked1() throws Exception {
    final Board CuT = new Board();
    CuT.clearBoard();

    // If the board is empty, both should be blocked
    assertTrue(CuT.isColorBlocked(Color.RED));
    assertTrue(CuT.isColorBlocked(Color.WHITE));

    Space spaceWhitePiece = CuT.getSpaceByPosition(new Position(7, 0));
    spaceWhitePiece.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    // White shouldn't be blocked now
    assertFalse(CuT.isColorBlocked(Color.WHITE));

    Space spaceSecondRedPiece = CuT.getSpaceByPosition(new Position(5, 2));
    spaceSecondRedPiece.setPiece(new Piece(Type.SINGLE, Color.RED));
    // Red shouldn't be blocked either
    assertFalse(CuT.isColorBlocked(Color.RED));
  }

  @Test
  public void clearBoard() throws Exception {
    final Board CuT = new Board();
    CuT.clearBoard();

    // Loop through the board and make sure that it's empty
    for (int row = 0; row < 8; row++) {
      for (int cell = 0; cell < 8; cell++) {
        Position tempPosition = new Position(row, cell);
        Space tempSpace = CuT.getSpaceByPosition(tempPosition);
        assertNull(tempSpace.getPiece());
      }
    }
  }

  @Test
  public void isMoveValid() throws Exception {
    final Board CuT = new Board();
    Position start = new Position(0,0);
    Position end = new Position(7, 7);

    assertFalse(CuT.isMoveValid(new Move(start, end)));
    // Test with a move that's obviously not valid
  }

  @Test
  public void getSpaceByPosition() throws Exception {
    final Board CuT = new Board();

    // Loop through the board and make sure than every position corresponds with the resulting space
     for (int row = 0; row < 8; row++) {
       Row tempRow = CuT.getRows().get(row);
       assertEquals(tempRow.getIndex(), row);

       for (int cell = 0; cell < 8; cell++) {
         Space tempSpace = CuT.getSpaceByPosition(new Position(row, cell));
         assertEquals(tempSpace.getCellIdx(), cell);
       }
     }
  }

  @Test
  public void canKingMove() throws Exception {
    final Board CuT = new Board();
    CuT.clearBoard();
    // Get the scenario for a king to be blocked through all four directions

    Space spaceRedKingPiece = CuT.getSpaceByPosition(new Position(3, 4));
    Space spaceWhiteSinglePiece1 = CuT.getSpaceByPosition(new Position(1, 2));
    Space spaceWhiteSinglePiece2 = CuT.getSpaceByPosition(new Position(2, 3));
    Space spaceWhiteSinglePiece3 = CuT.getSpaceByPosition(new Position(2, 5));
    Space spaceWhiteSinglePiece4 = CuT.getSpaceByPosition(new Position(1, 6));
    Space spaceWhiteSinglePiece5 = CuT.getSpaceByPosition(new Position(4, 3));
    Space spaceWhiteSinglePiece6 = CuT.getSpaceByPosition(new Position(4, 5));
    Space spaceWhiteSinglePiece7 = CuT.getSpaceByPosition(new Position(5, 2));
    Space spaceWhiteSinglePiece8 = CuT.getSpaceByPosition(new Position(5, 6));

    spaceRedKingPiece.setPiece(new Piece(Type.KING, Color.RED));
    spaceWhiteSinglePiece1.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece2.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece3.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece4.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece5.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece6.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece7.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceWhiteSinglePiece8.setPiece(new Piece(Type.SINGLE, Color.WHITE));

    // The king shouldn't be able to move, it's blocked in all directions
    assertFalse(CuT.canMove(new Position(3, 4)));
  }

  @Test
  public void isColorBlocked() throws Exception {
    final Board CuT = new Board();
    // Create the following situation: a white piece that is surrounded by red pieces and can't move
    CuT.clearBoard();

    Space spaceWhitePiece = CuT.getSpaceByPosition(new Position(7, 0));
    Space spaceFirstRedPiece = CuT.getSpaceByPosition(new Position(6, 1));
    Space spaceSecondRedPiece = CuT.getSpaceByPosition(new Position(5, 2));

    spaceWhitePiece.setPiece(new Piece(Type.SINGLE, Color.WHITE));
    spaceFirstRedPiece.setPiece(new Piece(Type.SINGLE, Color.RED));
    spaceSecondRedPiece.setPiece(new Piece(Type.SINGLE, Color.RED));

    CuT.currentTurn = Color.WHITE;
    boolean result = CuT.isColorBlocked(Color.WHITE);
    assertTrue(result);
  }

  @Test
  public void isColorNotBlocked() throws Exception {
    final Board CuT = new Board();
    // In the default board setup, no Color should be blocked
    boolean whiteResult = CuT.isColorBlocked(Color.WHITE);
    boolean redResult = CuT.isColorBlocked(Color.RED);

    assertFalse(whiteResult);
    assertFalse(redResult);
  }

  @Test
  public void getRows() throws Exception {
    final Board CuT = new Board();
    assertTrue(CuT.getRows().size() == 8);
  }

  @Test
  public void testCrowning() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    Position whitePieceToDelete1 = new Position(6, 1);
    Position whitePieceToDelete2 = new Position(7, 0);

    Position redPieceStart = new Position(6, 1);
    Position redPieceEnd = new Position(7, 0);
    Move crowningMove = new Move(redPieceStart, redPieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.RED);

    CuT.setPieceByPosition(whitePieceToDelete1, null);
    CuT.setPieceByPosition(whitePieceToDelete2, null);
    CuT.setPieceByPosition(redPieceStart, pieceToBeTested);
    CuT.makeMove(crowningMove);

    Piece pieceAfterMove = CuT.getSpaceByPosition(redPieceEnd).getPiece();
    assertTrue(pieceAfterMove.getType() == Type.KING);
  }

  @Test
  public void testCrowningWhite() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;

    // setting up the board pieces
    Position redPieceToDelete1 = new Position(1, 6);
    Position redPieceToDelete2 = new Position(0, 7);

    Position whitePieceStart = new Position(1, 6);
    Position whitePieceEnd = new Position(0, 7);
    Move crowningMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);

    CuT.setPieceByPosition(redPieceToDelete1, null);
    CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(crowningMove);

    Piece pieceAfterMove = CuT.getSpaceByPosition(whitePieceEnd).getPiece();
    assertTrue(pieceAfterMove.getType() == Type.KING);

    //capturing move test
  }


  @Test
  public void testSingleCapturingWhite() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;

    // setting up the board pieces
    Position redPieceToDelete = new Position(1, 2);

    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(1, 2);
    Move capturingMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);

    CuT.setPieceByPosition(redPieceToDelete, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(capturingMove);


    assertTrue( (CuT.getRows().get(1).getSpaces().get(2).getPiece()!=null )&& (CuT.getRows().get(3).getSpaces().get(0).getPiece() ==null) && (CuT.getRows().get(2).getSpaces().get(1).getPiece()==null));

    //move left capture

  }

  @Test
  public void testSingleCapturingWhiteKing() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;

    // setting up the board pieces
    Position redPieceToDelete = new Position(1, 2);

    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(1, 2);
    Move capturingMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.WHITE);

    CuT.setPieceByPosition(redPieceToDelete, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(capturingMove);


    assertTrue( (CuT.getRows().get(1).getSpaces().get(2).getPiece()!=null )&& (CuT.getRows().get(3).getSpaces().get(0).getPiece() ==null) && (CuT.getRows().get(2).getSpaces().get(1).getPiece()==null));

    //move left capture

  }


  @Test
  public void testSingleCapturingWhiteLeft() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;
    Position redPieceToDelete=new Position(1,2);

    Position whitePieceStart = new Position(3, 4);
    Position whitePieceEnd = new Position(1, 2);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);
    CuT.setPieceByPosition(redPieceToDelete, null);
    //CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    Move capturingMove = new Move(whitePieceStart, whitePieceEnd);
    CuT.makeMove(capturingMove);

    assertTrue( (CuT.getRows().get(1).getSpaces().get(2).getPiece()!=null )&& (CuT.getRows().get(3).getSpaces().get(4).getPiece() ==null) && (CuT.getRows().get(2).getSpaces().get(3).getPiece()==null));


  }


  @Test
  public void testSingleCapturingWhiteLeftRed() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;
    Position redPieceToDelete=new Position(1,2);

    Position whitePieceStart = new Position(3, 4);
    Position whitePieceEnd = new Position(1, 2);
    Piece pieceToBeTested = new Piece(Type.KING, Color.WHITE);
    CuT.setPieceByPosition(redPieceToDelete, null);
    //CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    Move capturingMove = new Move(whitePieceStart, whitePieceEnd);
    CuT.makeMove(capturingMove);

    assertTrue( (CuT.getRows().get(1).getSpaces().get(2).getPiece()!=null )&& (CuT.getRows().get(3).getSpaces().get(4).getPiece() ==null) && (CuT.getRows().get(2).getSpaces().get(3).getPiece()==null));


  }

  @Test
  public void testRedCapturingWhite() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    Position whitePieceToDelete = new Position(6, 1);

    Position redPieceStart = new Position(4, 3);
    Position redPieceEnd = new Position(6, 1);
    Move capturingMove = new Move(redPieceStart, redPieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.RED);

    CuT.setPieceByPosition(whitePieceToDelete, null);
    CuT.setPieceByPosition(redPieceStart, pieceToBeTested);
    CuT.makeMove(capturingMove);


    assertTrue( (CuT.getRows().get(6).getSpaces().get(1).getPiece()!=null )&& (CuT.getRows().get(5).getSpaces().get(2).getPiece() ==null) && (CuT.getRows().get(4).getSpaces().get(3).getPiece()==null));

    //capturing move test
  }


  @Test
  public void testRedCapturingWhiteKing() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    Position whitePieceToDelete = new Position(6, 1);

    Position redPieceStart = new Position(4, 3);
    Position redPieceEnd = new Position(6, 1);
    Move capturingMove = new Move(redPieceStart, redPieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.RED);

    CuT.setPieceByPosition(whitePieceToDelete, null);
    CuT.setPieceByPosition(redPieceStart, pieceToBeTested);
    CuT.makeMove(capturingMove);


    assertTrue( (CuT.getRows().get(6).getSpaces().get(1).getPiece()!=null )&& (CuT.getRows().get(5).getSpaces().get(2).getPiece() ==null) && (CuT.getRows().get(4).getSpaces().get(3).getPiece()==null));

    //capturing move test
  }

  @Test
  public void invalidMoveWhite() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(4, 1);
    Position whitePieceEnd = new Position(4, 3);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);

    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(3).getPiece()==null ));

  }

  @Test
  public void invalidMoveWhiteRed() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(4, 1);
    Position whitePieceEnd = new Position(4, 3);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.WHITE);

    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(3).getPiece()==null ));

  }


  @Test
  public void invalidMoveRed() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(3, 2);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);


    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(3).getSpaces().get(2).getPiece()==null ));

  }

  @Test
  public void invalidMoveRedKing() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(3, 2);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.WHITE);


    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(3).getSpaces().get(2).getPiece()==null ));

  }

  @Test
  public void imaginaryPiece() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;



    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(3, 2);
    Move imaginaryMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.RED);


    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(imaginaryMove);

    assertTrue((CuT.getRows().get(3).getSpaces().get(2).getPiece()==null ));

  }

  @Test
  public void notDiagonalMoveRed() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    //Position whitePieceToDelete = new Position(6, 1);
    //Position redPieceToDelete2 = new Position(0, 7);

    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(4, 0);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.RED);

    //CuT.setPieceByPosition(whitePieceToDelete, null);
    //CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(0).getPiece()==null ));

  }

  @Test
  public void notDiagonalMoveRedKing() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    //Position whitePieceToDelete = new Position(6, 1);
    //Position redPieceToDelete2 = new Position(0, 7);

    Position whitePieceStart = new Position(3, 0);
    Position whitePieceEnd = new Position(4, 0);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.RED);

    //CuT.setPieceByPosition(whitePieceToDelete, null);
    //CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(0).getPiece()==null ));

  }

  @Test
  public void notDiagonalMoveWhite() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(5, 0);
    Position whitePieceEnd = new Position(4, 0);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);

    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(0).getPiece()==null ));


  }

  @Test
  public void notDiagonalMoveWhiteKing() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;


    Position whitePieceStart = new Position(5, 0);
    Position whitePieceEnd = new Position(4, 0);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.KING, Color.WHITE);

    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(0).getPiece()==null ));


  }

  @Test
  public void testMessage() throws Exception{
    final Board CuT = new Board();
    CuT.currentTurn = Color.WHITE;

    Position whitePieceStart = new Position(5, 0);
    Position whitePieceEnd = new Position(4, 0);
    Move invalidMove = new Move(whitePieceStart, whitePieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.WHITE);

    //CuT.setPieceByPosition(whitePieceToDelete, null);
    //CuT.setPieceByPosition(redPieceToDelete2, null);
    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    List<Move> invalidMoves = new ArrayList<>();
    invalidMoves.add(invalidMove);
    Message receivedInvalid=CuT.validateMove(invalidMoves);

    assertTrue(receivedInvalid.getType()==MessageType.ERROR);


    whitePieceEnd=new Position(4,1);

    Move validMove = new Move(whitePieceStart, whitePieceEnd);
    List<Move> validMoves = new ArrayList<>();
    validMoves.add(validMove);

    CuT.setPieceByPosition(whitePieceStart, pieceToBeTested);
    Message receivedValid=CuT.validateMove(validMoves);

    assertTrue(receivedValid.getType()==MessageType.INFO);



  }

  @Test
  public void moveRedRight() throws Exception {
    final Board CuT = new Board();
    CuT.currentTurn = Color.RED;

    // setting up the board pieces
    Position whitePieceToDelete = new Position(6, 7);

    Position redPieceStart = new Position(4, 5);
    Position redPieceEnd = new Position(6, 7);
    Move invalidMove = new Move(redPieceStart, redPieceEnd);
    Piece pieceToBeTested = new Piece(Type.SINGLE, Color.RED);

    CuT.setPieceByPosition(whitePieceToDelete, null);
    CuT.setPieceByPosition(redPieceStart, pieceToBeTested);
    CuT.makeMove(invalidMove);

    assertTrue((CuT.getRows().get(4).getSpaces().get(5).getPiece()==null ) && ((CuT.getRows().get(5).getSpaces().get(6).getPiece()==null)) && ((CuT.getRows().get(6).getSpaces().get(7).getPiece()!=null)) );

    final Board CuTK = new Board();
    CuTK.currentTurn = Color.RED;
    Piece kingPieceToBeTested = new Piece(Type.KING, Color.RED);

    CuTK.setPieceByPosition(whitePieceToDelete, null);
    CuTK.setPieceByPosition(redPieceStart, kingPieceToBeTested);
    CuTK.makeMove(invalidMove);

    assertTrue((CuTK.getRows().get(4).getSpaces().get(5).getPiece()==null ) && ((CuTK.getRows().get(5).getSpaces().get(6).getPiece()==null)) && ((CuTK.getRows().get(6).getSpaces().get(7).getPiece()!=null)) );

  }

}