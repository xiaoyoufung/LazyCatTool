package se233.lazycattool.view;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.cropPane.SeperateLine;

public class EdgeDetectPane extends ScrollPane {
    public EdgeDetectPane(){}

    private Pane getDetailsPane(){
        Pane edgeDetectInfoPane = new VBox(20);
        edgeDetectInfoPane.setPrefWidth(400);

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
        mainArea.setPrefWidth(400);
        Label headLbl, algoLbl, algoSubLbl, uploadLbl, uploadSubLbl;

        headLbl = new Label("Detect Edge");
        SeperateLine line1 = new SeperateLine(mainArea, 1.25);


        algoLbl = new Label("Algorithm");
        algoSubLbl = new Label("Choose an option to cuztomize the edge detection algorithm.");

        // Three button

        // Create an HBox
        HBox hbox = new HBox();

        // Create three labels
        Label label1 = new Label("Label 1");
        Label label2 = new Label("Label 2");
        Label label3 = new Label("Label 3");

        // Add labels to the HBox
        hbox.getChildren().addAll(label1, label2, label3);

        // Set Hgrow for each label to make them take equal width
        HBox.setHgrow(label1, Priority.ALWAYS);
        HBox.setHgrow(label2, Priority.ALWAYS);
        HBox.setHgrow(label3, Priority.ALWAYS);

        // Set max width to infinity to ensure they grow
        label1.setMaxWidth(Double.MAX_VALUE);
        label2.setMaxWidth(Double.MAX_VALUE);
        label3.setMaxWidth(Double.MAX_VALUE);


        SeperateLine line2 = new SeperateLine(mainArea, 1.25);

        uploadLbl = new Label("Uploaded Images");
        uploadSubLbl = new Label("Images that have been uploaded to this program.");

        // main image
        // multiimage

        SeperateLine line3 = new SeperateLine(mainArea, 1.25);
        // button take full width

        mainArea.getChildren().addAll(headLbl, line1, algoLbl, algoSubLbl, hbox, line2, uploadLbl, uploadSubLbl, line3);

        return mainArea;
    }
}
