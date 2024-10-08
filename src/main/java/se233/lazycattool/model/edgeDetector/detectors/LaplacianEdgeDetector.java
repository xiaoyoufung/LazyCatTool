package se233.lazycattool.model.edgeDetector.detectors;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import se233.lazycattool.model.edgeDetector.util.Threshold;
import se233.lazycattool.model.edgeDetector.util.Grayscale;

public class LaplacianEdgeDetector implements EdgeDetector {
    private int maskSize;
    private int threshold;
    private boolean[][] edges;

    private final double[][] kernel3x3 = {
            {0, -1, 0},
            {-1, 4, -1},
            {0, -1, 0}
    };

    private final double[][] kernel5x5 = {
            {0, 0, -1, 0, 0},
            {0, -1, -2, -1, 0},
            {-1, -2, 16, -2, -1},
            {0, -1, -2, -1, 0},
            {0, 0, -1, 0, 0}
    };

    // คอนสตรัคเตอร์ที่รับขนาดของ mask เป็นพารามิเตอร์
    public LaplacianEdgeDetector(int maskSize) {
        this.maskSize = maskSize;
    }

    // ค้นหาขอบภาพ
    private void findEdges(int[][] image) {
        // ขั้นตอนที่ 1: Gaussian smoothing เพื่อลด noise
        int[][] smoothedImage = applyGaussianSmoothing(image);

        // ขั้นตอนที่ 2: ทำการคอนโวลูชันด้วย Laplacian kernel ที่เลือก
        double[][] chosenKernel = (maskSize == 5) ? kernel5x5 : kernel3x3;
        int[][] convolvedImage = applyLaplacian(smoothedImage, chosenKernel);

        int rows = convolvedImage.length;
        int columns = convolvedImage[0].length;

        // ขั้นตอนที่ 3: คำนวณ threshold โดยใช้ adaptive thresholding
        threshold = Threshold.adaptiveThreshold(convolvedImage);

        // ขั้นตอนที่ 4: ค้นหาขอบโดยใช้ threshold
        edges = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                edges[i][j] = Math.abs(convolvedImage[i][j]) > threshold;
            }
        }
    }

    // Getter for edges
    public boolean[][] getEdges() {
        return edges;
    }

    // ฟังก์ชันคืนค่า threshold
    public int getThreshold() {
        return threshold;
    }

    // ฟังก์ชัน detectEdges ที่ใช้ใน ImageController
    @Override
    public File detectEdges(File imageFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);
        int[][] grayImage = Grayscale.imgToGrayPixels(originalImage);

        // เรียกใช้ฟังก์ชันการค้นหาขอบ
        findEdges(grayImage);

        BufferedImage edgeImage = Threshold.applyThresholdReversed(edges);
        File result = new File("laplacian_result.png");
        ImageIO.write(edgeImage, "png", result);
        return result;
    }

    public File detectEdges(File imageFile, String path) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile);
        int[][] grayImage = Grayscale.imgToGrayPixels(originalImage);

        // เรียกใช้ฟังก์ชันการค้นหาขอบ
        findEdges(grayImage);

        BufferedImage edgeImage = Threshold.applyThresholdReversed(edges);
        File result = new File(path);
        ImageIO.write(edgeImage, "png", result);
        return result;
    }

    // ฟังก์ชัน Gaussian smoothing
    private int[][] applyGaussianSmoothing(int[][] image) {
        double[][] gaussianKernel = {
                {1 / 16.0, 1 / 8.0, 1 / 16.0},
                {1 / 8.0, 1 / 4.0, 1 / 8.0},
                {1 / 16.0, 1 / 8.0, 1 / 16.0}
        };
        return applyConvolution(image, gaussianKernel);
    }

    // ฟังก์ชัน Laplacian Convolution
    private int[][] applyLaplacian(int[][] image, double[][] kernel) {
        return applyConvolution(image, kernel);
    }

    // ฟังก์ชัน Convolution สำหรับ Gaussian และ Laplacian
    private int[][] applyConvolution(int[][] image, double[][] kernel) {
        int kernelSize = kernel.length;
        int offset = kernelSize / 2;
        int width = image[0].length;
        int height = image.length;
        int[][] result = new int[height][width];

        for (int y = offset; y < height - offset; y++) {
            for (int x = offset; x < width - offset; x++) {
                double sum = 0.0;
                for (int ky = -offset; ky <= offset; ky++) {
                    for (int kx = -offset; kx <= offset; kx++) {
                        int pixel = image[y + ky][x + kx];
                        double weight = kernel[ky + offset][kx + offset];
                        sum += pixel * weight;
                    }
                }
                result[y][x] = (int) Math.max(0, Math.min(255, sum));
            }
        }
        return result;
    }
}

