package hsa.maxist.se.telefonbuch.util;

import hsa.maxist.se.telefonbuch.ui.BookStage;

import java.io.File;
import java.nio.file.FileSystems;

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
}
