package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.lang.invoke.VarHandle;
import java.util.Locale;

public class SearchArea {

    private final AnchorPane anchorPane = new AnchorPane();
    private final TextField searchTextField = new TextField("Type to search");
    private final Button searchButton = new Button("Search");


    public SearchArea(TelefonBook telefonBook) {
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

    public void setAction(EventHandler<ActionEvent> onClick) {
        searchButton.onActionProperty().setValue(onClick);
    }

    public Node getPane() {
        return anchorPane;
    }
}