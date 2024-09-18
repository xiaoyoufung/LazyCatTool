package se233.lazycattool.controller;

import se233.lazycattool.model.CropImage;
import se233.lazycattool.model.ImageFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ImageCropper {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public void cropImages(ArrayList<ImageFile> imagesToCrop, String desPath) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        // loop list of image to crop
        for (ImageFile image : imagesToCrop) {
            System.out.println("Image" + image.getName() + ": " + image.getCropInfo().getCropX() + ", " + image.getCropInfo().getCropY());
            executor.submit(() -> cropImage(image, desPath));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void cropImage(ImageFile imageFile, String desPath) {
        try {
            // Read image
            BufferedImage originalImg = ImageIO.read(new File(imageFile.getFilepath()));

            System.out.println("Original Image Dimension: " + originalImg.getWidth() + "x" + originalImg.getHeight());

            // Get crop detail and set crop
            CropImage cropInfo = imageFile.getCropInfo();
            int x = cropInfo.getCropX();
            int y = cropInfo.getCropY();
            int width = cropInfo.getCropWidth();
            int height = cropInfo.getCropHeight();

            // Crop the Image
            BufferedImage subImg = originalImg.getSubimage(x, y, width, height);

            System.out.println("Cropped Image Dimension: " + subImg.getWidth() + "x" + subImg.getHeight());

            // Define output path
            File outputfile = new File(desPath + "/copy-" + imageFile.getName());

            ImageIO.write(subImg, "png", outputfile);

            System.out.println("Cropped Image created successfully: " + imageFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
