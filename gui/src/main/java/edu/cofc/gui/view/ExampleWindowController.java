package edu.cofc.gui.view;

import edu.cofc.core.serialize.CommaSeparable;
import edu.cofc.core.serialize.Example;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ExampleWindowController implements Initializable {
    @FXML
    private Button  saveAsButton;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField dataField;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSaveAsButton();
        initIDField();
        initNameField();
        initDataField();
    }

    public ExampleWindowController(Stage stage) {
        this.stage = stage;
    }

    private void initNameField() {

    }

    private void initDataField() {
       Pattern validPattern = Pattern.compile("-?(0|[1-9][0-9]*)?(\\.[0-9]*)?");
       UnaryOperator<TextFormatter.Change> filter = change -> {
           String text = change.getControlNewText();
           if(validPattern.matcher(text).matches()) {
                return change;
            } else {
                return null;
            }
       };

        StringConverter<Double> converter = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                if (string.isEmpty() ||
                        string.equals("-") ||
                        string.equals(".") ||
                        string.equals("-.") ){
                    return 0.0;
                } else {
                    return Double.valueOf(string);
                }
            }
        };

        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);
        dataField.setTextFormatter(textFormatter);
    }

    private void initIDField() {
    }

    private void initSaveAsButton() {
        saveAsButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt((idField.getText()));
                String name = nameField.getText();
                double data = Double.parseDouble(dataField.getText());
                String fileName = String.format(("../%s.csv"),name);
                CommaSeparable.writeToCSVFile(fileName, new Example(id, name, data));

            } catch (Throwable ex) {

            }
        });
    }

    private void showSaveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Example As");
        fileChooser.showSaveDialog(stage);

    }
}
