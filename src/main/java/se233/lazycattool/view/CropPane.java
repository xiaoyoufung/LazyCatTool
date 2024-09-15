package se233.lazycattool.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.cropPane.CropBtmSection;
import se233.lazycattool.view.template.cropPane.CropMidSection;
import se233.lazycattool.view.template.cropPane.CropTopSection;
import se233.lazycattool.view.template.cropPane.components.SeperateLine;

public class CropPane extends ScrollPane {
    public CropPane(){}
    private CropMidSection midSection;

    private Pane getDetailsPane(){
        Pane cropInfoPane = new MainInfoPane("crop-pane");

        // use CropTopSection from [template/cropPane/CropTopSection]
        HBox topSection = new CropTopSection();

        // use MiddleSection from [template/cropPane/CropMidSection]
        midSection = new CropMidSection();

        // use Line from [template/cropPane/SeperateLine]
        Line line = new SeperateLine();

        // use BottomSection from [template/cropPane/CropBtmSection]
        Pane btmSection = new CropBtmSection();

        cropInfoPane.getChildren().addAll(topSection, midSection, line, btmSection);
        return cropInfoPane;
    }

    public IconWithBorder getAddButton(){
        return midSection.getAddButton();
    }

    public void drawPane(){
        Pane cropInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(cropInfoPane);
    }

}
