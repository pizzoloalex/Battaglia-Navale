package pizzolo.com.model;

import java.util.ArrayList;

/**
 * classe che gestisce le caratteristiche di un giocatore
 */
public class Giocatore {
    private String nome;
    private boolean turno;  //true = turno giocatore  --- false = turno AI
    private ArrayList<Nave> navi;

    public Giocatore() {}

    /**
     *
     * @param nome del giocatore
     */
    public Giocatore(String nome) {
        this.nome = nome;
        turno = true; //inizia il giocatore
        navi = new ArrayList<>();
    }

    public ArrayList<Nave> getNavi() {
        return navi;
    }

    public void setNavi(ArrayList<Nave> navi) {
        this.navi = navi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }
}
