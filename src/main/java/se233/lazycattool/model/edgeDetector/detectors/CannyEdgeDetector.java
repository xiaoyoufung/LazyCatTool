package se233.lazycattool.model.edgeDetector.detectors;

import java.io.File;
import java.util.Stack;
import java.util.HashSet;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import se233.lazycattool.model.edgeDetector.util.KMeans;
import se233.lazycattool.model.edgeDetector.util.Threshold;
import se233.lazycattool.model.edgeDetector.util.Grayscale;
import se233.lazycattool.model.edgeDetector.util.Hypotenuse;
import se233.lazycattool.model.edgeDetector.util.NonMaximumSuppression;
import se233.lazycattool.model.edgeDetector.imagederivatives.ImageConvolution;
import se233.lazycattool.model.edgeDetector.imagederivatives.ConvolutionKernel;

public class CannyEdgeDetector implements EdgeDetector {
    private static final double[][] X_KERNEL = {
            {-1, 0 ,  1},
            {-2, 0 ,  2},
            {-1, 0 ,  1}
    };

    private static final double[][] Y_KERNEL = {
            {1 , 2 ,  1},
            {0 , 0 ,  0},
            {-1, -2, -1}
    };

    private boolean L1norm;
    private boolean calcThreshold;
    private int highThreshold;
    private int lowThreshold;
    private int minEdgeSize;

    private boolean[][] edges;
    private boolean[][] strongEdges;
    private boolean[][] weakEdges;

//   private int numEdgePixels;
//   private int numStrongEdgePixels;
//   private int numWeakEdgePixels;

    private int rows;
    private int columns;

    private CannyEdgeDetector() {

    }

    private CannyEdgeDetector(Builder builder) {
        this.L1norm = builder.L1norm;
        this.minEdgeSize = builder.minEdgeSize;
        if (!(this.calcThreshold = builder.calcThreshold)) {
            this.lowThreshold = builder.lowThreshold;
            this.highThreshold = builder.highThreshold;
        }
        findEdges(builder.image);
    }

    public static class Builder {
        private int[][] image;
        private boolean calcThreshold = true;
        private int lowThreshold;
        private int highThreshold;
        private boolean L1norm = false;
        private int minEdgeSize = 0;

        public Builder(int[][] image) {
            this.image = image;
        }

        public Builder thresholds(int lowThreshold, int highThreshold) {
            if (lowThreshold > highThreshold || lowThreshold < 0 || highThreshold > 255) {
                throw new IllegalArgumentException("Invalid threshold values");
            }
            this.calcThreshold = false;
            this.lowThreshold = lowThreshold;
            this.highThreshold = highThreshold;
            return this;
        }

        public Builder L1norm(boolean L1norm) {
            this.L1norm = L1norm;
            return this;
        }

        public Builder minEdgeSize(int minEdgeSize) {
            this.minEdgeSize = 0;
            return this;
        }

        public CannyEdgeDetector build() {
            return new CannyEdgeDetector(this);
        }
    }

    private void findEdges(int[][] image) {
        //STEP 1: GAUSSIAN SMOOTHING นำรูปไปเข้ากระบวนการทำให้มันเบลอโดย Gaussian function
        //Kernel คือหน่วยเล็กๆ (pixel) ที่อัลกอริทึ่มใช้ในการทำให้รูปเบลอ, หรือชัดเจนขึ้นก็ได้
        ImageConvolution gaussianConvolution = new ImageConvolution(image, ConvolutionKernel.GAUSSIAN_KERNEL);
        int[][] smoothedImage = gaussianConvolution.getConvolvedImage();

        //STEP 2: IMAGE GRADIENT คำนวนหาเกรเดี้ยนเพื่อหาความเข้มข้นของ pixel จะมีการใช้คณิตศาสตร์เข้าช่วย
        ImageConvolution x_ic = new ImageConvolution(smoothedImage, X_KERNEL);
        ImageConvolution y_ic = new ImageConvolution(smoothedImage, Y_KERNEL);

        // calculate magnitude of gradients
        int[][] x_imageConvolution = x_ic.getConvolvedImage();
        int[][] y_imageConvolution = y_ic.getConvolvedImage();

        // note: image convolutions have slightly different dimensions that original image
        rows = x_imageConvolution.length;
        columns = x_imageConvolution[0].length;

        // calculate magnitude of gradient and tangent angle to edge
        int[][] mag = new int[rows][columns];
        NonMaximumSuppression.EdgeDirection[][] angle = new NonMaximumSuppression.EdgeDirection[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mag[i][j] = hypotenuse(x_imageConvolution[i][j], y_imageConvolution[i][j]);
                angle[i][j] = direction(x_imageConvolution[i][j], y_imageConvolution[i][j]);
            }
        }

        //STEP 3: NON-MAXIMUM SUPPRESSION คัดแค่ pixel ที่ชัดเจนและคมมากที่สุดให้เหลือไว้ ที่เบลอๆตัดทิ้ง พิกเซ่ลที่ผ่านการคัดเลือกจะกลายไปเป็น edge
        edges = new boolean[rows][columns];
      weakEdges = new boolean[rows][columns];
      strongEdges = new boolean[rows][columns];

        // apply non-maximum suppression (suppress false edges)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (NonMaximumSuppression.nonMaximumSuppression(mag, angle[i][j], i, j)) {
                    mag[i][j] = 0;
                }
            }
        }

        //STEP 4: HYSTERESIS THRESHOLDING เอา pixel ที่ผ่านการการเข้าเลือกไปเป็น edge แล้วไปจำแนกตามเกณฑ์
        //เกณฑ์แบ่งเป็นสองคือ strong edge และ weak edge
        // calculate high and low thresholds if user did not provide
        if (calcThreshold) {
            calculateThresholds(mag);
        }

        //STEP 5: EDGE TRACING
        // data structures to help with DFS (depth-first search) in edge tracing
        HashSet<Integer> strongSet = new HashSet<Integer>();
        HashSet<Integer> weakSet = new HashSet<Integer>();

        //find strong and weak edges
        int index = 0;
        //numWeakEdgePixels = 0;
        //numStrongEdgePixels = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (mag[r][c] >= highThreshold) {
                    strongSet.add(index);
                    strongEdges[r][c] = true;
                    //numStrongEdgePixels++;
                } else if (mag[r][c] >= lowThreshold) {
                    weakSet.add(index);
                    weakEdges[r][c] = true;
                    //numWeakEdgePixels++;
                }
                index++;
            }
        }

        // if false --> not checked. if true --> checked
        boolean[][] marked = new boolean[rows][columns];
        Stack<Integer> toAdd = new Stack<Integer>();

        // depth-first search to track all contiguous edge segments, each consisting
        // of weak edge pixels and at least 1 strong edge pixel
        for (int strongIndex : strongSet) {
            dfs(ind2sub(strongIndex, columns)[0], ind2sub(strongIndex, columns)[1], weakSet, strongSet, marked, toAdd);
            if (toAdd.size() >= minEdgeSize) {
                for (int edgeIndex : toAdd) {
                    edges[ind2sub(edgeIndex, columns)[0]][ind2sub(edgeIndex, columns)[1]] = true;
                }
            }
            toAdd.clear();
        }
    }

    private void calculateThresholds(int[][] mag) {
        int k = 3;
        double[][] points = new double[rows * columns][1];
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                points[counter++][0] = mag[i][j];
            }
        }
        KMeans clustering = new KMeans.Builder(k, points).iterations(10).epsilon(.01).useEpsilon(true).build();
        double[][] centroids = clustering.getCentroids();

        boolean b = centroids[0][0] < centroids[1][0];
        lowThreshold = (int) (b ? centroids[0][0] : centroids[1][0]);
        highThreshold = (int) (b ? centroids[1][0] : centroids[0][0]);
    }

    private void dfs(int r, int c, HashSet<Integer> weakSet, HashSet<Integer> strongSet, boolean[][] marked, Stack<Integer> toAdd) {
        if (r < 0 || r >= rows || c < 0 || c >= columns || marked[r][c]) {
            return;
        }

        marked[r][c] = true;

        int index = sub2ind(r, c, columns);
        if (weakSet.contains(index) || strongSet.contains(index)) {
            // mark as possible edge (must also have >= minEdgeSize # of pixels)
            toAdd.push(sub2ind(r, c, columns));
            // continue depth first search
            dfs(r - 1, c - 1, weakSet, strongSet, marked, toAdd);
            dfs(r - 1, c, weakSet, strongSet, marked, toAdd);
            dfs(r - 1, c + 1, weakSet, strongSet, marked, toAdd);
            dfs(r, c - 1, weakSet, strongSet, marked, toAdd);
            dfs(r, c + 1, weakSet, strongSet, marked, toAdd);
            dfs(r + 1, c - 1, weakSet, strongSet, marked, toAdd);
            dfs(r + 1, c, weakSet, strongSet, marked, toAdd);
            dfs(r + 1, c + 1, weakSet, strongSet, marked, toAdd);
        }
    }

    private static int[] ind2sub(int index, int columns) {
        return new int[] {index / columns, index - columns * (index / columns)};
    }

    private static int sub2ind(int r, int c, int columns) {
        return columns * r + c;
    }

    private int hypotenuse(int x, int y) {
        return (int) (L1norm ? Hypotenuse.L1(x, y) : Hypotenuse.L2(x, y));
    }

    private NonMaximumSuppression.EdgeDirection direction(int G_x, int G_y) {
        return NonMaximumSuppression.EdgeDirection.getDirection(G_x, G_y);
    }

    // Getter for edges
    public boolean[][] getEdges() {
        return edges;
    }

    @Override
    public File detectEdges(File imageFile) throws Exception {
        BufferedImage originalImage = ImageIO.read(imageFile);
        int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

        CannyEdgeDetector canny = new CannyEdgeDetector.Builder(pixels).minEdgeSize(10).thresholds(15, 35).L1norm(false).build();

        boolean[][] edges = canny.getEdges();
        BufferedImage cannyImage = Threshold.applyThresholdReversed(edges);

        File result = new File("cannyResult.png");
        ImageIO.write(cannyImage, "png", result);
        return result;
    }
}

