package com.example.javafxapps;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CopyHandler implements EventHandler<MouseEvent> {
    private ShapeCanvas canvas;
    private MyShape closestShape;
    private double x0, y0;

    public CopyHandler(ShapeCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // Find the shape to copy
            closestShape = canvas.closestShape(event.getX(), event.getY());
            if (closestShape != null) {
                // Create a new copy and add it to the canvas
                MyShape copy = (MyShape) closestShape.clone();
                canvas.addShape(copy);
                // Add CopyEdit to undo stack
                canvas.addEdit(new CopyEdit(canvas, copy));
                // Save the initial mouse press coordinates
                x0 = event.getX();
                y0 = event.getY();
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (closestShape != null) {
                // Calculate the displacement and move the shape
                double dx = event.getX() - x0;
                double dy = event.getY() - x0;
                closestShape.move(dx, dy);

                // Update the initial coordinates for the next drag
                x0 = event.getX();
                y0 = event.getY();
                canvas.paint();
            }
        }
    }
}
