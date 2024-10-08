package se233.lazycattool.model;

public class ConfigEdge {
    protected int kernalSize;
    protected int threshold;
    protected double convolutionMask;
    protected int lowThreshold;
    protected int highThreshold;

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public int getHighThreshold() {
        return highThreshold;
    }

    public void setHighThreshold(int highThreshold) {
        this.highThreshold = highThreshold;
    }

    public int getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public double getConvolutionMask() {
        return convolutionMask;
    }

    public void setConvolutionMask(double convolutionMask) {
        this.convolutionMask = convolutionMask;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public double getKernalSize() {
        return kernalSize;
    }

    public void setKernalSize(int kernalSize) {
        this.kernalSize = kernalSize;
    }

    protected AlgorithmType algorithmType;

    // Contructor for Canny
    public ConfigEdge(int lowThreshold, int highThreshold){
        this.algorithmType = AlgorithmType.Canny;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
    }

    // Contructor for Laplacian
    public ConfigEdge(double convolutionMask){
        this.algorithmType = AlgorithmType.Laplacian;
        this.convolutionMask = convolutionMask;
    }

    // Contructor for Sobel
    public ConfigEdge(String type, int kernalSize, int threshold){
        this.algorithmType = AlgorithmType.Sobel;
        this.kernalSize = kernalSize;
        this.threshold = threshold;
    }

}
