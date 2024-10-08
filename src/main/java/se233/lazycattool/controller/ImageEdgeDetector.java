package se233.lazycattool.controller;

import se233.lazycattool.model.AlgorithmType;
import se233.lazycattool.model.ConfigEdge;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.model.edgeDetector.detectors.CannyEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.LaplacianEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.SobelEdgeDetector;
import se233.lazycattool.model.edgeDetector.util.Grayscale;
import se233.lazycattool.model.edgeDetector.util.Threshold;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEdgeDetector {
    public void edgeDetectImage(String desPath, ImageFile imageFile, ConfigEdge configuration){
        try {
            AlgorithmType type = configuration.getAlgorithmType();
            String inputPath = imageFile.getFilepath();
            String outputPath = desPath;
            String imageType = imageFile.getType();
            BufferedImage outputImage = null;

            // Step 3: Read the input image
            BufferedImage originalImage = ImageIO.read(new File(inputPath));

            // Step 4: Convert to grayscale
            int[][] pixels = Grayscale.imgToGrayPixels(originalImage);


            // If Algorithm type is Canny
            if(type == AlgorithmType.Canny){
                int lowThreshold = configuration.getLowThreshold();
                int highThreshold = configuration.getHighThreshold();

                CannyEdgeDetector canny = new CannyEdgeDetector.Builder(pixels)
                        .minEdgeSize(10)
                        .thresholds(lowThreshold, highThreshold)
                        .L1norm(false)
                        .build();
                boolean[][] edges = canny.getEdges();
                outputImage = Threshold.applyThresholdReversed(edges);

                // If Algorithm type is Laplacian
            } else if (type == AlgorithmType.Laplacian) {
                double maskSizeIndex = configuration.getConvolutionMask();
                int maskSize;

                if (maskSizeIndex == 0){
                    maskSize = 3;
                } else{
                    maskSize = 5;
                }

                // Step 4: Apply Laplacian Edge Detection
                LaplacianEdgeDetector laplacian = new LaplacianEdgeDetector(maskSize); // Use 3x3 mask
                File outputFile = laplacian.detectEdges(new File(inputPath));
                outputImage = ImageIO.read(outputFile);

                // If Algorithm type is Sobel
            } else if (type == AlgorithmType.Sobel) {
                double kernelSizeIndex = configuration.getKernalSize();
                int threshold = configuration.getThreshold();

                int kernelSize;
                if(kernelSizeIndex == 0){
                    kernelSize = 3;
                } else{
                    kernelSize = 5;
                }

                // Step 3: Apply Sobel Edge Detection
                // You can adjust the kernel size (3 or 5) and threshold as needed
                SobelEdgeDetector sobel = new SobelEdgeDetector(kernelSize, threshold);
                File outputFile = sobel.detectEdges(new File(inputPath));

                // Optional: If you want to save with a custom name/location
                outputImage = ImageIO.read(outputFile);
            }

            // Step 7: Save the output image
            assert outputImage != null;
            ImageIO.write(outputImage, imageType, new File(outputPath));

            System.out.println("Edge detection completed. Output saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
