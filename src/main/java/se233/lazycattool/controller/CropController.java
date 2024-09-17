package se233.lazycattool.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CropController {
    public static String desPath;
    public static ArrayList<ImageFile> croppedImages = new ArrayList<>();
    public static ArrayList<ImageFile> unCropImages;
    public static ArrayList<ImageFile> allImagesFile;

    public static void onMouseClicked(CropImage cropImage, ArrayList<ImageFile> imageFile) {

        if (allImagesFile == null) {
            allImagesFile = new ArrayList<>(imageFile);
        }

        unCropImages = imageFile;

        // ถ้ารูปที่ต้องการครอปมี 1 รูป
        if (imageFile.size() == 1){

            initializeCropPane();

        } else {
            // รูปที่ต้องการครอปมีมากกว่า 1 รูป
            //System.out.println(croppedImages.size());

            if (croppedImages.size() == allImagesFile.size()){

                // ขึ้นโฟลเดอร์ให้กดเลือก
                initializeCropPane();

                //unCropImages.add(allImagesFile.getLast());

            } else {
                unCropImages.removeFirst();
                croppedImages.add(imageFile.getFirst());
                Launcher.refreshCropPane(unCropImages);
            }
        }

    }

    public static void initializeCropPane() {

        // (1) This ensures that the directory chooser dialog is shown without blocking the JavaFX Application Thread.
        CompletableFuture.supplyAsync(() -> chooseDirectory())
                .thenAccept(selectedPath -> {
                    if (selectedPath != null) {
                        Platform.runLater(() -> {
                            desPath = selectedPath;
                            System.out.println("Selected directory: " + desPath);

                            //Launcher.refreshCropPane(unCropImages);


                            // Add new Pane or perform other UI updates here

                        });
                    } else {
                        Platform.runLater(() -> {
                            System.out.println("No directory selected.");
                            // Handle the case when no directory is selected
                        });
                    }
                });
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

    public static void onAddButtonClicked(){
        Launcher.switchToUpload();
    }
}