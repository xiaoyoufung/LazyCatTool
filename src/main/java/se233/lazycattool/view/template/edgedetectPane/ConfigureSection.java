package se233.lazycattool.view.template.edgedetectPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import se233.lazycattool.view.template.components.ImageViewURL;
import static se233.lazycattool.controller.EdgeDetectController.onArrowButtonClicked;

public class ConfigureSection extends VBox {
    private static boolean isClicked = false;

    public String getChoosenAlgo() {
        return choosenAlgo;
    }

    public void setChoosenAlgo(String choosenAlgo) {
        this.choosenAlgo = choosenAlgo;
    }

    private String choosenAlgo;

    public static void setClicked(boolean clicked) {
        isClicked = clicked;
    }
    public static boolean isClicked() {
        return isClicked;
    }

    public ConfigureSection(){
        VBox mainArea = genMainArea(choosenAlgo);
        this.getChildren().addAll(mainArea);
    }

    private VBox genMainArea(String choosenAlgo){
        VBox mainArea = new VBox(10);
        //mainArea.setPadding(new Insets(15, 10, 15, 0));
        Label lowThesholdLbl, highThesholdLbl;

        HBox topArea = genTopArea();

        VBox configureBox = new VBox(10);
        configureBox.setPrefWidth(545);

        if(isClicked){
            mainArea.getStyleClass().add("config-section");

            Region spacer1 = new Region();
            HBox.setHgrow(spacer1, Priority.ALWAYS);
            Region spacer2 = new Region();
            HBox.setHgrow(spacer2, Priority.ALWAYS);

            HBox lowThesholdBox = new HBox();
            lowThesholdLbl = new Label("Low threshold");
            Slider lowSlider = new Slider(0, 100, 0);
            lowThesholdBox.getChildren().addAll(lowThesholdLbl, spacer1, lowSlider);

            HBox highThesholdBox = new HBox();
            highThesholdLbl = new Label("High threshold");
            Slider highSlider = new Slider(0, 100, 0);
            highThesholdBox.getChildren().addAll(highThesholdLbl, spacer2, highSlider);

            configureBox.getChildren().addAll(lowThesholdBox, highThesholdBox);

            if(choosenAlgo == "Canny"){
                //
            } else if (choosenAlgo == "Laplacian") {
                //
            } else if (choosenAlgo == "Robert") {
                //
            }
        } else{
            mainArea.getStyleClass().remove("config-section");
        }

        mainArea.getChildren().addAll(topArea, configureBox);
        return mainArea;
    }

    private HBox genTopArea(){
        HBox topArea = new HBox();
        topArea.setPadding(new Insets(15, 10, 15, 0));

        Label configLbl;
        configLbl = new Label("Configuration");
        configLbl.getStyleClass().add("small-heading");

        // icon
        ImageViewURL configIcon;
        if(!isClicked){
            configIcon = new ImageViewURL("assets/icons/arrowDownIcon.png", 18);
            topArea.getStyleClass().add("config-section");
            topArea.getStyleClass().remove("config-section-active");
        }else{
            configIcon = new ImageViewURL("assets/icons/arrowUpIcon.png", 18);
            topArea.getStyleClass().remove("config-section");
            topArea.getStyleClass().add("config-section-active");
        }
        configIcon.setStyle("-fx-cursor: hand;");
        configIcon.setOnMouseClicked(_ -> onArrowButtonClicked());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topArea.setAlignment(Pos.CENTER);
        topArea.getChildren().addAll(configLbl, spacer, configIcon);

        return topArea;
    }
}
