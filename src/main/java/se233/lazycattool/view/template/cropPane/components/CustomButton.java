package se233.lazycattool.view.template.cropPane.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class CustomButton extends StackPane {

    public CustomButton(String buttonLblTxt, String bgColor, String lblTxtColor){
        Label buttonLbl = new Label(buttonLblTxt);
        buttonLbl.setPadding(new Insets(10,18,10,18));

        this.setStyle("-fx-background-color: " + bgColor + "; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");

        buttonLbl.setStyle("-fx-text-fill: " + lblTxtColor + "; " +
                "-fx-font-family: 'Inter', sans-serif; " +
                "-fx-font-weight: 600; -fx-font-size: 15;");
        this.getChildren().add(buttonLbl);
    }

    public void setBorder(){
        this.getStyleClass().add("btn");
    }
}
