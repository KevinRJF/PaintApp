package com.example.javafxapps;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class GroupHandler implements EventHandler<MouseEvent> {
    private ShapeCanvas canvas;
    private double startX, startY, endX, endY;
    private boolean dragging = false;
    private double[] originalLineDashes;

    public GroupHandler(ShapeCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startX = event.getX();
            startY = event.getY();
            dragging = true;
            originalLineDashes = canvas.getGraphicsContext2D().getLineDashes();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && dragging) {
            endX = event.getX();
            endY = event.getY();
            canvas.paint(); // Redraw the canvas to update the bounding box
            canvas.getGraphicsContext2D().setLineDashes(5); // Set line dashes for the bounding box
            canvas.getGraphicsContext2D().strokeRect(startX, startY, endX - startX, endY - startY); // Draw the bounding box
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED && dragging) {
            dragging = false;
            endX = event.getX();
            endY = event.getY();

            // Create a new ShapeGroup and find all shapes within the selection box
            ShapeGroup group = new ShapeGroup(startX, startY, endX, endY);
            ArrayList<MyShape> shapesToRemove = new ArrayList<>();
            for (MyShape shape : canvas.getShapes()) {
                if (group.within(shape)) {
                    group.addMember(shape);
                    shapesToRemove.add(shape);
                }
            }

            // Remove the shapes that are now in the group
            for (MyShape shape : shapesToRemove) {
                canvas.deleteShape(shape);
            }

            // If the group is not empty, add it to the canvas
            if (!group.isEmpty()) {
                canvas.addShape(group);
                // Add the GroupEdit to the canvas
                GroupEdit groupEdit = new GroupEdit(canvas, group);
                canvas.addEdit(groupEdit); // Add to the undo/redo stack
            }

            // Restore the original line dashes and repaint
            canvas.getGraphicsContext2D().setLineDashes(originalLineDashes);
            canvas.paint();
        }
    }
}
