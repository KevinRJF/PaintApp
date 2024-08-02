package com.example.javafxapps;

import java.util.ArrayList;

public class GroupEdit extends Edit {
    private ShapeCanvas canvas;
    private ShapeGroup shapeGroup;
    private ArrayList<MyShape> memberShapes;

    /**
     * Constructor to create a GroupEdit instance.
     * 
     * @param canvas The ShapeCanvas associated with this edit
     * @param shapeGroup The ShapeGroup created by the user
     */
    public GroupEdit(ShapeCanvas canvas, ShapeGroup shapeGroup) {
        super(canvas, shapeGroup); // Call the parent constructor (for any shared initialization)
        this.canvas = canvas;
        this.shapeGroup = shapeGroup;
        this.memberShapes = shapeGroup.getMembers(); // Store the member shapes for undo/redo
    }

    /**
     * Undo the group edit by removing the ShapeGroup and re-adding its members.
     */
    @Override
    public void undo() {
        // Remove the shape group from the canvas
        canvas.deleteShape(shapeGroup);

        // Add the individual member shapes back to the canvas
        for (MyShape shape : memberShapes) {
            canvas.addShape(shape);
        }
    }

    /**
     * Redo the group edit by re-adding the ShapeGroup and removing its members.
     */
    @Override
    public void redo() {
        // Remove individual member shapes from the canvas
        for (MyShape shape : memberShapes) {
            canvas.deleteShape(shape);
        }

        // Add the shape group back to the canvas
        canvas.addShape(shapeGroup);
    }
}
