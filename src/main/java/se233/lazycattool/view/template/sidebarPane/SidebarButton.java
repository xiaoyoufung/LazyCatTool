package se233.lazycattool.view.template.sidebarPane;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import se233.lazycattool.view.template.components.ImageViewURL;

public class SidebarButton extends StackPane {
    private boolean isOnClick = false;

    public void setOnClick(boolean onClick) {
        isOnClick = onClick;
        setOnClickStyle();
    }

    public SidebarButton(String btnTxt, String iconURL){
        ImageViewURL btnIcon = new ImageViewURL(iconURL, 14);
        Label btnTextLbl = new Label(" " + btnTxt, btnIcon);
        btnTextLbl.setPadding(new Insets(0, 0, 0,20));
        btnTextLbl.getStyleClass().add("sidebar-btn-lbl");
        btnTextLbl.setFont(Font.font("Inter", 600));

        this.getChildren().add(btnTextLbl);
        this.setAlignment(Pos.BASELINE_LEFT);
        setMargin(this, new Insets(0));
    }

    public void setOnAction(EventHandler<MouseEvent> eventHandler) {
        this.setOnMouseClicked(eventHandler);
    }

    private void setOnClickStyle(){
        if (this.isOnClick){
            this.setPadding(new Insets(12,150,12,0));
            this.setStyle("-fx-background-radius: 8; -fx-background-color: #FFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 8, 0, 0, 0);");
        } else {
            this.setPadding(new Insets(0));
            this.setStyle("");
        }
    }
}
