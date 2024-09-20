package se233.lazycattool.view.template.components;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class IconWithBorder extends BorderPane {

    public IconWithBorder(ImageViewURL icon, double size){
        // set margin around icon
        setMargin(icon, new Insets(0, size,0, size));

        this.setCenter(icon);
        this.getStyleClass().add("file-icon-container");
    }

    // for create Add-Button
    public IconWithBorder(ImageViewURL icon, double width, double height, double radius){
        setMargin(icon, new Insets(height, width,height, width));
        this.setCenter(icon);
        this.getStyleClass().add("file-icon-container");
        this.setStyle("-fx-border-radius: " + radius + "; -fx-cursor: hand;");
    }

    public IconWithBorder(ImageViewURL icon, double width, double height){
        setMargin(icon, new Insets(height, width,height, width));
        this.setCenter(icon);
        this.getStyleClass().add("three-dot-icon-active");
    }


}
