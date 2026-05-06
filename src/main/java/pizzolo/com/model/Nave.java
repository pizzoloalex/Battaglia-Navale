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
    private boolean isVerticale;

    public Nave(TipoNave tipoNave, int rigaNave, int colonnaNave, boolean isVerticale) {
        this.lunghezza = tipoNave.getLunghezza();
        this.rigaNave = rigaNave;
        this.colonnaNave = colonnaNave;
        this.isVerticale  = isVerticale;
        if (this.isVerticale) {
            for (int i = 0; i <= tipoNave.getLunghezza(); i++){
                this.rigaNave++;
            }
        }else{
            for (int i = 0; i <= tipoNave.getLunghezza() ; i++) {
                this.colonnaNave++;
            }
        }
    }

    public void controlloBordi(){
        //TODO
        //metodo che controlla che se la posizione della nave e [1;1] e va verso alto verticale la sua lunghezza non puo essere piu lunga di dimensione -1
    }

    public boolean colpito(int riga, int colonna, Button btn) {
        if (riga == rigaNave && colonna == colonnaNave) {
            return colpito = true;
        }
        return colpito = false;
    }

    private int contaColpiAssegno() {
        return contaColpi++;
    }

    public boolean affondato() {
        if (colpito) {
            if (contaColpiAssegno() >= lunghezza) {
                return affondato = true;
            }
        }
        return affondato = false;
    }

    public int getContaColpi() {
        return contaColpi;
    }

    public int getRigaNave() {
        return rigaNave;
    }

    public int getColonnaNave() {
        return colonnaNave;
    }

    public boolean isVerticale() {
        return isVerticale;
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
