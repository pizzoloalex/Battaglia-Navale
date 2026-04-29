module pizzolo.com {
    requires javafx.controls;
    requires javafx.fxml;

    opens pizzolo.com to javafx.fxml;
    exports pizzolo.com;
}
