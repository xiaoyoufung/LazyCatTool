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

    Label fileName, fileSize, percent;

    // icon
    private final ImageViewURL closeIcon = new ImageViewURL("assets/icons/closeIcon.png", 11);
    private final ImageViewURL fileIcon = new ImageViewURL("assets/icons/imageFileIcon.png", 14);
    private final IconWithBorder iconContainer = new IconWithBorder(fileIcon, 10);

    public UploadedFile(String name, double size){
        this.getStyleClass().add("uploaded-file");
        fileName = new Label(name);
        fileSize = new Label(size + " MB");
        percent = new Label(100 + "%");


        // File's icon + File's Container
        HBox leftSection = genLeftArea();


        // Process bar and Upload's Percentage.
        HBox btmSection = genBottomArea();


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

    private ProgressBar genProgressBar(){
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefWidth(460);
        progressBar.setProgress(10);
        return progressBar;
    }

    private HBox genBottomArea(){
        ProgressBar progressBar = genProgressBar();

        HBox bottomArea = new HBox(10);
        bottomArea.setPadding(new Insets(10,0,0,48));
        bottomArea.getChildren().addAll(progressBar, percent);
        bottomArea.setAlignment(Pos.CENTER);
        return bottomArea;
    }

    private HBox genLeftArea(){
        HBox leftArea = new HBox(10);

        // File's name and size
        VBox fileContainer = new VBox(4);
        fileContainer.getChildren().addAll(fileName, fileSize);

        leftArea.getChildren().addAll(iconContainer, fileContainer);
        leftArea.setPadding(new Insets(0,122,0,0));
        return leftArea;
    }

}
