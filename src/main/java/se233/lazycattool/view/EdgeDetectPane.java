package se233.lazycattool.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.CripBorder;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.components.MultiPicturePane;
import se233.lazycattool.view.template.cropPane.SeperateLine;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;

import java.util.ArrayList;

public class EdgeDetectPane extends ScrollPane {
    public EdgeDetectPane(){}
    private final double PANE_WIDTH = 545;
    private final double LINE_BOLD = 1.25;
    private ArrayList<ImageFile> unProcessedImages;
    VBox mainBtmArea;

    public StretchButton getCannyLbl() {
        return cannyLbl;
    }

    public StretchButton getLaplacianLbl() {
        return laplacianLbl;
    }

    public StretchButton getSobelLbl() {
        return sobelLbl;
    }

    StretchButton cannyLbl;
    StretchButton laplacianLbl;
    StretchButton sobelLbl;

    private Pane getDetailsPane(){
        Pane edgeDetectInfoPane = new MainInfoPane("edge-detect-pane");
        //edgeDetectInfoPane.setPrefWidth(PANE_WIDTH);

        Pane mainArea = genMainArea();

        edgeDetectInfoPane.getChildren().addAll(mainArea);
        return edgeDetectInfoPane;
    }

    public void drawPane(ArrayList<ImageFile> allUploadedImages){
        // BEWARE!! the order is matter!
        this.unProcessedImages = allUploadedImages;
        Pane edgeDetectInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(edgeDetectInfoPane);
    }

    private VBox genMainArea(){
        VBox mainArea = new VBox(20);


        VBox mainTopArea = genMainTopArea();

        VBox middleArea = genMainMiddleArea();

        SeperateLine line2 = new SeperateLine(PANE_WIDTH, LINE_BOLD);

        VBox bottomArea = genMainBtmArea();

        SeperateLine line3 = new SeperateLine(PANE_WIDTH, LINE_BOLD);

        HBox confirmButton = genConfirmBtn();

        // button take full width

        mainArea.getChildren().addAll(mainTopArea, middleArea, line2, bottomArea, line3, confirmButton);

        return mainArea;
    }

    private VBox genMainTopArea(){
        VBox mainTopArea = new VBox(10);
        Label headLbl, algoLbl, algoSubLbl;

        headLbl = new Label("Detect Edge");
        headLbl.getStyleClass().add("heading");

        Line line1 = new SeperateLine(PANE_WIDTH, LINE_BOLD);
        //setPadding(line1, new Insets(10,0,20,0));

        mainTopArea.getChildren().addAll(headLbl, line1);
        return mainTopArea;
    }

    private VBox genMainMiddleArea(){
        VBox mainMiddleArea = new VBox(12);
        Label algoLbl, algoSubLbl;

        algoLbl = new Label("Algorithm");
        algoLbl.getStyleClass().add("small-heading");

        algoSubLbl = new Label("Choose an option to cuztomize the edge detection algorithm.");
        algoSubLbl.getStyleClass().add("sub-heading");

        // Three button
        HBox buttonArea = genSelectButtonArea();

        mainMiddleArea.getChildren().addAll(algoLbl, algoSubLbl, buttonArea);
        return mainMiddleArea;
    }

    private HBox genSelectButtonArea(){
        HBox selectBtnArea = new HBox(6);

        // Create three labels
        cannyLbl = new StretchButton("Canny");
        laplacianLbl = new StretchButton("Laplacian");
        sobelLbl = new StretchButton("Sobel");

        cannyLbl.setId("Canny");
        laplacianLbl.setId("Laplacian");
        sobelLbl.setId("Sobel");

        cannyLbl.setOnAction();
        laplacianLbl.setOnAction();
        sobelLbl.setOnAction();

        selectBtnArea.setAlignment(Pos.CENTER);

        // Add labels to the HBox
        selectBtnArea.getChildren().addAll(cannyLbl, laplacianLbl, sobelLbl);

        // Set Hgrow for each label to make them take equal width
        HBox.setHgrow(cannyLbl, Priority.ALWAYS);
        HBox.setHgrow(laplacianLbl, Priority.ALWAYS);
        HBox.setHgrow(sobelLbl, Priority.ALWAYS);

        return selectBtnArea;
    }

    private VBox genMainBtmArea(){
        mainBtmArea = new VBox(12);
        Label uploadLbl, uploadSubLbl;

        uploadLbl = new Label("Uploaded Images");
        uploadLbl.getStyleClass().add("small-heading");
        uploadSubLbl = new Label("Images that have been uploaded to this program.");
        uploadSubLbl.getStyleClass().add("sub-heading");

        ImageView mainImage = genMainImage();

        MultiPicturePane multiPictureArea = new MultiPicturePane(unProcessedImages, 0);

        mainBtmArea.getChildren().addAll(uploadLbl, uploadSubLbl, mainImage, multiPictureArea);
        return mainBtmArea;
    }

    private ImageView genMainImage() {
        if (unProcessedImages == null || unProcessedImages.isEmpty()) {
            System.out.println("No images available to display");
            return new ImageView(); // Return an empty ImageView
        }

        String filepath = unProcessedImages.getFirst().getFilepath();
        Image image = new Image("file:" + filepath);
        ImageView imageView = new ImageView(image);

        // make image's border radius of 6px
        Rectangle clip = new CripBorder(PANE_WIDTH, 200, 10);
        imageView.setClip(clip);

        resizeImageView(imageView);
        return imageView;
    }

    private void resizeImageView(ImageView imageView) {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(PANE_WIDTH);
        imageView.setFitHeight(200);

        double imageWidth = imageView.getImage().getWidth();
        double imageHeight = imageView.getImage().getHeight();

        // Calculate the center coordinates
        double centerX = imageWidth / 2;
        double centerY = imageHeight / 2;

        // Calculate the viewport coordinates
        double viewportX = centerX - (545.0 / 2);
        double viewportY = centerY - ((double) 200 / 2);

        // Ensure the viewport does not exceed the image boundaries
        viewportX = Math.max(0, viewportX);
        viewportY = Math.max(0, viewportY);

        // Set the viewport to display the center part of the image
        imageView.setViewport(new Rectangle2D(viewportX, viewportY, 545.0, 200));
    }

    private HBox genConfirmBtn(){
        HBox confirmBtnArea = new HBox();
        confirmBtnArea.setPrefWidth(PANE_WIDTH);
        Label confirmBtn = new Label("Confirm");

        // Set max width to infinity to ensure they grow
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        confirmBtn.setAlignment(Pos.CENTER);
        confirmBtn.getStyleClass().add("confirm-btn");
        confirmBtn.setPadding(new Insets(16,0,16,0));

        // Allow the label to grow horizontally
        HBox.setHgrow(confirmBtn, Priority.ALWAYS);

        confirmBtnArea.setPadding(new Insets(0, 0, 25, 0));
        confirmBtnArea.getChildren().add(confirmBtn);
        return confirmBtnArea;
    }
}
