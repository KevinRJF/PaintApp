package com.example.javafxapps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyCanvas extends Canvas {
    
    public static final String FILENAME = "drawing.txt";

    private ArrayList<Stroke> strokes;
    private GraphicsContext gc;
    private double width, height;
    
    public MyCanvas(double w, double h) {
        super(w, h);   // make sure to give proper dimensions
        
        strokes = new ArrayList<>();
        width  = w;
        height = h;
        
        gc = getGraphicsContext2D();   // inherits from Canvas class

    }
    
    public void addStroke(Stroke s) {
        strokes.add(s);
        paint();
    }
    
    public void clearCanvas() {
        strokes.clear();
        paint();
    }
    
    public void paint() {
        // clear canvas
        gc.clearRect(0, 0, width, height);
        
        // draw all strokes
        for (Stroke s : strokes) {
            s.draw(gc);
        }
    }
    
    public void toTextFile(File fileObj) {
        // try-catch block
        try {
            PrintWriter fileOut = new PrintWriter(fileObj);
        
            fileOut.println(strokes.size());
            
            // for each Stroke in strokes, get the String representation of the stroke
            // and print to the file
            for (Stroke s : strokes) {
                //System.out.println(s.toString());
                fileOut.write(s.toString());
            }
            fileOut.close();
        }
        catch(IOException e) {
            System.out.println(FILENAME + " could not be opened/created for writing");
            e.printStackTrace();
        }
    }
    
    public void fromTextFile(File selectedFile) {
        // Scanner: hasNext(), nextInt(), nextDouble(), next()
        
        try {
            //File    fileObj = new File(FILENAME);
            Scanner fileIn  = new Scanner(selectedFile);   // new Scanner(System.in)
            
            clearCanvas();
            
            int nStrokes = fileIn.nextInt();
            
            
            // loop through and read each stroke line and create an appropriate Stroke object
            // then add the stroke to array list, strokes
            // type cx cy size red green blue 
            for (int i = 0; i < nStrokes; ++i) {
                String type = fileIn.next();
                int    cx   = fileIn.nextInt();
                int    cy   = fileIn.nextInt();
                int    size = fileIn.nextInt();
                double r    = fileIn.nextDouble();
                double g    = fileIn.nextDouble();
                double b    = fileIn.nextDouble();
                Color  col  = Color.color(r, g, b);
                
                Stroke stroke = null;
                if (type.equalsIgnoreCase("circular")) { 
                    stroke = new CircularStroke(cx, cy, size, col);
                }
                else {
                    stroke = new SquareStroke(cx, cy, size, col);
                }
                
                strokes.add(stroke);      // <---- addStroke(stroke): can but should not
            }
            
            fileIn.close();
            
            paint();
        }
        catch(IOException e) {
            System.out.println(FILENAME + " could not be opened for reading");
            e.printStackTrace();
        }
    }
    
    /** binary saving/loading */
    
    public void fromBinaryFile(File fileObj) {
        try {
        	FileInputStream fIS = new FileInputStream(fileObj);
        	ObjectInputStream fIn = new ObjectInputStream(fIS);
        	clearCanvas();
        	int n = fIn.readInt(); // read the # of strokes
        	// read n # of stroke objects into stroke collection
        	Stroke s = (Stroke) fIn.readObject();
        	strokes.add(s); // add s to array list
        	fIn.close();
        	fIS.close();
        	paint();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
    }
    
    public void toBinaryFile(File fileObj) {
        try {
			FileOutputStream fOS = new FileOutputStream(fileObj);
			ObjectOutputStream fOut = new ObjectOutputStream(fOS);
			fOut.writeInt(5);
			
			for (Stroke s : strokes) {
				fOut.writeObject(s);
			}
			fOut.close();
			fOS.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
    }
    
}
