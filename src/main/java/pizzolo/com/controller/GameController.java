package pizzolo.com.controller;

import javafx.fxml.FXML;
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

    @FXML
    public void initialize() {
        partita = new Partita();
        inizializzaGrigliaGiocatore();
        mostraNaviGiocatore();
        inizializzaGrigliaAi();
        mostraGrigliaAi();
        partita.iniziaPartita();
    }

    private void mostraNaviGiocatore() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StackPane stk = new StackPane();
                if (partita.getGrigliaGiocatore().getStatoCella()[i][j] == StatoCella.NAVE) {
                    stk.setStyle("-fx-background-color: grey"); //colore della cella se ce la barca
                } else {
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
                gridPanePersonale.add(stk, j, i);
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

    private void mostraGrigliaAi() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StackPane stk = new StackPane();
                if (partita.getGrigliaAi().getStatoCella()[i][j] == StatoCella.NAVE) {
                    stk.setStyle("-fx-background-color: grey"); //colore della cella se ce la barca
                } else {
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
                gridPaneNemica.add(stk, j, i);
                //OTTENIMENTO DELLA CELLA CLICCATA
                int row = j;
                int col = i;
                stk.setOnMouseClicked(mouseEvent -> {
                    partita.gestioneTurnoGiocatore(col, row);
                    System.out.println(row);
                    System.out.println(col);

                });
            }
        }
    }

    //TODO  controllare bug sulla gestione del click della nave  perche da problemi su quando colpisco e  quando no
    //gestire grafica
    //todo gestione  logica inverti  tturno  se  colpita senno sttreak di turno giocator e o viceversa

    private void inizializzaGrigliaAi() {
        partita.mostraGrigliaAi();
    }

}
