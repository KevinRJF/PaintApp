package com.example.javafxapps;

/**
 * Abstract base class for all edit operations in the shape editor.
 */
public abstract class Edit {
    protected MyShape shape; // Shape involved in this edit
    protected ShapeCanvas canvas; // Canvas where the edit occurs
    
    /**
     * Constructor for Edit.
     * 
     * @param c  the canvas where the edit is applied
     * @param s  the shape involved in this edit
     */
    public Edit(ShapeCanvas c, MyShape s) {
        this.canvas = c;
        this.shape = s;
    }
    
    /**
     * Undo the effect of this edit.
     * This is an abstract method, which must be implemented by subclasses.
     */
    public abstract void undo();
    
    /**
     * Redo the effect of this edit.
     * This is an abstract method, which must be implemented by subclasses.
     */
    public abstract void redo();
}
