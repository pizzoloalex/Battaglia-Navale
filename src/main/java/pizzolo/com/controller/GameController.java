package pizzolo.com.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pizzolo.com.model.Nave;
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
    private StackPane[][] celleAi;
    private StackPane[][] cellaGiocatore;

    @FXML
    public void initialize() {
        partita = new Partita();
        celleAi = new StackPane[partita.getDimensione()][partita.getDimensione()];
        cellaGiocatore = new StackPane[partita.getDimensione()][partita.getDimensione()];
        partita.iniziaPartita();
        inizializzaGrigliaGiocatore();
        mostraNaviGiocatore();
        inizializzaGrigliaAi();
        mostraGrigliaAi();
    }

    private void mostraNaviGiocatore() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StackPane stk = new StackPane();
                if (partita.getGrigliaGiocatore().getStatoCella()[i][j] == StatoCella.NAVE) {
                    stk.setStyle("-fx-background-color: grey");
                } else {
                    stk.setStyle("-fx-background-color: transparent");
                }
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                cellaGiocatore[i][j] = stk;
                gridPanePersonale.add(stk, j, i);
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
                    stk.setStyle("-fx-background-color: grey");
                } else {
                    stk.setStyle("-fx-background-color: transparent");
                }
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                celleAi[i][j] = stk;
                gridPaneNemica.add(stk, j, i);

                int riga = i;
                int colonna = j;
                stk.setOnMouseClicked(mouseEvent -> {
                    if (!partita.getGrigliaGiocatore().getGiocatore().isTurno()) {
                        return;
                    }

                    if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.COLPITA ||
                            partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA) {
                        return;
                    }

                    partita.gestioneTurnoGiocatore(riga, colonna);
//                    if(partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA){
//                        stk.setStyle("-fx-background-color: blue");
//                    }
                    aggiornaGrigliaAi(riga, colonna, stk);
                    if (partita.getGrigliaAi().getIa().isTurno()) {
                        partita.gestioneTurnoAi();
                        aggiornaGrigliaGiocatore();
                    }

                });
            }
        }
    }


    private void aggiornaGrigliaAi(int riga, int colonna, StackPane stk) {
        if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.COLPITA) {
            stk.setStyle("-fx-background-color: red");
            Nave affondata = partita.getUtlimaNaveAffondata();
            if (affondata != null && affondata.affondato()) {
                coloraNaveAffondata(affondata);
                partita.setUtlimaNaveAffondata(null);
            }
        } else if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA) {
            stk.setStyle("-fx-background-color: blue");
        }
    }

    private void aggiornaGrigliaGiocatore() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StatoCella stato = partita.getGrigliaGiocatore().getStatoCella()[i][j];
                if (stato == StatoCella.COLPITA) {
                    cellaGiocatore[i][j].setStyle("-fx-background-color: red");
                } else if (stato == StatoCella.MANCATA) {
                    cellaGiocatore[i][j].setStyle("-fx-background-color: blue");
                }
            }
        }
        Nave affondata = partita.getUtlimaNaveAffondata();
        if (affondata != null && affondata.affondato()) {
            coloraNaveAffondataGiocatore(affondata);
            partita.setUtlimaNaveAffondata(null);
        }
    }

    private void coloraNaveAffondataGiocatore(Nave n) {
        for (int i = 0; i < n.getLunghezza(); i++) {
            int r, c;
            if (n.isVerticale()) {
                r = n.getRigaNave() + i;
                c = n.getColonnaNave();
            } else {
                r = n.getRigaNave();
                c = n.getColonnaNave() + i;
            }
            if (cellaGiocatore[r][c] != null) {
                cellaGiocatore[r][c].setStyle("-fx-background-color: black");
            }
        }
    }

    private void coloraNaveAffondata(Nave n) {
        for (int i = 0; i < n.getLunghezza(); i++) {
            int r, c;
            if (n.isVerticale()) {
                r = n.getRigaNave() + i;
                c = n.getColonnaNave();
            } else {
                r = n.getRigaNave();
                c = n.getColonnaNave() + i;
            }
            if (celleAi[r][c] != null) {
                celleAi[r][c].setStyle("-fx-background-color: black");
            }
        }
    }

    private void inizializzaGrigliaAi() {
        partita.mostraGrigliaAi();
    }
}