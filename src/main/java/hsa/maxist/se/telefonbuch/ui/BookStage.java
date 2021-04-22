package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.ui.menu.NewWindow;
import hsa.maxist.se.telefonbuch.ui.menu.OpenWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

public class BookStage extends Stage {

    private final TelefonMenu menuBar;
    public static final String[] pathToBooks = {"main", "resources", "books"};
    public static final String first = "src";
    private Path filepath;
    private TelefonBook telefonBook;

    public BookStage(String name) {
        BorderPane root = new BorderPane();

        // --Fenster Einstellen
        setTitle(name);
        setScene(new Scene(root, 370, 400));

        // --Filepath
        String[] filepathString = Arrays.copyOf(pathToBooks, pathToBooks.length + 1);
        filepathString[pathToBooks.length] = getTitle() + ".json";
        filepath = FileSystems.getDefault().getPath(first, filepathString);

        // --Telefon Buch instanzieren
        TelefonBook telefonBook = new TelefonBook();
        telefonBook.load(filepath);

        // --Menu Bar
        menuBar = new TelefonMenu(
                onSave -> telefonBook.save(filepath)
        );

        // --Entry Area
        EntryArea entryArea = new EntryArea(telefonBook.getTelefonNumbers());
        root.setCenter(entryArea.getAnchorPane());

        // --Search Area
        SearchArea searchArea = new SearchArea();
        searchArea.setButtonAction(onSearch -> entryArea.setItems(telefonBook.search(searchArea.getSearchText())));
        root.setTop(new VBox(menuBar, searchArea.getPane()));

        // --Delete Area
        DeleteArea deleteArea = new DeleteArea(
                onDelete -> telefonBook.delete(entryArea.getSelectedEntries()),
                onAdd -> telefonBook.add());
        root.setBottom(deleteArea.getPane());

        // --save on close
        setOnCloseRequest(windowEvent ->
                telefonBook.save(filepath)
        );
    }

    private class TelefonMenu extends MenuBar {

        public TelefonMenu(EventHandler<ActionEvent> onSave) {

            // --File Menu
            Menu fileMenu = new Menu("File");
            //new
            MenuItem newFile = new MenuItem("New");
            newFile.onActionProperty().setValue(t -> new NewWindow());
            //open
            MenuItem open = new MenuItem("Open");
            open.onActionProperty().setValue(t -> new OpenWindow("Open"));
            //save
            MenuItem save = new MenuItem("Save");
            save.onActionProperty().setValue(onSave);
            //import
            MenuItem importItem = new MenuItem("Import");
            importItem.onActionProperty().setValue(t ->
                    new OpenWindow("Import")
            );

            fileMenu.getItems().addAll(newFile, open, save, importItem);

            // --Edit Menu
            Menu editMenu = new Menu("Edit");

            // --View Menu
            Menu viewMenu = new Menu("View");

            getMenus().addAll(fileMenu, editMenu, viewMenu);
        }

    }
}
