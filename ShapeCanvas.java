package com.example.javafxapps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Canvas for drawing shapes.
 */
public class ShapeCanvas extends Canvas {

	public static final String FILENAME = "shapeDrawing.txt";

	private GraphicsContext gc;
	private ArrayList<MyShape> shapes;
	private MyShape currShape;
	private boolean currFilled;
	private Color currColor;
	
	private Stack<Edit> stackUndo;
    private Stack<Edit> stackRedo;

	/**
	 * Constructor for ShapeCanvas.
	 *
	 * @param width  The width of the canvas
	 * @param height The height of the canvas
	 */
	public ShapeCanvas(double width, double height) {
		super(width, height);
		gc = getGraphicsContext2D();
		shapes = new ArrayList<>();
		currShape = null;
		currColor = Color.BLACK;
		currFilled = false;
		
		stackUndo = new Stack<>();
        stackRedo = new Stack<>();
	}
	
	// Method to add a new edit to the undo stack
    public void addEdit(Edit edit) {
        stackUndo.push(edit);
        stackRedo.clear(); // Clear redo stack when a new edit is added
    }
    
    // Undo the most recent edit
    public void undo() {
        if (!stackUndo.isEmpty()) { // Check for stack underflow
            Edit edit = stackUndo.pop();
            edit.undo(); // Call undo on the edit
            stackRedo.push(edit); // Save the undone edit for potential redo
            paint();
        }
    }
    
    // Redo the most recently undone edit
    public void redo() {
        if (!stackRedo.isEmpty()) { // Check for stack underflow
            Edit edit = stackRedo.pop();
            edit.redo(); // Call redo on the edit
            stackUndo.push(edit); // Move the edit back to the undo stack
            paint();
        }
    }
    
    // Check if undo operation is possible
    public boolean canUndo() {
        return !stackUndo.isEmpty();
    }

    // Check if redo operation is possible
    public boolean canRedo() {
        return !stackRedo.isEmpty();
    }
	
	public void drawBoundingBox(double startX, double startY, double endX, double endY) {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineDashes(5); // Set line dashes for the bounding box
        gc.strokeRect(startX, startY, endX - startX, endY - startY);
    }
	
	public ShapeGroup createGroup(double startX, double startY, double endX, double endY) {
        ShapeGroup group = new ShapeGroup();
        for (MyShape shape : shapes) {
            if (shape.within(startX, startY, endX, endY)) {
                group.addMember(shape);
            }
        }
        return group;
    }
	
	public ArrayList<MyShape> getShapes() {
	    return shapes;
	}

	// Method to find the shape closest to the given point (x, y)
	public MyShape closestShape(double x, double y) {
		if (shapes.isEmpty()) {
			return null;
		}

		// Initialize variables to keep track of the closest shape
		MyShape closest = shapes.get(0);
		double minDistance = closest.getCenter().distance(x, y);

		// Iterate over all shapes to find the closest one
		for (MyShape shape : shapes) {
			double distance = shape.getCenter().distance(x, y);
			if (distance < minDistance) {
				minDistance = distance;
				closest = shape;
			}
		}

		return closest;
	}
	
	public void clearBoundingBox() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        paint();
    }

	public void deleteShape(MyShape s) {
		shapes.remove(s);
		paint();
	}

	public Color getCurrColor() {
		return currColor;
	}

	public void setCurrColor(Color currColor) {
		this.currColor = currColor;
	}

	public boolean isCurrFilled() {
		return currFilled;
	}

	/**
	 * Add a shape to the canvas.
	 *
	 * @param s The shape to add
	 */
	public void addShape(MyShape s) {
		shapes.add(s);
		paint();
	}

	/**
	 * Clear the canvas.
	 */
	public void clear() {
		shapes.clear();
		currShape = null;
		paint();
	}

	/**
	 * Repaint the canvas.
	 */
	public void paint() {
		gc.clearRect(0, 0, getWidth(), getHeight());
		for (MyShape shape : shapes) {
			shape.draw(gc);
		}
		if (currShape != null) {
			currShape.draw(gc);
		}
	}
	
	public MyShape getCurrentShape() {
	    return currShape;
	}

	/**
	 * Set the current shape being drawn.
	 *
	 * @param s The current shape
	 */
	public void setCurrentShape(MyShape s) {
		currShape = s;
		if (currShape != null) {
			currShape.setColor(currColor);
			currShape.setFilled(currFilled);
		}
	}

	/**
	 * Set whether the current shape should be filled.
	 *
	 * @param filled True to fill the shape, false for outline only
	 */
	public void setCurrFilled(boolean filled) {
		currFilled = filled;
		if (currShape != null) {
//    		currShape.setFilled(currFilled);
			currShape.setColor(currColor);
		}
	}

	/**
	 * replaces the current mouse listener (press/release) and mouse motion listener
	 * (drag) with the passed listener object
	 *
	 * @param listener an EventHandler object
	 */
	public void replaceMouseHandler(EventHandler<MouseEvent> listener) {
		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);

		// add at the end of the method
		setOnMouseClicked(listener);
	}

	public void fromTextFile(File fileObj) {
	    try {
	        Scanner fileIn = new Scanner(fileObj);
	        clear();

	        while (fileIn.hasNext()) {
	            String type = fileIn.next();
	            if (type.equals("ShapeGroup")) {
	                ShapeGroup group = loadGroupText(fileIn);
	                shapes.add(group);
	            } else {
	                MyShape shape = null;
	                if (type.equalsIgnoreCase("line") || type.equalsIgnoreCase("oval") || type.equalsIgnoreCase("rectangle")) {
	                    shape = loadSingletonText(fileIn, type);
	                }
	                if (shape != null) {
	                    shapes.add(shape);
	                }
	            }
	        }

	        fileIn.close();
	        paint();
	    } catch (FileNotFoundException e) {
	        System.out.println(fileObj.getName() + " could not be opened for reading");
	        e.printStackTrace();
	    }
	}

	private MyShape loadSingletonText(Scanner fileIn, String shapeType) {
	    double x1 = fileIn.nextDouble();
	    double y1 = fileIn.nextDouble();
	    double x2 = fileIn.nextDouble();
	    double y2 = fileIn.nextDouble();
	    double r = fileIn.nextDouble();
	    double g = fileIn.nextDouble();
	    double b = fileIn.nextDouble();
	    Color col = Color.color(r, g, b);
	    boolean filled = fileIn.nextBoolean();

	    if (shapeType.equalsIgnoreCase("line")) {
	        return new Line(x1, y1, x2, y2, col);
	    } else if (shapeType.equalsIgnoreCase("oval")) {
	        return new Oval(x1, y1, x2, y2, col, filled);
	    } else if (shapeType.equalsIgnoreCase("rectangle")) {
	        return new Rect(x1, y1, x2, y2, col, filled);
	    }

	    return null;
	}

	private ShapeGroup loadGroupText(Scanner fileIn) {
	    ShapeGroup group = new ShapeGroup();
	    // Read the number of member shapes in the group
	    int nShapes = fileIn.nextInt();
	    // Load each member shape
	    for (int i = 0; i < nShapes; i++) {
	        String type = fileIn.next();
	        MyShape shape = loadSingletonText(fileIn, type);
	        if (shape != null) {
	            group.addMember(shape);
	        }
	    }
	    return group;
	}



	public void toTextFile(File fileObj) {
		// try-catch block
		try {
			PrintWriter fileOut = new PrintWriter(fileObj);

			fileOut.println(shapes.size());

			// for each Stroke in strokes, get the String representation of the stroke
			
			// and print to the file
			for (MyShape s : shapes) {
				// System.out.println(s.toString());
				fileOut.println(s.toString());
			}

			fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println(FILENAME + " could not be opened/created for writing");
			e.printStackTrace();
		}

	}

	public void fromBinaryFile(File fileObj) {
		try {
			FileInputStream fIS = new FileInputStream(fileObj);
			ObjectInputStream fIn = new ObjectInputStream(fIS);
			clear();
			int n = fIn.readInt();

			for (int i = 0; i < n; ++i) {
				MyShape s = (MyShape) fIn.readObject();
				shapes.add(s);
				System.out.println(s);
			}

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

			fOut.writeInt(shapes.size());

			for (MyShape s : shapes) {
				fOut.writeObject(s);
			}
			fOut.close();
			fOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}