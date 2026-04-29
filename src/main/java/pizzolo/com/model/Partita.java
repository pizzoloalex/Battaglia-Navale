package pizzolo.com.model;


import java.util.ArrayList;
import java.util.Timer;

/**
 * classe che gestisce tutta la partita
 */
public class Partita {
    private ArrayList<Nave> naviPersonali;
    private ArrayList<Nave> naviNemiche;
    private Giocatore giocatore;
    private int[][] grigliaPersonale, grigliaNemica;

    /**
     *
     * @param row righe della griglia (gridpane)
     * @param col colonne della griglia (gridpane)
     */
    public Partita(int row, int col) {
        naviNemiche = new ArrayList<>();
        naviPersonali = new ArrayList<>();
        grigliaPersonale = new int[row][col];
        grigliaNemica = new int[row][col];
        giocatore = new Giocatore();
    }
    /*

    METODO DA USARE NEL MENU PRIMA DI GIOCARE

    public void gestioneGiocatore() throws PartitaException{
        giocatori.add(new Giocatore("Alex", true, naviPersonali));
        giocatori.add(new Giocatore("AI", false, naviNemiche));
        if (giocatori.size() > 2) {
            throw new PartitaException("Massimo due giocatori");
        }

    }
    */

    public void iniziaTurno() {
        //inizia sempre la persona

    }

    public void colpito(int riga, int colonna) {
        if (!giocatore.isTurnoSuo()) {
           return;
        }
        StatoCella statoCella  = grigliaNemica.
    }

}
