package com.example.javafxapps;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing an oval shape.
 */
public class Oval extends MyShape {
	
	/**
    * Default constructor for Oval.
	 * @param filled 
	 * @param col 
	 * @param y2 
	 * @param x2 
	 * @param y1 
	 * @param x1 
    */
	public Oval() {
		super();
	}
	
	/**
     * Constructor for Oval with two points.
     *
     * @param p1 First point of the shape
     * @param p2 Second point of the shape
     * 
     */
	public Oval(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	/**
     * Constructor for Oval with two points.
     *
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     */
	public Oval(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	public Oval(double x1, double y1, double x2, double y2, Color col, boolean filled) {
		super(x1, y1, x2, y2, col, filled);
	}
	
	/**
     * Method to draw the oval on the canvas.
     *
     * @param gc GraphicsContext object to draw on
     */
	public void draw(GraphicsContext gc) {
		gc.setStroke(color);
		if(filled) {
			gc.setFill(color);
			gc.fillOval(ulx, uly, width, height);
		} else {
			gc.strokeOval(ulx, uly, width, height);
		}
	}
	
	@Override
    public String toString() {
		System.out.println(super.toString());
        return "oval " + super.toString();
    }
}
