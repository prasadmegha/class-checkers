package com.webcheckers.model;

/**
 *  Class to represent piece movement
 *  Author: <a href="mailto:mfabbu@rit.edu">Matt Arlauckas</a>
 *  Date: 2017-10-25
 */
public class Move {

    //
    //  Constants
    //


    //
    //  Attributes
    //
    private final Position start;
    private final Position end;

    //
    //  Constructors
    //
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    //
    //  Methods
    //

    public Position getStart() { return start; }
    public Position getEnd() { return end; }
}
