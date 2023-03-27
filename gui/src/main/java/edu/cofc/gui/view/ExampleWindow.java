package edu.cofc.gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ExampleWindow {
    private Stage stage;

    public ExampleWindow(Stage stage) throws IOException {
        this.stage = stage;
        URL fxmlResource = ExampleWindow.class.getResource("example-window.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        loader.setController(new ExampleWindowController(this.stage));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);

    }

    public void show() {
        this.stage.show();
    }
}
