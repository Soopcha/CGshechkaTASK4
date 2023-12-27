package com.cgvsu;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.affine_transformation.AffineTransf;
import com.cgvsu.affine_transformation.OrderRotation;
import com.cgvsu.model.TransformedModel;
import com.cgvsu.model.TriangulatedModelWithCorrectNormal;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import com.cgvsu.objwriter.ObjWriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

//import com.cgvsu.model.TransformedTriangulatedModel;
import com.cgvsu.model.PolygonRemover;
import com.cgvsu.model.RemoveVertices;
import com.cgvsu.affine_transformation.AffineTransf;
import com.cgvsu.affine_transformation.OrderRotation;
import com.cgvsu.model.TransformedModel;
import com.cgvsu.model.TriangulatedModelWithCorrectNormal;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
import javax.vecmath.Vector3f;
import com.cgvsu.objwriter.ObjWriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;


public class GuiController {
    private TransformedModel transformedModel;

    final private float TRANSLATION = 0.5F;

    @FXML
    private TextField removeVerticesField;

    @FXML
    private TextField removePolygonsField;

    private int selectedModelIndex;

    private List<Boolean> modelVisibility = new ArrayList<>();
    private int activeModelIndex = 0;

    private int currentModelIndex = 0;
    @FXML
    AnchorPane anchorPane;

    private double mouseX, mouseY;

    @FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> modelComboBox; // Добавлен ComboBox

    private Model mesh = null;
    @FXML
    public TextField xRotateField;

    private List<Model> models = new ArrayList<>();

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
            new Vector3(0, 0, 100),
            new Vector3(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;



    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

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

        // Инициализируем список видимости для каждой модели
        for (int i = 0; i < models.size(); i++) {
            modelVisibility.add(true);
        }

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {

                // RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedModel.getTransformations().transformModel(mesh), (int) width, (int) height);

            }
            for (Model model : models) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height);
            }

            renderScene();
        });


        timeline.getKeyFrames().add(frame);
        timeline.play();
        canvas.setOnMouseMoved(event2 -> camera.handleMouseInput(event2.getX(), event2.getY(), false, false));
        canvas.setOnMouseDragged(event2 -> camera.handleMouseInput(event2.getX(), event2.getY(), event2.isPrimaryButtonDown(), event2.isSecondaryButtonDown()));
        canvas.setOnScroll(event2 -> {
            camera.mouseDeltaY = event2.getDeltaY();
            camera.handleMouseInput(event2.getX(), event2.getY(), false, false);
        });

        // Инициализация ComboBox с названиями моделей
        updateModelComboBox();
    }


    private void updateModelComboBox() {
        modelComboBox.getItems().clear();
        for (Model model : models) {
            modelComboBox.getItems().add(model.modelName);
        }
    }

    @FXML
    private void handleModelSelection(ActionEvent event) {
        int selectedIndex = modelComboBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < models.size()) {
            // Сброс трансформаций у предыдущей активной модели
            if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
                resetTransformations();
            }

            activeModelIndex = selectedIndex;

            // Устанавливаем видимость для выбранной модели в true, а для остальных в false
            for (int i = 0; i < modelVisibility.size(); i++) {
                modelVisibility.set(i, (i == activeModelIndex));
            }
        }
    }

    private void resetTransformations() {
        AffineTransf zeroTransformations = new AffineTransf(
                OrderRotation.XYZ, 1, 1, 1,
                0, 0, 0,
                0, 0, 0);

        TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(getActiveModel());

        transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, zeroTransformations);
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
            String objName = String.valueOf(fileName.getFileName());
            mesh = ObjReader.read(fileContent);
            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(mesh);
            transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, new AffineTransf());
            Model model = ObjReader.read(fileContent);
            models.add(model);
            model.modelName = objName;


            // todo: обработка ошибок
            updateModelComboBox();
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveModelMenuItemClick() {
        if (!models.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
            fileChooser.setTitle("Save Original Model");

            File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
            if (file == null) {
                return;
            }

            String fileName = file.getAbsolutePath();
            ObjWriter.write(fileName, models.get(currentModelIndex));
        }
    }

    public Model getActiveModel() {
        if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
            return transformedModel.getTransformations().transformModel(models.get(activeModelIndex));
        } else {
            return null; // Индекс за пределами массива
        }
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
        camera.movePosition(new Vector3(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3(0, -TRANSLATION, 0));
    }


    @FXML
    private void onTransformButtonClick() {
        try {
            Model activeModel = getActiveModel();
            if (activeModel == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Model Loaded");
                alert.setContentText("Please load a model before applying transformations.");
                alert.showAndWait();
                return;
            }
            updateTransformations();

//             Model transformedMesh = transformedModel.getTransformations().transformModel(mesh);
//             RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedMesh, (int) canvas.getWidth(), (int) canvas.getHeight());
            Model transformedMesh = transformedModel.getTransformations().transformModel(activeModel);
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedMesh, (int) canvas.getWidth(), (int) canvas.getHeight());

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
                    OrderRotation.ZYX, xScaleValue, yScaleValue, zScaleValue,
                    xRotate, yRotate, zRotate,
                    translateXValue, translateYValue, translateZValue);
            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(getActiveModel());

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

            camera.movePosition(new Vector3((float) (-deltaX * sensitivity), (float) (deltaY * sensitivity), 0));

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        }
    }

    private void handleScrollEvent(ScrollEvent event) {

        double sensitivity = 0.1;


        double deltaZoom = event.getDeltaY() * sensitivity;
        camera.movePosition(new Vector3(0, 0, (float) deltaZoom));
    }

    private void renderActiveModel() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        camera.setAspectRatio((float) (width / height));

        if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
            Model activeModel = models.get(activeModelIndex);
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformedModel.getTransformations().transformModel(activeModel), (int) width, (int) height);
        }
    }

    private void renderAllModels() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        camera.setAspectRatio((float) (width / height));

        for (int i = 0; i < models.size(); i++) {
            if (modelVisibility.get(i)) {
                Model model = models.get(i);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, model, (int) width, (int) height);
            }
        }
    }

    private void renderScene() {
        if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
            renderActiveModel();
        } else {
            renderAllModels();
        }
    }

    @FXML
    private void onRemoveButtonClick() {
        try {
            if (mesh == null) {
                return;
            }

            String verticesInput = removeVerticesField.getText().trim();
            String polygonsInput = removePolygonsField.getText().trim();

            if (!verticesInput.isEmpty()) {
                String[] indicesString = verticesInput.split(",");
                ArrayList<Integer> verticesToRemove = new ArrayList<>();
                for (String indexStr : indicesString) {
                    verticesToRemove.add(Integer.parseInt(indexStr.trim()));
                }
                RemoveVertices.removeVertices(mesh, verticesToRemove);
            }

            if (!polygonsInput.isEmpty()) {
                String[] indicesString = polygonsInput.split(",");
                ArrayList<Integer> polygonsToRemove = new ArrayList<>();
                for (String indexStr : indicesString) {
                    polygonsToRemove.add(Integer.parseInt(indexStr.trim()));
                }
                PolygonRemover.removeSelectedPolygons(mesh, polygonsToRemove);
            }

            if (transformedModel != null) {
                transformedModel = new TransformedModel(new TriangulatedModelWithCorrectNormal(mesh), transformedModel.getTransformations());
            }

        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}