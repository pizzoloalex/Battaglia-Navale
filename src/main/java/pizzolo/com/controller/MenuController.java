package pizzolo.com.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import pizzolo.com.App;

/**
 * controller che gestisce il menu del applicazione
 */
public class MenuController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
