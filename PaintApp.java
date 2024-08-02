package com.example.javafxapps;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PaintApp extends Application {

	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 700;// add action listener to bnClear

	private static final int CONTROL_HEIGHT = 40;
	private static final int CANVAS_HEIGHT = APP_HEIGHT - CONTROL_HEIGHT;

	/** GUI variables */
	private BorderPane mainPane;
	private FlowPane controlPanel;
	private MyCanvas canvas;
	private RadioButton rbCircular, rbSquare;
	private Button bnClear;
	private Spinner<Integer> spinSize;
	private ColorPicker colorPicker;
	private MenuBar menuBar;
	private Menu menuFile, menuAbout;
	private MenuItem miOpen, miSave, miOpenB, miSaveB;
	private FileChooser fcOpen, fcSave;

	/** non-GUI (functional) variables */
	private Color currentColor = Color.BLACK;
	private int currentSize = 5;

	// (4) create button handler class here
	// private class ClearButtonHandler ...

	@Override
	public void start(Stage stage) {
		mainPane = new BorderPane();

		setupControls();
		setupMenu();
		setupCanvas();

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);

		stage.setScene(scene);
		stage.setTitle("CS112 FX PaintApp");

		stage.show();
	}

	private void setupMenu() {
		menuBar = new MenuBar();

		menuFile = new Menu("File");
		menuAbout = new Menu("About");

		miOpen = new MenuItem("Open");
		miSave = new MenuItem("Save");

		miOpenB = new MenuItem("Open Binary Format");
		miSaveB = new MenuItem("Save in Binary Format");

		fcOpen = new FileChooser();
		fcOpen.setTitle("Load a drawing");

		fcSave = new FileChooser();
		fcSave.setTitle("Save current drawing as");

		// action handlers to menu items
		miOpen.setOnAction(e -> {

			// fcOpen.setTitle("Load a drawing");
			File selectedFile = fcOpen.showOpenDialog(null);

			if (selectedFile != null) {
				canvas.fromTextFile(selectedFile);
			}
		});

		miSave.setOnAction(e -> {
			File newFile = fcSave.showSaveDialog(null);

			if (newFile != null) {
				canvas.toTextFile(newFile);
			}
		});

		miOpenB.setOnAction(e -> {

			// fcOpen.setTitle("Load a drawing");
			File selectedFile = fcOpen.showOpenDialog(null);

			if (selectedFile != null) {
				canvas.fromBinaryFile(selectedFile);
			}
		});

		miSaveB.setOnAction(e -> {
			File newFile = fcSave.showSaveDialog(null);

			if (newFile != null) {
				canvas.toBinaryFile(newFile);
			}
		});

		menuBar.getMenus().addAll(menuFile, menuAbout);

		menuFile.getItems().addAll(miOpen, miSave, miOpenB, miSaveB);

		// add the menu bar to the BorderPane
		mainPane.setLeft(menuBar);
	}

	private void setupControls() {

		controlPanel = new FlowPane();

		// RadioButtons: stroke type
		// isSelected(), setToggleGroup(ToggleGroup group)
		rbCircular = new RadioButton("Circle");
		rbSquare = new RadioButton("Square");

		// by default, circular strokes are drawn
		rbCircular.setSelected(true);

		ToggleGroup group = new ToggleGroup();
		rbCircular.setToggleGroup(group);
		rbSquare.setToggleGroup(group);

		// Button: clear canvas
		bnClear = new Button("Clear Canvas");

		// (1) add action listener to bnClear

		/*
		 * bnClear.setOnAction(e -> { canvas.clearCanvas(); });
		 */

		// (2) defines and instantiates an Action EventHandler that clears the canvas
		// anonymous class
		// bnClear.setOnAction(new EventHandler<ActionEvent>() {
		// public void handle(ActionEvent e) {
		// canvas.clearCanvas();
		// }
		// });

		// (3) inner class
		//
		class ClearButtonHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				canvas.clearCanvas();
			}
		}
		bnClear.setOnAction(new ClearButtonHandler());

		// spinner osea size control
		spinSize = new Spinner<>(1, 1800, currentSize, 1);

		// color pickkker
		colorPicker = new ColorPicker(currentColor);

		colorPicker.setOnAction(e -> {
			currentColor = colorPicker.getValue();
		});

		controlPanel.getChildren().addAll(rbCircular, rbSquare, spinSize, colorPicker, bnClear);

		mainPane.setTop(controlPanel);
	}

	private void setupCanvas() {
		canvas = new MyCanvas(APP_WIDTH, CANVAS_HEIGHT);

		// add mouse listeners to canvas
		// setOnMousePressed, setOnMouseDragged, setOnMouseReleased

		canvas.setOnMousePressed(e -> {
			// size cannot change while drawing (dragging)
			currentSize = spinSize.getValue();
		});

		canvas.setOnMouseDragged(e -> {
			// get mouse position
			double mx = e.getX();
			double my = e.getY();

			// create a circular stroke at mouse position
			Stroke s = null;

			if (rbCircular.isSelected()) {
				s = new CircularStroke(mx, my, currentSize, currentColor);
			} else {
				s = new SquareStroke(mx, my, currentSize, currentColor);
			}

			// add the stroke to canvas & repaint
			canvas.addStroke(s);

		});

		mainPane.setCenter(canvas);
		canvas.paint();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
