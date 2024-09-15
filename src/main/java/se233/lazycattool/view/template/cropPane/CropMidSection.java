package se233.lazycattool.view.template.cropPane;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.ImageViewURL;
import se233.lazycattool.view.template.components.MultiPicturePane;

import java.util.ArrayList;

public class CropMidSection extends VBox {

    private ArrayList<ImageFile> imageArray;
    private Pane bigImage = new StackPane();
    ImageViewURL inputImage = new ImageViewURL("assets/images/blue_dusk.png", 398, 259);
    MultiPicturePane cropMultipic = new MultiPicturePane();

    public CropMidSection(){
        this.setSpacing(20);
        this.setPadding(new Insets(0, 25, 0,25));
        inputImage.getStyleClass().add("first-image");

        //imageArray = Launcher.getAllUploadedImages();

        ImageViewURL imageWrap = new ImageViewURL("assets/images/imageWrap.jpg", 512, 312, 16);
        cropMultipic.getAddButton();

        bigImage.getChildren().addAll(imageWrap, inputImage);
        this.getChildren().addAll(bigImage, cropMultipic);
    }
    public IconWithBorder getAddButton(){
        return cropMultipic.getAddButton();
    }
}
