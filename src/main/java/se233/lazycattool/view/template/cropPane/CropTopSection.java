package se233.lazycattool.view.template.cropPane;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import se233.lazycattool.view.template.components.HeadingSection;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.ImageViewURL;

public class CropTopSection extends HBox {
    private HeadingSection headingSection = new HeadingSection("Crop an image", "Upload a 1600 x 480 px image for best results.");
    private ImageViewURL cropIcon = new ImageViewURL("assets/icons/cropIcon.png", 24);
    private IconWithBorder cropIconContainer = new IconWithBorder(cropIcon, 12);

    public CropTopSection(){
        this.setSpacing(16);
        this.getChildren().addAll(cropIconContainer, headingSection);
        this.setPadding(new Insets(0, 25, 0, 25));
    }
}