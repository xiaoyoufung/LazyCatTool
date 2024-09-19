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
import se233.lazycattool.view.template.cropPane.SeperateLine;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;

import java.util.ArrayList;

public class EdgeDetectPane extends ScrollPane {
    public EdgeDetectPane(){}
    private final double PANE_WIDTH = 545;
    private final double LINE_BOLD = 1.25;
    private ArrayList<ImageFile> unProcessedImages;
    VBox mainBtmArea;

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
        VBox mainArea = new VBox(10);


        VBox mainTopArea = genMainTopArea();

        VBox middleArea = genMainMiddleArea();

        SeperateLine line2 = new SeperateLine(PANE_WIDTH, LINE_BOLD);

        VBox bottomArea = genMainBtmArea();

        SeperateLine line3 = new SeperateLine(PANE_WIDTH, LINE_BOLD);
        // button take full width

        mainArea.getChildren().addAll(mainTopArea, middleArea, line2, bottomArea, line3);

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

        mainMiddleArea.setPadding(new Insets(12,0,12,0));
        mainMiddleArea.getChildren().addAll(algoLbl, algoSubLbl, buttonArea);
        return mainMiddleArea;
    }

    private HBox genSelectButtonArea(){
        HBox selectBtnArea = new HBox(6);

        // Create three labels
        Label cannyLbl = new StretchButton("Canny");
        Label laplacianLbl = new StretchButton("Laplacian");
        Label sobelLbl = new StretchButton("Sobel");

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

        mainBtmArea.getChildren().addAll(uploadLbl, uploadSubLbl, mainImage);
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
        Rectangle clip = new CripBorder(PANE_WIDTH, 200, 6);
        imageView.setClip(clip);

        resizeImageView(imageView, PANE_WIDTH, 200);

        return imageView;
    }

    private void resizeImageView(ImageView imageView, double width, double height) {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        double imageWidth = imageView.getImage().getWidth();
        double imageHeight = imageView.getImage().getHeight();

        // Calculate the center coordinates
        double centerX = imageWidth / 2;
        double centerY = imageHeight / 2;

        // Calculate the viewport coordinates
        double viewportX = centerX - (width / 2);
        double viewportY = centerY - (height / 2);

        // Ensure the viewport does not exceed the image boundaries
        viewportX = Math.max(0, viewportX);
        viewportY = Math.max(0, viewportY);

        // Set the viewport to display the center part of the image
        imageView.setViewport(new Rectangle2D(viewportX, viewportY, width, height));
    }
}
