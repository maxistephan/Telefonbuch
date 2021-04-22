package hsa.maxist.se.telefonbuch;

import hsa.maxist.se.telefonbuch.ui.BookStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BookStage frame = new BookStage("phonebook");
        frame.show();
    }

    /*******************************************************************************************************************
     *
     * Main
     *
     *******************************************************************************************************************/

    public static void main(String[] args) {
        launch(args);
    }
}