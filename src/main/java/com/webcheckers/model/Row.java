package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *  Class to represent Row (made up of Spaces)
 *  Author: <a href="mailto:mfabbu@rit.edu">Matt Arlauckas</a>
 *  Date: 2017-10-25
 */
public class Row {

    //
    //  Constants
    //


    //
    //  Attributes
    //

    private final int index;
    private List<Space> spaces;

    //
    //  Constructors
    //

    public Row(int index) {
        this.index = index;
        this.spaces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            // determines if the space is black or white
            boolean isBlackSpace = ((this.index + i) % 2 != 0);
            Space newSpace = new Space(i, isBlackSpace);
            //determines wherever to add a red/white piece to the space
            if (isBlackSpace && (index >= 0 && index <= 2)) {
                newSpace.setPiece(new Piece(Type.SINGLE, Color.RED));
            } else if (isBlackSpace && (index >= 5 && index <= 7)) {
                newSpace.setPiece(new Piece(Type.SINGLE, Color.WHITE));
            }
            spaces.add(newSpace);
        }
    }

    //
    //  Methods
    //

    /**
     * Get the row's index.
     *
     * @return
     *   The row's index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the list of Spaces inside the Row.
     *
     * @return
     *   The list of spaces.
     */
    public List<Space> getSpaces() {
        return spaces;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Row)) return false;
        final Row that = (Row) obj;
        return this.index == that.index && Objects.equals(this.spaces, that.spaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, spaces);
    }

    @Override
    public String toString() {
        return "Row{" +
                "index=" + index +
                ", spaces=" + spaces +
                '}';
    }
}
