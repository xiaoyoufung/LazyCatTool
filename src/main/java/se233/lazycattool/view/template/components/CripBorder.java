package se233.lazycattool.view.template.components;

import javafx.scene.shape.Rectangle;

public class CripBorder extends Rectangle {
    public CripBorder(double width, double height, double radius){
        this.setWidth(width);
        this.setHeight(height);
        this.setArcWidth(radius);
        this.setArcHeight(radius);
    }
}
