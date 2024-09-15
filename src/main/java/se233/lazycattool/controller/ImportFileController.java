package se233.lazycattool.controller;

import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.model.FileType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ImportFileController {
    public static void onDragOver(DragEvent event){
        Dragboard db = event.getDragboard();

        // Check if file name is .zip .png .jpg or not?
        final boolean isAccepted = db.getFiles().getFirst().getName().toLowerCase()
                .endsWith(".zip") || db.getFiles().getFirst().getName().toLowerCase()
                .endsWith(".png") || db.getFiles().getFirst().getName().toLowerCase()
                .endsWith(".jpg");

        // if it's accept and copy to the dragBoard
        if (db.hasFiles() && isAccepted){
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }
    }

    public static void onDragDropped(DragEvent event) throws IOException {
        Dragboard db = event.getDragboard();

        // Get all uploaded Image
        ArrayList<ImageFile> allImages = Launcher.getAllUploadedImages();

        ZipExtractor extractZipFile = new ZipExtractor();

        boolean success = false;
        if (db.hasFiles()){
            success = true;
            String filePath;
            String fileName;
            Double fileSize;
            int total_files = db.getFiles().size();

            // Loop through all files
            for (int i = 0; i < total_files; i++) {
                File file = db.getFiles().get(i);
                filePath = file.getAbsolutePath();
                fileName = file.getName();
                fileSize = (double) (file.length() / 1024);
                FileType fileType = fileName.endsWith(".png") ? FileType.png : FileType.jpg;

                // Check if the Drop file is .zip -> extract file
                if (fileName.endsWith(".zip")){
                    extractZipFile.extractZip(filePath);
                } else {
                    allImages.add(new ImageFile(fileName, filePath, fileSize, fileType));
                }
            }

            Launcher.setAllUploadedImages(allImages);
            Launcher.refreshUploadPane();
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
