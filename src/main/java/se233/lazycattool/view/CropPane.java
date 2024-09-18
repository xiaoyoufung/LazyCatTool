package se233.lazycattool.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.*;
import se233.lazycattool.view.template.cropPane.CropMainImage;
import se233.lazycattool.view.template.cropPane.CustomButton;
import se233.lazycattool.view.template.cropPane.SeperateLine;
import se233.lazycattool.view.template.progressImageBar.ProgressedImage;
import se233.lazycattool.view.template.progressImageBar.ProgressingImage;

// import from controller

import java.util.ArrayList;

import static se233.lazycattool.controller.CropController.*;

public class CropPane extends ScrollPane {
    public CropPane(){}

    public CropMainImage getMainImage() {
        return mainImage;
    }
    public static ArrayList<ImageFile> allImages;
    private CropMainImage mainImage;
    private ArrayList<ImageFile> unCropImages;
    private final CustomButton confirmBtn = new CustomButton("Confirm", "#101828", "#FFF");;
    private final CustomButton cancelBtn = new CustomButton("Cancel", "#FFF", "#101828");

    public static ArrayList<ImageFile> getAllImages() {
        return allImages;
    }

    public void setAllImages(ArrayList<ImageFile> allImages) {
        if (CropPane.allImages == null) {
            CropPane.allImages = new ArrayList<>(allImages);
        }
    }

    private Pane getDetailsPane() {
        BorderPane cropInfoPane = new BorderPane();

        Pane mainArea = genMainArea();

        Pane processArea = genProcessArea();

        cropInfoPane.setLeft(mainArea);

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

        HBox topArea = genMainTopArea();

        VBox middleArea = genMainMiddleArea();

        // use Line from [template/cropPane/SeperateLine]
        Line line = new SeperateLine(565,1.25);

        HBox btmArea = genMainBtmArea();

        mainArea.getChildren().addAll(topArea, middleArea, line, btmArea);

        return mainArea;
    }

    private HBox genMainTopArea(){
        HBox topArea = new HBox(16);
        topArea.setPadding(new Insets(0, 25, 0, 25));

        HeadingSection headingSection = new HeadingSection("Crop an image", "Upload a 1600 x 480 px image for best results.");
        ImageViewURL cropIcon = new ImageViewURL("assets/icons/cropIcon.png", 24);
        IconWithBorder cropIconContainer = new IconWithBorder(cropIcon, 12);

        topArea.getChildren().addAll(cropIconContainer, headingSection);
        return topArea;
    }

    private VBox genMainMiddleArea(){
        VBox middleArea = new VBox(20);
        middleArea.setPadding(new Insets(0, 25, 0,25));

        setAllImages(unCropImages);

        mainImage = new CropMainImage(unCropImages.getFirst().getFilepath());

        MultiPicturePane cropMultiplePic = new MultiPicturePane(getAllImages(), allImages.size() - unCropImages.size());
        cropMultiplePic.getAddButton().setOnMouseClicked(_ -> onAddButtonClicked());

        middleArea.getChildren().addAll(mainImage, cropMultiplePic);
        return middleArea;
    }

    private HBox genMainBtmArea(){
        HBox btmArea = new HBox(250);
        btmArea.setPadding(new Insets(0, 0, 20, 0));
        btmArea.setAlignment(Pos.CENTER);

        HBox btnContainer = new HBox(10);

        // when User click confirm button
        confirmBtn.setOnMouseClicked(_ -> {
            // get image crop size from CropMainImage getCroppedImage method.
            CropImage cropImage = getMainImage().getCroppedImage();
            onMouseClicked(cropImage, unCropImages);
        });

        cancelBtn.setBorder();
        cancelBtn.setOnMouseClicked(event -> {
            Launcher.refreshCropPane(unCropImages);
        });

        btnContainer.getChildren().addAll(cancelBtn, confirmBtn);

        HBox helpContainer = new HBox(5);
        ImageViewURL helpIcon = new ImageViewURL("assets/icons/questionIconGrey.png", 14);
        Label helpLbl = new Label("Support");
        helpContainer.getChildren().addAll(helpIcon, helpLbl);
        helpContainer.setAlignment(Pos.CENTER);

        btmArea.getChildren().addAll(helpContainer, btnContainer);
        return btmArea;
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
        processingLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14");

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

        Line line = new SeperateLine(270, 1.25);
        Line line1 = new SeperateLine(270, 1.25);
        Line line2 = new SeperateLine(270, 1.25);

        ProgressedImage progressedImage = new ProgressedImage("autumn_spring", 123);
        ProgressedImage progressedImage1 = new ProgressedImage("autumn_spring", 123);

        btmArea.getChildren().addAll(headingLbl, subHeadLbl, line, progressedImage, line1, progressedImage1, line2);
        return btmArea;
    }

}
