package com.example.javafxapps;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MoveHandler implements EventHandler<MouseEvent> {
    private ShapeCanvas canvas;
    private MyShape closestShape;
    private double x0, y0;  // Initial mouse press coordinates
    private double x1, y1;  // Coordinates for dragging

    public MoveHandler(ShapeCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            // Save the initial mouse press coordinates
            x0 = event.getX();
            y0 = event.getY();
            closestShape = canvas.closestShape(x0, y0);  // Find the shape to move
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (closestShape != null) {
                // Calculate the displacement and move the shape
                double dx = event.getX() - x0;
                double dy = event.getY() - y0;
                closestShape.move(dx, dy);

                // Update the coordinates for the next drag
                x0 = event.getX();
                y0 = event.getY();
                canvas.paint();
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (closestShape != null) {
                // Calculate the total displacement from initial coordinates
                double finalDx = event.getX() - x1;
                double finalDy = event.getY() - y1;

                // Add MoveEdit to the undo stack
                canvas.addEdit(new MoveEdit(canvas, closestShape, finalDx, finalDy));
            }
        }
    }
}
