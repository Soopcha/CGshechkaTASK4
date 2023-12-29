package com.cgvsu;

//import com.cgvsu.model.TransformedTriangulatedModel;
import com.cgvsu.affine_transformation.AffineTransf;
import com.cgvsu.affine_transformation.OrderRotation;
import com.cgvsu.model.Polygon;
import com.cgvsu.model.TransformedModel;
import com.cgvsu.model.TriangulatedModelWithCorrectNormal;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.render_engine.RenderEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.vecmath.Vector3f;
import com.cgvsu.objwriter.ObjWriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;


public class GuiController {
    private TransformedModel transformedModel;
    private double[][] zBuffer;


    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;
    private double mouseX, mouseY;
    @FXML
    private Canvas canvas;

    private Model mesh = null;
    @FXML
    public TextField xRotateField;
    @FXML
    public TextField yRotateField;
    @FXML
    public TextField zRotateField;
    @FXML
    public TextField xScale;
    @FXML
    public TextField yScale;
    @FXML
    public TextField zScale;
    @FXML
    public TextField translateX;
    @FXML
    public TextField translateY;
    @FXML
    public TextField translateZ;


    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        zBuffer = new double[(int) canvas.getWidth()][(int) canvas.getHeight()];
//        for (int i = 0; i < canvas.getWidth(); i++) {
//            for (int j = 0; j < canvas.getHeight(); j++) {
//                zBuffer[i][j] = Double.POSITIVE_INFINITY; // Инициализация значением положительной бесконечности
//            }
//        }
        for (double[] row : zBuffer) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }



        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        xRotateField.setText("0");
        yRotateField.setText("0");
        zRotateField.setText("0");

        xScale.setText("1");
        yScale.setText("1");
        zScale.setText("1");

        translateX.setText("0");
        translateY.setText("0");
        translateZ.setText("0");



        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();
            clearZBuffer();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedModel.getTransformations().transformModel(mesh), (int) width, (int) height,zBuffer);

            }

        });



        timeline.getKeyFrames().add(frame);
        timeline.play();
        canvas.addEventHandler(MouseEvent.ANY, this::handleMouseEvents);
        canvas.addEventHandler(ScrollEvent.SCROLL, this::handleScrollEvent);
    }
    private void clearZBuffer() {

//        for (int i = 0; i < canvas.getWidth(); i++) {
//            for (int j = 0; j < canvas.getHeight(); j++) {
//                zBuffer[i][j] = Double.POSITIVE_INFINITY;
//            }
//        }
        for (double[] row : zBuffer) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }
    }


    @FXML
    private void onOpenModelMenuItemClick() throws IncorrectFileException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(mesh);
            transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, new AffineTransf());



            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Original Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        String fileName = file.getAbsolutePath();
        ObjWriter.write(fileName, mesh);
    }

    @FXML
    private void onSaveTransformedModelButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Transformed Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        String fileName = file.getAbsolutePath();
        ObjWriter.write(fileName, transformedModel.getTransformations().transformModel(mesh));
    }
    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }


    @FXML
    private void onTransformButtonClick() {
        try {
            if (mesh == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Model Loaded");
                alert.setContentText("Please load a model before applying transformations.");
                alert.showAndWait();
                return;
            }
            updateTransformations();

            //нижнее 2 стр были поч в комите
            // Model transformedMesh = transformedModel.getTransformations().transformModel(mesh);
             //RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedMesh, (int) canvas.getWidth(), (int) canvas.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateTransformations() {
        try {
            double xRotate = Double.parseDouble(xRotateField.getText());
            double yRotate = Double.parseDouble(yRotateField.getText());
            double zRotate = Double.parseDouble(zRotateField.getText());

            double xScaleValue = Double.parseDouble(xScale.getText());
            double yScaleValue = Double.parseDouble(yScale.getText());
            double zScaleValue = Double.parseDouble(zScale.getText());

            double translateXValue = Double.parseDouble(translateX.getText());
            double translateYValue = Double.parseDouble(translateY.getText());
            double translateZValue = Double.parseDouble(translateZ.getText());

            AffineTransf updatedTransformations = new AffineTransf(
                    OrderRotation.XYZ, xScaleValue, yScaleValue, zScaleValue,
                    xRotate, yRotate, zRotate,
                    translateXValue, translateYValue, translateZValue);

            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(mesh);

            transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, updatedTransformations);

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
    }
    private void handleMouseEvents(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;

            double sensitivity = 0.2;

            camera.movePosition(new Vector3f((float) (-deltaX * sensitivity), (float) (deltaY * sensitivity), 0));

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        }
    }

    private void handleScrollEvent(ScrollEvent event) {

        double sensitivity = 0.1;


        double deltaZoom = event.getDeltaY() * sensitivity;
        camera.movePosition(new Vector3f(0, 0, (float) deltaZoom));
    }

}