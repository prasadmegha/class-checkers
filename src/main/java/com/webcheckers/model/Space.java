package com.webcheckers.model;

import java.util.Objects;

public class Space {

    //
    // Constants
    //

    //
    // Attributes
    //

    private final int cellIdx;
    private boolean isValid;
    private final boolean isBlackSquare;
    private Piece piece = null;

    //
    // Constructors
    //

    public Space(int cellIdx, boolean isValid) {
        this.cellIdx = cellIdx;
        this.isValid = isValid;
        this.isBlackSquare = isValid;
    }

    //
    //  Methods
    //

    /**
     * Get the cell's id.
     *
     * @return
     *   The cell's id.
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Get the Piece inside the Space object.
     *
     * @return
     *   The Piece inside the object.
     */
    public Piece getPiece() { return this.piece; }

    /**
     * Set the Piece inside the Space object.
     *
     * @param piece
     *          The new Piece object.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        if (this.piece != null) {
            this.isValid = false;
        } else if (this.isBlackSquare && this.piece == null) {
            this.isValid = true;
        }
    }

    /**
     * Tells whenever the space is valid.
     *
     * @return
     *   True if the space is valid, false otherwise.
     */
    public boolean isValid() { return this.isValid; }

    public boolean isBlackSquare() { return this.isBlackSquare; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Space space = (Space) obj;
        return cellIdx == space.cellIdx &&
                isValid == space.isValid &&
                Objects.equals(piece, space.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellIdx, isValid, piece);
    }

    @Override
    public String toString() {
        return "Space{" +
                "cellIdx=" + cellIdx +
                ", isValid=" + isValid +
                ", piece=" + piece +
                '}';
    }
}
