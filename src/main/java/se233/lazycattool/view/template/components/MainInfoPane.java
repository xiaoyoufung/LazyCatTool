package se233.lazycattool.view.template.components;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class MainInfoPane extends VBox {
    public MainInfoPane(String styleClass){
        this.setSpacing(25);
        this.getStyleClass().add(styleClass);
        this.setAlignment(Pos.TOP_LEFT);
    }
}
