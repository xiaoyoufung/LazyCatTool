package se233.lazycattool.model.edgeDetector.util;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Threshold {

    public static int calcThresholdEdges(int[][] magnitude) {
        return (int) Statistics.calcMean(magnitude);
    }

    public static BufferedImage applyThreshold(int[][] pixels, int threshold) {
        int height = pixels.length;
        int width = pixels[0].length;

        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = thresholdedImage.getRaster();

        int[] black = {0, 0, 0};
        int[] white = {255, 255, 255};

        // cache-efficient for both BufferedImage and int[][]
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                raster.setPixel(col, row, pixels[row][col] > threshold ? white : black);

        return thresholdedImage;
    }

    public static BufferedImage applyThreshold(boolean[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;

        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = thresholdedImage.getRaster();

        int[] black = {0, 0, 0};
        int[] white = {255, 255, 255};

        // cache efficient for both BufferedImage and int[][]
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                raster.setPixel(col, row, pixels[row][col] ? white : black);

        return thresholdedImage;
    }

    public static BufferedImage applyThresholdReversed(boolean[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;

        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = thresholdedImage.getRaster();

        int[] black = {0, 0, 0};
        int[] white = {255, 255, 255};

        // cache efficient for both BufferedImage and int[][]
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++)
                raster.setPixel(col, row, pixels[row][col] ? black : white);

        return thresholdedImage;
    }

    public static BufferedImage applyThresholdWeakStrongCanny(boolean[][] weakEdges, boolean[][] strongEdges) {
        int height = weakEdges.length;
        int width = weakEdges[0].length;

        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = thresholdedImage.getRaster();

        int[] white = {255, 255, 255};
        int[] blue = {0, 0, 255};
        int[] green = {0, 255, 0};

        // cache efficient for both BufferedImage and int[][]
        for (int row = 0; row < height; row++)
            for (int col = 0; col < width; col++) {
                if (strongEdges[row][col])
                    raster.setPixel(col, row, green);
                else if (weakEdges[row][col])
                    raster.setPixel(col, row, blue);
                else
                    raster.setPixel(col, row, white);
            }

        return thresholdedImage;
    }

    public static BufferedImage applyThresholdOriginal(boolean[][] edges, BufferedImage originalImage) {
        int height = edges.length;
        int width = edges[0].length;

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster_new = newImage.getRaster();
        WritableRaster raster_old = originalImage.getRaster();

        int[] white = {255, 255, 255};
        int[] arr = new int[3];
        int min;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!edges[row][col]) {                   // not edge
                    raster_new.setPixel(col, row, white);
                } else {                                  // edge
                    // get original pixel color
                    raster_old.getPixel(col, row, arr);

                    // scale to max intensity
                    min = 255;
                    for (int i : arr)
                        if (i < min)
                            min = i;
                    double scale = 255.0 / (255.0 - min);
                    for (int i = 0; i < 3; i++)
                        arr[i] = 255 - (int) (scale * (255.0 - arr[i]));
                    raster_new.setPixel(col, row, arr);
                }
            }
        }
        return newImage;
    }

    public static int adaptiveThreshold(int[][] magnitude) {
        int rows = magnitude.length;
        int cols = magnitude[0].length;

        int sum = 0;
        int count = 0;

        // คำนวณค่าเฉลี่ย (mean) ของ magnitude
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum += magnitude[i][j];
                count++;
            }
        }

        int mean = sum / count;

        // คำนวณค่าส่วนเบี่ยงเบนมาตรฐาน (Standard Deviation)
        int varianceSum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                varianceSum += Math.pow(magnitude[i][j] - mean, 2);
            }
        }

        int stdDev = (int) Math.sqrt(varianceSum / count);

        // ใช้ค่า mean และ stdDev ในการปรับ threshold
        int adaptiveThreshold = mean + (int) (0.5 * stdDev);  // สามารถปรับตัวคูณ stdDev เพื่อผลลัพธ์ที่แตกต่าง

        return adaptiveThreshold;
    }
}

