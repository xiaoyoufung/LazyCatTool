package se233.lazycattool.controller;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;

import java.io.File;
import java.util.ArrayList;

public class CropController {
    public static String desPath;
    public static ArrayList<ImageFile> croppedImages = new ArrayList<>();

    public static final ArrayList<ImageFile> allUploadedImages = Launcher.getAllUploadedImages();
    public static final ArrayList<ImageFile> unCropImages = new ArrayList<>(allUploadedImages);

    public static void onMouseClicked(CropImage cropImage, ImageFile imageFile){
       if (croppedImages.size() < 1){

           // Prompt user to select path
           chooseDirectory(cropImage, imageFile);
           System.out.println(desPath);

           // add new Pane
       } else {

       }

    }

    public static void chooseDirectory(CropImage cropImage, ImageFile imageFile) {
        Stage stage = (Stage) Launcher.getMainPane().getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Ensure the primary stage is focused
        Platform.runLater(() -> {
            stage.requestFocus();
            File selectedDirectory = directoryChooser.showDialog(stage);

            // If the path is choose
            if (selectedDirectory != null) {

                desPath = selectedDirectory.getAbsolutePath();

                croppedImages.add(imageFile);
                unCropImages.removeFirst();


                System.out.println("Selected directory: " + desPath);
            } else {
                System.out.println("Directory selection cancelled.");
            }
        });

    }
}
