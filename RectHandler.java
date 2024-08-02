package com.example.javafxapps;

import javafx.scene.input.MouseEvent;

public class RectHandler extends DrawHandler {

	/**
	 * Constructor for RectHandler 
	 * @param sc shapecanvas
	 */
    public RectHandler(ShapeCanvas sc) {
        super(sc);
    }

    /**
     * appropiate myshape object is created and assigned to the internal shape variable
     * to the passed parameter
     */
    @Override
    protected void mousePressed(MouseEvent e) {
        // Create a new Rect shape
        shape = new Rect();
        super.mousePressed(e);
    }
}
