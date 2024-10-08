package se233.lazycattool.view.template.edgedetectPane;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import se233.lazycattool.controller.EdgeDetectController;
import se233.lazycattool.view.template.components.ImageViewURL;
import static se233.lazycattool.controller.EdgeDetectController.onArrowButtonClicked;

public class ConfigureSection extends VBox {
    private static boolean isClicked = false;
    private static Slider lowSlider;
    private static Slider highSlider;
    private static ComboBox<String> markSizeComboBox;
    private static ComboBox<String> kernalSizeComboBox;
    private static Slider thresholdSlider;
    private String choosenAlgo;

    public static int getKernalSizeComboBox() {
        return kernalSizeComboBox.getSelectionModel().getSelectedIndex();
    }

    public static double getThresholdSlider() {
        return thresholdSlider.getValue();
    }

    public static int getMarkSizeComboBox() {
        return markSizeComboBox.getSelectionModel().getSelectedIndex();
    }

    public static double getHighSlider() {
        return highSlider.getValue();
    }

    public static double getLowSlider() {
        return lowSlider.getValue();
    }

    public static boolean isIsClicked() {
        return isClicked;
    }

    public String getChoosenAlgo() {
        return choosenAlgo;
    }

    public void setChoosenAlgo(String choosenAlgo) {
        this.choosenAlgo = choosenAlgo;
    }

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

        HBox topArea = genTopArea();

        VBox configureArea = genConfigureArea();

        mainArea.getChildren().add(topArea);

        if(isClicked){
            mainArea.getStyleClass().add("config-main-section");
            mainArea.getChildren().add(configureArea);
        } else{
            mainArea.getStyleClass().remove("config-main-section");
        }

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

    private VBox genConfigureArea(){
        Label lowThesholdLbl, highThesholdLbl, convolutionMarkLbl, kernalSizeLbl, thresholdLbl;
        VBox configureArea = new VBox(15);
        configureArea.setPrefWidth(545);
        configureArea.setPadding(new Insets(10, 0, 10,0));

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        if(EdgeDetectController.getChoosenAlgo() == "Canny"){
            HBox lowThesholdBox = new HBox();
            lowThesholdLbl = new Label("Low threshold");

            // low slider
            lowSlider = new Slider(1.0, 150.0, 100.0);
            lowThesholdBox.getChildren().addAll(lowThesholdLbl, spacer1, lowSlider);

            HBox highThresholdBox = new HBox();
            highThesholdLbl = new Label("High threshold");
            highSlider = new Slider(1.0, 90.0, 45.0);
            highThresholdBox.getChildren().addAll(highThesholdLbl, spacer2, highSlider);

            configureArea.getChildren().addAll(lowThesholdBox, highThresholdBox);
        } else if (EdgeDetectController.getChoosenAlgo() == "Laplacian") {
            convolutionMarkLbl = new Label("Size of the convolution mask");

            String[] markSizes = { "3x3", "5x5" };

            markSizeComboBox = new ComboBox<>(FXCollections.observableArrayList(markSizes));
            markSizeComboBox.getSelectionModel().select(0);
            markSizeComboBox.setPrefWidth(150); // Set a specific width for the ComboBox

            // Create HBox for layout
            HBox kernelSizeBox = new HBox(10);
            kernelSizeBox.setAlignment(Pos.CENTER_LEFT);

            // Add components to HBox
            kernelSizeBox.getChildren().addAll(convolutionMarkLbl, markSizeComboBox);

            // Use a Region as a spacer to push the ComboBox to the right
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            kernelSizeBox.getChildren().add(1, spacer);

            configureArea.getChildren().addAll(kernelSizeBox);
        } else if (EdgeDetectController.getChoosenAlgo() == "Sobel") {
            kernalSizeLbl = new Label("Kernal Size");
            thresholdLbl = new Label("Threshold");

            String[] kernelSizes = { "3x3", "5x5" };
            kernalSizeComboBox = new ComboBox<>(FXCollections.observableArrayList(kernelSizes));
            kernalSizeComboBox.getSelectionModel().select(0);
            kernalSizeComboBox.setPrefWidth(150); // Set a specific width for the ComboBox

            thresholdSlider = new Slider(0, 350, 0);


            // Create GridPane for layout
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // Add components to GridPane
            gridPane.add(kernalSizeLbl, 0, 0);
            gridPane.add(kernalSizeComboBox, 1, 0);
            gridPane.add(thresholdLbl, 0, 1);
            gridPane.add(thresholdSlider, 1, 1);

            // Set column constraints
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHgrow(Priority.SOMETIMES);
            col1.setHalignment(HPos.LEFT);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHgrow(Priority.SOMETIMES);
            col2.setHalignment(HPos.RIGHT);
            gridPane.getColumnConstraints().addAll(col1, col2);

            configureArea.getChildren().addAll(gridPane);
        }
        return configureArea;
    }
}
