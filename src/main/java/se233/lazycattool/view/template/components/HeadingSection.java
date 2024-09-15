package se233.lazycattool.view.template.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.CropPane;

public class HeadingSection extends VBox {

    Label headLbl, subHeadLbl;

    public HeadingSection(String headTxt, String subHeadTxt){

        this.setSpacing(8);

        // Top Heading
        headLbl = new Label(headTxt);
        headLbl.getStyleClass().add( "heading" );

        subHeadLbl = new Label(subHeadTxt);
        subHeadLbl.getStyleClass().add("sub-heading");

        this.getChildren().addAll(headLbl, subHeadLbl);
    }
}
