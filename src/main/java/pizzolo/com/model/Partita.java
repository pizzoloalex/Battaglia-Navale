package pizzolo.com.model;


import java.util.ArrayList;
import java.util.Arrays;
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
        griglia = new Griglia();
    }

    public Griglia getGriglia() {
        return griglia;
    }

    //METODO DI DEBUG SENZA NAVI
    public void mostraGrigliaIniziale() {
        System.out.println("GRIGLIA SENZA NAVI");
        System.out.println(griglia.toString());
    }

    //METODO DI DEBUG CON NAVI
    public void mostraGrigliaConNavi() {
        griglia.inizia();
        System.out.println("GRIGLIA CON NAVI");
        System.out.println(griglia.toString());
    }

}
