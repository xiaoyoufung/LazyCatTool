package se233.lazycattool.view.template.components;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//List<String> originalList = Arrays.asList("a", "b", "c");
//List<String> newList = originalList.stream()
//        .map(String::toUpperCase)
//        .collect(Collectors.toList());

public class MultiPicturePane extends HBox {

    private final ImageViewURL addIcon = new ImageViewURL("assets/icons/addIcon.png", 14);
    IconWithBorder addButton = new IconWithBorder(addIcon, 11,11, 4);
    List<String> images = Arrays.asList(
            "assets/images/blue_dusk.png",
            "assets/images/blue_dusk.png",
            "assets/images/blue_dusk.png",
            "assets/images/blue_dusk.png",
            "assets/images/blue_dusk.png"
            );

    public MultiPicturePane(){
        this.setSpacing(12);

        // add Border around first image
        StackPane wrapFirstImg = new StackPane();
        wrapFirstImg.setStyle("-fx-padding: 2px; -fx-border-width: 2.5px; -fx-border-color: black; -fx-border-style: solid; -fx-border-radius: 6");

        // Create and collect ImageViewURL objects
        List<ImageViewURL> imageViews = images.stream()
                .map(image -> new ImageViewURL(image, 46, 30, 8))
                .collect(Collectors.toList());

        // Apply CSS class to the first image
        if (!imageViews.isEmpty()) {
            wrapFirstImg.getChildren().add(imageViews.getFirst());
            imageViews.removeFirst();
        }

        this.getChildren().add(wrapFirstImg);
        this.getChildren().addAll(imageViews);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(addButton);
    }

    public IconWithBorder getAddButton() {
        return addButton;
    }

}
