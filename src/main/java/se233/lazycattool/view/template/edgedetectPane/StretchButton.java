package se233.lazycattool.view.template.edgedetectPane;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import static se233.lazycattool.controller.EdgeDetectController.onAlgorithmSelected;

public class StretchButton extends Label {
    private boolean isOnClick = false;

    public void setOnClick(boolean onClick) {
        isOnClick = onClick;
        setOnClickStyle();
    }

    public StretchButton(String text){
        this.setText(text);

        // Set max width to infinity to ensure they grow
        this.setMaxWidth(Double.MAX_VALUE);

        this.getStyleClass().add("stretch-btn");
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(13,0,13,0));
    }

    public void setOnAction() {
        this.setOnMouseClicked(event -> onAlgorithmSelected(event));
        //System.out.println("Select Algo");
    }

    private void setOnClickStyle(){
        if (this.isOnClick){
            this.getStyleClass().add("stretch-btn-active");
        } else {
            this.getStyleClass().remove("stretch-btn-active");
        }
    }
}
