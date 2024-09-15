package se233.lazycattool.view.template.cropPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import se233.lazycattool.view.template.components.ImageViewURL;
import se233.lazycattool.view.template.cropPane.components.CustomButton;

public class CropBtmSection extends HBox {
    private CustomButton cancelBtn = new CustomButton("Cancel", "#FFF", "#101828");
    private CustomButton confirmBtn = new CustomButton("Confirm", "#101828", "#FFF");
    private HBox twoButtonContainer = new HBox(10);
    private HBox helpContainer = new HBox(5);
    private ImageViewURL helpIcon = new ImageViewURL("assets/icons/questionIconGrey.png", 14);
    private Label helpLbl = new Label("Support");

    public CropBtmSection(){
        // right two Button [cancel, comfirm]
        cancelBtn.setBorder();
        twoButtonContainer.getChildren().addAll(cancelBtn, confirmBtn);

        // left Section [help-icon & "Support"]
        helpContainer.getChildren().addAll(helpIcon, helpLbl);
        helpContainer.setAlignment(Pos.CENTER);

        this.setSpacing(250);
        this.getChildren().addAll(helpContainer, twoButtonContainer);
        this.setPadding(new Insets(0, 0, 20, 0));
        this.setAlignment(Pos.CENTER);
    }
}
