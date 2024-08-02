package com.example.javafxapps;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeGroup extends MyShape {
	private ArrayList<MyShape> group;

	public ShapeGroup() {
		group = new ArrayList<>();
	}

	public ShapeGroup(double startX, double startY, double endX, double endY) {
		super(new Point2D(Math.min(startX, endX), Math.min(startY, endY)),
				new Point2D(Math.max(startX, endX), Math.max(startY, endY)));
		group = new ArrayList<>();
		updateCenter();
	}

	// Method to return the list of all member shapes
	public ArrayList<MyShape> getMembers() {
		return group;
	}

	public boolean isEmpty() {
		return group.isEmpty();
	}

	public int size() {
		return group.size();
	}

	@Override
	public Object clone() {
		ShapeGroup clonedGroup = new ShapeGroup();
		for (MyShape shape : group) {
			clonedGroup.addMember((MyShape) shape.clone());
		}
		return clonedGroup;
	}

	public void addMember(MyShape shape) {
		if (!group.contains(shape)) {
			group.add(shape);
			updateCenter();
		}
	}

	public void removeMember(MyShape shape) {
		group.remove(shape);
		updateCenter();
	}

	public boolean within(MyShape shape) {
		double centerX = getCenter().getX();
		double centerY = getCenter().getY();
		double halfWidth = Math.abs(getP1().getX() - getP2().getX()) / 2;
		double halfHeight = Math.abs(getP1().getY() - getP2().getY()) / 2;
		return shape.getCenter().getX() >= centerX - halfWidth && shape.getCenter().getX() <= centerX + halfWidth
				&& shape.getCenter().getY() >= centerY - halfHeight && shape.getCenter().getY() <= centerY + halfHeight;
	}

	@Override
	public void draw(GraphicsContext g) {
		for (MyShape shape : group) {
			shape.draw(g);
		}

		g.setStroke(Color.LIGHTGRAY);
		g.setLineDashes(5);
		double x1 = Math.min(getP1().getX(), getP2().getX());
		double y1 = Math.min(getP1().getY(), getP2().getY());
		double x2 = Math.max(getP1().getX(), getP2().getX());
		double y2 = Math.max(getP1().getY(), getP2().getY());
		g.strokeRect(x1, y1, x2 - x1, y2 - y1);
	}

	@Override
	public void move(double dx, double dy) {
		for (MyShape shape : group) {
			shape.move(dx, dy);
		}
		updateCenter();
	}

	@Override
	public String toString() {
		String result = String.format("ShapeGroup %d %d %d %d %d:%n", group.size(), (int) getP1().getX(),
				(int) getP1().getY(), (int) getP2().getX(), (int) getP2().getY());
		for (MyShape shape : group) {
			result += String.format("%s%n", shape.toString());
		}
		return result;
	}

}
