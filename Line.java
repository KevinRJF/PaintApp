package com.example.javafxapps;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing a line shape.
 */
public class Line extends MyShape {
	
	/**
     * Default constructor for Line.
	 * @param col 
	 * @param y2 
	 * @param x2 
	 * @param y1 
	 * @param x1 
     */
	public Line() {
		super();
	}
	
	/**
     * Constructor for Line with two points.
     *
     * @param p1 First point of the shape
     * @param p2 Second point of the shape
     * 
     */
	public Line(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	/**
     * Constructor for Line with two points.
     *
     * @param x1 X-coordinate of the first point
     * @param y1 Y-coordinate of the first point
     * @param x2 X-coordinate of the second point
     * @param y2 Y-coordinate of the second point
     */
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	public Line(double x1, double y1, double x2, double y2, Color col) {
		super(x1, y1, x2, y2, col);
	}
	
	/**
     * Method to draw the line on the canvas.
     *
     * @param gc GraphicsContext object to draw on
     */
	public void draw(GraphicsContext gc) {
		gc.setStroke(color);
		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	@Override
    public String toString() {
		System.out.println(super.toString());
        return "line " + super.toString();
    }
}
