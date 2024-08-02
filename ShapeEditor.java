package com.example.javafxapps;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ShapeEditor extends Application {
	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 700;
	private static final int CONTROL_HEIGHT = 40;
	private static final int CANVAS_HEIGHT = APP_HEIGHT - CONTROL_HEIGHT;

	private BorderPane mainPane;
	private ShapeCanvas canvas;
	private HBox controlPanel;
	private Button bnClear, bnUndo, bnRedo;
	private CheckBox cbFilled;
	private Color currentColor = Color.BLACK;
	private RadioButton rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;
	private DeleteHandler deleteHandler;
	private MoveHandler moveHandler;
	private CopyHandler copyHandler;
	private GroupHandler groupHandler;

	private ColorPicker colorPicker;
	private MenuBar menuBar;
	private Menu menuFile, menuAbout;
	private MenuItem miOpen, miSave, miOpenB, miSaveB;
	private FileChooser fcOpen, fcSave;

	@Override
	public void start(Stage stage) {
		mainPane = new BorderPane();

		setupCanvas();
		setupControls();
		setupMenu();

		mainPane.setCenter(canvas);
		mainPane.setBottom(controlPanel);

		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Shape Editor");
		stage.show();
	}

	public void setupMenu() {
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

	private void setupCanvas() {
		canvas = new ShapeCanvas(APP_WIDTH, CANVAS_HEIGHT);
	}

	private void setupControls() {
		controlPanel = new HBox();
		bnClear = new Button("Clear");
		cbFilled = new CheckBox("Filled");
		ToggleGroup shapeGroup = new ToggleGroup();
		rbLine = new RadioButton("Line");
		rbLine.setToggleGroup(shapeGroup);
		rbLine.setSelected(true); // Default selection
		rbOval = new RadioButton("Oval");
		rbOval.setToggleGroup(shapeGroup);
		rbRect = new RadioButton("Rectangle");
		rbRect.setToggleGroup(shapeGroup);

		rbDelete = new RadioButton("Delete");
		rbDelete.setToggleGroup(shapeGroup);

		rbMove = new RadioButton("Move");
		rbMove.setToggleGroup(shapeGroup);

		rbCopy = new RadioButton("Copy");
		rbCopy.setToggleGroup(shapeGroup);

		rbGroup = new RadioButton("Group");
		rbGroup.setToggleGroup(shapeGroup);
		
		bnUndo = new Button("Undo");
		bnUndo.setOnAction(e -> {
			if (canvas.canUndo()) {
                canvas.undo();
            }
		});
		
		bnRedo = new Button("Redo");
		bnRedo.setOnAction(e -> {
			if (canvas.canRedo()) {
                canvas.redo();
            }
		});

		colorPicker = new ColorPicker(currentColor); // create color picker
		colorPicker.setOnAction(e -> {
			Color selectedColor = colorPicker.getValue();
			canvas.setCurrColor(selectedColor); // Update current color in canvas
		});

		// Instantiate listeners
		lineHandler = new LineHandler(canvas);
		ovalHandler = new OvalHandler(canvas);
		rectHandler = new RectHandler(canvas);
		deleteHandler = new DeleteHandler(canvas);
		canvas.replaceMouseHandler(lineHandler);
		moveHandler = new MoveHandler(canvas);
		copyHandler = new CopyHandler(canvas);
		groupHandler = new GroupHandler(canvas);

		// Set event handlers
		bnClear.setOnAction(e -> {
			canvas.clear();
		});
		cbFilled.setOnAction(e -> {
			canvas.setCurrFilled(cbFilled.isSelected());
		});
		rbLine.setOnAction(e -> {
			canvas.replaceMouseHandler(lineHandler);
		});
		rbOval.setOnAction(e -> {
			canvas.replaceMouseHandler(ovalHandler);
		});
		rbRect.setOnAction(e -> {
			canvas.replaceMouseHandler(rectHandler);
		});

		rbDelete.setOnAction(e -> {
			canvas.replaceMouseHandler(deleteHandler);
		});

		rbMove.setOnAction(event -> {
			canvas.replaceMouseHandler(moveHandler);
		});

		rbCopy.setOnAction(e -> {
			canvas.replaceMouseHandler(copyHandler);
		});

		rbGroup.setOnAction(e -> {
			canvas.replaceMouseHandler(groupHandler);
		});
		
		rbGroup.setOnAction(e -> {
		    if (rbGroup.isSelected()) {
		        canvas.replaceMouseHandler(new GroupHandler(canvas));
		    }
		});
		
		controlPanel.getChildren().addAll(bnClear, cbFilled, rbLine, rbOval,
				rbRect, rbDelete, rbMove, rbCopy, rbGroup,
				bnUndo, bnRedo, colorPicker);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
