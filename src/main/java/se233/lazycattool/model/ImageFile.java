package se233.lazycattool.model;

public class ImageFile {
    protected String name;
    protected double sizeKB;
    protected String path;
    protected FileType type;
    protected CropImage cropInfo;

    public boolean isCropped() {
        return isCropped;
    }

    public void setCropped(boolean cropped) {
        isCropped = cropped;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    protected boolean isCropped;
    protected boolean isProcessed;

    public ImageFile(String name, String path, double size, FileType type){
        this.name = name;
        this.path = path;
        this.sizeKB = size;
        this.type = type;
        this.isCropped = false;
        this.isProcessed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilepath() {
        return path;
    }

    public void setFilepath(String filepath) {
        this.path = filepath;
    }

    public double getSize() {
        return sizeKB;
    }

    public void setSize(int size) {
        this.sizeKB = size;
    }

    public CropImage getCropInfo() {
        return cropInfo;
    }

    public void setCropInfo(CropImage cropInfo) {
        this.cropInfo = cropInfo;
    }

    public String getType() {
        if (this.type == FileType.png){
            return "png";
        } else {
            return "jpg";
        }
    }

}
