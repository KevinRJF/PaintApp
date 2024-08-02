package com.example.javafxapps;

public class DeleteEdit extends Edit {
    
    /**
     * Constructor for DeleteEdit.
     *
     * @param c the canvas where the shape is deleted
     * @param s the shape to be deleted
     */
    public DeleteEdit(ShapeCanvas c, MyShape s) {
        super(c, s);
    }
    
    @Override
    public void redo() {
        // Remove the shape from the canvas
        canvas.deleteShape(shape);
    }

    @Override
    public void undo() {
        // Add the shape back to the canvas
        canvas.addShape(shape);
    }
}
