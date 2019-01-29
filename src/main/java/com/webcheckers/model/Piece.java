package com.webcheckers.model;

import java.util.Objects;

public class Piece {

    //
    // Constants
    //


    //
    // Attributes
    //

    private Type type;
    private Color color;

    //
    // Constructors
    //

    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;

    }

    //
    //  Methods
    //

    /**
     * Get the Piece's type.
     *
     * @return
     *   The Piece's types
     */
    public Type getType() {
        return type;
    }

    /**
     * Get the Piece's color.
     *
     * @return
     *   The Piece's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the Type for the Piece object.
     *
     * @param type
     *          The new Type enum.
     */
    public void setType(Type type) { this.type = type; }

    public void setColor(Color color) { this.color = color; }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;
        final Piece that = (Piece) obj;
        return (this.type == that.type) && (this.color == that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.color);
    }
}
