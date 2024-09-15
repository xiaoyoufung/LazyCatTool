package se233.lazycattool.view.template.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import se233.lazycattool.Launcher;

import java.util.Objects;

public class ImageViewURL extends ImageView {

    private Image imageURL;

    public Image getImageURL(String url) {
        this.imageURL = new Image(Objects.requireNonNull(Launcher.class.getResource(url)).toString());
        return this.imageURL;
    }

    public void setImageURL(String url) {
        this.imageURL = getImageURL(url);
        this.setImage(imageURL);
    }

    public ImageViewURL(String url, double width, double height){
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setPreserveRatio(true);
        this.imageURL = getImageURL(url);

        this.setImage(this.imageURL);
    }

    // define ImageViewURL with w x h and border Radius
    public ImageViewURL(String url, double width, double height, double radius){
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setPreserveRatio(true);
        this.imageURL = getImageURL(url);

        this.setImageBorderRadius(radius, width, height);
        this.setImage(this.imageURL);
    }

    public ImageViewURL(String url, double width){
        this.setFitWidth(width);
        this.setPreserveRatio(true);
        this.imageURL = getImageURL(url);

        this.setImage(this.imageURL);
    }

    public void setImageBorderRadius(double radius, double width, double height){
        Rectangle clip = new Rectangle(width, height);
        clip.setArcWidth(radius);
        clip.setArcHeight(radius);
        this.setClip(clip);
    }


}
