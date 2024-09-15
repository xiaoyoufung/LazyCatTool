package se233.lazycattool.view.template.uploadPane;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ScrollSection extends ScrollPane {

    private final VBox FileGroup = new VBox(10);

    public void setChildrenElement(UploadedFile[] childrenElement) {
        FileGroup.getChildren().addAll(childrenElement);
        this.setContent(FileGroup);
    }

    public ScrollSection(){
        this.setStyle("-fx-background: rgb(255,255,255);\n -fx-background-color: rgb(255,255,255)");
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.getStyleClass().add("scroll-pane");
        this.setPrefSize(588, 225);
        //this.setPadding(new Insets(0,0,10,0));
    }
}
