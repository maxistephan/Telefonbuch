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
                Method m = currentBook.getClass().getDeclaredMethod(function, String.class);
                m.invoke(currentBook, filename);
                close();
            } catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        AnchorPane.setBottomAnchor(open, 10.0);
        AnchorPane.setLeftAnchor(open, 80.0);
        AnchorPane.setRightAnchor(open, 80.0);

        // --Add
        anchorPane.getChildren().addAll(files, open);

        // --Show
        show();
    }
}
