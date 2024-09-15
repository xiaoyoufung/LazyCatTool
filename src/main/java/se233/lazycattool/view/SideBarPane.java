package se233.lazycattool.view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import se233.lazycattool.view.template.sidebarPane.SidebarButton;

public class SideBarPane extends ScrollPane {
    public SideBarPane(){}

    // Element in Sidebar Pane
    SidebarButton uploadButton = new SidebarButton("Upload", "assets/icons/uploadBarIcon.png");
    SidebarButton detectEdgeButton = new SidebarButton("Detect Edge", "assets/icons/edgeIcon.png");
    SidebarButton cropButton = new SidebarButton("Crop", "assets/icons/cropIcon.png");

    private Pane getDetailsPane(){
        VBox sidebarInfoPane = new VBox(25);
        sidebarInfoPane.setAlignment(Pos.TOP_LEFT);
        sidebarInfoPane.setPadding(new Insets(25,8,100,8));

        // Add all the Buttons to the Sidebar Pane
        sidebarInfoPane.getChildren().addAll(uploadButton, detectEdgeButton, cropButton);

        return sidebarInfoPane;
    }

    public void drawPane(){
        Pane sidebarInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
        this.setContent(sidebarInfoPane);
    }

    public SidebarButton getUploadButton(){ return uploadButton; }

    public SidebarButton getCropButton() { return cropButton; }

    public SidebarButton getDetectEdgeButton() { return detectEdgeButton; }
}
