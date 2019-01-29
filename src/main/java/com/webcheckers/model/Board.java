package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class to represent the Board (made up of Rows)
 *  Author: <a href="mailto:nd7896@rit.edu">Matt Arlauckas</a>
 *  Date: 2017-10-25
 */
public class Board {

    //
    //  Constants
    //
    static final String VALID_MOVE_MESSAGE = "Good move!";
    static final String INVALID_MOVE_MESSAGE = "Sorry, that play is invalid. Please try again.";

    //
    //  Attributes
    //
    private List<Row> rows;
    public Color currentTurn;

    //
    //  Constructors
    //

    public Board() {
        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            rows.add(new Row(i));
        }
    }

    //
    //  Methods
    //

    /**
     * Queries whether the Move is a capture move.
     *
     * @param move
     *          The Player's Move.
     *
     * @return true if the Move is a capture, otherwise, false
     */
    private boolean isMoveACapture(Move move) {
        if (isMoveValid(move)) {
            // Get the Piece about to be moved
            Space startSpace = getSpaceByPosition(move.getStart());
            Piece jumpingPiece = startSpace.getPiece();
            if (jumpingPiece == null) { return false; }

            // Check if the end position is actually two squares away from the starting position
            int rowDifference = Math.abs(move.getStart().getRow() - move.getEnd().getRow());
            int cellDifference = Math.abs(move.getStart().getCell() - move.getEnd().getCell());
            if (rowDifference != 2 || cellDifference != 2) { return false; }

            // Check if there's a Piece in the middle
            int capturingRow = (move.getStart().getRow() + move.getEnd().getRow()) / 2;
            int capturingCell = (move.getStart().getCell() + move.getEnd().getCell()) / 2;
            Space capturedSpace = getSpaceByPosition(new Position(capturingRow, capturingCell));
            Piece capturedPiece = capturedSpace.getPiece();
            if (capturedPiece == null) { return false; }

            // Check if the captured piece is of the opposite color as the jumping piece
            if (capturedPiece.getColor() == jumpingPiece.getColor()) { return false; }

            // If it reached this point, it must be a capture move
            return true;
        } else {
            return false;
        }
    }

    /**
     * Queries whether the Move is a crowning move.
     *
     * @param move
     *          The Player's Move.
     *
     * @return true if the Move is a crowning move, otherwise, false
     */
    private boolean isACrowningMove(Move move) {
        if (isMoveValid(move)) {
            // Get the Piece about to be moved
            Space startSpace = getSpaceByPosition(move.getStart());
            Piece crowningPieceToBe = startSpace.getPiece();

            // First, check that's it's not already a King
            if (crowningPieceToBe.getType() == Type.KING) { return false; }

            // Second, check the color and use it to determine the crowning row
            Color pieceColor = crowningPieceToBe.getColor();

            boolean isRedKing = (pieceColor == Color.RED) && (move.getEnd().getRow() == 7);
            boolean isWhiteKing = (pieceColor == Color.WHITE) && (move.getEnd().getRow() == 0);

            // If it fits any of the two situations, it's a crowning move
            if (isRedKing || isWhiteKing) { return true; }
            else { return false; }
        } else {
            return false;
        }
    }

    /**
     * Gets the {@Link Space} between a jump on the given capturing {@link Move}
     *
     * @param move
     *    The Player's Move.
     *
     * @return an {@link Space} if the move is a valid capture, null otherwise.
     */
    private Space getCapturedSpace(Move move) {
        if (isMoveACapture(move)) {
            // Retrieve the space in the middle, at this point it must be valid
            int capturingRow = (move.getStart().getRow() + move.getEnd().getRow()) / 2;
            int capturingCell = (move.getStart().getCell() + move.getEnd().getCell()) / 2;
            Space capturedSpace = getSpaceByPosition(new Position(capturingRow, capturingCell));
            return capturedSpace;
        } else {
            return null;
        }
    }

    /**
     * Gets the list of possible movements by the {@link Piece} on the given {@link Position}
     *
     * @param position
     *    The {@link Board}'s {@link Position}.
     *
     * @return an ArrayList of the valid {@link Position}s to which the {@link Piece} can move to. If there's no {@link Piece}, it returns an empty list.
     */
    private List<Position> getAllowedMovements(Position position) {
        // Create a list to hold the allowed Positions that the piece
        List<Position> allowedPositions = new ArrayList<>();
        List<Position> tempPositions = new ArrayList<>();

        // First thing, check if there's actually a Piece on the given Position
        Piece observedPiece = this.getSpaceByPosition(position).getPiece();
        if (observedPiece == null) { return allowedPositions; }

        // Check if the Piece is SINGLE or KING, in order to calculate the valid moves around it
        switch (observedPiece.getType()) {
            case KING:
                // STEP 1: Get non-capturing positions
                // can get non-capturing moves in both directions, no matter the color
                Position upperLeftPosition = new Position(position.getRow() - 1, position.getCell() - 1);
                Position upperRightPosition = new Position( position.getRow() - 1, position.getCell() + 1);
                Position lowerLeftPosition = new Position(position.getRow() + 1, position.getCell() - 1);
                Position lowerRightPosition = new Position(position.getRow() + 1, position.getCell() + 1);

                if (upperLeftPosition.isValid()) { tempPositions.add(upperLeftPosition); }
                if (upperRightPosition.isValid()) { tempPositions.add(upperRightPosition); }
                if (lowerLeftPosition.isValid()) { tempPositions.add(lowerLeftPosition); }
                if (lowerRightPosition.isValid()) { tempPositions.add(lowerRightPosition); }
                break;
            case SINGLE:
                // STEP 1: Get non-capturing positions
                // can get non-capturing moves either downwards or upwards, depending of the color
                // Check it the Piece's color is RED or WHITE, in order to calculate the valid moves around it
                switch (observedPiece.getColor()) {
                    case RED:
                        Position lowerLeftRedPosition = new Position(position.getRow() + 1, position.getCell() - 1);
                        Position lowerRightRedPosition = new Position(position.getRow() + 1, position.getCell() + 1);

                        if (lowerLeftRedPosition.isValid()) { tempPositions.add(lowerLeftRedPosition); }
                        if (lowerRightRedPosition.isValid()) { tempPositions.add(lowerRightRedPosition); }
                        break;
                    case WHITE:
                        Position upperLeftWhitePosition = new Position(position.getRow() - 1, position.getCell() - 1);
                        Position upperRightWhitePosition = new Position( position.getRow() - 1, position.getCell() + 1);

                        if (upperLeftWhitePosition.isValid()) { tempPositions.add(upperLeftWhitePosition); }
                        if (upperRightWhitePosition.isValid()) { tempPositions.add(upperRightWhitePosition); }
                        break;
                    default:
                }
                break;
            default:
        }

        // Check the available capturing and non-capturing moves (no need to check multiple capture moves) and move them to the allowedPositions list
        // Check if the allowed non-capturing positions have any Piece on them: if they don't, the position is valid
        for (Position item : tempPositions) {
            Piece tempPiece = getSpaceByPosition(item).getPiece();
            if (tempPiece == null) {
                // If there's no Piece there, we can add it as a non-capturing landing position
                allowedPositions.add(item);
            } else {
                // STEP 2: Get capturing positions
                // If there's an opposing Piece, we can at least check for the possibility of a capturing move by going over it
                if (observedPiece.getColor() != tempPiece.getColor()) {
                    // Check if the jump end position is empty and valid
                    Position jumpEndPosition = position.getJumpEndPosition(item);
                    if (jumpEndPosition != null && getSpaceByPosition(jumpEndPosition).getPiece() == null) {
                        allowedPositions.add(jumpEndPosition);
                    }
                }
            }
        }
        return allowedPositions;
    }

    /**
     * Queries whether the {@link Color}'s associated user can capture any pieces on the board
     *
     * @param color
     *          The {@link Player}'s {@link Color}.
     *
     * @return true if the user associated with the {@link Color} can capture any pieces, otherwise false
     */
    private boolean canColorCapture(Color color) {
        // Loop through every black Space
        for (int row = 0; row < 8; row++) {
            for (int cell = 0; cell < 8; cell++) {
                Position tempPosition = new Position(row, cell);
                Space tempSpace = getSpaceByPosition(tempPosition);
                // Skip white pieces
                if (!tempSpace.isBlackSquare()) { continue; }
                // Skip empty Spaces
                if (tempSpace.getPiece() == null) { continue; }
                // Skip pieces from the opposite color
                if (tempSpace.getPiece().getColor() != color) { continue; }
                // Get the allowed movements from that position
                List<Position> movements = getAllowedMovements(tempPosition);
                // Loop through every one of those, and check if any of them is a capturing move
                for (Position endPosition : movements) {
                    Move tempMove = new Move(tempPosition, endPosition);
                    boolean isCapturing = isMoveACapture(tempMove);
                    // If we found a capturing Move, no need to check the rest: return true
                    if (isCapturing) { return true; }
                }
            }
        }
        // If we actually went through the entire loop and none of the Pieces could make a capturing move, return false
        return false;
    }

    /**
     * Queries whether the Move is valid.
     *
     * @param move
     *          The Player's Move.
     *
     * @return true if the Move is valid, otherwise, false
     */
    public boolean isMoveValid(Move move) {
        // Gets the starting and ending Position
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        // If the end position is within the allowed moves, the move is valid
        List<Position> validMoves = getAllowedMovements(startPosition);
        return validMoves.contains(endPosition);
    }

    /**
     * Returns the Space occupied by the given Position
     *
     * @param position
     *          The Move's Position.
     *
     * @return the Space object
     */
    public Space getSpaceByPosition(Position position) {
        int rowIndex = position.getRow();
        int cellIndex = position.getCell();

        //Look for the row object
        Row rowObjStart = rows.stream().filter(x -> x.getIndex() == rowIndex).findFirst().get();
        //Look for the space object
        return rowObjStart.getSpaces().stream().filter(x -> x.getCellIdx() == cellIndex).findFirst().get();
    }

    /**
     * Sets the Piece occupied by the given Position
     *
     * @param position
     *          The Move's Position
     * @param piece
     *          The Piece
     */
    public void setPieceByPosition(Position position, Piece piece) {
        Space space = getSpaceByPosition(position);
        space.setPiece(piece);
    }

    /**
     * Get the list of Rows inside the Board.
     *
     * @return
     *   The list of rows.
     */
    public List<Row> getRows() {
        return rows;
    }

    /**
     * Validates a Move inside the Board.
     *
     * @param moves
     *    The {@link Move} from the user.
     * @return
     *    The Message object.
     */
    public Message validateMove(List<Move> moves) {
        boolean areMovesValid = true;
        boolean isNonCaptureAllowed = true;
        // Save starting/ending Position and Piece
        Space startSpace = null;
        Piece movedPiece = null;
        Space endSpace = null;

        if (moves.size() > 0) {
            startSpace = getSpaceByPosition(moves.get(0).getStart());
            movedPiece = startSpace.getPiece();
            endSpace = getSpaceByPosition(moves.get(moves.size() - 1).getEnd());
        }

        // There has to be a Piece at the starting point and an empty space at the ending point to stay valid
        if (movedPiece == null || endSpace.getPiece() != null) { areMovesValid = false; }

        // Enforce the Capture Move Rule: Disallow non-capturing moves from the start if the color can make a capture
        if (areMovesValid && canColorCapture(movedPiece.getColor())) { isNonCaptureAllowed = false; }

        if (areMovesValid) {
            // Validate each move in order
            for (Move moveItem : moves) {
                boolean isValid = isMoveValid(moveItem);

                // Check if the previous move was a non-capture
                if (isMoveACapture(moveItem)) {
                    isNonCaptureAllowed = false;
                } else if (isNonCaptureAllowed && !isMoveACapture(moveItem)) {
                    isNonCaptureAllowed = false;
                } else {
                    areMovesValid = false;
                    break;
                }

                if (isValid) {
                    // If it's valid, put the piece in the ending position and remove it from the starting position
                    Space tempStartSpace = getSpaceByPosition(moveItem.getStart());
                    Space tempEndSpace = getSpaceByPosition(moveItem.getEnd());
                    tempEndSpace.setPiece(tempStartSpace.getPiece());
                    tempStartSpace.setPiece(null);
                } else {
                    areMovesValid = false;
                    break;
                }
            }

            if (areMovesValid) {
                // Put the piece again in the starting position
                startSpace.setPiece(movedPiece);
                // Remove the piece from the end position
                endSpace.setPiece(null);
            }
        }

        if (areMovesValid) {
            return new Message(VALID_MOVE_MESSAGE, MessageType.INFO);
        } else {
            return new Message(INVALID_MOVE_MESSAGE, MessageType.ERROR);
        }
    }

    /**
     * Makes a Move inside the Board.
     *
     * @param move
     *    The {@link Move} from the user.
     */
    public void makeMove(Move move) {
        if (isMoveValid(move)) {
            Space startSpace = getSpaceByPosition(move.getStart()); // get the origin position
            Piece movedPiece = startSpace.getPiece();               // get the piece about to be moved

            // Check if the Piece is eligible for crowning
            boolean shouldBeSetAsKing = isACrowningMove(move) || (movedPiece.getType() == Type.KING);
            movedPiece.setType(shouldBeSetAsKing ? Type.KING : Type.SINGLE);

            // Remove captured pieces from the board, if any
            // Note: This has to be done *after* validating for the King condition, because otherwise removing the piece
            // would interfere with the result of the inner isMoveValid() method
            if (isMoveACapture(move)) {
                Space capturedSpace = getCapturedSpace(move);
                capturedSpace.setPiece(null);
            }

            setPieceByPosition(move.getEnd(), movedPiece);          // put the piece in its new position
            startSpace.setPiece(null);                              // remove the piece from the origin position
        }
    }

    /**
     * Queries whether the {@link Piece}'s present on the given {@link Position} can move or not
     *
     * @param position
     *    The {@link Board}'s {@link Position}.
     *
     * @return true if the {@link Piece} present in the {@link Position} can move, false if it can't or if there's no {@link Piece}
     */
    public boolean canMove(Position position) {
        // First thing, check if there's actually a Piece on the given Position
        Piece observedPiece = this.getSpaceByPosition(position).getPiece();
        if (observedPiece == null) { return false; }

        // Gets the list of allowed positions
        List<Position> allowedPositions = getAllowedMovements(position);

        // If the total is more than 0, return true. Otherwise return
        if (allowedPositions.size() > 0) { return true; }
        else { return false; }
    }

    /**
     * Queries whether the {@link Color}'s associated user is blocked from moving any pieces
     *
     * @param color
     *          The {@link Player}'s {@link Color}.
     *
     * @return true if the user associated with the {@link Color} can't move any pieces, otherwise false
     */
    public boolean isColorBlocked(Color color) {
        // Loop through every black Space
        for (int row = 0; row < 8; row++) {
            for (int cell = 0; cell < 8; cell++) {
                Position tempPosition = new Position(row, cell);
                Space tempSpace = getSpaceByPosition(tempPosition);
                // Skip white pieces
                if (!tempSpace.isBlackSquare()) { continue; }
                // Skip empty Spaces
                if (tempSpace.getPiece() == null) { continue; }
                // Skip pieces from the opposite color
                if (tempSpace.getPiece().getColor() != color) { continue; }
                // If the Piece in that position can Move, no need to check the rest: return false
                if (canMove(tempPosition)) { return false; }
            }
        }
        // If we actually went through the entire loop and none of the Pieces could move, return true
        return true;
    }

    /**
     * Removes all the Pieces from the Board.
     */
    public void clearBoard() {
        // loop through the whole board, clear all the Pieces
        for (int row = 0; row < 8; row++) {
            for (int cell = 0; cell < 8; cell++) {
                Position tempPosition = new Position(row, cell);
                Space tempSpace = getSpaceByPosition(tempPosition);
                tempSpace.setPiece(null);
            }
        }
    }
}
