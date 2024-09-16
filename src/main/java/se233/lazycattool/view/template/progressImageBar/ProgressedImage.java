package se233.lazycattool.view.template.progressImageBar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ProgressedImage extends BorderPane {

    Label fileName, fileSize, percent;

    // icon
    private final ImageViewURL closeIcon = new ImageViewURL("assets/icons/closeIcon.png", 10);

    public ProgressedImage(String name, double size){
        this.getStyleClass().add("processing-image");
        fileName = new Label(name);
        fileSize = new Label(size + " MB");
        percent = new Label(100 + "%");

        // File's name and size
        VBox leftSection = genLeftArea();

        // Add all sections to main BorderPane
        this.setLeft(leftSection);
        this.setRight(closeIcon);
    }

    public void isToggle(boolean isActive){
        if (isActive){
            this.getStyleClass().add("uploaded-file-active");
        } else {
            this.getStyleClass().removeAll("uploaded-file-active");
        }
    }

    public ImageViewURL getCloseIcon() {
        return closeIcon;
    }

    private ProgressBar genProgressBar(){
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefWidth(220);
        progressBar.setProgress(0.4);
        return progressBar;
    }

    private VBox genLeftArea(){
        VBox leftArea = new VBox(4);
        leftArea.getChildren().addAll(fileName, fileSize);
        //leftArea.setPadding(new Insets(0,52,0,0));
        return leftArea;
    }

}