package se233.lazycattool.view;

import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.cropPane.CropBtmSection;
import se233.lazycattool.view.template.cropPane.CropMidSection;
import se233.lazycattool.view.template.cropPane.CropTopSection;
import se233.lazycattool.view.template.cropPane.components.SeperateLine;

import java.util.ArrayList;

public class CropPane extends ScrollPane {
    public CropPane(){}
    private CropMidSection midSection;
    private ArrayList<ImageFile> allUploadedImages;

    private Pane getDetailsPane() {
        Pane cropInfoPane = new MainInfoPane("crop-pane");

        // use CropTopSection from [template/cropPane/CropTopSection]
        HBox topSection = new CropTopSection();

        // use MiddleSection from [template/cropPane/CropMidSection]
        midSection = new CropMidSection(allUploadedImages);

        // use Line from [template/cropPane/SeperateLine]
        Line line = new SeperateLine();

        // use BottomSection from [template/cropPane/CropBtmSection]
        Pane btmSection = new CropBtmSection();


        // when User click confirm button
        ((CropBtmSection) btmSection).getConfirmBtn().setOnMouseClicked(e -> {

            // add controller soon...
            CropImage cropImage = midSection.getMainImage().getCroppedImage();
            System.out.println(cropImage.getCropX());
        });


        cropInfoPane.getChildren().addAll(topSection, midSection, line, btmSection);
        return cropInfoPane;
    }

    public IconWithBorder getAddButton(){
        return midSection.getAddButton();
    }

    public void drawPane(ArrayList<ImageFile> allUploadedImages){
        // get uploadedImages from Launcher
        this.allUploadedImages = allUploadedImages;
        Pane cropInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(cropInfoPane);
    }

}
