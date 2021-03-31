package hsa.maxist.se.telefonbuch.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SearchArea {

    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField searchTextField = new TextField("Search here");
    private final Button searchButton = new Button("Search");


    public SearchArea() {
        searchTextField.onMouseClickedProperty().setValue(mouseEvent -> searchTextField.clear());

        AnchorPane.setLeftAnchor(searchTextField, 10.0);
        AnchorPane.setTopAnchor(searchTextField, 10.0);
        AnchorPane.setRightAnchor(searchTextField, 90.0);
        AnchorPane.setBottomAnchor(searchTextField, 10.0);

        AnchorPane.setTopAnchor(searchButton, 10.0);
        AnchorPane.setRightAnchor(searchButton, 10.0);
        AnchorPane.setBottomAnchor(searchButton, 10.0);

        anchorPane.getChildren().addAll(searchTextField, searchButton);
    }

    public String getSearchText() {
        return searchTextField.getText();
    }

    public void setButtonAction(EventHandler<ActionEvent> onClick) {
        searchButton.onActionProperty().setValue(onClick);
    }

    public Node getPane() {
        return anchorPane;
    }
}