package se233.lazycattool.view.template.components;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageFileURL extends ImageView {

    public ImageFileURL(String url, double width, double height, double radius){
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setPreserveRatio(true);
        this.setImageBorderRadius(radius, width, height);
        this.setImage(new Image("file:" + url));
    }

    public ImageFileURL(String url){
        this.setImage(new Image("file:" + url));
    }

    public void setImageBorderRadius(double radius, double width, double height){
        Rectangle clip = new Rectangle(width, height);
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        this.setClip(clip);
    }
}
