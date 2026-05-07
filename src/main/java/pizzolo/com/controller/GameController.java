package pizzolo.com.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pizzolo.com.model.Partita;
import pizzolo.com.model.StatoCella;

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

    private Partita partita;
    private final int DIMENSIONE  = 10;
    @FXML
    public void initialize() {
        partita = new Partita();
        inizializzaGrigliaGiocatore();
        mostraNaviGiocatore();
        inizializzaGrigliaAi();
    }

    private void mostraNaviGiocatore() {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                StackPane stk = new StackPane();
                if (partita.getGriglia().getStatoCella()[i][j] == StatoCella.NAVE){
                    stk.setStyle("-fx-background-color: grey"); //colore della cella se ce la barca
                }else {
                    stk.setStyle("-fx-background-color: transparent"); // nessuna barca
                }
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                //gestisce lo spazio di colonna/riga
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                //dice al nodo quanto spazio occupare
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                gridPanePersonale.add(stk, j,i);
                //OTTENIMENTO DELLA CELLA CLICCATA
                int row = j;
                int col = i;
                stk.setOnMouseClicked(mouseEvent -> {
                    System.out.println("click su col: " + col + "   riga: " + row);

                });
            }
        }
    }

    private void inizializzaGrigliaGiocatore() {
        partita.mostraGrigliaIniziale();
        partita.mostraGrigliaConNavi();
    }

    private void inizializzaGrigliaAi(){
        partita.mostraGrigliaAi();
    }
}
