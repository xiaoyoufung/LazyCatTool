package se233.lazycattool.view.template.progressImageBar;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ProgressedImage extends BorderPane {

    Label fileName, fileSize;

    // Change close icon to 3 dots

    // icon
    private final ImageViewURL moreIcon = new ImageViewURL("assets/icons/threeDotIcon.png", 12, 12);
    private final ImageViewURL fileIcon = new ImageViewURL("assets/icons/Image.png", 18);
    private final IconWithBorder iconContainer = new IconWithBorder(fileIcon, 4, 2, 6);

    public ProgressedImage(String name, double size){
        this.getStyleClass().add("processed-image");
        fileName = new Label(name);
        fileSize = new Label(size + " MB");
        fileSize.getStyleClass().add("processed-file-size");

        // File's name and size
        HBox leftSection = genLeftArea();

        // Add all sections to main BorderPane
        this.setLeft(leftSection);
        this.setRight(moreIcon);
        BorderPane.setAlignment(moreIcon, Pos.CENTER);
    }

    private HBox genLeftArea(){
        HBox leftArea = new HBox(4);

        VBox fileDetail = new VBox(4);
        fileDetail.getChildren().addAll(fileName, fileSize);

        leftArea.getChildren().addAll(iconContainer, fileDetail);
        return leftArea;
    }

}