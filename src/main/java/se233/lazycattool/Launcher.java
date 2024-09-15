package se233.lazycattool;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
    private static EdgeDetectPane edgeDetectPane = null;
    private static ArrayList<ImageFile> allUploadedImages = new ArrayList<>();
    private static MultiPicturePane multiPicturePane = null;

    public static ArrayList<ImageFile> getAllUploadedImages() {
        return allUploadedImages;
    }

    public static void setAllUploadedImages(ArrayList<ImageFile> allUploadedImages) {
        Launcher.allUploadedImages = allUploadedImages;
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
        multiPicturePane = new MultiPicturePane();

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
        cropPane.drawPane();
        edgeDetectPane.drawPane();
    }

    public static void refreshUploadPane(){
        uploadPane.drawPane(allUploadedImages);
    }

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
