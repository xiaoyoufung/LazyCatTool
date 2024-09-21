package se233.lazycattool;

import javafx.application.Application;
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

public class Launcher extends Application {
    private static Scene mainScene;
    private static BorderPane mainPane;
    private static SideBarPane sideBarPane = null;
    private static UploadPane uploadPane = null;
    private static CropPane cropPane = null;

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
    private static MultiPicturePane multiPicturePane = null;

    private static ArrayList<ImageFile> tempImageFiles = new ArrayList<>();

    public static ArrayList<ImageFile> getAllUploadedImages() {
        return allUploadedImages;
    }

    public static void setAllUploadedImages(ArrayList<ImageFile> allUploadedImages) {
        Launcher.allUploadedImages = allUploadedImages;
    }

    public void start(Stage primaryStage){
        primaryStage.setResizable(false);
        Font.loadFont(Launcher.class.getResource("assets/fonts/Inter-VariableFont_opsz,wght.ttf").toExternalForm(), 10);

        // delete soon...
        ImageFile image1 = new ImageFile("blue_dusk.png", "/Users/xiaoyoufung/Desktop/test-photo-resize/blue_dusk.png", 2048.5, FileType.png);
        ImageFile image2 = new ImageFile("botanists_window.png", "/Users/xiaoyoufung/Desktop/test-photo-resize/botanists_window.png", 1024.0, FileType.png);
        ImageFile image3 = new ImageFile("dusty_lilac.png", "/Users/xiaoyoufung/Desktop/test-photo-resize/dusty_lilac.png", 512.75, FileType.png);
        ImageFile image4 = new ImageFile("summer_haze.png", "/Users/xiaoyoufung/Desktop/test-photo-resize/summer_haze.png", 2334.8, FileType.png);

        tempImageFiles.add(image1);
        tempImageFiles.add(image2);
        tempImageFiles.add(image3);
        tempImageFiles.add(image4);
        //

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
            uploadPane.drawPane(allUploadedImages);
            cropPane.drawPane(allUploadedImages);
            edgeDetectPane.drawPane(allUploadedImages);
            System.out.println("Draw + " + allUploadedImages.size());
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

    public static void refreshCropPane(ArrayList<ImageFile> imageFiles){
        cropPane.drawPane(imageFiles);
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
            }

            Launcher.refreshPane();
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
