package hsa.maxist.se.telefonbuch.ui.menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        // --Button
        Button create = new Button("Create");
        create.setOnAction(t -> {
            System.out.println("New");
        });
        AnchorPane.setBottomAnchor(create, 10.0);
        AnchorPane.setLeftAnchor(create, 80.0);
        AnchorPane.setRightAnchor(create, 80.0);

        // --Apply
        anchorPane.getChildren().addAll(fileName, create);

        // --Show
        show();
    }

    public String getText() {
        return fileName.getText();
    }

}
