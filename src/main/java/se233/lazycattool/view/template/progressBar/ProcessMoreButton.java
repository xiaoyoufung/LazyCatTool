package se233.lazycattool.view.template.progressBar;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ProcessMoreButton extends BorderPane {
    ImageViewURL icon = new ImageViewURL("assets/icons/threeDotIcon.png", 15,15);
    private boolean isOnClick = false;
    ProcessPane processPane;

    public boolean isOnClick() { return isOnClick; }

    public void setOnClick(boolean onClick) {
        this.isOnClick = onClick;
    }

    public ProcessMoreButton(double width, double height, ProcessPane processPane){
        this.processPane = processPane;
        icon.setRotate(90);
        setMargin(icon, new Insets(height, width,height, width));
        this.setCenter(icon);
        this.onMoreIconClicked();
        this.setMaxSize(20,20);

        this.setOnMouseClicked(_ -> onMoreIconClicked());
    }

    public void setActiveStyle(boolean isOnClick){
        if (isOnClick){
            this.getStyleClass().remove("three-dot-icon");
            this.getStyleClass().add("three-dot-icon-active");
        } else {
            this.getStyleClass().add("three-dot-icon");
            this.getStyleClass().remove("three-dot-icon-active");
        }
    }

    public void onMoreIconClicked(){
        if (!this.isOnClick()){
            processPane.setVisible(false);
            setActiveStyle(false);
            this.setOnClick(true);
        } else {
            processPane.setVisible(true);
            setActiveStyle(true);
            this.setOnClick(false);
        }
    }
}
