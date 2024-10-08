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
            System.out.println("in this");
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

        try{
            int maxSteps = 100;
            String outputPath = desPath;
            for (int i = 1; i < maxSteps; i++) {
                final int step = i;
                Platform.runLater(() -> {
                    progressingImage.updateProgress((double) step / maxSteps);
                    progressingImage.updatePercent(step + "%");
                });

                // If Algorithm type is Canny
                System.out.println(type);
                if(type == AlgorithmType.Canny){
                    System.out.println(config.getLowThreshold());
                    System.out.println(config.getHighThreshold());

                    try {
                        // Step 3: Read the input image
                        BufferedImage originalImage = ImageIO.read(new File(inputPath));

                        // Step 4: Convert to grayscale
                        int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

                        // Step 5: Apply Canny Edge Detection
                        CannyEdgeDetector canny = new CannyEdgeDetector.Builder(pixels)
                                .minEdgeSize(10)
                                .thresholds(5, 100)
                                .L1norm(false)
                                .build();
                        boolean[][] edges = canny.getEdges();

                        // Step 6: Create output image
                        BufferedImage outputImage = Threshold.applyThresholdReversed(edges);

                        System.out.println(outputPath);

                        // Step 7: Save the output image
                        File resultPath = new File(outputPath + "/" + imageFile.getName());
                        ImageIO.write(outputImage, "png", resultPath);

                        System.out.println("Edge detection completed. Output saved to: " + outputPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // If Algorithm type is Laplacian
                } else if (type == AlgorithmType.Laplacian) {
                    System.out.println(config.getConvolutionMask());
                    int maskSize = 5;
                    if (config.getConvolutionMask() == 0){
                        maskSize = 3;
                    }

                    try {
                        // Step 2: Read the input image
                        BufferedImage originalImage = ImageIO.read(new File(inputPath));

                        // Step 3: Convert to grayscale
                        int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

                        // Step 4: Apply Laplacian Edge Detection
                        LaplacianEdgeDetector laplacian = new LaplacianEdgeDetector(maskSize); // Use 3x3 mask
                        File outputFile = laplacian.detectEdges(new File(inputPath), outputPath + "/" + imageFile.getName());

                        // Step 5: The result is already saved by the detectEdges method
                        System.out.println("Edge detection completed. Output saved to: " + outputFile.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // If Algorithm type is Sobel
                } else if (type == AlgorithmType.Sobel) {
                    int kernelSize = 5;

                    if (config.getKernalSize() == 0){
                        kernelSize = 3;
                    }
                    System.out.println(config.getKernalSize() == 0);
                    System.out.println(config.getThreshold());

                    try {
                        // Step 1: Specify input and output paths

                        // Step 2: Read the input image
                        BufferedImage originalImage = ImageIO.read(new File(inputPath));

                        // Step 3: Apply Sobel Edge Detection
                        // You can adjust the kernel size (3 or 5) and threshold as needed
                        SobelEdgeDetector sobel = new SobelEdgeDetector(kernelSize, config.getHighThreshold());
                        File outputFile = sobel.detectEdges(new File(inputPath), outputPath + "/" + imageFile.getName());

                        // Step 4: The result is already saved by the detectEdges method
                        System.out.println("Edge detection completed. Output saved to: " + outputFile.getAbsolutePath());

                        // Optional: If you want to save with a custom name/location
                        //BufferedImage edgeImage = ImageIO.read(outputFile);
                        //ImageIO.write(edgeImage, "PNG", new File(outputPath + "/sobel_" + imageFile.getName()));
                       // System.out.println("Edge detection result copied to: " + outputPath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                // Step 7: Save the output image


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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public BufferedImage getCannyEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {
        // Step 3: Read the input image

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
        BufferedImage outputImage = Threshold.applyThresholdReversed(edges);

        return outputImage;
    }

    public BufferedImage getLaplacianEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {

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
        BufferedImage outputImage = ImageIO.read(outputFile);

        return outputImage;
    }

    public BufferedImage getSobelEdgeDetectorImage(ConfigEdge config, String inputPath) throws IOException {

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
        return ImageIO.read(outputFile);
    }
}
