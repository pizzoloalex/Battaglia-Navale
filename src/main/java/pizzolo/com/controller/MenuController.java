package pizzolo.com.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import pizzolo.com.App;

public class MenuController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
