package se233.lazycattool.model.edgeDetector.util;

public class Statistics {

    public static double calcMean(double[][] image) {
        double mean = 0;

        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[0].length; j++)
                mean += image[i][j];

        return mean / (double) (image.length * image[0].length);
    }

    public static double calcMean(int[][] image) {
        double mean = 0;

        for (int i = 0; i < image.length; i++)
            for (int j = 0; j < image[0].length; j++)
                mean += image[i][j];

        return mean / (double) (image.length * image[0].length);
    }

    public static double calcStdDev(double[][] image, double mean) {
        double sigma = 0;

        double offMean;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                offMean = mean - image[i][j];
                sigma += offMean * offMean;
            }
        }

        return Math.sqrt(sigma / (double) (image.length * image[0].length - 1));
    }

    public static double calcStdDev(int[][] image, double mean) {
        double sigma = 0;

        double offMean;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                offMean = mean - image[i][j];
                sigma += offMean * offMean;
            }
        }
        return Math.sqrt(sigma / (double) (image.length * image[0].length - 1));
    }
}

