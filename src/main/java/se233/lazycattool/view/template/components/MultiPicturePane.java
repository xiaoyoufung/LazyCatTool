package se233.lazycattool.view.template.components;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import se233.lazycattool.model.ImageFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiPicturePane extends HBox {

    private final ImageViewURL addIcon = new ImageViewURL("assets/icons/addIcon.png", 14);
    IconWithBorder addButton = new IconWithBorder(addIcon, 11,11, 4);

    public MultiPicturePane(ArrayList<ImageFile> imageFiles){
        this.setSpacing(12);

        // add Border around first image
        StackPane firstImgBorder = new StackPane();
        firstImgBorder.getStyleClass().add("first-image");

        // Create and collect ImageViewURL objects
        List<ImageViewURL> imageViews = imageFiles.stream()
                .map(image -> new ImageViewURL(image.getFilepath(),"local", 46, 30, 8))
                .collect(Collectors.toList());

        // Apply CSS class to the first image
        if (!imageViews.isEmpty()) {
            firstImgBorder.getChildren().add(imageViews.getFirst());
            imageViews.removeFirst();
        }

        this.getChildren().add(firstImgBorder);
        this.getChildren().addAll(imageViews);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(addButton);
    }

    public IconWithBorder getAddButton() {
        return addButton;
    }

}
