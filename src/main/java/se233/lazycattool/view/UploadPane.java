package se233.lazycattool.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import se233.lazycattool.Launcher;
import se233.lazycattool.model.ImageFile;
import se233.lazycattool.view.template.uploadPane.DragDropBox;
import se233.lazycattool.view.template.components.HeadingSection;
import se233.lazycattool.view.template.uploadPane.ScrollSection;
import se233.lazycattool.view.template.uploadPane.UploadedFile;

import java.io.IOException;
import java.util.ArrayList;

import static se233.lazycattool.controller.ImportFileController.onDragOver;
import static se233.lazycattool.controller.ImportFileController.onDragDropped;

public class UploadPane extends ScrollPane {
    public UploadPane() {}
    private ArrayList<ImageFile> imageArray;

    private Pane getDetailsPane() {
        VBox uploadInfoPane = new VBox(25);
        uploadInfoPane.getStyleClass().add("upload-pane");
        uploadInfoPane.setAlignment(Pos.TOP_LEFT);

        // use Heading component from [view/template]
        HeadingSection headingGroup = new HeadingSection("Upload and attach files", "Upload and attach images to the system.");

        // use Drag and Drop box component from view/template
        DragDropBox dragDropGroup = getDragDropBox();


        //Bottom Section

        // use Scroll Section component from [view/template]
        ScrollSection scrollGroup = new ScrollSection();

        // Uploaded File's Component from [view/template]
        if (!imageArray.isEmpty()) {
            UploadedFile[] uploadedList = new UploadedFile[imageArray.size()];

            for (int i = 0; i < imageArray.size(); i++) {
                int finalI = i;

                uploadedList[i] = new UploadedFile(imageArray.get(i).getName(), imageArray.get(i).getSize());

                uploadedList[i].getCloseIcon().setOnMouseClicked(event -> onDeleteFileClick(finalI));

                uploadedList[i].setOnMouseEntered(event -> {
                    uploadedList[finalI].isToggle(true);
                });
                int finalI1 = i;
                uploadedList[i].setOnMouseExited(event -> {
                    uploadedList[finalI1].isToggle(false);
                });
                System.out.println(imageArray.get(i).getFilepath());
            }
            //System.out.println(imageArray.size());
            scrollGroup.setChildrenElement(uploadedList);
        }

        uploadInfoPane.getChildren().addAll(headingGroup, dragDropGroup, scrollGroup);

        return uploadInfoPane;
    }

    // Event hading when click remove file button
    private void onDeleteFileClick(int fileIndex){
        imageArray.remove(fileIndex);
        Launcher.refreshUploadPane();
    }

    // Drag and Drop box functionality
    private static DragDropBox getDragDropBox() {
        DragDropBox dragDropGroup = new DragDropBox();

        // Middle Drag drop
        dragDropGroup.setOnDragOver(e -> onDragOver(e));
        dragDropGroup.setOnDragDropped(e -> {
            try {
                onDragDropped(e);
            } catch (IOException ex) {
                // (E.2) RuntimeException
                throw new RuntimeException(ex);
            }
        });

        dragDropGroup.setOnDragEntered(event -> {
            dragDropGroup.isToggle(true);

        });
        dragDropGroup.setOnDragExited(event->{
            dragDropGroup.isToggle(false);
        });
        return dragDropGroup;
    }


    public void drawPane(ArrayList<ImageFile> imageArray){
        this.imageArray = imageArray;
        Pane uploadInfoPane = getDetailsPane();

        this.setStyle("-fx-background-color:#FFF;");
        this.setContent(uploadInfoPane);
    }

}
