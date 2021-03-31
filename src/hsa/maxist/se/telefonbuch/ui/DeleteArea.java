package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DeleteArea {

    AnchorPane anchorPane = new AnchorPane();

    public DeleteArea(EntryArea entryArea, TelefonBook telefonBook) {

        Button deleteButon = new Button("Delete");
        AnchorPane.setTopAnchor(deleteButon, 10.0);
        AnchorPane.setRightAnchor(deleteButon, 10.0);
        AnchorPane.setBottomAnchor(deleteButon, 10.0);

        deleteButon.onActionProperty().setValue(actionEvent -> {
            ObservableList<TelefonEntry> selected = entryArea.getSelectedEntries();
            for(TelefonEntry item : selected) {
                telefonBook.getTelefonNumbers().remove(item);
            }
        });

        anchorPane.getChildren().addAll(deleteButon);
    }

    public Node getPane() {
        return anchorPane;
    }
}
