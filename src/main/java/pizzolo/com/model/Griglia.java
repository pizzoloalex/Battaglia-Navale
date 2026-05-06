package pizzolo.com.model;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella[][] statoCella;
    private Giocatore giocatore;

    public Griglia() {
        giocatore = new Giocatore();
        statoCella = new StatoCella[DIMENSIONE][DIMENSIONE];
        setCelleIniziali();
    }

    public  void inizia(){
       setNaviGiocatore();
    }

    /**
     * inizializza le celle della griglia a vuote, nessuna nave
     */
    public void setCelleIniziali() {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                statoCella[i][j] = StatoCella.VUOTA;
            }
        }
    }


    public StatoCella[][] getStatoCella() {
        return statoCella;
    }

    /**
     * toString per il debug della tabella
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                if (statoCella[i][j] == StatoCella.VUOTA) {
                    s += "0 ";
                } else{
                    s += "1 ";
                }
            }
            s += "\n";
        }
        return s;
    }

    /**
     * metodo che aggiunge la nave alla griglia
     */
    public void setNaviGiocatore() {
        Nave nave = new Nave(TipoNave.PORTAEREI, 3, 5, true);
        giocatore.getNavi().add(nave);
        posizionaNave(nave);
    }

    /**
     * metodo che posiziona la nave e la posiziona in base al suo orientamento
     * @param nave nave da aggiungere
     */
    private void posizionaNave(Nave nave) {
        for (int i = 0; i < nave.getLunghezza(); i++) {
            if (nave.isVerticale()) {
                statoCella[nave.getRigaNave() + i][nave.getColonnaNave()] = StatoCella.NAVE;
            } else {
                statoCella[nave.getRigaNave()][nave.getColonnaNave() + i] = StatoCella.NAVE;
            }
        }
    }

}
