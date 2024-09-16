package se233.lazycattool.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.cropPane.CropBtmSection;
import se233.lazycattool.view.template.cropPane.CropMidSection;
import se233.lazycattool.view.template.cropPane.CropTopSection;
import se233.lazycattool.view.template.cropPane.components.SeperateLine;
import se233.lazycattool.view.template.progressImageBar.ProgressingImage;

import static se233.lazycattool.controller.CropController.onMouseClicked;

import java.util.ArrayList;

public class CropPane extends ScrollPane {
    public CropPane(){}
    private CropMidSection midSection;
    private ArrayList<ImageFile> unCropImages;


    private Pane getDetailsPane() {
        BorderPane cropInfoPane = new BorderPane();

        Pane mainArea = genMainArea();

        Pane processArea = genProcessArea();

        cropInfoPane.setLeft(mainArea);
        cropInfoPane.setRight(processArea);
        return cropInfoPane;
    }

    public void drawPane(ArrayList<ImageFile> allUploadedImages){
        // get uploadedImages from Launcher
        this.unCropImages = allUploadedImages;
        Pane cropInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(cropInfoPane);
    }

    private Pane  genMainArea(){
        MainInfoPane mainArea = new MainInfoPane("crop-pane");

        // use CropTopSection from [template/cropPane/CropTopSection]
        HBox topSection = new CropTopSection();

        // use MiddleSection from [template/cropPane/CropMidSection]
        midSection = new CropMidSection(unCropImages);

        // use Line from [template/cropPane/SeperateLine]
        Line line = new SeperateLine(565,1.25);

        // use BottomSection from [template/cropPane/CropBtmSection]
        CropBtmSection btmSection = new CropBtmSection();

        // when User click confirm button
        btmSection.getConfirmBtn().setOnMouseClicked(event -> {
            CropImage cropImage = midSection.getMainImage().getCroppedImage();
            System.out.println(cropImage.getCropX());

            onMouseClicked(cropImage, unCropImages);
        });

        mainArea.getChildren().addAll(topSection, midSection, line, btmSection);

        return mainArea;
    }

    private Pane genProcessArea(){
        VBox processArea = new VBox(25);
        processArea.setPadding(new Insets(25,9,5,9));
        processArea.getStyleClass().add("process-area");
        processArea.setPrefWidth(270);

        VBox topArea = genProcessTopArea();
        VBox btmArea = genProcessBtmArea();

        processArea.getChildren().addAll(topArea, btmArea);
        return processArea;
    }

    private VBox genProcessTopArea(){
        VBox topArea = new VBox(10);
        Label processingLbl = new Label("Processing");
        processingLbl.setStyle("-fx-font-weight: bold");

        // Processing Image show [!!! we will adjust process bar here !!!]
        Pane progressingImage = new ProgressingImage("blue_dusk", 203);

        topArea.getChildren().addAll(processingLbl, progressingImage);
        return topArea;
    }

    private VBox genProcessBtmArea(){
        VBox btmArea = new VBox(8);
        Label headingLbl, subHeadLbl;
        headingLbl = new Label("Processed Images");
        subHeadLbl = new Label("Files and assets that have been processed in this program.");

        headingLbl.getStyleClass().add("heading");
        subHeadLbl.getStyleClass().add("sub-heading");

        Line line = new SeperateLine(270, 1);


        btmArea.getChildren().addAll(headingLbl, subHeadLbl, line);
        return btmArea;
    }
}
