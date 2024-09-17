package se233.lazycattool.view.template.components;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import se233.lazycattool.model.ImageFile;
import java.util.ArrayList;
import java.util.List;

public class MultiPicturePane extends HBox {

    private final ImageViewURL addIcon = new ImageViewURL("assets/icons/addIcon.png", 14);
    IconWithBorder addButton = new IconWithBorder(addIcon, 11,11, 4);

    public MultiPicturePane(ArrayList<ImageFile> imageFiles, int imgIndex){
        this.setSpacing(12);

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

        this.getChildren().addAll(imageViews);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(addButton);
    }

    public IconWithBorder getAddButton() {
        return addButton;
    }
}
