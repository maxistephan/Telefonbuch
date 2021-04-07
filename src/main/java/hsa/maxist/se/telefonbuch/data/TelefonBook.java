package hsa.maxist.se.telefonbuch.data;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class TelefonBook implements Iterable<TelefonEntry>{

    private final ObservableList<TelefonEntry> telefonNumbers;
    private ObservableList<TelefonEntry> searchResults;
    private final Path savedBookPath = FileSystems.getDefault().getPath("src", "main", "resources", "data.json");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String empty = "<empty>";

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
        JsonFactory factory = new JsonFactory();
        try(OutputStream os = Files.newOutputStream(savedBookPath);
            JsonGenerator jg = factory.createGenerator(os)) {

            jg.writeStartArray();

            for(TelefonEntry entry : telefonNumbers) {
                jg.writeStartObject();
                jg.writeNumberField("id", entry.getId());
                jg.writeObjectFieldStart("name");
                jg.writeStringField("first", entry.getFirstName());
                jg.writeStringField("last", entry.getLastName());
                jg.writeEndObject();
                jg.writeStringField("number", entry.getNumber());
                jg.writeEndObject();
            }
            jg.writeEndArray();

        } catch ( IOException e) {
            e. printStackTrace () ;
        }

    }

    public void load() {
        try (InputStream is = Files.newInputStream(savedBookPath)) {

            JsonNode rootArray = mapper.readTree(is);

            for (JsonNode root : rootArray) {

                TelefonEntry entry = new TelefonEntry();

                // Get id
                entry.setId(root.path("id").asLong());

                // Get Name
                JsonNode nameNode = root.path("name");
                if (!nameNode.isMissingNode()) {        // if "name" node is exist
                    entry.setFirstName(nameNode.path("first").asText());
                    entry.setLastName(nameNode.path("last").asText());
                }

                // Get Number
                entry.setNumber( root.path("number").asText());
                telefonNumbers.add(entry);
            }

        } catch (IOException e) {
            e.printStackTrace();
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
            content.append('\t');

            // Last Name
            if(entry.getLastName().equals("") || entry.getLastName().equals(TelefonEntry.empty))
                content.append(empty);
            else content.append(entry.getLastName());
            content.append('\t');

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
