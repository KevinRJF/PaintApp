package com.example.javafxapps;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Base class for handling mouse events for drawing shapes.
 */
public class DrawHandler implements EventHandler<MouseEvent> {
	protected MyShape shape;
	protected ShapeCanvas canvas;
	
	/**
     * Constructor for DrawHandler.
     *
     * @param sc The ShapeCanvas associated with this handler
     */
	public DrawHandler(ShapeCanvas sc) {
        this.canvas = sc;
    }

	/**
     * Handles mouse press event.
     *
     * @param e The mouse event
     */
	protected void mousePressed(MouseEvent e) {
		// Create a new shape if current shape is null
            if (shape != null) {
                canvas.setCurrentShape(shape);
                shape.setP1(e.getX(), e.getY());
            }	
	}
	
	protected MyShape createShape(double x, double y) {
        return null;
    }
	
	/**
     * Handles mouse drag event.
     *
     * @param e The mouse event
     */
	protected void mouseDragged(MouseEvent e) {
		if (shape != null) {
            shape.setP2(e.getX(), e.getY());
            canvas.paint();
        }
    }
	
	/**
     * Handles mouse release event.
     *
     * @param e The mouse event
     */
	protected void mouseReleased(MouseEvent e) {
        if (shape != null) {
            canvas.addShape(shape);  // Add the shape to the canvas
            canvas.addEdit(new DrawEdit(canvas, shape));  // Add DrawEdit to undo stack
            canvas.setCurrentShape(null);  // Clear current shape
            shape = null;  // Reset the shape
        }
    }
	
    /**
     * calls appropiate method from mouse pressed, dragged or released based on the type of event
     * @param event the mouse event
     */
    @Override
    public void handle(MouseEvent event) {
        //System.out.println("Event Type: " + event.getEventType().getName()); // Print event type
        switch (event.getEventType().getName()) {
            case "MOUSE_PRESSED":
                mousePressed(event);
                break;
            case "MOUSE_DRAGGED":
                mouseDragged(event);
                break;
            case "MOUSE_RELEASED":
                mouseReleased(event);
                break;
        }
    }
}
