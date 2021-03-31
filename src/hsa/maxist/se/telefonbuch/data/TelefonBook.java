package hsa.maxist.se.telefonbuch.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TelefonBook implements Iterable<TelefonEntry>{

    ObservableList<TelefonEntry> telefonNumbers;
    File savedBook = new File("src/hsa/maxist/se/telefonbuch/resources/data.txt");
    String regex = "    ";
    String empty = "###";

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
        }
    }

    public void read() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(savedBook);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't load the requested File.");
        }
        if(scanner != null)
            try {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lines = line.split(regex);
                    if(lines.length != 0) {
                        TelefonEntry telefonEntry = new TelefonEntry();
                        telefonEntry.setFirstName(lines[0]);
                        telefonEntry.setLastName(lines[1]);
                        telefonEntry.setNumber(lines[2]);
                        telefonNumbers.add(telefonEntry);
                    }
                }

            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Something is wrong with that File");
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
            // First Name
            if(entry.getFirstName() == null || entry.getFirstName().equals(TelefonEntry.empty) || entry.getFirstName().equals(empty))
                content.append(empty);
            else content.append(entry.getFirstName());
            content.append(regex);

            // Last Name
            if(entry.getLastName() == null || entry.getLastName().equals(TelefonEntry.empty) || entry.getLastName().equals(empty))
                content.append(empty);
            else content.append(entry.getLastName());
            content.append(regex);

            // Number
            if (entry.getNumber() == null || entry.getNumber().equals(TelefonEntry.empty) || entry.getNumber().equals(empty))
                content.append(empty);
            else content.append(entry.getNumber());

            // New Line
            content.append('\n');
        }
        return content.toString();
    }
}
