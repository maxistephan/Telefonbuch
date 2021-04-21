package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Frame extends Stage {

    private final SearchArea searchArea;
    private final EntryArea entryArea;
    private final DeleteArea deleteArea;
    private TelefonBook telefonBook;

    public Frame() {
        BorderPane root = new BorderPane();

        // Telefon Buch instanzieren
        TelefonBook telefonBook = new TelefonBook();
        telefonBook.load();

        // Entry Area
        entryArea = new EntryArea(telefonBook.getTelefonNumbers());
        root.setCenter(entryArea.getAnchorPane());

        // Search Area
        searchArea = new SearchArea();
        searchArea.setButtonAction(onSearch -> entryArea.setItems(telefonBook.search(searchArea.getSearchText())));
        root.setTop(searchArea.getPane());

        // Delete Area
        deleteArea = new DeleteArea(
                onDelete -> telefonBook.delete(entryArea.getSelectedEntries()),
                onAdd -> telefonBook.add(),
                onSave -> telefonBook.save());
        root.setBottom(deleteArea.getPane());

        // Fenster Einstellen
        this.setTitle("Telefon Buch");
        this.setScene(new Scene(root, 330, 275));

        // save on close
        this.setOnCloseRequest(windowEvent -> telefonBook.save());
    }

}
