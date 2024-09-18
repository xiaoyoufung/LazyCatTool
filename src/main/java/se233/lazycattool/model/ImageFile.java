package se233.lazycattool.model;

public class ImageFile {
    protected String name;
    protected double sizeKB;
    protected String path;
    protected FileType type;
    protected CropImage cropInfo;

    public ImageFile(String name, String path, double size, FileType type){
        this.name = name;
        this.path = path;
        this.sizeKB = size;
        this.type = type;
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
