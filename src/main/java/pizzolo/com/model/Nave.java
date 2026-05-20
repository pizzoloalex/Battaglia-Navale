package pizzolo.com.model;

public class Nave {
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
        this.isVerticale = isVerticale;
    }

    public Nave(TipoNave tipoNave, int rigaNave, int colonnaNave) {
        this.lunghezza = tipoNave.getLunghezza();
        this.rigaNave = rigaNave;
        this.colonnaNave = colonnaNave;
    }

    public Nave(TipoNave tipoNave) {
        this.lunghezza = tipoNave.getLunghezza();
    }

    // NUOVO: restituisce tutte le celle occupate
    public int[][] getCelle() {
        int[][] celle = new int[lunghezza][2];
        for (int i = 0; i < lunghezza; i++) {
            celle[i][0] = isVerticale ? rigaNave + i : rigaNave;
            celle[i][1] = isVerticale ? colonnaNave : colonnaNave + i;
        }
        return celle;
    }

    // NUOVO: sposta la nave durante il setup
    public void sposta(int nuovaRiga, int nuovaColonna, boolean verticale) {
        this.rigaNave = nuovaRiga;
        this.colonnaNave = nuovaColonna;
        this.isVerticale = verticale;
    }

    // Controlla se la nave è colpita in una cella specifica
    public boolean colpito(int riga, int colonna) {
        if (isVerticale) {
            if (colonna == colonnaNave && riga >= rigaNave && riga <= rigaNave + lunghezza - 1) {
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

    // Controlla se la nave è affondata
    public boolean affondato() {
        return contaColpi >= lunghezza;
    }

    // Getter e Setter
    public void setRigaNave(int rigaNave) { this.rigaNave = rigaNave; }
    public void setColonnaNave(int colonnaNave) { this.colonnaNave = colonnaNave; }
    public void setVerticale(boolean verticale) { isVerticale = verticale; }
    public void setLunghezza(int lunghezza) { this.lunghezza = lunghezza; }
    public void setAffondato(boolean affondato) { this.affondato = affondato; }
    public void setColpito(boolean colpito) { this.colpito = colpito; }
    public int getRigaNave() { return rigaNave; }
    public int getColonnaNave() { return colonnaNave; }
    public boolean isVerticale() { return isVerticale; }
    public int getLunghezza() { return lunghezza; }
    public boolean isAffondato() { return affondato; }
    public boolean isColpito() { return colpito; }
    public int getContaColpi() { return contaColpi; }
}
