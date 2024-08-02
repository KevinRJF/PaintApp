package com.example.javafxapps;

public class MoveEdit extends Edit {
    private double dx; // Horizontal displacement
    private double dy; // Vertical displacement
    
    /**
     * Constructor for MoveEdit.
     *
     * @param c the canvas where the shape is moved
     * @param s the shape to be moved
     * @param dx horizontal displacement
     * @param dy vertical displacement
     */
    public MoveEdit(ShapeCanvas c, MyShape s, double dx, double dy) {
        super(c, s);
        this.dx = dx;
        this.dy = dy;
    }
    
    @Override
    public void redo() {
        // Move the shape by (dx, dy)
        shape.move(dx, dy);
    }

    @Override
    public void undo() {
        // Move the shape by (-dx, -dy)
        shape.move(-dx, -dy);
    }
}
