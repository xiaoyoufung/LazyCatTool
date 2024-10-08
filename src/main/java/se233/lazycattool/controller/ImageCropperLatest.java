package se233.lazycattool.controller;

import javafx.application.Platform;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.FileType;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.CropPane;
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

public class ImageCropperLatest {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public void cropImages(ArrayList<ImageFile> imagesToCrop, String desPath, Map<ImageFile, ProgressingImage> progressingImages) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // loop list of image to crop
        for (ImageFile image : imagesToCrop) {
            System.out.println("Image" + image.getName() + ": " + image.getCropInfo().getCropX() + ", " + image.getCropInfo().getCropY());
            executor.submit(() -> cropImage(image, desPath, progressingImages));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void cropImage(ImageFile croppedImage, String desPath, Map<ImageFile, ProgressingImage> progressingImages) {
        ProgressingImage progressingImage = progressingImages.get(croppedImage);

        if (progressingImage == null) {
            System.err.println("Error: ProgressingImage not found for " + croppedImage.getName());
            return;
        }

        // Get crop detail and set crop
        CropImage cropInfo = croppedImage.getCropInfo();
        int x = cropInfo.getCropX();
        int y = cropInfo.getCropY();
        int width = cropInfo.getCropWidth();
        int height = cropInfo.getCropHeight();
        String imageType = croppedImage.getType();

        try {
            int maxSteps = 100;
            for (int i = 1; i <= maxSteps; i++) {
                final int step = i;
                Platform.runLater(() -> {
                    progressingImage.updateProgress((double) step / maxSteps);
                    progressingImage.updatePercent(step + "%");
                });

                BufferedImage originalImg = ImageIO.read(new File(croppedImage.getFilepath()));
                //System.out.println("Original Image Dimension: " + originalImg.getWidth() + "x" + originalImg.getHeight());
                BufferedImage subImg = originalImg.getSubimage(x, y, width, height);
                //System.out.println("Cropped Image Dimension: " + subImg.getWidth() + "x" + subImg.getHeight());
                File outputfile = new File(desPath + "/copy-" + croppedImage.getName());
                ImageIO.write(subImg, imageType, outputfile);
                //System.out.println("Cropped Image created successfully: " + croppedImage.getName());

                Thread.sleep(25);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
