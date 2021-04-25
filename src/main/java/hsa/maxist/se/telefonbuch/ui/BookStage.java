package hsa.maxist.se.telefonbuch.ui;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.ui.menu.FileListWindow;
import hsa.maxist.se.telefonbuch.ui.menu.NewWindow;
import hsa.maxist.se.telefonbuch.util.FileUtility;
import javafx.beans.binding.When;
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
import java.util.ArrayList;
import java.util.Arrays;

public class BookStage extends Stage {

    private static final ArrayList<BookStage> instances = new ArrayList<>();
    private final Path filepath;
    private final TelefonBook telefonBook;
    public static final String DEFAULT_NAME = "NewBook";

    public BookStage() {
        this(DEFAULT_NAME);
    }

    public BookStage(String name, boolean fullscreen) {
        this(name);
        setFullScreen(fullscreen);
    }

    public BookStage(String name) {
        BorderPane root = new BorderPane();

        // --Fenster Einstellen
        setTitle(name.equals("") ? DEFAULT_NAME : name);
        setScene(new Scene(root, 370, 400));

        // --Filepath
        String[] filepathString = Arrays.copyOf(FileUtility.pathToBooks, FileUtility.pathToBooks.length + 1);
        filepathString[FileUtility.pathToBooks.length] = getTitle() + ".json";
        filepath = FileSystems.getDefault().getPath(FileUtility.first, filepathString);

        // --Telefon Buch instanzieren
        telefonBook = new TelefonBook();
        telefonBook.load(filepath);

        // --Menu Bar
        TelefonMenu menuBar = new TelefonMenu(
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
        setOnCloseRequest(windowEvent -> close());
        instances.add(this);
    }

    private class TelefonMenu extends MenuBar {


        public TelefonMenu(EventHandler<ActionEvent> onSave) {

            // --File Menu
            Menu fileMenu = new Menu("File");
            //new
            MenuItem newFile = new MenuItem("New");
            newFile.onActionProperty().setValue(t ->
                    new NewWindow()
            );
            //open
            MenuItem open = new MenuItem("Open");
            open.onActionProperty().setValue(t ->
                    new FileListWindow("Open", BookStage.this)
            );
            //save
            MenuItem save = new MenuItem("Save");
            save.onActionProperty().setValue(onSave);
            //import
            MenuItem importItem = new MenuItem("Import");
            importItem.onActionProperty().setValue(t ->
                    new FileListWindow("Import", BookStage.this)
            );

            fileMenu.getItems().addAll(newFile, open, save, importItem);

            // --Edit Menu
            Menu editMenu = new Menu("Edit");
            //delete all
            MenuItem deleteAll = new MenuItem("Delete all");
            deleteAll.onActionProperty().setValue(t ->
                BookStage.this.telefonBook.delete(telefonBook.getTelefonNumbers())
            );

            editMenu.getItems().addAll(deleteAll);

            // --View Menu
            Menu viewMenu = new Menu("View");
            //fullscreen toggle
            MenuItem fullscreenToggle = new MenuItem("Fullscreen");
            fullscreenToggle.textProperty().bind(
                    new When(BookStage.this.fullScreenProperty())
                            .then("Exit Full Screen")
                            .otherwise("Full Screen"));
            fullscreenToggle.onActionProperty().setValue(t ->
                BookStage.this.setFullScreen(!BookStage.this.isFullScreen())
            );

            viewMenu.getItems().addAll(fullscreenToggle);

            getMenus().addAll(fileMenu, editMenu, viewMenu);
        }
    }

    public static BookStage[] getInstances() {
        BookStage[] bookStages = new BookStage[instances.size()];
        for(int i = 0; i < bookStages.length; i++) {
            bookStages[i] = instances.get(i);
        }
        return bookStages;
    }

    /*******************************************************************************************************************
     * Opens a new Book
     * @param filename Name of the File to open (without the .json Extension)
     ******************************************************************************************************************/
    public static void Open(String filename) {
        BookStage[] currentBooks = getInstances();

        for (BookStage bs : currentBooks) {
            if (bs.getTitle().equals(filename)) {
                return;
            }
        }
        new BookStage(filename).show();
    }

    /*******************************************************************************************************************
     * Imports entries from a Book
     * @param filename Name of the File to import (without the .json Extension)
     ******************************************************************************************************************/
    public void Import(String filename) {
        String[] filepathString = Arrays.copyOf(FileUtility.pathToBooks, FileUtility.pathToBooks.length + 1);
        filepathString[FileUtility.pathToBooks.length] = filename + ".json";
        telefonBook.load(FileSystems.getDefault().getPath(FileUtility.first, filepathString));
    }

    @Override
    public final void close() {
        telefonBook.save(filepath);
        instances.remove(this);
        super.close();
    }
}
