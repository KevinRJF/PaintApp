package com.example.javafxapps;

public class DrawEdit extends Edit {
    
    /**
     * Constructor for DrawEdit.
     *
     * @param c the canvas where the shape is drawn
     * @param s the shape to be drawn
     */
    public DrawEdit(ShapeCanvas c, MyShape s) {
        super(c, s);
    }
    
    @Override
    public void redo() {
        // Draw the shape on the canvas
        canvas.addShape(shape);
    }

    @Override
    public void undo() {
        // Remove the shape from the canvas
        canvas.deleteShape(shape);
    }
}
