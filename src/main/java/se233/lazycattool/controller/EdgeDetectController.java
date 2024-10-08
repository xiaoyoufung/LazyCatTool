package se233.lazycattool.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ConfigEdge;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.EdgeDetectPane;
import se233.lazycattool.view.template.edgedetectPane.ConfigureSection;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;
import se233.lazycattool.view.template.progressBar.ProcessMoreButton;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import static se233.lazycattool.view.template.edgedetectPane.ConfigureSection.*;


public class EdgeDetectController {
    private static final EdgeDetectPane edgeDetectPane = Launcher.getEdgeDetectPane();
    private static final ProcessMoreButton moreBtn = edgeDetectPane.getThreeDotsButton();
    private static final ScrollPane processPane = edgeDetectPane.getProcessPane();
    public static String desPath;
    public static ConfigEdge configureEdge;

    public static String getChoosenAlgo() {
        return choosenAlgo;
    }

    private static String choosenAlgo = "Canny";

    public static void onAlgorithmSelected(MouseEvent event){

        Object source = event.getSource();
        if (source instanceof Label clickedLabel) {
            switch (clickedLabel.getId()) {
                case "Canny":
                    choosenAlgo = "Canny";
                    break;
                case "Laplacian":
                    choosenAlgo = "Laplacian";
                    break;
                case "Sobel":
                    choosenAlgo = "Sobel";
                    break;
            }
        }
        Launcher.refreshEdgeDetectPane();
    }

    public static void checkCurrentAlgo(){
        StretchButton cannyBtn = edgeDetectPane.getCannyLbl();
        StretchButton laplacianBtn = edgeDetectPane.getLaplacianLbl();
        StretchButton sobelBtn = edgeDetectPane.getSobelLbl();
        if (choosenAlgo == "Canny"){
            cannyBtn.setOnClick(true);
            laplacianBtn.setOnClick(false);
            sobelBtn.setOnClick(false);
        } else if (choosenAlgo == "Laplacian") {
            cannyBtn.setOnClick(false);
            laplacianBtn.setOnClick(true);
            sobelBtn.setOnClick(false);
        } else if (choosenAlgo == "Sobel") {
            cannyBtn.setOnClick(false);
            laplacianBtn.setOnClick(false);
            sobelBtn.setOnClick(true);
        }
    }

    // When user clicked Submit button
    public static void onSubmitAlgo(){

        if (choosenAlgo == "Canny"){
            // Apply Canny algorithms to the image.
            configureEdge = new ConfigEdge((int) getLowSlider(), (int) getHighSlider());

        } else if (choosenAlgo == "Laplacian") {
            // Apply Laplacian algorithms to the image.
            configureEdge = new ConfigEdge(ConfigureSection.getMarkSizeComboBox());

        } else if (choosenAlgo == "Sobel") {
            // Apply Sobel algorithms to the image.
            configureEdge = new ConfigEdge(
                    "Sobel",
                    ConfigureSection.getKernalSizeComboBox(),
                    (int) getThresholdSlider()
            );
        }

        System.out.println(Launcher.getAllOutprocessedImages().size());
        initializeEdgeDetectPane(Launcher.getAllOutprocessedImages());

    }

    public static void onArrowButtonClicked(){
        ConfigureSection.setClicked(!ConfigureSection.isClicked());

        Launcher.refreshEdgeDetectPane();
        checkCurrentAlgo();

        Stage stage = (Stage) Launcher.getMainPane().getScene().getWindow();
        stage.sizeToScene();
    }


    public static void initializeEdgeDetectPane(ArrayList<ImageFile> croppedImages) {

        // (1) This ensures that the directory chooser dialog is shown without blocking the JavaFX Application Thread.
        CompletableFuture.supplyAsync(() -> chooseDirectory())
                .thenAccept(selectedPath -> {
                    if (selectedPath != null) {
                        Platform.runLater(() -> {
                            desPath = selectedPath;
                            System.out.println("Selected directory: " + desPath);

                            // Add new Pane or perform other UI updates here
                            Launcher.refreshEdgeDetectPane();

                            // show ProcessPane
                            Launcher.getEdgeDetectPane().getThreeDotsButton().setVisible(true);

                            // ProcessButton onClick
                            Launcher.getEdgeDetectPane().getThreeDotsButton().setOnClick(true);
                            Launcher.getEdgeDetectPane().getThreeDotsButton().onMoreIconClicked();

                            Launcher.getCropPane().showProcessingPane();
                            //Launcher.getEdgeDetectPane().showProcessingPane();

                            startDetectingProcess();
                            //
                            //startCroppingProcess();
                        });
                    } else {
                        Platform.runLater(() -> {
                            System.out.println("No directory selected.");
                            // Handle the case when no directory is selected
                        });
                    }
                });
    }

    private static void startDetectingProcess() {
        ImageEdgeDetector imageDetector = new ImageEdgeDetector();
        new Thread(() -> {
            imageDetector.detectImages(Launcher.getAllOutprocessedImages(), desPath, Launcher.getEdgeDetectPane().getProgressingImages(), configureEdge);
        }).start();
    }

    // (2) Returns the selected path directly, or null if no directory was selected.
    public static String chooseDirectory() {
        CompletableFuture<String> future = new CompletableFuture<>();

        Platform.runLater(() -> {
            Stage stage = (Stage) Launcher.getMainPane().getScene().getWindow();
            DirectoryChooser directoryChooser = new DirectoryChooser();

            File selectedDirectory = directoryChooser.showDialog(stage);

            if (selectedDirectory != null) {
                String selectedPath = selectedDirectory.getAbsolutePath();
                future.complete(selectedPath);
            } else {
                showErrorDialog();
                System.out.println("Uncrop " + Launcher.getAllOutcroppedImages().size());
                System.out.println("Crop " + Launcher.getAllCroppedImages().size());
                future.complete(null);
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // (3) Handle showing the error dialog when no directory is selected
    private static void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Directory Selected");
        alert.setContentText("Please choose a directory to continue.");
        alert.showAndWait();
    }

}
