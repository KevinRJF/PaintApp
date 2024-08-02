package com.example.javafxapps;

import javafx.scene.input.MouseEvent;

public class LineHandler extends DrawHandler {

	/**
	 * Constructor for LineHandler 
	 * @param sc shapecanvas
	 */
    public LineHandler(ShapeCanvas sc) {
        super(sc);
    }

    /**
     * appropiate myshape object is created and assigned to the internal shape variable
     * to the passed parameter
     */
    @Override
    protected void mousePressed(MouseEvent e) {
        // Create a new Line shape
            shape = new Line();
            super.mousePressed(e);
    }
}
