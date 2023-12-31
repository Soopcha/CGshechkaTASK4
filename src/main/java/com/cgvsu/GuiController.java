package com.cgvsu;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.affine_transformation.AffineTransformations;
import com.cgvsu.affine_transformation.RotationOrder;
import com.cgvsu.model.TransformedModel;
import com.cgvsu.model.TriangulatedModelWithCorrectNormal;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.render_engine.*;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
import com.cgvsu.model.PolygonRemover;
import com.cgvsu.model.RemoveVertices;
import com.cgvsu.render_engine.RenderEngine;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.cgvsu.render_engine.Camera;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GuiController {
    public TextField newCameraX;
    public TextField newCameraY;
    public TextField newCameraZ;
    private TransformedModel transformedModel;
    private CameraManager cameraManager;
    @FXML
    private TextField removeVerticesField;

    @FXML
    private TextField removePolygonsField;


    private List<Boolean> modelVisibility = new ArrayList<>();
    private int activeModelIndex = 0;

    private int currentModelIndex = 0;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> modelComboBox;

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
    private Timeline timeline;
    @FXML
    private Button addCameraButton;

    @FXML
    private Button removeCameraButton;

    @FXML
    private Button switchCameraButton;


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

        Camera camera = new Camera(
                new Vector3(0, 0, 100),
                new Vector3(0, 0, 0),
                1.0F, 1, 0.01F, 100);
        cameraManager = new CameraManager();
        cameraManager.addCamera(camera);


        addCameraButton.setOnAction(event -> addCamera());
        removeCameraButton.setOnAction(event -> removeCamera());
        switchCameraButton.setOnAction(event -> switchCamera());

        // Инициализируем список видимости для каждой модели
        for (int i = 0; i < models.size(); i++) {
            modelVisibility.add(true);
        }

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            cameraManager.getCurrentCamera().setAspectRatio((float) (width / height));

            if (getActiveModel() != null) {
                Model model = getActiveModel();
                RenderEngine.render(canvas.getGraphicsContext2D(), cameraManager.getCurrentCamera(), transformedModel.getTransformations().transformModel(model), (int) width, (int) height);

            }
            for (Model model : models) {
                RenderEngine.render(canvas.getGraphicsContext2D(), cameraManager.getCurrentCamera(), model, (int) width, (int) height);
            }

            renderScene();
        });


        timeline.getKeyFrames().add(frame);
        timeline.play();
        canvas.setOnMouseMoved(event2 -> cameraManager.getCurrentCamera().handleMouseInput(event2.getX(), event2.getY(), false, false));
        canvas.setOnMouseDragged(event2 -> cameraManager.getCurrentCamera().handleMouseInput(event2.getX(), event2.getY(), event2.isPrimaryButtonDown(), event2.isSecondaryButtonDown()));
        canvas.setOnScroll(event2 -> {
            cameraManager.getCurrentCamera().mouseDeltaY = event2.getDeltaY();
            cameraManager.getCurrentCamera().handleMouseInput(event2.getX(), event2.getY(), false, false);
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
    private
    void addCamera() {
        try {
            double newCameraXValue = Double.parseDouble(newCameraX.getText());
            double newCameraYValue = Double.parseDouble(newCameraY.getText());
            double newCameraZValue = Double.parseDouble(newCameraZ.getText());

            Camera newCamera = new Camera(
                    new Vector3(newCameraXValue, newCameraYValue, newCameraZValue),
                    new Vector3(0, 0, 0),
                    1.0F, 1, 0.01F, 100);

            cameraManager.addCamera(newCamera);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void removeCamera() {
        List<String> cameraNames = new ArrayList<>();
        for (int i = 0; i < cameraManager.getNumCameras(); i++) {
            cameraNames.add("Camera " + (i + 1));
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(cameraNames.get(0), cameraNames);
        dialog.setTitle("Remove Camera");
        dialog.setHeaderText("Select the camera to remove:");
        dialog.setContentText("Camera:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cameraName -> {
            int selectedIndex = cameraNames.indexOf(cameraName);
            cameraManager.removeCamera(selectedIndex);
        });
    }

    @FXML
    private void switchCamera() {
        List<String> cameraNames = new ArrayList<>();
        for (int i = 0; i < cameraManager.getNumCameras(); i++) {
            cameraNames.add("Camera " + (i + 1));
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(cameraNames.get(0), cameraNames);
        dialog.setTitle("Switch Camera");
        dialog.setHeaderText("Select the camera to switch to:");
        dialog.setContentText("Camera:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(cameraName -> {
            int selectedIndex = cameraNames.indexOf(cameraName);
            cameraManager.switchCamera(selectedIndex);
        });
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
        AffineTransformations zeroTransformations = new AffineTransformations(
                RotationOrder.XYZ, 1, 1, 1,
                0, 0, 0,
                0, 0, 0);

        TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(getActiveModel());

        transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, zeroTransformations);
        xRotateField.setText("0");
        yRotateField.setText("0");
        zRotateField.setText("0");

        xScale.setText("1");
        yScale.setText("1");
        zScale.setText("1");

        translateX.setText("0");
        translateY.setText("0");
        translateZ.setText("0");
    }
    @FXML
    private void onResetTransformButtonClick() {
        try {
            if (getActiveModel() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Model Loaded");
                alert.setContentText("Please load a model before applying transformations.");
                alert.showAndWait();
                return;
            }
            resetTransformations();


        } catch (Exception e) {
            e.printStackTrace();
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
            String objName = String.valueOf(fileName.getFileName());
            Model model = ObjReader.read(fileContent);
            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(model);
            transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, new AffineTransformations());
            transformedModel.getTriangulatedModel().getInitialModel().modelName = objName;
            models.add(transformedModel.getTriangulatedModel().getInitialModel());
            updateModelComboBox();
        } catch (IOException exception) {
            // Ошибка при чтении файла.
            showErrorAlert("Error Reading File", "An error occurred while reading the file.");
        } catch (ObjReaderException objReaderException) {
            // Ошибка разбора файла OBJ.
            showErrorAlert("Error Parsing OBJ File", objReaderException.getMessage());
        } catch (IncorrectFileException incorrectFileException) {
            // Ошибка формата файла.
            showErrorAlert("Incorrect File Format", incorrectFileException.getMessage());
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        // Обработчик события для кнопки "Ок"
        alert.showAndWait().ifPresent(response -> {
            if (response == okButton) {
            }
        });
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
            ObjWriter.write(fileName, getActiveModel());
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
        ObjWriter.write(fileName, transformedModel.getTransformations().transformModel(getActiveModel()));
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

            Model transformedMesh = transformedModel.getTransformations().transformModel(activeModel);
            RenderEngine.render(canvas.getGraphicsContext2D(), cameraManager.getCurrentCamera(), transformedMesh, (int) canvas.getWidth(), (int) canvas.getHeight());

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

            AffineTransformations updatedTransformations = new AffineTransformations(
                    RotationOrder.XYZ, xScaleValue, yScaleValue, zScaleValue,
                    xRotate, yRotate, zRotate,
                    translateXValue, translateYValue, translateZValue);
            TriangulatedModelWithCorrectNormal triangulatedModelWithCorrectNormal = new TriangulatedModelWithCorrectNormal(getActiveModel());

            transformedModel = new TransformedModel(triangulatedModelWithCorrectNormal, updatedTransformations);

        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
    }

    public Model getActiveModel() {
        if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
            return models.get(activeModelIndex);
        } else {
            return null; // Индекс за пределами массива
        }
    }

    private void renderActiveModel() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        cameraManager.getCurrentCamera().setAspectRatio((float) (width / height));

        if (activeModelIndex >= 0 && activeModelIndex < models.size()) {
            Model activeModel = models.get(activeModelIndex);
         RenderEngine.render(canvas.getGraphicsContext2D(), cameraManager.getCurrentCamera(), transformedModel.getTransformations().transformModel(activeModel), (int) width, (int) height);
        }
    }

    private void renderAllModels() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
        cameraManager.getCurrentCamera().setAspectRatio((float) (width / height));

        for (int i = 0; i < models.size(); i++) {
            if (modelVisibility.get(i)) {
                Model model = models.get(i);
                RenderEngine.render(canvas.getGraphicsContext2D(), cameraManager.getCurrentCamera(), model, (int) width, (int) height);
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
            Model model = getActiveModel();
            if (getActiveModel() == null) {
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
                RemoveVertices.removeVertices(model, verticesToRemove);
            }

            if (!polygonsInput.isEmpty()) {
                String[] indicesString = polygonsInput.split(",");
                ArrayList<Integer> polygonsToRemove = new ArrayList<>();
                for (String indexStr : indicesString) {
                    polygonsToRemove.add(Integer.parseInt(indexStr.trim()));
                }
                PolygonRemover.removeSelectedPolygons(model, polygonsToRemove);
            }

            if (transformedModel != null) {
                transformedModel = new TransformedModel(new TriangulatedModelWithCorrectNormal(model), transformedModel.getTransformations());
            }

        } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}