package se233.lazycattool.controller;

import javafx.application.Platform;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.AlgorithmType;
import se233.lazycattool.model.ConfigEdge;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.model.edgeDetector.detectors.CannyEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.LaplacianEdgeDetector;
import se233.lazycattool.model.edgeDetector.detectors.SobelEdgeDetector;
import se233.lazycattool.model.edgeDetector.util.Grayscale;
import se233.lazycattool.model.edgeDetector.util.Threshold;
import se233.lazycattool.view.template.progressBar.ProgressingImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageEdgeDetector {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public void detectImages(ArrayList<ImageFile> imagesToDetect, String desPath, Map<ImageFile, ProgressingImage> progressingImages, ConfigEdge config){
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (ImageFile image: imagesToDetect){
            executor.submit(() -> detectImage(image, desPath, progressingImages, config));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void detectImage(ImageFile imageFile, String desPath, Map<ImageFile, ProgressingImage> progressingImages, ConfigEdge config){
        ProgressingImage progressingImage = progressingImages.get(imageFile);
        ArrayList<ImageFile> unProcessImages = Launcher.getAllOutprocessedImages();
        //ArrayList<ImageFile> processImages = Launcher.getAllProcessedImages();

        if (progressingImage == null) {
            System.err.println("Error: ProgressingImage not found for " + imageFile.getName());
            return;
        }

        AlgorithmType type = config.getAlgorithmType();
        String inputPath = imageFile.getFilepath();
        String imageType = imageFile.getType();
        BufferedImage outputImage = null;

        try{
            int maxSteps = 100;
            String outputPath = desPath;
            for (int i = 1; i < maxSteps; i++) {
                final int step = i;
                Platform.runLater(() -> {
                    progressingImage.updateProgress((double) step / maxSteps);
                    progressingImage.updatePercent(step + "%");
                });

                // Step 3: Read the input image
//                BufferedImage originalImage = ImageIO.read(new File(inputPath));

//                // Step 4: Convert to grayscale
//                int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

                // If Algorithm type is Canny
                if(type == AlgorithmType.Canny){
                    outputImage = getCannyEdgeDetectorImage(config, inputPath);

                    // If Algorithm type is Laplacian
                } else if (type == AlgorithmType.Laplacian) {
                    outputImage = getLaplacianEdgeDetectorImage(config, inputPath);

                    // If Algorithm type is Sobel
                } else if (type == AlgorithmType.Sobel) {
                    outputImage = getSobelEdgeDetectorImage(config, inputPath);
                }

                // Step 7: Save the output image
                assert outputImage != null;
                ImageIO.write(outputImage, imageType, new File(outputPath));

                // kept new processed image....
                if(!unProcessImages.isEmpty()){
                    if(unProcessImages.get(i).equals(imageFile)){
                        imageFile.setProcessed(true);
                        imageFile.setFilepath(outputPath);
                        //processImages.add(imageFile);
                    }
                }

                System.out.println("Edge detection completed. Output saved to: " + outputPath);

                Thread.sleep(25);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getCannyEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {
        // Step 3: Read the input image
        BufferedImage outputImage = null;
        BufferedImage originalImage = ImageIO.read(new File(inputPath));

        // Step 4: Convert to grayscale
        int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

        int lowThreshold = config.getLowThreshold();
        int highThreshold = config.getHighThreshold();

        CannyEdgeDetector canny = new CannyEdgeDetector.Builder(pixels)
                .minEdgeSize(10)
                .thresholds(lowThreshold, highThreshold)
                .L1norm(false)
                .build();
        boolean[][] edges = canny.getEdges();
        outputImage = Threshold.applyThresholdReversed(edges);

        return outputImage;
    }

    public BufferedImage getLaplacianEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {
        BufferedImage outputImage = null;

        double maskSizeIndex = config.getConvolutionMask();
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

        return outputImage;
    }

    public BufferedImage getSobelEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {
        BufferedImage outputImage = null;

        double kernelSizeIndex = config.getKernalSize();
        int threshold = config.getThreshold();

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
        return outputImage;
    }
}
