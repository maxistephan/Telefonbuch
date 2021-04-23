package hsa.maxist.se.telefonbuch.ui.menu;

import hsa.maxist.se.telefonbuch.ui.BookStage;
import hsa.maxist.se.telefonbuch.util.FileManager;
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
                FXCollections.observableList(Arrays.asList(FileManager.getFileNames(false)))
        );
        AnchorPane.setTopAnchor(files, 10.0);
        AnchorPane.setLeftAnchor(files, 10.0);
        AnchorPane.setRightAnchor(files, 10.0);
        AnchorPane.setBottomAnchor(files, 40.0);
        files.setMaxHeight(200);

        // --Button
        Button open = new Button(function);
        open.setOnAction(t -> {
            String filename = FileManager.getFileName(files.getSelectionModel().getSelectedItem());
            if(filename.equals(""))
                return;
            try {
                Method m = getClass().getDeclaredMethod(function, String.class, BookStage.class);
                m.invoke(this, filename, currentBook);
            } catch(NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
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
        if(filename.equals(currentBook.getTitle())) {
            this.close();
            return;
        }
        currentBook.close();
        new BookStage(filename).show();
        this.close();
    }

    private void Import(String filename, BookStage currentBook) {
        String[] filepathString = Arrays.copyOf(BookStage.pathToBooks, BookStage.pathToBooks.length + 1);
        filepathString[BookStage.pathToBooks.length] = filename + ".json";
        Path filepath = FileSystems.getDefault().getPath(BookStage.first, filepathString);
        currentBook.getTelefonBook().load(filepath);
        this.close();
    }
}
