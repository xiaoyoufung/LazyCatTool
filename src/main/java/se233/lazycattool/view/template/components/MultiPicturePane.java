package se233.lazycattool.view.template.components;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import java.util.ArrayList;
import java.util.List;
//import static se233.lazycattool.controller.CropController.onAddButtonClicked;

public class MultiPicturePane extends HBox {

    private final ImageViewURL addIcon = new ImageViewURL("assets/icons/addIcon.png", 14);
    IconWithBorder addButton = new IconWithBorder(addIcon, 11,11, 4);
    public static final ArrayList<ImageFile> imageFiles = Launcher.getAllUploadedImages();
    public static final int TOTAL_IMAGES = Launcher.getAllUploadedImages().size();

    public MultiPicturePane(int imgIndex){
        this.setSpacing(12);
        System.out.println(imageFiles.size() + "Multi");

        if (imageFiles != null){
            // Create and collect ImageViewURL objects
            List<StackPane> imageViews = imageFiles.stream()
                    .map(image -> new ImageFileURL(image.getFilepath(), 46, 30, 8))
                    .map(StackPane::new)
                    .toList();

            if (imgIndex != imageViews.size()){
                // Apply CSS class to the selected image
                if (!imageViews.isEmpty()) {
                    imageViews.get(imgIndex).getStyleClass().add("first-image");
                }
            }

            //addButton.setOnMouseClicked(_ -> onAddButtonClicked());

            this.getChildren().addAll(imageViews);
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(addButton);
        }
    }
}
