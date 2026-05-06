package pizzolo.com.model;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella statoCella;

    /**
     * inizializza le celle della griglia a vuote a vuote
     */
    public void setCelleIniziali() {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                statoCella = StatoCella.VUOTA;
            }
        }
    }

}
