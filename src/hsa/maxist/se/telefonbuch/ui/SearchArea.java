package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchArea {

    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField searchTextField = new TextField();
    private final Button searchButton = new Button("Search");

    public SearchArea() {
        AnchorPane.setLeftAnchor(searchTextField, 10.0);
        AnchorPane.setTopAnchor(searchTextField, 10.0);
        AnchorPane.setRightAnchor(searchTextField, 90.0);
        AnchorPane.setBottomAnchor(searchTextField, 10.0);

        AnchorPane.setTopAnchor(searchButton, 10.0);
        AnchorPane.setRightAnchor(searchButton, 10.0);
        AnchorPane.setBottomAnchor(searchButton, 10.0);

        searchButton.onActionProperty().setValue(actionEvent -> {
            
        });

        anchorPane.getChildren().addAll(searchTextField, searchButton);
    }

    public Node getPane() {
        return anchorPane;
    }
}