package edu.cofc.gui;

import edu.cofc.gui.view.ExampleWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ExampleStorage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ExampleWindow exampleWindow = new ExampleWindow(primaryStage);
        exampleWindow.show();
    }
}
