package se233.lazycattool.model;

public class CropImage {
    protected int cropX;

    public int getCropWidth() {
        return cropWidth;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public int getCropX() {
        return cropX;
    }

    public int getCropY() {
        return cropY;
    }

    protected int cropY;
    protected int cropWidth;
    protected int cropHeight;

    public CropImage(double cropX, double cropY, double cropWidth, double cropHeight){
        this.cropX = (int) cropX;
        this.cropY = (int) cropY;
        this.cropWidth = (int) cropWidth;
        this.cropHeight = (int) cropHeight;
    }
}
