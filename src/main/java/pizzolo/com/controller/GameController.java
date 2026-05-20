package pizzolo.com.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pizzolo.com.model.Nave;
import pizzolo.com.model.Partita;
import pizzolo.com.model.StatoCella;
import pizzolo.com.model.TipoNave;

import java.util.HashMap;
import java.util.Map;

public class GameController {

    @FXML private HBox hBoxFlottaDisponibile;
    @FXML private VBox vBoxFlottaPersonale;
    @FXML private GridPane gridPanePersonale;
    @FXML private VBox vBoxFlottaNemica;
    @FXML private GridPane gridPaneNemica;
    @FXML private Button btnIniziaPartita;
    @FXML private Label labelStatus;
    @FXML private VBox vBoxFlottaDisponibile;

    private Partita partita;
    private StackPane[][] celleAi;
    private StackPane[][] cellaGiocatore;

    private Nave naveSelezionata;
    private int rigaPrecedente, colonnaPrecedente;
    private boolean orientamentoPrecedente;
    private boolean setupCompletato = false;

    private double dragOffsetX, dragOffsetY;
    private Map<Nave, StackPane> naveBlockMap = new HashMap<>();

    @FXML
    public void initialize() {
        partita = new Partita();
        celleAi = new StackPane[partita.getDimensione()][partita.getDimensione()];
        cellaGiocatore = new StackPane[partita.getDimensione()][partita.getDimensione()];

        // NON posizionare navi giocatore all'inizio
        partita.mostraGrigliaAi(); // posiziona navi AI

        inizializzaGrigliaSetup();
        inizializzaGrigliaAiVisiva();
        creaFlottaDisponibile();

        mostraNaviAi();

        // Setup tasto R per rotazione
        Platform.runLater(() ->
                gridPanePersonale.getScene().setOnKeyPressed(e -> {
                    if (setupCompletato || naveSelezionata == null) return;
                    if (e.getCode() == javafx.scene.input.KeyCode.R) {
                        naveSelezionata.sposta(
                                naveSelezionata.getRigaNave(),
                                naveSelezionata.getColonnaNave(),
                                !naveSelezionata.isVerticale()
                        );
                        mostraNaviGiocatore();
                    }
                })
        );
    }

    // ======================== FLOTTA DISPONIBILE ========================

    private void creaFlottaDisponibile() {
        hBoxFlottaDisponibile.getChildren().clear();

        for (Nave nave : partita.getGrigliaGiocatore().getGiocatore().getNavi()) {
            StackPane bloccoNave = creaBlockoNave(nave);
            hBoxFlottaDisponibile.getChildren().add(bloccoNave);
            naveBlockMap.put(nave, bloccoNave);
        }
    }

    private StackPane creaBlockoNave(Nave nave) {
        StackPane contenitore = new StackPane();
        contenitore.setStyle("-fx-border-color: #1976D2; -fx-border-width: 2; -fx-padding: 8; -fx-background-color: #E3F2FD;");

        // Crea i quadratini della nave
        HBox hboxNave = new HBox(2);
        hboxNave.setStyle("-fx-alignment: center;");

        for (int i = 0; i < nave.getLunghezza(); i++) {
            StackPane quadrato = new StackPane();
            quadrato.setStyle("-fx-background-color: #2196F3; -fx-border-color: #1565C0; -fx-border-width: 1;");
            quadrato.setPrefWidth(30);
            quadrato.setPrefHeight(30);
            hboxNave.getChildren().add(quadrato);
        }

        // Etichetta con tipo di nave
        Label label = new Label(getNomeNave(nave.getLunghezza()));
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976D2;");

        contenitore.getChildren().addAll(hboxNave, label);

        // Drag & Drop dal pannello alla griglia
        contenitore.setOnMousePressed(e -> onNaveFlottaPressata(nave, e));
        contenitore.setOnMouseDragged(e -> onNaveFlottaTrascinata(nave, e));
        contenitore.setOnMouseReleased(e -> onNaveFlottaRilasciata(nave, e));

        return contenitore;
    }

    private String getNomeNave(int lunghezza) {
        switch (lunghezza) {
            case 5:
                return "Portaerei";
            case 4:
                return "Cacciatorpediere";
            case 3:
                return "Incrociatore";
            case 2:
                return "Sottomarino";
            default:
                return "Nave";
        }
    }

    private void onNaveFlottaPressata(Nave nave, MouseEvent e) {
        if (setupCompletato) return;

        naveSelezionata = nave;
        rigaPrecedente = nave.getRigaNave();
        colonnaPrecedente = nave.getColonnaNave();
        orientamentoPrecedente = nave.isVerticale();

        dragOffsetX = e.getSceneX();
        dragOffsetY = e.getSceneY();

        e.consume();
    }

    private void onNaveFlottaTrascinata(Nave nave, MouseEvent e) {
        if (setupCompletato || naveSelezionata == null) return;

        double cellWidth = gridPanePersonale.getWidth() / partita.getDimensione();
        double cellHeight = gridPanePersonale.getHeight() / partita.getDimensione();

        // Calcola la posizione relativa al GridPane
        Bounds gridBounds = gridPanePersonale.localToScene(gridPanePersonale.getBoundsInLocal());
        double localX = e.getSceneX() - gridBounds.getMinX();
        double localY = e.getSceneY() - gridBounds.getMinY();

        int nuovaColonna = (int)(localX / cellWidth);
        int nuovaRiga = (int)(localY / cellHeight);

        // Aggiorna la posizione della nave
        if (nuovaRiga >= 1 && nuovaRiga < partita.getDimensione() &&
                nuovaColonna >= 1 && nuovaColonna < partita.getDimensione()) {

            naveSelezionata.sposta(nuovaRiga, nuovaColonna, naveSelezionata.isVerticale());
            mostraNaviGiocatore();

            // Mostra feedback visivo: verde se valido, rosso se invalido
            boolean valido = partita.getGrigliaGiocatore().posizionamentoValido(naveSelezionata);
            String colore = valido ? "-fx-background-color: #4CAF50; -fx-cursor: hand;" :
                    "-fx-background-color: #E24B4A; -fx-cursor: hand;";

            for (int[] cella : naveSelezionata.getCelle()) {
                int r = cella[0];
                int c = cella[1];
                if (r >= 1 && r < partita.getDimensione() && c >= 1 && c < partita.getDimensione()) {
                    cellaGiocatore[r][c].setStyle(colore);
                }
            }
        }

        e.consume();
    }

    private void onNaveFlottaRilasciata(Nave nave, MouseEvent e) {
        if (setupCompletato || naveSelezionata == null) return;

        // Verifica se la posizione è valida
        if (partita.getGrigliaGiocatore().posizionamentoValido(naveSelezionata)) {
            partita.getGrigliaGiocatore().aggiornaStatoCelleNave();
            controllaSeAlleNaviSonoPoste();
        } else {
            // Rollback alla posizione precedente
            naveSelezionata.sposta(rigaPrecedente, colonnaPrecedente, orientamentoPrecedente);
        }

        naveSelezionata = null;
        mostraNaviGiocatore();

        e.consume();
    }

    // ======================== SETUP GRIGLIA GIOCATORE ========================

    private void inizializzaGrigliaSetup() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StackPane stk = new StackPane();
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                stk.setStyle("-fx-border-color: transparent;");
                cellaGiocatore[i][j] = stk;
                gridPanePersonale.add(stk, j, i);
            }
        }
    }

    // ======================== VISIVA NAVI GIOCATORE ========================

    private void mostraNaviGiocatore() {
        // Pulisci la griglia
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                if (cellaGiocatore[i][j] != null) {
                    cellaGiocatore[i][j].getChildren().clear();
                    cellaGiocatore[i][j].setStyle("-fx-background-color: transparent;");
                }
            }
        }

        // Disegna le navi del giocatore come blocchi trascinabili
        for (Nave nave : partita.getGrigliaGiocatore().getGiocatore().getNavi()) {
            disegnaNaveGiocatore(nave);
        }
    }

    private void disegnaNaveGiocatore(Nave nave) {
        int[][] celle = nave.getCelle();

        for (int[] cella : celle) {
            int r = cella[0];
            int c = cella[1];

            if (r >= 1 && r < partita.getDimensione() && c >= 1 && c < partita.getDimensione()) {
                StackPane cellPane = cellaGiocatore[r][c];
                cellPane.setStyle("-fx-background-color: #9E9E9E; -fx-cursor: hand;");
            }
        }
    }

    private void controllaSeAlleNaviSonoPoste() {
        boolean tutte = true;
        for (Nave n : partita.getGrigliaGiocatore().getGiocatore().getNavi()) {
            if (!partita.getGrigliaGiocatore().posizionamentoValido(n)) {
                tutte = false;
                break;
            }
        }

        if (tutte) {
            vBoxFlottaDisponibile.setVisible(false);
            vBoxFlottaDisponibile.setManaged(false);
            if (labelStatus != null) {
                labelStatus.setText("✓ Tutte le navi posizionate! Premi 'Inizia partita'");
                labelStatus.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            }
        }
    }

    @FXML
    public void onIniziaPartita() {
        setupCompletato = true;
        if (btnIniziaPartita != null) btnIniziaPartita.setDisable(true);
        if (labelStatus != null) labelStatus.setText("Partita iniziata! Clicca sulla griglia nemica.");
        attivaGrigliaAi();
    }

    // ======================== GRIGLIA AI ========================

    private void inizializzaGrigliaAiVisiva() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StackPane stk = new StackPane();
                stk.setStyle("-fx-background-color: transparent");
                stk.setMaxWidth(Double.MAX_VALUE);
                stk.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHgrow(stk, Priority.ALWAYS);
                GridPane.setVgrow(stk, Priority.ALWAYS);
                GridPane.setFillWidth(stk, true);
                GridPane.setFillHeight(stk, true);
                celleAi[i][j] = stk;
                gridPaneNemica.add(stk, j, i);
            }
        }
    }

    private void mostraNaviAi() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                if (celleAi[i][j] == null) continue;
                celleAi[i][j].setStyle("-fx-background-color: transparent");
            }
        }
    }

    private void attivaGrigliaAi() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                final int riga = i, colonna = j;
                final StackPane stk = celleAi[i][j];
                stk.setOnMouseClicked(mouseEvent -> {
                    if (!partita.getGrigliaGiocatore().getGiocatore().isTurno()) return;
                    if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.COLPITA ||
                            partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA) return;

                    partita.gestioneTurnoGiocatore(riga, colonna);
                    aggiornaGrigliaAiDopoGiocatore(riga, colonna, stk);

                    if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA) {
                        Platform.runLater(this::turnoAiCompleto);
                    }
                });
            }
        }
    }

    // ======================== LOGICA DI GIOCO ========================

    private void turnoAiCompleto() {
        boolean colpita = partita.gestioneTurnoAi();
        aggiornaGrigliaGiocatoreDopoAi();
        if (colpita && partita.getGrigliaAi().getIa().isTurno()) {
            PauseTransition pausa = new PauseTransition(Duration.seconds(0.8));
            pausa.setOnFinished(e -> turnoAiCompleto());
            pausa.play();
        }
    }

    private void aggiornaGrigliaAiDopoGiocatore(int riga, int colonna, StackPane stk) {
        if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.COLPITA) {
            stk.setStyle("-fx-background-color: red");
            Nave affondata = partita.getUltimaNaveAffondataAi();
            if (affondata != null && affondata.affondato()) {
                coloraNaveAffondataAi(affondata);
                partita.setUltimaNaveAffondataAi(null);
            }
        } else if (partita.getGrigliaAi().getStatoCella(riga, colonna) == StatoCella.MANCATA) {
            stk.setStyle("-fx-background-color: blue");
        }
    }

    private void aggiornaGrigliaGiocatoreDopoAi() {
        for (int i = 1; i < partita.getDimensione(); i++) {
            for (int j = 1; j < partita.getDimensione(); j++) {
                StatoCella stato = partita.getGrigliaGiocatore().getStatoCella(i, j);
                if (cellaGiocatore[i][j] == null) continue;
                if (stato == StatoCella.COLPITA) {
                    cellaGiocatore[i][j].setStyle("-fx-background-color: red");
                    Nave affondata = partita.getUtlimaNaveAffondataGiocatore();
                    if (affondata != null && affondata.affondato()) {
                        coloraNaveAffondataGiocatore(affondata);
                        partita.setUtlimaNaveAffondataGiocatore(null);
                    }
                } else if (stato == StatoCella.MANCATA) {
                    cellaGiocatore[i][j].setStyle("-fx-background-color: blue");
                }
            }
        }
    }

    private void coloraNaveAffondataAi(Nave n) {
        for (int i = 0; i < n.getLunghezza(); i++) {
            int r = n.isVerticale() ? n.getRigaNave() + i : n.getRigaNave();
            int c = n.isVerticale() ? n.getColonnaNave() : n.getColonnaNave() + i;
            if (celleAi[r][c] != null) celleAi[r][c].setStyle("-fx-background-color: black");
        }
    }

    private void coloraNaveAffondataGiocatore(Nave n) {
        for (int i = 0; i < n.getLunghezza(); i++) {
            int r = n.isVerticale() ? n.getRigaNave() + i : n.getRigaNave();
            int c = n.isVerticale() ? n.getColonnaNave() : n.getColonnaNave() + i;
            if (cellaGiocatore[r][c] != null) cellaGiocatore[r][c].setStyle("-fx-background-color: black");
        }
    }
}
