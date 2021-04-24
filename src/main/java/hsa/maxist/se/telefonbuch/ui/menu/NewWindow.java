package hsa.maxist.se.telefonbuch.ui.menu;

import hsa.maxist.se.telefonbuch.ui.BookStage;
import hsa.maxist.se.telefonbuch.util.FileUtility;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;

public class NewWindow extends Stage {

    private final TextField fileName = new TextField("enter name");

    public NewWindow() {

        // --Panes
        AnchorPane anchorPane = new AnchorPane();

        // --Stage Preferences
        setTitle("New File");
        setScene(new Scene(anchorPane, 230, 100));
        initModality(Modality.APPLICATION_MODAL);
        resizableProperty().setValue(false);

        // --TextField
        fileName.onMouseClickedProperty().setValue(mouseEvent -> fileName.clear());
        AnchorPane.setTopAnchor(fileName, 10.0);
        AnchorPane.setLeftAnchor(fileName, 10.0);
        AnchorPane.setRightAnchor(fileName, 10.0);
        fileName.onActionProperty().setValue(t -> buttonAction());

        // --Button
        Button create = new Button("Create");
        create.setOnAction(t -> buttonAction());

        AnchorPane.setBottomAnchor(create, 10.0);
        AnchorPane.setLeftAnchor(create, 80.0);
        AnchorPane.setRightAnchor(create, 80.0);

        // --Apply
        anchorPane.getChildren().addAll(fileName, create);

        // --Show
        show();
    }

    private void buttonAction() {
        String fileNameText = fileName.getText().strip();
        if(fileNameText.equals(""))
            return;
        String[] splittedName = fileNameText.split("\\s");
        StringBuilder newFileName = new StringBuilder();
        for (String s : splittedName) {
            newFileName.append(s);
        }

        if(Arrays.stream(FileUtility.getFileNames(false))
                .anyMatch(fileName -> fileName.equals(newFileName.toString()))) {
            BookStage.Open(newFileName.toString());
            close();
            return;
        }

        new BookStage(newFileName.toString()).show();
        close();
    }
}
