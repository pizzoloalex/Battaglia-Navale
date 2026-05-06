package pizzolo.com.model;


import java.util.ArrayList;
import java.util.Timer;

/**
 * classe che gestisce tutta la partita
 */
public class Partita {

    private Giocatore giocatore;
    private Giocatore ia;
    private Griglia griglia;
    private boolean turno; //true = turno del giocatore ---- false = turno ai

    public Partita() {
        this.turno = true;//inizia il giocatore
        giocatore = new Giocatore();
        ia = new Giocatore();
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


}
