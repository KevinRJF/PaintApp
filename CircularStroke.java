package com.example.javafxapps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// Stroke: parent class (super class or base class)
// CircularStroke: child (sub class or derived class)
public class CircularStroke extends Stroke {
    
    public CircularStroke(double x, double y, double r) {
        super(x, y, r);  // calls parent's constructor that takes 3 double parameters
    }
    
    /**
     * 2nd constructor takes a specific color and assign it to this circular stroke
     * constructor overloading: multiple constructors with different parameter lists are allowed
     */
    public CircularStroke(double x, double y, double r, Color color) {
        super(x, y, r, color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        //canvas.fillCircle(cx, cy, size, color);
        gc.setFill(color);
        gc.fillOval(ulx, uly, width, width);
    }
    
    @Override
    public String toString() {
        return "circular " + super.toString();
    }
    
}

