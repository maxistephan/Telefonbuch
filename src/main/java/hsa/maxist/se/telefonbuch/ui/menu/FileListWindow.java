package hsa.maxist.se.telefonbuch.ui.menu;

import hsa.maxist.se.telefonbuch.ui.BookStage;
import hsa.maxist.se.telefonbuch.util.FileUtility;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class FileListWindow extends Stage {

    public FileListWindow(String function, BookStage currentBook) {

        // --Panes
        AnchorPane anchorPane = new AnchorPane();

        // --Stage Preferences
        setTitle(function + " File");
        setScene(new Scene(anchorPane));
        initModality(Modality.APPLICATION_MODAL);
        resizableProperty().setValue(false);

        // --ListBox
        ListView<String> files = new ListView<>();
        files.setItems(
                FXCollections.observableList(Arrays.asList(FileUtility.getFileNames(false)))
        );
        AnchorPane.setTopAnchor(files, 10.0);
        AnchorPane.setLeftAnchor(files, 10.0);
        AnchorPane.setRightAnchor(files, 10.0);
        AnchorPane.setBottomAnchor(files, 40.0);
        files.setMaxHeight(200);

        // --Button
        Button open = new Button(function);
        open.setOnAction(t -> {
            String filename = FileUtility.getFileName(files.getSelectionModel().getSelectedItem());
            if (filename.equals(""))
                return;
            try {
                Method m = getClass().getDeclaredMethod(function, String.class, BookStage.class);
                m.invoke(this, filename, currentBook);
            } catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        AnchorPane.setBottomAnchor(open, 10.0);
        AnchorPane.setLeftAnchor(open, 80.0);
        AnchorPane.setRightAnchor(open, 80.0);

        // --Apply
        anchorPane.getChildren().addAll(files, open);

        // --Show
        show();
    }

    private void Open(String filename, BookStage currentBook) {
        ArrayList<BookStage> currentBooks = BookStage.getInstances();

        for (BookStage bs : currentBooks) {
            if (bs.getTitle().equals(filename)) {
                this.close();
                return;
            }
        }
        new BookStage(filename).show();
        this.close();
    }

    private void Import(String filename, BookStage currentBook) {
        String[] filepathString = Arrays.copyOf(FileUtility.pathToBooks, FileUtility.pathToBooks.length + 1);
        filepathString[FileUtility.pathToBooks.length] = filename + ".json";
        Path filepath = FileSystems.getDefault().getPath(FileUtility.first, filepathString);
        currentBook.getTelefonBook().load(filepath);
        this.close();
    }
}
