package se233.lazycattool.view.template.progressBar;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ProcessMoreButton extends BorderPane {
    ImageViewURL icon = new ImageViewURL("assets/icons/threeDotIcon.png", 15,15);
    private boolean isOnClick = false;

    public boolean isOnClick() { return isOnClick; }

    public void setOnClick(boolean onClick) {
        isOnClick = onClick;
        setOnClickStyle();
    }

    public ProcessMoreButton(double width, double height){
        icon.setRotate(90);
        setMargin(icon, new Insets(height, width,height, width));
        this.setCenter(icon);
        this.getStyleClass().add("three-dot-icon-active");
        this.setMaxSize(20,20);
    }

    private void setOnClickStyle(){
        if (this.isOnClick){
            this.getStyleClass().remove("three-dot-icon");
            this.getStyleClass().add("three-dot-icon-active");
        } else {
            this.getStyleClass().add("three-dot-icon");
            this.getStyleClass().remove("three-dot-icon-active");
        }
    }
}
