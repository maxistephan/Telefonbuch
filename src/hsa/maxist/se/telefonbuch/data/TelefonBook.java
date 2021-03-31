package hsa.maxist.se.telefonbuch.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class TelefonBook implements Iterable<TelefonEntry>{

    ObservableList<TelefonEntry> telefonNumbers;
    File savedBook = new File("src/hsa/maxist/se/telefonbuch/resources/data.txt");


    public TelefonBook() {
        telefonNumbers = FXCollections.observableArrayList();
    }

    public void delete(ObservableList<TelefonEntry> selected){
        for (TelefonEntry item : selected) {
            telefonNumbers.remove(item);
        }
    }

    public void add(Event event) {
        telefonNumbers.add(new TelefonEntry());
    }

    public void search(Event event) {

    }

    public void save() {
        try {
            // Content schreiben
            FileWriter writer = new FileWriter(savedBook);
            writer.write(toString());
            writer.close();
            System.out.println("Content Saved!");
        } catch (IOException ioe) {
            System.out.println("Something went wrong while saving");
            ioe.printStackTrace();
        }
    }

    public ObservableList<TelefonEntry> getTelefonNumbers(){
        return telefonNumbers;
    }

    @Override
    public Iterator<TelefonEntry> iterator() {
        return telefonNumbers.iterator();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (TelefonEntry entry : telefonNumbers) {
            if(entry.getFirstName().equals("Click to edit")) {
                content.append('\n');
                continue;
            }
            content.append(entry.getFirstName()).append("    ");
            content.append(entry.getLastName()).append("    ");
            content.append(entry.getNumber()).append("\n");
        }
        return content.toString();
    }
}
