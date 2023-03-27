module edu.cofc.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires edu.cofc.core;


    opens edu.cofc.gui to javafx.fxml;
    exports edu.cofc.gui;
    opens edu.cofc.gui.view to javafx.fxml;
    exports edu.cofc.gui.view;
}