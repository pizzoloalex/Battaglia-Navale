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
//    private static int contNavi;

    public Nave(TipoNave tipoNave, int rigaNave, int colonnaNave, boolean isVerticale) {
        this.lunghezza = tipoNave.getLunghezza();
        this.rigaNave = rigaNave;
        this.colonnaNave = colonnaNave;
        this.isVerticale = isVerticale;
//        contNavi++;
    }

    public void setRigaNave(int rigaNave) {
        this.rigaNave = rigaNave;
    }

    public void setColonnaNave(int colonnaNave) {
        this.colonnaNave = colonnaNave;
    }

    public Nave(TipoNave tipoNave, int rigaNave, int colonnaNave) {
        this.lunghezza = tipoNave.getLunghezza();
        this.rigaNave = rigaNave;
        this.colonnaNave = colonnaNave;
    }

    public Nave(TipoNave tipoNave) {
        this.lunghezza = tipoNave.getLunghezza();
    }


    public void setVerticale(boolean verticale) {
        isVerticale = verticale;
    }

//    public void controlloBordi() {
//        //metodo che controlla che se la posizione della nave e [1;1] e va verso alto verticale la sua lunghezza non puo essere piu lunga di dimensione -1
//    }

    public boolean colpito(int riga, int colonna) {
        if (isVerticale) {
            if (colonna == colonnaNave && riga >= rigaNave  && riga <= rigaNave + lunghezza - 1) {
                contaColpi++;
                return colpito = true;
            }
        } else {
            if (riga == rigaNave && colonna >= colonnaNave && colonna <= colonnaNave + lunghezza - 1) {
                contaColpi++;
                return colpito = true;
            }
        }
        return colpito = false;
    }

    public boolean affondato() {
        if (contaColpi >= lunghezza) {
            return affondato = true;

        }
        return affondato = false;
    }

//    @Override
//    public String toString() {
//        return String.valueOf(contNavi);
//    }

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
