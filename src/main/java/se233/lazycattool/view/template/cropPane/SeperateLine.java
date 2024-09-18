package se233.lazycattool.view.template.cropPane;

import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.shape.Line;

public class SeperateLine extends Line {

    public SeperateLine(double length, double weight){
        this.setStartX(0);
        this.setEndX(length);
        this.setStrokeWidth(weight);
        this.setStyle("-fx-stroke: #E4E7EC;");

    }

    public SeperateLine(Parent parent, double weight) {
        this.setStartX(0);
        this.setStrokeWidth(weight);
        this.setStyle("-fx-stroke: #E4E7EC;");

        // Add a listener to update the endX property when the parent's width changes
        parent.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            this.setEndX(newValue.getWidth());
        });
    }
}
