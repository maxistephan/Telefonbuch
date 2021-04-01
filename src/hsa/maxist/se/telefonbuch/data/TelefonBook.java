package hsa.maxist.se.telefonbuch.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TelefonBook implements Iterable<TelefonEntry>{

    private final ObservableList<TelefonEntry> telefonNumbers;
    private ObservableList<TelefonEntry> searchResults;
    private final File savedBook = new File("src/hsa/maxist/se/telefonbuch/resources/data.txt");
    private final String regex = "    ";
    private final String empty = "###";

    private boolean isSearching = false;

    public TelefonBook() {
        telefonNumbers = FXCollections.observableArrayList();
    }

    public void delete(ObservableList<TelefonEntry> selected){
        // Copy to ArrayList
        ArrayList<TelefonEntry> selectedEntries = new ArrayList<>(selected);
        for (TelefonEntry item : selectedEntries) {
            telefonNumbers.remove(item);
            if(isSearching)
                searchResults.remove(item);
        }
        // update phonebook
        update();
    }

    public void add() {
        TelefonEntry newEntry = new TelefonEntry();
        telefonNumbers.add(newEntry);
        if(isSearching)
            searchResults.add(newEntry);
    }

    public ObservableList<TelefonEntry> search(String term) {

        if(term.matches(" *")) {
            isSearching = false;
            return telefonNumbers;
        }

        isSearching = true;
        term = term.toLowerCase();

        searchResults = FXCollections.observableArrayList();
        for(TelefonEntry entry : telefonNumbers) {
            if (entry.getFirstName().toLowerCase().contains(term)
                || entry.getLastName().toLowerCase().contains(term)
                || entry.getNumber().toLowerCase().contains(term))
                searchResults.add(entry);
        }

        if(searchResults.size() == 0) {
            isSearching = false;
            return telefonNumbers;
        }
        return searchResults;
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

    public void load() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(savedBook);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Couldn't load the requested file.");
        }
        if(scanner != null)
            try {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] lines = line.split(regex);

                    // if not empty
                    if(lines.length != 0) {

                        // make actually empty cells out of empty signs
                        for(int i = 0; i < lines.length; i++) {
                            if(lines[i].equals(empty))
                                lines[i] = "";
                        }

                        // create Entry
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

    public void update() {
        for (int i = 0; i < telefonNumbers.size(); i++) {
            telefonNumbers.get(i).setId(i);
        }
        TelefonEntry.numOfInstances = telefonNumbers.size();
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
            if(entry.getFirstName().equals("") || entry.getFirstName().equals(TelefonEntry.empty))
                content.append(empty);
            else content.append(entry.getFirstName());
            content.append(regex);

            // Last Name
            if(entry.getLastName().equals("") || entry.getLastName().equals(TelefonEntry.empty))
                content.append(empty);
            else content.append(entry.getLastName());
            content.append(regex);

            // Number
            if (entry.getNumber().equals("") || entry.getNumber().equals(TelefonEntry.empty))
                content.append(empty);
            else content.append(entry.getNumber());

            // New Line
            content.append('\n');
        }
        return content.toString();
    }
}
