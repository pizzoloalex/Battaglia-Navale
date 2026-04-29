package pizzolo.com.model;

import java.util.ArrayList;

/**
 * classe che gestisce le caratteristiche di un giocatore
 */
public class Giocatore {
    private String nome;
    private boolean turnoSuo;  //true = turno giocatore  --- false = turno AI
    private ArrayList<Nave> naviGiocatore;

    public Giocatore() {
    }

    /**
     *
     * @param nome del giocatore
     * @param navi il giocatore conosce le proprie navi
     */
    public Giocatore(String nome, ArrayList<Nave> navi) {
        this.nome = nome;
        turnoSuo = true; //inizia il giocatore
        this.naviGiocatore = navi;
    }

    public ArrayList<Nave> getNaviGiocatore() {
        return naviGiocatore;
    }

    public void setNaviGiocatore(ArrayList<Nave> naviGiocatore) {
        this.naviGiocatore = naviGiocatore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isTurnoSuo() {
        return turnoSuo;
    }

    public void setTurnoSuo(boolean turnoSuo) {
        this.turnoSuo = turnoSuo;
    }
}
