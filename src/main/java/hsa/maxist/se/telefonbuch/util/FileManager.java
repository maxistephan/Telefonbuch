package hsa.maxist.se.telefonbuch.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hsa.maxist.se.telefonbuch.data.TelefonEntry;
import hsa.maxist.se.telefonbuch.ui.BookStage;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public static File[] getFiles() {
        File folder =
                new File(String.valueOf(
                        FileSystems.getDefault().getPath(BookStage.first, BookStage.pathToBooks)
                )
        );
        return folder.listFiles();
    }

    public static String[] getFileNames(boolean fullName) {
        File[] listOfFiles = getFiles();
        String[] filenames = new String[listOfFiles.length];
        for(int i = 0; i < listOfFiles.length; i++) {
            if(fullName)  filenames[i] = listOfFiles[i].getName();
            else filenames[i] = getFileName(listOfFiles[i]);
        }
        return filenames;
    }

    public static String getFileName(File file) {
        if(file.getName().endsWith(".json"))
            return file.getName().split(".json")[0];
        return file.getName();
    }

    public static String getFileName(String file) {
        if(file.endsWith(".json"))
            return file.split(".json")[0];
        return file;
    }

    public static String asJSONString(List<TelefonEntry> entries) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        for(TelefonEntry entry : entries) {

            ObjectNode root = mapper.createObjectNode();

            ObjectNode nameNode = mapper.createObjectNode();
            nameNode.put("first", entry.getFirstName());
            nameNode.put("last", entry.getLastName());

            root.set("name", nameNode);

            root.put("number", entry.getNumber());
            arrayNode.add(root);
        }

        try {
            return mapper.writeValueAsString(arrayNode);
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<TelefonEntry> fromJson(String jsonString) {
        List<TelefonEntry> outputList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonString);
            extractNodeArray(outputList, root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return outputList;
    }

    public static void extractNodeArray(List<TelefonEntry> outputList, JsonNode root) {
        for(JsonNode node : root) {
            TelefonEntry entry = new TelefonEntry();

            // Get Name
            JsonNode nameNode = node.path("name");
            if (!nameNode.isMissingNode()) {        // if "name" node is exist
                entry.setFirstName(nameNode.path("first").asText());
                entry.setLastName(nameNode.path("last").asText());
            }

            // Get Number
            entry.setNumber(node.path("number").asText());
            outputList.add(entry);
        }
    }
}
