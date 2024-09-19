package se233.lazycattool.view.template.edgedetectPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class StretchButton extends Label {
    public StretchButton(String text){
        this.setText(text);

        // Set max width to infinity to ensure they grow
        this.setMaxWidth(Double.MAX_VALUE);

        this.getStyleClass().add("stretch-btn");
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(13,0,13,0));
    }
}
