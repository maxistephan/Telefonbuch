package hsa.maxist.se.telefonbuch.data;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hsa.maxist.se.telefonbuch.util.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class TelefonBook implements Iterable<TelefonEntry>{

    private final ObservableList<TelefonEntry> telefonNumbers;
    private ObservableList<TelefonEntry> searchResults;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EMPTY = "<empty>";

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

    public void save(Path path) {
        if(confirmedPath(path)) {
            JsonFactory factory = new JsonFactory();
            try (OutputStream os = Files.newOutputStream(path);
                 JsonGenerator jg = factory.createGenerator(os)) {
//
//                jg.writeStartArray();
//
//                for (TelefonEntry entry : telefonNumbers) {
//                    jg.writeStartObject();
//                    jg.writeObjectFieldStart("name");
//                    jg.writeStringField("first", entry.getFirstName());
//                    jg.writeStringField("last", entry.getLastName());
//                    jg.writeEndObject();
//                    jg.writeStringField("number", entry.getNumber());
//                    jg.writeEndObject();
//                }
//                jg.writeEndArray();
                 jg.writeRaw(FileManager.asJSONString(telefonNumbers));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load(Path path) {
        if(confirmedPath(path)) {
            try (InputStream is = Files.newInputStream(path)) {

                JsonNode rootArray = MAPPER.readTree(is);
                FileManager.extractNodeArray(telefonNumbers, rootArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
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
            if(entry.getFirstName().equals("") || entry.getFirstName().equals(TelefonEntry.empty))
                content.append(EMPTY);
            else content.append(entry.getFirstName());
            content.append('\t');

            // Last Name
            if(entry.getLastName().equals("") || entry.getLastName().equals(TelefonEntry.empty))
                content.append(EMPTY);
            else content.append(entry.getLastName());
            content.append('\t');

            // Number
            if (entry.getNumber().equals("") || entry.getNumber().equals(TelefonEntry.empty))
                content.append(EMPTY);
            else content.append(entry.getNumber());

            // New Line
            content.append('\n');
        }
        return content.toString();
    }

    private boolean confirmedPath(Path filepath) {
        File f = new File(String.valueOf(filepath));
        try {
            if(f.exists())
                return true;
            return f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
