package se233.lazycattool.model.edgeDetector.detectors;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import se233.lazycattool.model.edgeDetector.imagederivatives.ConvolutionKernel;
import se233.lazycattool.model.edgeDetector.imagederivatives.ImageConvolution;
import se233.lazycattool.model.edgeDetector.util.Grayscale;
import se233.lazycattool.model.edgeDetector.util.Threshold;

public class SobelEdgeDetectorN implements EdgeDetector {

    private boolean[][] edges;
    private int rows;
    private int columns;
    private int threshold;
    private int kernelSize;

    // Sobel Kernels for 3x3 and 5x5
    private static final double[][] X_KERNEL_3x3 = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private static final double[][] Y_KERNEL_3x3 = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
    };

    private static final double[][] X_KERNEL_5x5 = {
            {-2, -1, 0, 1, 2},
            {-2, -1, 0, 1, 2},
            {-4, -2, 0, 2, 4},
            {-2, -1, 0, 1, 2},
            {-2, -1, 0, 1, 2}
    };

    private static final double[][] Y_KERNEL_5x5 = {
            {2, 2, 4, 2, 2},
            {1, 1, 2, 1, 1},
            {0, 0, 0, 0, 0},
            {-1, -1, -2, -1, -1},
            {-2, -2, -4, -2, -2}
    };

    // Constructor with kernel size and threshold
    public SobelEdgeDetectorN(int kernelSize, int threshold) {
        this.kernelSize = kernelSize;
        this.threshold = threshold;
    }

    @Override
    public File detectEdges(File imageFile) throws IOException {
        // Load and convert the image to grayscale
        BufferedImage originalImage = ImageIO.read(imageFile);
        if (originalImage == null) {
            throw new IOException("Failed to load image.");
        }
        int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

        // Apply Sobel Operator
        int[][] gradientMagnitude = applySobelOperator(pixels);

        // Thresholding to detect edges
        calculateEdges(gradientMagnitude);

        // Generate the output image showing detected edges
        BufferedImage edgeImage = Threshold.applyThresholdReversed(edges);
        File result = new File("sobel_edge_result.png");
        ImageIO.write(edgeImage, "png", result);

        return result;
    }

    // Apply Sobel Operator with chosen kernel size
    private int[][] applySobelOperator(int[][] pixels) {
        double[][] xKernel = (kernelSize == 3) ? X_KERNEL_3x3 : X_KERNEL_5x5;
        double[][] yKernel = (kernelSize == 3) ? Y_KERNEL_3x3 : Y_KERNEL_5x5;

        ImageConvolution xConvolution = new ImageConvolution(pixels, xKernel);
        ImageConvolution yConvolution = new ImageConvolution(pixels, yKernel);

        int[][] xGradient = xConvolution.getConvolvedImage();
        int[][] yGradient = yConvolution.getConvolvedImage();

        rows = xGradient.length;
        columns = xGradient[0].length;
        int[][] gradientMagnitude = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gradientMagnitude[i][j] = (int) Math.hypot(xGradient[i][j], yGradient[i][j]);
            }
        }

        return gradientMagnitude;
    }

    // Apply threshold to detect edges
    private void calculateEdges(int[][] gradientMagnitude) {
        edges = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                edges[i][j] = gradientMagnitude[i][j] >= threshold;
            }
        }
    }

    // Getter for edges
    public boolean[][] getEdges() {
        return edges;
    }
}
