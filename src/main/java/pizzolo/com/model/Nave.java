package pizzolo.com.model;

public class Nave {
    //numero di bottoni a cui appartiene la nave
    private int lunghezza;
    private boolean affondato;
    private boolean colpito;

    public Nave(int lunghezza, boolean affondato){
        this.lunghezza = lunghezza;
        this.affondato = affondato;
        colpito = false;
    }

    public int getLunghezza() {
        return lunghezza;
    }

    public void setLunghezza(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public boolean isAffondato() {
        return affondato;
    }

    public void setAffondato(boolean affondato) {
        this.affondato = affondato;
    }

    public boolean isColpito() {
        return colpito;
    }

    public void setColpito(boolean colpito) {
        this.colpito = colpito;
    }
}
