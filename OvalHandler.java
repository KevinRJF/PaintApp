package com.example.javafxapps;

import javafx.scene.input.MouseEvent;

public class OvalHandler extends DrawHandler {

	/**
	 * Constructor for OvalHandler 
	 * @param sc shapecanvas
	 */
    public OvalHandler(ShapeCanvas sc) {
        super(sc);
    }

    /**
     * appropiate myshape object is created and assigned to the internal shape variable
     * to the passed parameter
     */
    @Override
    protected void mousePressed(MouseEvent e) {
        // Create a new Oval shape
        shape = new Oval();
        super.mousePressed(e);
    }
}
