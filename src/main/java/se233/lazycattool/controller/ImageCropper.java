package se233.lazycattool.controller;

import se233.lazycattool.model.ImageFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper {
    public void cropImage(ImageFile croppedImage, String desPath, int x, int y, int width, int height){
        // Try block to check for exceptions
        try {

            // Reading original image from local path by
            // creating an object of BufferedImage class
            BufferedImage originalImg = ImageIO.read(
                    new File(croppedImage.getFilepath()));


            // Fetching and printing alongside the
            // dimensions of original image using getWidth()
            // and getHeight() methods
            System.out.println("Original Image Dimension: "
                    + originalImg.getWidth()
                    + "x"
                    + originalImg.getHeight());

            // Creating a subimage of given dimensions
            BufferedImage SubImg
                    = originalImg.getSubimage(x, y, width, height);

            // Printing Dimensions of new image created
            System.out.println("Cropped Image Dimension: "
                    + SubImg.getWidth() + "x"
                    + SubImg.getHeight());

            // Creating new file for cropped image by
            // creating an object of File class
            File outputfile
                    = new File(desPath + "/" + croppedImage.getName());

            // Writing image in new file created
            ImageIO.write(SubImg, "png", outputfile);

            // Display message on console representing
            // proper execution of program
            System.out.println(
                    "Cropped Image created successfully");
        }

        // Catch block to handle the exceptions
        catch (IOException e) {

            // Print the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
    }
}
