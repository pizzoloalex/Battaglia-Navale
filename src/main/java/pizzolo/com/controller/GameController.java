package pizzolo.com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * classe del controller che gestisce la grafica del gioco
 */
public class GameController {
    @FXML
    private VBox vBoxFlottaPersonale;
    @FXML
    private GridPane gridPanePersonale;
    @FXML
    private VBox vBoxFlottaNemica;
    @FXML
    private GridPane gridPaneNemica;

    @FXML
    public void initialize() {
        for (int i = 1; i < gridPaneNemica.getRowCount(); i++) {
            for (int j = 1; j < gridPaneNemica.getColumnCount(); j++) {
                Button btn = new Button();
                btn.setStyle("-fx-background-color: white"); //inizialmente nessuna barca
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setMaxHeight(Double.MAX_VALUE);
                //gestisce lo spazio di colonna/riga
                GridPane.setHgrow(btn, Priority.ALWAYS);
                GridPane.setVgrow(btn, Priority.ALWAYS);
                //dice al nodo quanto spazio occupare
                GridPane.setFillWidth(btn, true);
                GridPane.setFillHeight(btn, true);
                gridPaneNemica.add(btn, i, j);
                btn.setOnAction(actionEvent -> btn.setText(colpito()));
            }
        }
    }

    private String colpito(){
        return "X";
    }

}