package hsa.maxist.se.telefonbuch.ui.menu;

import hsa.maxist.se.telefonbuch.ui.BookStage;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Locale;

public class OpenWindow extends Stage {

    public OpenWindow(String function) {

        // --Panes
        AnchorPane anchorPane = new AnchorPane();

        // --Stage Preferences
        setTitle(function + " File");
        setScene(new Scene(anchorPane));
        initModality(Modality.APPLICATION_MODAL);
        resizableProperty().setValue(false);

        // --ListBox
        ArrayList<String> filenames = new ArrayList<>();
        File folder = new File(
                String.valueOf(FileSystems.getDefault().getPath(BookStage.first, BookStage.pathToBooks))
        );
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                filenames.add(file.getName());
            }
        }
        ListView<String> files = new ListView<>();
        files.setItems(FXCollections.observableList(filenames));
        AnchorPane.setTopAnchor(files, 10.0);
        AnchorPane.setLeftAnchor(files, 10.0);
        AnchorPane.setRightAnchor(files, 10.0);
        AnchorPane.setBottomAnchor(files, 40.0);

        // --Button
        Button open = new Button(function);
        open.setOnAction(t -> {
            String completeName = files.getSelectionModel().getSelectedItem();
            if(completeName.equals(""))
                return;
            String[] name = completeName.split(".json");
            try {
                Method m = getClass().getDeclaredMethod(function.toLowerCase(Locale.ROOT) + "Func", String.class);
                m.invoke(this, name);
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

    private void openFunc(String filename) {
        new BookStage(filename).show();
        this.close();
    }

    private void importFunc(String file) {
        this.close();
    }
}
