package com.example.javafxapps;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareStroke extends Stroke {
    
    public SquareStroke(double x, double y, double s) {
        super(x, y, s);
    }
    
    public SquareStroke(double x, double y, double s, Color c) {
        super(x, y, s, c);
    }
    
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(ulx, uly, width, width);
    }
    
    @Override
    public String toString() {
        return "square " + super.toString();
    }
    
}

