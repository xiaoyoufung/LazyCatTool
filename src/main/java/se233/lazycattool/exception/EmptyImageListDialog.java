package se233.lazycattool.exception;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class EmptyImageListDialog extends Dialog<Boolean> {

    public EmptyImageListDialog(Stage owner) {
        this.initOwner(owner);
        setTitle("No Images Available");
        setHeaderText("There are no images available for cropping.");

        ButtonType uploadButtonType = new ButtonType("Upload Image", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(uploadButtonType, cancelButtonType);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label messageLabel = new Label("Would you like to upload a new image?");
        content.getChildren().add(messageLabel);

        getDialogPane().setContent(content);

        setResultConverter(dialogButton -> {
            if (dialogButton == uploadButtonType) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image File");
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                
                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(owner);
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    //ImportFileController.handleFileUpload(selectedFiles);
                    System.out.println(selectedFiles.size());
                    return true;
                }
            }
            return false;
        });
    }
}
