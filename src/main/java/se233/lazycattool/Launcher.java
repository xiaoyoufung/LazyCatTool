package se233.lazycattool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se233.lazycattool.exception.EmptyImageListException;
import se233.lazycattool.model.FileType;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.CropPane;
import se233.lazycattool.view.EdgeDetectPane;
import se233.lazycattool.view.SideBarPane;
import se233.lazycattool.view.UploadPane;
import se233.lazycattool.view.template.components.MultiPicturePane;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Launcher extends Application {
    private static Scene mainScene;
    private static BorderPane mainPane;
    private static SideBarPane sideBarPane = null;
    private static UploadPane uploadPane = null;
    private static CropPane cropPane = null;

    private static ArrayList<ImageFile> allCroppedImages = null;
    private static ArrayList<ImageFile> allUncroppedImages = null;

    private static ArrayList<ImageFile> allUnprocessedImages = null;
    private static ArrayList<ImageFile> allProcessedImages = null;

    public static CropPane getCropPane() {
        return cropPane;
    }
    public static BorderPane getMainPane() {
        return mainPane;
    }
    public static EdgeDetectPane getEdgeDetectPane() {
        return edgeDetectPane;
    }
    private static EdgeDetectPane edgeDetectPane = null;
    private static ArrayList<ImageFile> allUploadedImages = new ArrayList<>();

    private static ArrayList<ImageFile> tempImageFiles = new ArrayList<>();

    public static ArrayList<ImageFile> getAllUploadedImages() {
        return allUploadedImages;
    }

    public static void setAllUploadedImages(ArrayList<ImageFile> allUploadedImages) {
        Launcher.allUploadedImages = allUploadedImages;
    }

    public static void setAllCroppedImages(ArrayList<ImageFile> allCroppedImages) {
        Launcher.allCroppedImages = allCroppedImages;
    }

    public static void setAllOutcroppedImages(ArrayList<ImageFile> allUncroppedImages) {
        Launcher.allUncroppedImages = allUncroppedImages;
    }

    public static void setAllProcessedImages(ArrayList<ImageFile> allProcessedImages){
        Launcher.allProcessedImages = allProcessedImages;
    }

    public static void setAllUnprocessedImages(ArrayList<ImageFile> allUnprocessedImages){
        Launcher.allUnprocessedImages = allUnprocessedImages;
    }

    public void start(Stage primaryStage){
        primaryStage.setResizable(false);
        Font.loadFont(Launcher.class.getResource("assets/fonts/Inter-VariableFont_opsz,wght.ttf").toExternalForm(), 10);

        mainPane = new BorderPane();
        mainScene = new Scene(mainPane);

        mainScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());

        primaryStage.setScene(mainScene);

        initializeMainPane();

        primaryStage.show();
    }

    public void initializeMainPane(){
        sideBarPane = new SideBarPane();
        uploadPane = new UploadPane();
        cropPane = new CropPane();
        edgeDetectPane = new EdgeDetectPane();

        refreshPane();

        mainPane.setLeft(sideBarPane);

        // Set initial center pane
        mainPane.setCenter(uploadPane);
        sideBarPane.getUploadButton().setOnClick(true);

        // Add event handlers to buttons
        sideBarPane.getUploadButton().setOnAction(e -> switchRoot(uploadPane));
        sideBarPane.getCropButton().setOnAction(e -> switchRoot(cropPane));
        sideBarPane.getDetectEdgeButton().setOnAction(e -> switchRoot(edgeDetectPane));
//        cropPane.getAddButton().setOnMouseClicked(e -> switchRoot(uploadPane));
    }

    public static void refreshPane(){
        try {
            sideBarPane.drawPane();
            edgeDetectPane.drawPane(allUnprocessedImages, allProcessedImages);
            uploadPane.drawPane(allUploadedImages);
            cropPane.drawPane(allUncroppedImages, allCroppedImages);
            //edgeDetectPane.drawPane(allUploadedImages);
            System.out.println("refreshPane + " + allUploadedImages.size());
        }
         catch (EmptyImageListException e) {
            switchToUpload();
            showErrorAlert("No Images Available", e.getMessage());
            // You might want to load a default view or take other appropriate action
        }
    }

    public static void refreshUploadPane(){
        uploadPane.drawPane(allUploadedImages);
    }

    public static void refreshEdgeDetectPane(){
        edgeDetectPane.drawPane(allUnprocessedImages, allProcessedImages);
        Platform.runLater(() -> {
            edgeDetectPane.layout();
            mainPane.layout();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.sizeToScene();
        });
    }

    public static ArrayList<ImageFile> getAllCroppedImages() {
        return allUploadedImages.stream()
                .filter(ImageFile::isCropped)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<ImageFile> getAllOutcroppedImages() {
        return allUploadedImages.stream()
                .filter(img -> !img.isCropped())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<ImageFile> getAllOutprocessedImages(){
        return allUploadedImages.stream()
                .filter(img -> !img.isProcessed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<ImageFile> getAllProcessedImages(){
        return allUploadedImages.stream()
                .filter(ImageFile::isProcessed)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void refreshCropPane() {
        cropPane.drawPane(getAllOutcroppedImages(), getAllCroppedImages());
    }

    public static void resetCropSetting(ArrayList<ImageFile> allUploadedImages){
        // Reset allCroppedImages to be 0
        Launcher.setAllCroppedImages(new ArrayList<>());
        Launcher.setAllProcessedImages(new ArrayList<>());
        // Set allUncroppedImages
        Launcher.setAllOutcroppedImages(allUploadedImages);
        Launcher.setAllUnprocessedImages(allUploadedImages);
    }

    public static void switchToUpload(){
        switchRoot(uploadPane);
    }

    public static void switchRoot(Node newRoot) {
        BorderPane.setMargin(newRoot, new Insets(0)); // Ensure no margin

        try {
            if (newRoot instanceof CropPane) {
                if (allUploadedImages == null || allUploadedImages.isEmpty()) {
                    throw new EmptyImageListException("No images available for cropping.");
                }
                sideBarPane.getUploadButton().setOnClick(false);
                sideBarPane.getDetectEdgeButton().setOnClick(false);
                sideBarPane.getCropButton().setOnClick(true);
                mainPane.setCenter(newRoot);
            } else if (newRoot instanceof UploadPane) {
                sideBarPane.getCropButton().setOnClick(false);
                sideBarPane.getDetectEdgeButton().setOnClick(false);
                sideBarPane.getUploadButton().setOnClick(true);
                mainPane.setCenter(newRoot);
            } else if (newRoot instanceof EdgeDetectPane) {
                if (allUploadedImages == null || allUploadedImages.isEmpty()) {
                    throw new EmptyImageListException("No images available for edge detection.");
                }
                sideBarPane.getUploadButton().setOnClick(false);
                sideBarPane.getDetectEdgeButton().setOnClick(true);
                sideBarPane.getCropButton().setOnClick(false);
                mainPane.setCenter(newRoot);
                System.out.println("Edge Detect");
            }

            //Launcher.refreshPane();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.sizeToScene();
        } catch (EmptyImageListException e) {
            showErrorAlert("No Images Available", e.getMessage());
            switchToUpload();
        }

    }

    private static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
