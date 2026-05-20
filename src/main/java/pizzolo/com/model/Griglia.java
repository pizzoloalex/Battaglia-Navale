package pizzolo.com.model;

import java.util.Random;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella[][] statoCella;
    private Giocatore giocatore;
    private Giocatore ia;

    public Griglia() {
        giocatore = new Giocatore();
        ia = new Giocatore();
        statoCella = new StatoCella[DIMENSIONE][DIMENSIONE];
        setCelleIniziali();
    }

    public int getDIMENSIONE() {
        return DIMENSIONE;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public Giocatore getIa() {
        return ia;
    }

    public StatoCella[][] getStatoCella() {
        return statoCella;
    }

    public StatoCella getStatoCella(int riga, int colonna) {
        return statoCella[riga][colonna];
    }

    // ======================== SETUP CELLE ========================

    public void setCelleIniziali() {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                statoCella[i][j] = StatoCella.VUOTA;
            }
        }
    }

    // ======================== NAVI GIOCATORE ========================

    /**
     * Crea tutte le navi del giocatore fuori dalla griglia (in posizioni neutrali)
     */
    public void creaNaviGiocatoreDisponibili() {
        // PORTAEREI (5)
        Nave portaerei = new Nave(TipoNave.PORTAEREI, 0, 0, true);
        giocatore.getNavi().add(portaerei);

        // CACCIATORPENDIERE (4)
        Nave cacciatorpendiere = new Nave(TipoNave.CACCIATORPENDIERE, 0, 0, true);
        giocatore.getNavi().add(cacciatorpendiere);

        // INCROCIATORI (3)
        Nave incrociatori = new Nave(TipoNave.INCROCIATORI, 0, 0, true);
        giocatore.getNavi().add(incrociatori);

        // SOTTOMARINO (2)
        Nave sottomarino = new Nave(TipoNave.SOTTOMARINO, 0, 0, true);
        giocatore.getNavi().add(sottomarino);
    }

    /**
     * Controlla se una posizione è valida per una nave
     */
    public boolean posizionamentoValido(Nave nave) {
        for (int[] cella : nave.getCelle()) {
            int r = cella[0], c = cella[1];
            // Controlla se esce dai bordi della griglia (spazio 1-9)
            if (r < 1 || r >= DIMENSIONE || c < 1 || c >= DIMENSIONE) {
                return false;
            }
            // Controlla sovrapposizioni con altre navi
            for (Nave altra : giocatore.getNavi()) {
                if (altra == nave) continue;
                for (int[] altraCella : altra.getCelle()) {
                    if (altraCella[0] == r && altraCella[1] == c) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Aggiorna la matrice statoCella dopo uno spostamento di nave
     */
    public void aggiornaStatoCelleNave() {
        // Pulisci le celle delle navi
        for (int r = 1; r < DIMENSIONE; r++) {
            for (int c = 1; c < DIMENSIONE; c++) {
                if (statoCella[r][c] == StatoCella.NAVE) {
                    statoCella[r][c] = StatoCella.VUOTA;
                }
            }
        }
        // Ridisegna tutte le navi
        for (Nave n : giocatore.getNavi()) {
            for (int[] cella : n.getCelle()) {
                int r = cella[0], c = cella[1];
                if (r >= 1 && r < DIMENSIONE && c >= 1 && c < DIMENSIONE) {
                    statoCella[r][c] = StatoCella.NAVE;
                }
            }
        }
    }

    // ======================== NAVI AI ========================

    /**
     * Genera tutte le navi dell'AI in posizioni casuali
     */
    public void creaNaviAiCasuali() {
        TipoNave[] tipi = {TipoNave.PORTAEREI, TipoNave.CACCIATORPENDIERE, TipoNave.INCROCIATORI, TipoNave.SOTTOMARINO};

        for (TipoNave tipo : tipi) {
            Nave nave = new Nave(tipo);
            boolean orientamentoValido = false;

            // Tenta finché non trova una posizione valida
            while (!orientamentoValido) {
                Random rnd = new Random();
                boolean verticale = rnd.nextBoolean();
                int riga = generaPosizioneCasuale(verticale, nave.getLunghezza());
                int colonna = generaPosizioneCasuale(!verticale, nave.getLunghezza());

                nave.sposta(riga, colonna, verticale);
                orientamentoValido = posizionamentoAiValido(nave);
            }

            ia.getNavi().add(nave);
            aggiornaStatoCelleAi(nave);
        }
    }

    /**
     * Controlla se la posizione è valida per una nave dell'AI
     */
    private boolean posizionamentoAiValido(Nave nave) {
        for (int[] cella : nave.getCelle()) {
            int r = cella[0], c = cella[1];
            if (r < 1 || r >= DIMENSIONE || c < 1 || c >= DIMENSIONE) {
                return false;
            }
            for (Nave altra : ia.getNavi()) {
                if (altra == nave) continue;
                for (int[] altraCella : altra.getCelle()) {
                    if (altraCella[0] == r && altraCella[1] == c) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Genera una posizione casuale tenendo conto della lunghezza
     */
    private int generaPosizioneCasuale(boolean consideraLunghezza, int lunghezza) {
        Random rnd = new Random();
        if (consideraLunghezza) {
            return 1 + rnd.nextInt(DIMENSIONE - lunghezza);
        } else {
            return 1 + rnd.nextInt(DIMENSIONE - 1);
        }
    }

    /**
     * Aggiorna la matrice statoCella per le navi dell'AI
     */
    private void aggiornaStatoCelleAi(Nave nave) {
        for (int[] cella : nave.getCelle()) {
            int r = cella[0], c = cella[1];
            if (r >= 1 && r < DIMENSIONE && c >= 1 && c < DIMENSIONE) {
                statoCella[r][c] = StatoCella.NAVE;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                s += statoCella[i][j] == StatoCella.VUOTA ? "0 " : "1 ";
            }
            s += "\n";
        }
        return s;
    }
}
