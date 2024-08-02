package com.example.javafxapps;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// make Stroke implment Serializable so that it can be saved in binary format
// if all data members are serializable, we are done
// otherwise, we have to override readObject(ObjectInputStream) AND
//                                writeObject(ObjectOutputStream)
public abstract class Stroke implements Serializable {

    // access modifiers:
    // o public   : available to all
    // o private  : accessible to the class only (within the class)
    // o protected: accessible within family (inheritance) and same package
    protected double cx, cy;
    protected double size;      // radius for circle, half-width for square
    
    protected double ulx, uly, width;
    
    protected Color color;

    public Stroke(double x, double y, double s) {
        this(x, y, s, Color.BLACK);
    }

    public Stroke(double x, double y, double s, Color c) {
        cx = x;
        cy = y;
        size = s;

        ulx = cx - size;
        uly = cy - size;
        width = 2 * size;
        
        color = c;
    }

    // for the child-specific method, and if the parent doesn't have any default body for the method
    // then you still need to create a method signature here.

    // default behavior for all Stroke types , so a child class doesn't define draw() method,
    // this method is automatically executed
    public abstract void draw(GraphicsContext gc);
    
    @Override
    public String toString() {
        return String.format("%.0f %.0f %.0f %.3f %.3f %.3f", cx, cy, size, color.getRed(), color.getGreen(), color.getBlue());
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
    	// performs default serialization of all
    	// data that can be serialized (cx, cy, size, …)
    	out.defaultWriteObject();
    	// now writes each component of color
    	out.writeDouble(color.getRed());
    	out.writeDouble(color.getGreen());
    	out.writeDouble(color.getBlue());
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    	// performs default deserialization of all
    	// data that can be deserialized (cx, cy, size ..)
    	in.defaultReadObject();
    	// now reads each color component	
    	double r = in.readDouble();
    	double g = in.readDouble();
    	double b = in.readDouble();
    	// updates color
    	color = Color.color(r, g, b);
    }
}
