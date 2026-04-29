package pizzolo.com;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * classe del controller che gestisce la grafica del gioco
 */
public class SecondaryController {
    @FXML
    private VBox vBoxFlottaPersonale;
    @FXML
    private GridPane gridPanePersonale;
    @FXML
    private VBox vBoxFlottaNemica;
    @FXML
    private GridPane gridPaneNemica;

    Button btn = new Button("Click");

    public void initialize(){
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setMaxHeight(Double.MAX_VALUE);
        //gestisce lo spazio di colonna/riga
        GridPane.setHgrow(btn, Priority.ALWAYS);
        GridPane.setVgrow(btn, Priority.ALWAYS);
        //dice al nodo quanto spazio occupare
        GridPane.setFillWidth(btn, true);
        GridPane.setFillHeight(btn, true);
        gridPaneNemica.add(btn, 1,1);

    }
}