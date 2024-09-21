package se233.lazycattool.view;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.exception.EmptyImageListException;
import se233.lazycattool.view.template.components.*;
import se233.lazycattool.view.template.cropPane.CropMainImage;
import se233.lazycattool.view.template.cropPane.CustomButton;
import se233.lazycattool.view.template.cropPane.SeperateLine;
import se233.lazycattool.view.template.progressBar.ProcessMoreButton;
import se233.lazycattool.view.template.progressBar.ProcessPane;
import se233.lazycattool.view.template.progressBar.ProgressingImage;

// import from controller

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static se233.lazycattool.controller.CropController.*;

public class CropPane extends AnchorPane {
    public CropPane(){}

    public CropMainImage getMainImage() {
        return mainImage;
    }
    public static ArrayList<ImageFile> allImages;
    private CropMainImage mainImage;

    public ArrayList<ImageFile> getUnCropImages() {
        return unCropImages;
    }

    private ArrayList<ImageFile> unCropImages;
    private final CustomButton confirmBtn = new CustomButton("Confirm", "#101828", "#FFF");;
    private final CustomButton cancelBtn = new CustomButton("Cancel", "#FFF", "#101828");
    private ProcessPane processArea;

    public Map<ImageFile, ProgressingImage> getProgressingImages() {
        return progressingImages;
    }

    private final Map<ImageFile, ProgressingImage> progressingImages = new HashMap<>();

    public ProcessMoreButton getThreeDotsButton() {
        return threeDotsButton;
    }

    private ProcessMoreButton threeDotsButton;

    public static ArrayList<ImageFile> getAllImages() {
        return allImages;
    }

    public void setAllImages(ArrayList<ImageFile> allImages) {
        if (CropPane.allImages == null) {
            CropPane.allImages = new ArrayList<>(allImages);
            //System.out.println("create new Allimages");
        } else {
            CropPane.allImages = new ArrayList<>(allImages);
        }
    }

    private Pane getDetailsPane() {
        Pane cropInfoPane = new AnchorPane();
        processArea = new ProcessPane(croppedImages, progressingImages);
        Pane mainArea = genMainArea();
        VBox mainAreaContainer = new VBox(mainArea);
        cropInfoPane.getChildren().addAll(mainAreaContainer, processArea);

        AnchorPane.setTopAnchor(mainAreaContainer, 0.0);
        AnchorPane.setLeftAnchor(mainAreaContainer, 0.0);
        AnchorPane.setRightAnchor(mainAreaContainer, 0.0);
        AnchorPane.setBottomAnchor(mainAreaContainer, 0.0);

        AnchorPane.setTopAnchor(processArea, 69.0);
        AnchorPane.setRightAnchor(processArea, 13.0);
        return cropInfoPane;
    }

    public void drawPane(ArrayList<ImageFile> allUploadedImages){
        // get uploadedImages from Launcher
        this.unCropImages = new ArrayList<>(allUploadedImages);
        Pane cropInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color:#FFF;");
        this.getChildren().add(cropInfoPane);
    }

    private Pane  genMainArea(){
        // Debug
        //System.out.println("Uncropped file:  " + unCropImages.size() + ", Allimages is " );

        //System.out.println("Launcher imag is " + Launcher.getAllUploadedImages().size());
        if (allImages == null){
            System.out.println("null");
        } else {
            System.out.println(allImages.size());
        }

        MainInfoPane mainArea = new MainInfoPane("crop-pane");

        if (!unCropImages.isEmpty()){
            BorderPane topArea = genMainTopArea();

            VBox middleArea = genMainMiddleArea();

            // use Line from [template/cropPane/SeperateLine]
            Line line = new SeperateLine(565,1.25);

            HBox btmArea = genMainBtmArea();

            mainArea.getChildren().addAll(topArea, middleArea, line, btmArea);
        }

        return mainArea;
    }

    private BorderPane genMainTopArea(){
        BorderPane topArea = new BorderPane();

        HBox topLeftArea = new HBox(16);
        topArea.setPadding(new Insets(0, 25, 0, 25));

        HeadingSection headingSection = new HeadingSection("Crop an image", "Upload a 1600 x 480 px image for best results.");
        ImageViewURL cropIcon = new ImageViewURL("assets/icons/cropIcon.png", 24);
        IconWithBorder cropIconContainer = new IconWithBorder(cropIcon, 12);

        // More button
        threeDotsButton = new ProcessMoreButton( 15,9, processArea);
        threeDotsButton.setVisible(false);

        //threeDotsButton.setOnMouseClicked(_ -> onMoreIconClicked());

        topLeftArea.getChildren().addAll(cropIconContainer, headingSection);
        topArea.setLeft(topLeftArea);
        topArea.setRight(threeDotsButton);
        return topArea;
    }

    private VBox genMainMiddleArea(){
        VBox middleArea = new VBox(20);
        middleArea.setPadding(new Insets(0, 25, 0,25));



//        if (unCropImages == null || unCropImages.isEmpty()) {
//            Launcher.switchToUpload();
//            throw new EmptyImageListException("No images available for cropping.");
//        }

        mainImage = new CropMainImage(unCropImages.getFirst().getFilepath());
        MultiPicturePane cropMultiplePic = new MultiPicturePane(getAllImages(), 0);
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

    public void showProcessingPane() {
        if (processArea != null) {
            processArea.setVisible(true);
            processArea.toFront();

            // Ensure the processArea is properly positioned
            AnchorPane.setTopAnchor(processArea, 69.0);
            AnchorPane.setRightAnchor(processArea, 13.0);

            // If you want to animate the appearance, you could add a fade-in effect
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), processArea);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }
}
