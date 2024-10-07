package se233.lazycattool.view.template.edgedetectPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;
import static se233.lazycattool.controller.EdgeDetectController.onArrowButtonClicked;

public class ConfigureSection extends VBox {
    private static boolean isClicked = false;
    ImageViewURL configIcon;

    public static void setClicked(boolean clicked) {
        isClicked = clicked;
    }
    public static boolean isClicked() {
        return isClicked;
    }

    public ConfigureSection(){
        HBox mainArea = genMainArea();
        this.getChildren().addAll(mainArea);
    }

    private HBox genMainArea(){
        HBox mainArea = new HBox(10);
        mainArea.setPadding(new Insets(15, 10, 15, 0));

        Label configLbl;
        configLbl = new Label("Configuration");
        configLbl.getStyleClass().add("small-heading");

        // icon
        if(!isClicked){
            configIcon = new ImageViewURL("assets/icons/arrowDownIcon.png", 18);
            mainArea.getStyleClass().add("config-section");
            mainArea.getStyleClass().remove("config-section-active");
        }else{
            configIcon = new ImageViewURL("assets/icons/arrowUpIcon.png", 18);
            mainArea.getStyleClass().remove("config-section");
            mainArea.getStyleClass().add("config-section-active");
        }

        configIcon.setStyle("-fx-cursor: hand;");
        configIcon.setOnMouseClicked(_ -> onArrowButtonClicked());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        mainArea.setAlignment(Pos.CENTER);
        mainArea.getChildren().addAll(configLbl, spacer, configIcon);
        return mainArea;
    }
}
