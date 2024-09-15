package se233.lazycattool;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se233.lazycattool.model.FileType;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.CropPane;
import se233.lazycattool.view.EdgeDetectPane;
import se233.lazycattool.view.SideBarPane;
import se233.lazycattool.view.UploadPane;
import se233.lazycattool.view.template.components.MultiPicturePane;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    private static Scene mainScene;

    public static BorderPane getMainPane() {
        return mainPane;
    }

    private static BorderPane mainPane;
    private static SideBarPane sideBarPane = null;
    private static UploadPane uploadPane = null;
    private static CropPane cropPane = null;
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
        ImageFile image1 = new ImageFile("blue_dusk.jpg", "/Users/xiaoyoufung/Desktop/test-photo/blue_dusk.png", 2048.5, FileType.png);
        ImageFile image2 = new ImageFile("botanists_window.png", "/Users/xiaoyoufung/Desktop/test-photo/botanists_window.png", 1024.0, FileType.png);
        ImageFile image3 = new ImageFile("dusty_lilac.png", "/Users/xiaoyoufung/Desktop/test-photo/dusty_lilac.png", 512.75, FileType.png);
        ImageFile image4 = new ImageFile("summer_haze.png", "/Users/xiaoyoufung/Desktop/test-photo/summer_haze.png", 2334.8, FileType.png);

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
        mainPane.setCenter(cropPane);
        sideBarPane.getUploadButton().setOnClick(true);

        // Add event handlers to buttons
        sideBarPane.getUploadButton().setOnAction(e -> switchRoot(uploadPane));
        sideBarPane.getCropButton().setOnAction(e -> switchRoot(cropPane));
        sideBarPane.getDetectEdgeButton().setOnAction(e -> switchRoot(edgeDetectPane));
        cropPane.getAddButton().setOnMouseClicked(e -> switchRoot(uploadPane));
    }

    public static void refreshPane(){
        sideBarPane.drawPane();
        uploadPane.drawPane(allUploadedImages);
        cropPane.drawPane(tempImageFiles);
        edgeDetectPane.drawPane();
    }

    public static void refreshUploadPane(){
        uploadPane.drawPane(allUploadedImages);
    }

    public static void refreshCropPane(){cropPane.drawPane(tempImageFiles);}

    public static void switchRoot(Node newRoot) {
        BorderPane.setMargin(newRoot, new Insets(0)); // Ensure no margin

        // if click CropButton
        if (newRoot instanceof CropPane) {

            sideBarPane.getUploadButton().setOnClick(false);
            sideBarPane.getDetectEdgeButton().setOnClick(false);
            sideBarPane.getCropButton().setOnClick(true);
            mainPane.setCenter(newRoot);

            // if click UploadButton
        } else if (newRoot instanceof UploadPane) {

            sideBarPane.getCropButton().setOnClick(false);
            sideBarPane.getDetectEdgeButton().setOnClick(false);
            sideBarPane.getUploadButton().setOnClick(true);
            mainPane.setCenter(newRoot);

        } else if (newRoot instanceof EdgeDetectPane) {

            sideBarPane.getUploadButton().setOnClick(false);
            sideBarPane.getDetectEdgeButton().setOnClick(true);
            sideBarPane.getCropButton().setOnClick(false);
            mainPane.setCenter(newRoot);
        }

        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
