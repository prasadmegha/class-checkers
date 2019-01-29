package com.webcheckers.model;

import java.util.Objects;

/**
 *  Class to represent piece Position
 *  Author: <a href="mailto:mfabbu@rit.edu">Matt Arlauckas</a>
 *  Date: 2017-10-25
 */
public class Position {

    //
    //  Constants
    //
    static final int LOWER_BOARD_BOUNDARY = 0;
    static final int UPPER_BOARD_BOUNDARY = 7;

    //
    //  Attributes
    //
    private int row;
    private int cell;

    //
    //  Constructors
    //

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    //
    //  Methods
    //

    public int getRow() { return row; }
    public int getCell() { return cell; }

    public void setRow(int row){this.row=row;}
    public void setCell(int c){this.cell=c;}

    public boolean isValid() {
        if (row < LOWER_BOARD_BOUNDARY || row > UPPER_BOARD_BOUNDARY) {
            return false;
        } else if (cell < LOWER_BOARD_BOUNDARY || cell > UPPER_BOARD_BOUNDARY) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Given the {@Link Position} of a piece about to be captured, return the position in which a Piece would land
     *
     * @param capturedPosition
     *          The {@link Board}'s {@link Position}.
     *
     * @return a {@Link Position} object if the position is within the bounds of the board, null otherwise
     */
    public Position getJumpEndPosition(Position capturedPosition) {
        int endRow = ((capturedPosition.getRow() - this.row) * 2) + this.row;
        int endCell = ((capturedPosition.getCell() - this.cell) * 2) + this.cell;
        Position endPosition = new Position(endRow, endCell);
        if (endPosition.isValid()) {
            return endPosition;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Position)) return false;
        final Position that = (Position) obj;
        return (this.row == that.getRow()) && (this.cell == that.getCell());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.cell);
    }
}
