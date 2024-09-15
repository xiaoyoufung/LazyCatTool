package se233.lazycattool.view.template.uploadPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;

public class DragDropBox extends VBox {

    // use ImageViewURL component from [template/ImageViewURL]
    private final String normalIconURL = "assets/icons/uploadIcon.png";
    private final ImageViewURL uploadFileIcon = new ImageViewURL(normalIconURL, 36);

    public DragDropBox(){
        this.setSpacing(10);
        this.getStyleClass().add("drag-drop-group");
        this.setPadding(new Insets(30, 100, 30, 100));
        this.setAlignment(Pos.CENTER);

        Label dragDropHeadlBl = new Label("Drag and drop files to upload");
        dragDropHeadlBl.getStyleClass().add("drag-drop-head");

        Label dragDropTxtLbl = new Label("Supported formats: .jpg, .png, .zip");
        dragDropTxtLbl.getStyleClass().add("drag-drop-txt");

        this.getChildren().addAll(uploadFileIcon, dragDropHeadlBl, dragDropTxtLbl);
    }

    public void isToggle(boolean isActive){
        if (isActive){
            this.getStyleClass().add("drag-drop-group-active");
            String activeIconURL = "assets/icons/uploadIconBlack.png";
            uploadFileIcon.setImageURL(activeIconURL);
        } else {
            this.getStyleClass().removeAll("drag-drop-group-active");
            this.getStyleClass().add("drag-drop-group");
            uploadFileIcon.setImageURL(normalIconURL);
        }
    }

}
