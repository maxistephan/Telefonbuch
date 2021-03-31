package hsa.maxist.se.telefonbuch;

import hsa.maxist.se.telefonbuch.data.TelefonBook;
import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import hsa.maxist.se.telefonbuch.ui.DeleteArea;
import hsa.maxist.se.telefonbuch.ui.EntryArea;
import hsa.maxist.se.telefonbuch.ui.SearchArea;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main extends Application {

    File savedBook = new File("src/hsa/maxist/se/telefonbuch/resources/data.txt");

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        // Telefon Buch instanzieren
        TelefonBook telefonBook = new TelefonBook();
        TelefonEntry peter = new TelefonEntry();

        SearchArea searchArea = new SearchArea(telefonBook::search);
        root.setTop(searchArea.getPane());

        // Nummern hinzufügen
        telefonBook.getTelefonNumbers().add(peter);
        telefonBook.getTelefonNumbers().add(new TelefonEntry());

        peter.setNumber("0821/2799555");
        peter.setFirstName("Peter");
        peter.setLastName("Peterson");

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
        primaryStage.setOnCloseRequest(saveContent(telefonBook));
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

    /*******************************************************************************************************************
     *
     * On Close Operation (Save)
     *
     *******************************************************************************************************************/

    private EventHandler<WindowEvent> saveContent(TelefonBook telefonBook) {
        return windowEvent -> {

            // Content Holen
            StringBuilder content = new StringBuilder();

            for (TelefonEntry entry : telefonBook) {
                if(entry.getFirstName().equals("Click to edit")) {
                    content.append('\n');
                    continue;
                }
                content.append(entry.getFirstName()).append("    ");
                content.append(entry.getLastName()).append("    ");
                content.append(entry.getNumber()).append("\n");
            }

            try {
                // Content schreiben
                FileWriter writer = new FileWriter(savedBook);
                writer.write(content.toString());
                writer.close();
                System.out.println("Content Saved!");
            } catch (IOException ioe) {
                System.out.println("Something went wrong while saving");
                ioe.printStackTrace();
            }
        };
    }
}