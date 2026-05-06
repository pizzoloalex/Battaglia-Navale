package pizzolo.com.model;

import javafx.scene.control.Button;

public class Nave {
    //numero di bottoni a cui appartiene la nave
    private int lunghezza;
    private boolean affondato;
    private boolean colpito;
    private int contaColpi;
    private int rigaNave;
    private int colonnaNave;

    public Nave(boolean affondato, TipoNave tipoNave) {
        this.lunghezza = tipoNave.getLunghezza();
        this.affondato = affondato;
        colpito = false;
    }

    public boolean colpito(int riga, int colonna, Button btn) {
        if (riga == rigaNave && colonna == colonnaNave) {
            return colpito = true;
        }
        return colpito = false;
    }

    private int contaColpiAssegno(){
        return contaColpi++;
    }

    public boolean affondato(){
        if (colpito){
            if (contaColpiAssegno() >= lunghezza){
                return affondato = true;
            }
        }
        return affondato = false;
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
