package hsa.maxist.se.telefonbuch.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DeleteArea {

    AnchorPane anchorPane = new AnchorPane();
    private final Button deleteButon = new Button("Delete");

    public DeleteArea() {

        AnchorPane.setTopAnchor(deleteButon, 10.0);
        AnchorPane.setRightAnchor(deleteButon, 10.0);
        AnchorPane.setBottomAnchor(deleteButon, 10.0);

        deleteButon.onActionProperty().setValue(actionEvent -> {

        });

        anchorPane.getChildren().addAll(deleteButon);
    }


    public Node getPane() {
        return anchorPane;
    }
}
