package se233.lazycattool.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import se233.lazycattool.view.template.components.MainInfoPane;
import se233.lazycattool.view.template.cropPane.SeperateLine;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;

public class EdgeDetectPane extends ScrollPane {
    public EdgeDetectPane(){}
    private final double PANE_WIDTH = 545;
    private final double LINE_BOLD = 1.25;

    private Pane getDetailsPane(){
        Pane edgeDetectInfoPane = new MainInfoPane("edge-detect-pane");
        //edgeDetectInfoPane.setPrefWidth(PANE_WIDTH);

        Pane mainArea = genMainArea();

        edgeDetectInfoPane.getChildren().addAll(mainArea);
        return edgeDetectInfoPane;
    }

    public void drawPane(){
        Pane edgeDetectInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(edgeDetectInfoPane);
    }

    private VBox genMainArea(){
        VBox mainArea = new VBox(10);
        Label uploadLbl, uploadSubLbl;

        VBox mainTopArea = genMainTopArea();

        uploadLbl = new Label("Uploaded Images");
        uploadLbl.getStyleClass().add("small-heading");
        uploadSubLbl = new Label("Images that have been uploaded to this program.");

        // main image
        // multiimage

        SeperateLine line3 = new SeperateLine(PANE_WIDTH, LINE_BOLD);
        // button take full width

        mainArea.getChildren().addAll(mainTopArea, uploadLbl, uploadSubLbl, line3);

        return mainArea;
    }

    private VBox genMainTopArea(){
        VBox mainTopArea = new VBox(10);
        Label headLbl, algoLbl, algoSubLbl;

        headLbl = new Label("Detect Edge");
        headLbl.getStyleClass().add("heading");

        Line line1 = new SeperateLine(PANE_WIDTH, LINE_BOLD);
        //setPadding(line1, new Insets(10,0,20,0));

        VBox middleArea = genMainMiddleArea();

        SeperateLine line2 = new SeperateLine(PANE_WIDTH, LINE_BOLD);

        mainTopArea.getChildren().addAll(headLbl, line1, middleArea, line2);
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
        HBox btnArea = new HBox(6);
        btnArea.setAlignment(Pos.CENTER);

        // Create three labels
        Label cannyLbl = new StretchButton("Canny");
        Label laplacianLbl = new StretchButton("Laplacian");
        Label sobelLbl = new StretchButton("Sobel");

        // Add labels to the HBox
        btnArea.getChildren().addAll(cannyLbl, laplacianLbl, sobelLbl);

        // Set Hgrow for each label to make them take equal width
        HBox.setHgrow(cannyLbl, Priority.ALWAYS);
        HBox.setHgrow(laplacianLbl, Priority.ALWAYS);
        HBox.setHgrow(sobelLbl, Priority.ALWAYS);

        mainMiddleArea.setPadding(new Insets(12,0,12,0));
        mainMiddleArea.getChildren().addAll(algoLbl, algoSubLbl, btnArea);
        return mainMiddleArea;
    }
}
