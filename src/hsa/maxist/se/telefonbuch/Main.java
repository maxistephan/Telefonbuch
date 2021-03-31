package hsa.maxist.se.telefonbuch;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import hsa.maxist.se.telefonbuch.ui.DeleteArea;
import hsa.maxist.se.telefonbuch.ui.EntryArea;
import hsa.maxist.se.telefonbuch.ui.SearchArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        // Telefon Buch instanzieren
        TelefonBook telefonBook = new TelefonBook();

        // Search Area
        SearchArea searchArea = new SearchArea(telefonBook::search);
        root.setTop(searchArea.getPane());

        // Entry Area
        EntryArea entryArea = new EntryArea(telefonBook.getTelefonNumbers());
        root.setCenter(entryArea.getAnchorPane());

        // Delete Area
        DeleteArea deleteArea = new DeleteArea(
                onDelete -> telefonBook.delete(entryArea.getSelectedEntries()),
                telefonBook::add);
        root.setBottom(deleteArea.getPane());

        // Fenster Einstellen
        primaryStage.setTitle("Telefon Buch");
        primaryStage.setScene(new Scene(root, 300, 275));
        // save on close
        primaryStage.setOnCloseRequest(windowEvent -> telefonBook.save());
        primaryStage.show();
    }

    /*******************************************************************************************************************
     *
     * Main
     *
     *******************************************************************************************************************/

    public static void main(String[] args) {
        launch(args);
    }
}