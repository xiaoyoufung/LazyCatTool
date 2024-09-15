package se233.lazycattool.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EdgeDetectPane extends ScrollPane {
    public EdgeDetectPane(){}

    private Pane getDetailsPane(){
        Pane edgeDetectInfoPane = new VBox(20);
        return edgeDetectInfoPane;
    }

    public void drawPane(){
        Pane edgeDetectInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(edgeDetectInfoPane);
    }
}
