package com.example.javafxapps;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing a rectangle shape.
 */
public class Rect extends MyShape {
	
	/**
     * Default constructor for Rect.
	 * @param filled 
	 * @param col 
	 * @param y2 
	 * @param x2 
	 * @param y1 
	 * @param x1 
     */
	public Rect() {
		super();
	}
	
	/**
     * Constructor for Rectangle with two points.
     *
     * @param p1 First point of the shape
     * @param p2 Second point of the shape
     * 
     */
	public Rect(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	/**
     * Constructor for Rect with two points.
     *
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     */
	public Rect(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	public Rect(double x1, double y1, double x2, double y2, Color col, boolean filled) {
		super(x1, y1, x2, y2, col, filled);
	}
	
	/**
     * Method to draw the rectangle on the canvas.
     *
     * @param gc GraphicsContext object to draw on
     */
	@Override
	public void draw(GraphicsContext gc) {
		gc.setStroke(color);
		if(filled) {
			gc.setFill(color);
			gc.fillRect(ulx, uly, width, height);
		} else {
			gc.strokeRect(ulx, uly, width, height);
		}
	}
	
	@Override
    public String toString() {
		System.out.println(super.toString());
        return "rectangle " + super.toString();
    }
}
