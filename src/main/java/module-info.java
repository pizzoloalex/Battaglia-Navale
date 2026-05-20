module pizzolo.com {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;
    requires java.desktop;

    opens pizzolo.com to javafx.fxml;
    exports pizzolo.com;
    exports pizzolo.com.controller;
    opens pizzolo.com.controller to javafx.fxml;
}
