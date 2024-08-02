package com.example.javafxapps;

public class CopyEdit extends Edit {
    private MyShape copiedShape; // The cloned shape
    
    /**
     * Constructor for CopyEdit.
     *
     * @param c the canvas where the new shape is created
     * @param s the shape to be copied
     */
    public CopyEdit(ShapeCanvas c, MyShape s) {
        super(c, s);
        this.copiedShape = (MyShape) s.clone(); // Clone the shape
    }
    
    @Override
    public void redo() {
        // Add the cloned shape to the canvas
        canvas.addShape(copiedShape);
    }

    @Override
    public void undo() {
        // Remove the cloned shape from the canvas
        canvas.deleteShape(copiedShape);
    }
}
