package se233.lazycattool.view.template.edgedetectPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;

public class ConfigureSection extends VBox {
    boolean isClicked;

    public ConfigureSection(){
        HBox mainArea = genMainArea();
        this.getChildren().addAll(mainArea);
        isClicked = false;
    }

    private HBox genMainArea(){
        HBox mainArea = new HBox(10);
        mainArea.setStyle("-fx-background-color");
        Label configLbl;
        configLbl = new Label("Configuration");
        configLbl.getStyleClass().add("small-heading");

        // icon
        ImageViewURL cropIcon = new ImageViewURL("assets/icons/arrowDownIcon.png", 18);
        cropIcon.setStyle("-fx-cursor: hand;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        //String arrowUpIconURL = "assets/icons/arrowUpIcon.png";
        //ImageViewURL arrowUpIcon = new ImageViewURL(arrowUpIconURL, 36);

        mainArea.setAlignment(Pos.CENTER);
        mainArea.getChildren().addAll(configLbl, spacer, cropIcon);
        return mainArea;
    }
}
