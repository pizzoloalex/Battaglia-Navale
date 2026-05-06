package pizzolo.com.model;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella[][] statoCella;
    private Giocatore giocatore;

    public Griglia() {
        giocatore = new Giocatore();
    }

    /**
     * inizializza le celle della griglia a vuote a vuote
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

    public void setPosizioneNaviGiocatore() {
        giocatore.getNavi().add(new Nave(TipoNave.PORTAEREI, 1,1,true));
    }

}
