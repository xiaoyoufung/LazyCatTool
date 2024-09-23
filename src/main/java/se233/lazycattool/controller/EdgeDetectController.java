package se233.lazycattool.controller;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import se233.lazycattool.Launcher;
import se233.lazycattool.view.EdgeDetectPane;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;
import se233.lazycattool.view.template.progressBar.ProcessMoreButton;

import java.util.Objects;

public class EdgeDetectController {
    private static final EdgeDetectPane edgeDetectPane = Launcher.getEdgeDetectPane();
    private static final ProcessMoreButton moreBtn = edgeDetectPane.getThreeDotsButton();
    private static final ScrollPane processPane = edgeDetectPane.getProcessPane();
    private static String choosenAlgo;

    public static void onAlgorithmSelected(MouseEvent event){
        StretchButton cannyBtn = edgeDetectPane.getCannyLbl();
        StretchButton laplacianBtn = edgeDetectPane.getLaplacianLbl();
        StretchButton sobelBtn = edgeDetectPane.getSobelLbl();
        Object source = event.getSource();
        if (source instanceof Label clickedLabel) {
            switch (clickedLabel.getId()) {
                case "Canny":
                    cannyBtn.setOnClick(true);
                    laplacianBtn.setOnClick(false);
                    sobelBtn.setOnClick(false);
                    choosenAlgo = "Canny";
                    break;
                case "Laplacian":
                    cannyBtn.setOnClick(false);
                    laplacianBtn.setOnClick(true);
                    sobelBtn.setOnClick(false);
                    choosenAlgo = "Laplacian";
                    break;
                case "Sobel":
                    cannyBtn.setOnClick(false);
                    laplacianBtn.setOnClick(false);
                    sobelBtn.setOnClick(true);
                    choosenAlgo = "Sobel";
                    break;
            }
        }

    }

    // When user clicked Submit button
    public static void onSubmitAlgo(){
        if (choosenAlgo == "Canny"){
            // Apply Canny algorithms to the image.
        } else if (choosenAlgo == "Laplacian") {
            // Apply Laplacian algorithms to the image.
        } else if (choosenAlgo == "Sobel") {
            // Apply Sobel algorithms to the image.
        }
    }


}
