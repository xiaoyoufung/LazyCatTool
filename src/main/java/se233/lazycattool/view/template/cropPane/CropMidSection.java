package se233.lazycattool.view.template.cropPane;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.components.IconWithBorder;
import se233.lazycattool.view.template.components.MultiPicturePane;
import se233.lazycattool.view.template.cropPane.components.CropMainImage;

import java.util.ArrayList;

public class CropMidSection extends VBox {
    private ArrayList<ImageFile> allUploadedImages;

    public CropMainImage getMainImage() {
        return mainImage;
    }

    CropMainImage mainImage;
    MultiPicturePane cropMultiplePic;

    public CropMidSection(ArrayList<ImageFile> allUploadedImages){
        this.allUploadedImages = allUploadedImages;

        this.setSpacing(20);
        this.setPadding(new Insets(0, 25, 0,25));

        mainImage = new CropMainImage(allUploadedImages.getFirst().getFilepath());
        cropMultiplePic = new MultiPicturePane(allUploadedImages);

        System.out.println(allUploadedImages.getFirst().getFilepath());

        cropMultiplePic.getAddButton().setOnMouseClicked(event -> {
            onAddButtonClicked();
        });

        this.getChildren().addAll(mainImage, cropMultiplePic);
    }

    public void onAddButtonClicked(){
        Launcher.switchToUpload();
    }
}
