package hsa.maxist.se.telefonbuch.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DeleteArea {

    AnchorPane anchorPane = new AnchorPane();

    public DeleteArea(EventHandler<ActionEvent> onDelete, EventHandler<ActionEvent> onAdd, EventHandler<ActionEvent> onSave) {

        // <Delete> Button
        Button deleteButon = new Button("Delete");
        deleteButon.onActionProperty().setValue(onDelete);

        AnchorPane.setTopAnchor(deleteButon, 10.0);
        AnchorPane.setRightAnchor(deleteButon, 10.0);
        AnchorPane.setBottomAnchor(deleteButon, 10.0);

        // <+> Button
        Button addButton = new Button("+");
        addButton.onActionProperty().setValue(onAdd);

        AnchorPane.setLeftAnchor(addButton, 10.0);
        AnchorPane.setTopAnchor(addButton, 10.0);
        AnchorPane.setBottomAnchor(addButton, 10.0);

        //<Save> Button
        Button saveButton = new Button("Save");
        saveButton.onActionProperty().setValue(onSave);

        AnchorPane.setLeftAnchor(saveButton, 40.0);
        AnchorPane.setTopAnchor(saveButton, 10.0);
        AnchorPane.setBottomAnchor(saveButton, 10.0);

        anchorPane.getChildren().addAll(deleteButon, addButton, saveButton);
    }

    public Node getPane() {
        return anchorPane;
    }
}
