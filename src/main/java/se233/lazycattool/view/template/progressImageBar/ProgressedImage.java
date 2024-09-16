package se233.lazycattool.view.template.progressImageBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ProgressedImage extends BorderPane {

    Label fileName, fileSize;

    // Change close icon to 3 dots

    // icon
    private final ImageViewURL closeIcon = new ImageViewURL("assets/icons/closeIcon.png", 10);
    private final ImageViewURL fileIcon = new ImageViewURL("assets/icons/imageFileIcon.png", 11);
    private final IconWithBorder iconContainer = new IconWithBorder(fileIcon, 9, 8, 5);

    public ProgressedImage(String name, double size){
        this.getStyleClass().add("processing-image");
        fileName = new Label(name);
        fileSize = new Label(size + " MB");

        // File's name and size
        HBox leftSection = genLeftArea();

        // Add all sections to main BorderPane
        this.setLeft(leftSection);
        this.setRight(closeIcon);
    }

    private HBox genLeftArea(){
        HBox leftArea = new HBox(4);

        VBox fileDetail = new VBox(4);
        fileDetail.getChildren().addAll(fileName, fileSize);

        leftArea.getChildren().addAll(iconContainer, fileDetail);
        return leftArea;
    }

}