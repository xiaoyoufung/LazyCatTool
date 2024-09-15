package se233.lazycattool.view;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.cropPane.CropBtmSection;
import se233.lazycattool.view.template.cropPane.CropMidSection;
import se233.lazycattool.view.template.cropPane.CropTopSection;
import se233.lazycattool.view.template.cropPane.components.SeperateLine;

import static se233.lazycattool.controller.CropController.onMouseClicked;

import java.util.ArrayList;

public class CropPane extends ScrollPane {
    public CropPane(){}
    private CropMidSection midSection;
    private ArrayList<ImageFile> unCropImages;


    private Pane getDetailsPane() {
        Pane cropInfoPane = new MainInfoPane("crop-pane");

        // use CropTopSection from [template/cropPane/CropTopSection]
        HBox topSection = new CropTopSection();

        // use MiddleSection from [template/cropPane/CropMidSection]
        midSection = new CropMidSection(unCropImages);

        // use Line from [template/cropPane/SeperateLine]
        Line line = new SeperateLine();

        // use BottomSection from [template/cropPane/CropBtmSection]
        CropBtmSection btmSection = new CropBtmSection();


        // when User click confirm button
        btmSection.getConfirmBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CropImage cropImage = midSection.getMainImage().getCroppedImage();
                System.out.println(cropImage.getCropX());

                onMouseClicked(cropImage, unCropImages.getFirst());
            }
        });


        cropInfoPane.getChildren().addAll(topSection, midSection, line, btmSection);
        return cropInfoPane;
    }

    public IconWithBorder getAddButton(){
        return midSection.getAddButton();
    }

    public void drawPane(ArrayList<ImageFile> allUploadedImages){
        // get uploadedImages from Launcher
        this.unCropImages = allUploadedImages;
        Pane cropInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(cropInfoPane);
    }

}
