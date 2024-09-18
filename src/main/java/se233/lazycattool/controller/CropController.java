package se233.lazycattool.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.CropPane;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CropController {
    public static String desPath;
    public static ArrayList<ImageFile> croppedImages = new ArrayList<>();
    public static ArrayList<ImageFile> unCropImages;

    public static void onAddButtonClicked(){ Launcher.switchToUpload(); }

    public static void onMouseClicked(CropImage cropImage, ArrayList<ImageFile> imageFile) {
        unCropImages = imageFile;
        ImageFile selectedImage = imageFile.getFirst();
        selectedImage.setCropInfo(cropImage);

        // Crop one image
        if (CropPane.allImages.size() == 1){
            initializeCropPane(croppedImages);
        } else { // Crop multiple images
            if (croppedImages.size() != CropPane.allImages.size() - 1){
                unCropImages.removeFirst();
                croppedImages.add(selectedImage);

                Launcher.refreshCropPane(unCropImages);
            } else { // If crop all images
                croppedImages.add(selectedImage);

                initializeCropPane(croppedImages);

//                for (ImageFile image: croppedImages){
//                    System.out.println("Image" + image.getName() + ": " + image.getCropInfo().getCropX() + ", " + image.getCropInfo().getCropY());
//                }
            }
        }
    }

    public static void initializeCropPane(ArrayList<ImageFile> croppedImages) {

        // (1) This ensures that the directory chooser dialog is shown without blocking the JavaFX Application Thread.
        CompletableFuture.supplyAsync(() -> chooseDirectory())
                .thenAccept(selectedPath -> {
                    if (selectedPath != null) {
                        Platform.runLater(() -> {
                            desPath = selectedPath;
                            System.out.println("Selected directory: " + desPath);

                            ImageCropper imageCropper = new ImageCropper();
                            imageCropper.cropImages(croppedImages, desPath);
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


}