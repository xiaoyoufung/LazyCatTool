package se233.lazycattool.model.edgeDetector.util;

public class Hypotenuse {
    public static double L1(double x, double y) {
        return Math.abs(x) + Math.abs(y);
    }

    public static double L2(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}
