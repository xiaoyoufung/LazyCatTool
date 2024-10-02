package se233.lazycattool.controller;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.edgeDetector.detectors.CannyEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.LaplacianEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.SobelEdgeDetector;
import se233.lazycattool.model.edgeDetector.util.Grayscale;
import se233.lazycattool.model.edgeDetector.util.Threshold;
import se233.lazycattool.view.EdgeDetectPane;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.edgedetectPane.StretchButton;
import se233.lazycattool.view.template.progressBar.ProcessMoreButton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        String inputPath = "/Users/xiaoyoufung/Desktop/test-photo-resize/blue_dusk.png";

        if (choosenAlgo == "Canny"){
            try {
                // Step 3: Read the input image

                BufferedImage originalImage = ImageIO.read(new File(inputPath));

                // Step 4: Convert to grayscale
                int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

                // Step 5: Apply Canny Edge Detection
                CannyEdgeDetector canny = new CannyEdgeDetector.Builder(pixels)
                        .minEdgeSize(10)
                        .thresholds(50, 100)
                        .L1norm(false)
                        .build();
                boolean[][] edges = canny.getEdges();

                // Step 6: Create output image
                BufferedImage outputImage = Threshold.applyThresholdReversed(edges);

                // Step 7: Save the output image
                String outputPath = "/Users/xiaoyoufung/Desktop/test-photo-resize/blue_dusk_edge.png";
                ImageIO.write(outputImage, "PNG", new File(outputPath));

                // Optional: Display the results
//                BufferedImage[] toShow = {originalImage, outputImage};
//                ImageViewer.showImages(toShow, "Canny Edge Detection Results");
//                Image image = new Image("file:path/to/your/image.png");

                // Create an ImageView and set the image
                //ImageView imageView = new ImageView(image);

                System.out.println("Edge detection completed. Output saved to: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Apply Canny algorithms to the image.
        } else if (choosenAlgo == "Laplacian") {
            // Apply Laplacian algorithms to the image.

            try {
                // Step 1: Specify input and output paths
                String outputPath = "/Users/xiaoyoufung/Desktop/test-photo-resize/laplacian.png";
                // Step 2: Read the input image
                BufferedImage originalImage = ImageIO.read(new File(inputPath));

                // Step 3: Convert to grayscale
                int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

                // Step 4: Apply Laplacian Edge Detection
                LaplacianEdgeDetector laplacian = new LaplacianEdgeDetector(3); // Use 3x3 mask
                File outputFile = laplacian.detectEdges(new File(inputPath));

                // Step 5: The result is already saved by the detectEdges method
                System.out.println("Edge detection completed. Output saved to: " + outputFile.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choosenAlgo == "Sobel") {
            // Apply Sobel algorithms to the image.

            try {
                // Step 1: Specify input and output paths
                String outputPath = "/Users/xiaoyoufung/Desktop/test-photo-resize/_sobel.png";

                // Step 2: Read the input image
                BufferedImage originalImage = ImageIO.read(new File(inputPath));

                // Step 3: Apply Sobel Edge Detection
                // You can adjust the kernel size (3 or 5) and threshold as needed
                SobelEdgeDetector sobel = new SobelEdgeDetector(3, 50);
                File outputFile = sobel.detectEdges(new File(inputPath));

                // Step 4: The result is already saved by the detectEdges method
                System.out.println("Edge detection completed. Output saved to: " + outputFile.getAbsolutePath());

                // Optional: If you want to save with a custom name/location
                BufferedImage edgeImage = ImageIO.read(outputFile);
                ImageIO.write(edgeImage, "PNG", new File(outputPath));
                System.out.println("Edge detection result copied to: " + outputPath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
