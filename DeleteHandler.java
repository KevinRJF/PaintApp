package com.example.javafxapps;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DeleteHandler implements EventHandler<MouseEvent> {
    private ShapeCanvas canvas;

    // Constructor to initialize the canvas
    public DeleteHandler(ShapeCanvas canvas) {
        this.canvas = canvas;
    }

    // Override the handle method to respond to mouse clicks
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = event.getX();
            double y = event.getY();
            MyShape closestShape = canvas.closestShape(x, y);
            if (closestShape != null) {
                canvas.deleteShape(closestShape);  // Delete the shape
                canvas.addEdit(new DeleteEdit(canvas, closestShape));  // Add DeleteEdit to undo stack
                canvas.paint();
            }
        }
    }
}
