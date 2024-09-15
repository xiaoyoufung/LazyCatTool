package se233.lazycattool.view.template.uploadPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;
import se233.lazycattool.view.template.components.IconWithBorder;

public class UploadedFile extends BorderPane {

    // icon
    private final ImageViewURL closeIcon = new ImageViewURL("assets/icons/closeIcon.png", 11);

    public UploadedFile(String name, double size){
        this.getStyleClass().add("uploaded-file");

        ImageViewURL fileIcon = new ImageViewURL("assets/icons/imageFileIcon.png", 14);
        IconWithBorder iconContainer = new IconWithBorder(fileIcon, 10);

        Label fileName = new Label(name);
        Label fileSize = new Label(size + " MB");
        Label percent = new Label(100 + "%");

        // File's name and size
        VBox fileContainer = new VBox(4);
        fileContainer.getChildren().addAll(fileName, fileSize);

        // File's icon + File's Container
        HBox leftSection = new HBox(10);
        leftSection.getChildren().addAll(iconContainer, fileContainer);
        leftSection.setPadding(new Insets(0,122,0,0));

        // Process bar and Upload's Percentage.
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefWidth(460);
        progressBar.setProgress(10);
        //percent.getStyleClass().add("percent-txt");

        HBox btmSection = new HBox(10);
        btmSection.setPadding(new Insets(10,0,0,48));
        btmSection.getChildren().addAll(progressBar, percent);
        btmSection.setAlignment(Pos.CENTER);

        // Add all sections to main BorderPane
        this.setLeft(leftSection);
        this.setRight(closeIcon);
        this.setBottom(btmSection);
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
}
