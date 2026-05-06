package pizzolo.com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
        for (int i = 1; i < gridPanePersonale.getRowCount(); i++) {
            for (int j = 1; j < gridPanePersonale.getColumnCount(); j++) {
                StackPane stk = new StackPane();
                stk.setStyle("-fx-background-color: transparent"); //inizialmente nessuna barca
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                //gestisce lo spazio di colonna/riga
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                //dice al nodo quanto spazio occupare
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                gridPanePersonale.add(stk, i, j);
                //OTTENIMENTO DELLA CELLA CLICCATA
                int row = j;
                int col = i;
                stk.setOnMouseClicked(mouseEvent -> {
                    System.out.println("click su col: " + col + "   riga: " + row);

                });
            }
        }
    }
}
