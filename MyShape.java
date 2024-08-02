package com.example.javafxapps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Abstract class representing a shape that can be drawn on a canvas.
 */
public abstract class MyShape implements Serializable, Cloneable {
	protected transient Point2D p1, p2, center;
	protected transient Color color;
	protected boolean filled, isBoundingBox;
	protected double ulx, uly, width, height;

	/**
	 * Default constructor for MyShape.
	 */
	public MyShape() {
		this.p1 = new Point2D(0, 0);
		this.p2 = new Point2D(0, 0);
		this.color = Color.BLACK;
		this.filled = false;
		updateBounds();
		updateCenter();
	}

	/**
	 * Constructor for MyShape with two points.
	 *
	 * @param p1 First point of the shape
	 * @param p2 Second point of the shape
	 */
	public MyShape(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
		updateBounds();
		updateCenter();
	}

	/**
	 * Constructor for MyShape with four double parameters.
	 *
	 * @param x1 X-coordinate of the first point
	 * @param y1 Y-coordinate of the first point
	 * @param x2 X-coordinate of the second point
	 * @param y2 Y-coordinate of the second point
	 */
	public MyShape(double x1, double y1, double x2, double y2) {
		this.p1 = new Point2D(x1, y1);
		this.p2 = new Point2D(x2, y2);
		updateBounds();
		updateCenter();
	}
	
	public MyShape(double x1, double y1, double x2, double y2, Color col) {
		this.p1 = new Point2D(x1, y1);
		this.p2 = new Point2D(x2, y2);
		updateBounds();
		updateCenter();
		color = col;
	}
	
	public MyShape(double x1, double y1, double x2, double y2, Color col, boolean filled) {
		this.p1 = new Point2D(x1, y1);
		this.p2 = new Point2D(x2, y2);
		updateBounds();
		updateCenter();
		color = col;
		this.filled = filled;
	}
	
	public boolean isBoundingBox() {
        return isBoundingBox;
    }
	
	public void setBoundingBox(boolean boundingBox) {
        isBoundingBox = boundingBox;
    }

    public void resetBoundingBox() {
        isBoundingBox = false;
    }
	
	public boolean within(double x1, double y1, double x2, double y2) {
        Point2D center = getCenter();
        double centerX = center.getX();
        double centerY = center.getY();

        // Check if the center of the shape is within the given bounding box
        return centerX >= Math.min(x1, x2) && centerX <= Math.max(x1, x2)
                && centerY >= Math.min(y1, y2) && centerY <= Math.max(y1, y2);
    }
	
	// Method to move the shape by dx and dy
	public void move(double dx, double dy) {
		// Update the points p1 and p2
		p1 = p1.add(dx, dy);
		p2 = p2.add(dx, dy);

		// Update the bounds and center
		updateBounds();
		updateCenter();
	}

	// Override the clone method for deep cloning
	@Override
	public Object clone() {
		try {
            // Perform a deep copy of the shape
            MyShape clone = (MyShape) super.clone();
            
            clone.p1 = new Point2D(p1.getX(), p1.getY());
    		clone.p2 = new Point2D(p2.getX(), p2.getY());
    		clone.center = new Point2D(center.getX(), center.getY());
    		
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public String toString() {
		return String.format("%.0f %.0f %.0f %.0f %.3f %.3f %.3f", p1.getX(), p1.getY(), p2.getX(), p2.getY(),
				color.getRed(), color.getGreen(), color.getBlue(), filled);
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		// performs default serialization of all
		// data that can be serialized (cx, cy, size, …)
		out.defaultWriteObject();

		out.writeDouble(p1.getX());
		out.writeDouble(p1.getY());
		out.writeDouble(p2.getX());
		out.writeDouble(p2.getY());

		// now writes each component of color
		out.writeDouble(color.getRed());
		out.writeDouble(color.getGreen());
		out.writeDouble(color.getBlue());
		
		out.writeBoolean(filled);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// performs default deserialization of all
		// data that can be deserialized (cx, cy, size ..)
		in.defaultReadObject();

		double p1X = in.readDouble();
		double p1Y = in.readDouble();
		double p2X = in.readDouble();
		double p2Y = in.readDouble();

		// update points
		p1 = new Point2D(p1X, p1Y);
		updateCenter();

		// now reads each color component
		double r = in.readDouble();
		double g = in.readDouble();
		double b = in.readDouble();

		// updates color
		color = Color.color(r, g, b);
		
		boolean filled = in.readBoolean();
	}

	/**
	 * Get the first point of the shape.
	 * 
	 * @return The first point of the shape
	 */
	Point2D getP1() {
		return p1;
	}

	/**
	 * Get the second point of the shape.
	 * 
	 * @return The second point of the shape
	 */
	Point2D getP2() {
		return p2;
	}

	/**
	 * Get the color of the shape.
	 * 
	 * @return The color of the shape
	 */
	Color getColor() {
		return color;
	}

	/**
	 * Check if the shape is filled.
	 * 
	 * @return True if the shape is filled, false otherwise
	 */
	boolean isFilled() {
		return filled;
	}

	/**
	 * Get the upper-left x-coordinate of the shape's bounding box.
	 * 
	 * @return The upper-left x-coordinate of the bounding box
	 */
	double getULX() {
		return ulx;
	}

	/**
	 * Get the upper-left y-coordinate of the shape's bounding box.
	 * 
	 * @return The upper-left y-coordinate of the bounding box
	 */
	double getULY() {
		return uly;
	}

	/**
	 * Get the width of the shape's bounding box.
	 * 
	 * @return The width of the bounding box
	 */
	double getWidth() {
		return width;
	}

	/**
	 * Get the height of the shape's bounding box.
	 * 
	 * @return The height of the bounding box
	 */
	double getHeight() {
		return height;
	}

	/**
	 * Get the center of the shape.
	 * 
	 * @return The center point of the shape
	 */
	Point2D getCenter() {
		return center;
	}
	
	public void setCenter(Point2D center) {
		this.center = center;
    }

	// mutators
	/**
	 * Set the first point of the shape.
	 * 
	 * @param p1 The new first point of the shape
	 */
	void setP1(Point2D p1) {
		this.p1 = p1;
		updateBounds();
		updateCenter();
	}

	/**
	 * Set the first point of the shape using coordinates.
	 * 
	 * @param x The x-coordinate of the new first point
	 * @param y The y-coordinate of the new first point
	 */
	void setP1(double x, double y) {
		setP1(new Point2D(x, y));
	}

	/**
	 * Set the second point of the shape.
	 * 
	 * @param p2 The new second point of the shape
	 */
	void setP2(Point2D p2) {
		this.p2 = p2;
		updateBounds();
		updateCenter();
	}

	/**
	 * Set the second point of the shape using coordinates.
	 * 
	 * @param x The x-coordinate of the new second point
	 * @param y The y-coordinate of the new second point
	 */
	void setP2(double x, double y) {
		setP2(new Point2D(x, y));
	}

	/**
	 * Set the color of the shape.
	 * 
	 * @param color The new color of the shape
	 */
	void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Set whether the shape should be filled or not.
	 * 
	 * @param isFilled True to fill the shape, false for outline only
	 */
	void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Update the bounding box of the shape based on its points.
	 */
	void updateBounds() {
		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());
		width = Math.abs(p1.getX() - p2.getX());
		height = Math.abs(p1.getY() - p2.getY());
	}

	/**
	 * Update the center point of the shape based on its bounding box.
	 */
	void updateCenter() {
		center = new Point2D(ulx + width / 2, uly + height / 2);
	}

	// other methods
	/**
	 * Calculate the Euclidean distance between the center of this shape and a
	 * point.
	 * 
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 * @return The distance between the center of the shape and the point
	 */
	double distance(double x, double y) {
		return center.distance(x, y);
	}

	/**
	 * Draw the bounding box of the shape and the actual shape.
	 * 
	 * @param gc The graphics context to draw on
	 */
	void drawBounds(GraphicsContext gc) {
		gc.setLineDashes(null); // Disable dashes
		gc.strokeRect(ulx, uly, width, height);
	}

	// Abstract draw method
	public abstract void draw(GraphicsContext gc);
}
