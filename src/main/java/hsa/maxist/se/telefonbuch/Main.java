package hsa.maxist.se.telefonbuch;

import hsa.maxist.se.telefonbuch.ui.Frame;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Frame frame = new Frame();
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