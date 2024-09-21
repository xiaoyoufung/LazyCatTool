package se233.lazycattool.view.template.progressBar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import se233.lazycattool.model.ImageFile;

import java.util.ArrayList;
import java.util.Map;

public class ProcessPane extends ScrollPane {
    public ProcessPane(ArrayList<ImageFile> unCropImages, Map<ImageFile, ProgressingImage> progressingImages){
        this.getStyleClass().add("process-pane");
        this.setMaxHeight(600);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox insideProcess = new VBox(12);
        insideProcess.getStyleClass().add("inside-process-pane");

        // Add some content to make it visible
        Label processLbl = new Label("Processing");
        processLbl.getStyleClass().add("small-heading");

        insideProcess.getChildren().add(processLbl);

        for (ImageFile image : unCropImages) {
            ProgressingImage progressingImage = new ProgressingImage(image.getName(), image.getSize());
            insideProcess.getChildren().add(progressingImage);
            progressingImages.put(image, progressingImage);
        }

        insideProcess.setPadding(new Insets(12));

        this.setContent(insideProcess);
    }
}
