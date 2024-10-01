package se233.lazycattool.model.edgeDetector.imagederivatives;

// ยprovides convolution kernels for Gaussian image smoothing / blurring


public class ConvolutionKernel {

    // TODO: implement way to calculate generic Gaussian Kernel depending on kernel width and size
    // convolution kernel for Gaussian smoothing / blurring (kernel width (sigma) = 1.4, kernel size = 5)
    public static final double[][] GAUSSIAN_KERNEL = {
            {2/159.0, 4/159.0 , 5/159.0 , 4/159.0 , 2/159.0},
            {4/159.0, 9/159.0 , 12/159.0, 9/159.0 , 4/159.0},
            {5/159.0, 12/159.0, 15/159.0, 12/159.0, 5/159.0},
            {4/159.0, 9/159.0 , 12/159.0, 9/159.0 , 4/159.0},
            {2/159.0, 4/159.0 , 5/159.0 , 4/159.0 , 2/159.0}
    };

    // Sobel's kernels for X and Y direction
    public static final double[][] SOBEL_X_KERNEL = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    public static final double[][] SOBEL_Y_KERNEL = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
    };

    // Laplacian kernel
    public static final double[][] LAPLACIAN_KERNEL = {
            {-1, -1, -1},
            {-1, 8, -1},
            {-1, -1, -1}
    };

    // Prewitt kernels for X and Y direction
    public static final double[][] PREWITT_X_KERNEL = {
            {-1, 0, 1},
            {-1, 0, 1},
            {-1, 0, 1}
    };

    public static final double[][] PREWITT_Y_KERNEL = {
            {1, 1, 1},
            {0, 0, 0},
            {-1, -1, -1}
    };
    public static final double[][] GAUSSIAN_KERNEL_3X3 = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    };

    // New Gaussian Kernel 5x5 for better smoothing
    public static final double[][] GAUSSIAN_KERNEL_5X5 = {
            {1, 4, 6, 4, 1},
            {4, 16, 24, 16, 4},
            {6, 24, 36, 24, 6},
            {4, 16, 24, 16, 4},
            {1, 4, 6, 4, 1}
    };

    public static double[][] generateGaussianKernel(double sigma, int size) {
        if (size % 2 == 0 || size < 3) {
            throw new IllegalArgumentException("Kernel size must be an odd number and at least 3.");
        }

        double[][] kernel = new double[size][size];
        int center = size / 2;
        double sum = 0.0;

        // Compute the Gaussian function for each element in the kernel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double x = i - center;
                double y = j - center;
                kernel[i][j] = Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
                sum += kernel[i][j];
            }
        }

        // Normalize the kernel so that the sum of all elements is 1
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kernel[i][j] /= sum;
            }
        }
        return kernel;
    }
}

