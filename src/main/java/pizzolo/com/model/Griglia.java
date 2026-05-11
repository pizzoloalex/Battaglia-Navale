package pizzolo.com.model;

import java.util.Random;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella[][] statoCella;
    private Giocatore giocatore;
    private Giocatore ia;

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public Giocatore getIa() {
        return ia;
    }

    public int getDIMENSIONE() {
        return DIMENSIONE;
    }

    public Griglia() {
        giocatore = new Giocatore();
        ia = new Giocatore();
        statoCella = new StatoCella[DIMENSIONE][DIMENSIONE];
        setCelleIniziali();
    }

    public void posizionaNaviGiocatore() {
        setNaviGiocatore();
    }

    public void posizionaNavi() {
        setNaveAi();
//        System.out.println("Navi AI:  " + ia.toString());
    }

//    public String toStringAi(){
//        return ia.getNavi().toString();
//    }

    /**
     * inizializza le celle della griglia a vuote, nessuna nave
     */
    public void setCelleIniziali() {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                statoCella[i][j] = StatoCella.VUOTA;
            }
        }
    }


    public StatoCella[][] getStatoCella() {
        return statoCella;
    }

    public StatoCella getStatoCella(int riga, int colonna) {
        return statoCella[riga][colonna];
    }

    /**
     * toString per il debug della tabella
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                if (statoCella[i][j] == StatoCella.VUOTA) {
                    s += "0 ";
                } else {
                    s += "1 ";
                }
            }
            s += "\n";
        }
        return s;
    }

    /**
     * metodo che aggiunge la nave alla griglia
     */
    public void setNaviGiocatore() {
        Nave nave = new Nave(TipoNave.PORTAEREI, 3, 5, true);
        giocatore.getNavi().add(nave);
        posizionaNave(nave);
    }

    /**
     * metodo che posiziona la nave e la posiziona in base al suo orientamento
     *
     * @param nave nave da aggiungere
     */
    private void posizionaNave(Nave nave) {
        for (int i = 0; i < nave.getLunghezza(); i++) {
            if (nave.isVerticale()) {
                statoCella[nave.getRigaNave() + i][nave.getColonnaNave()] = StatoCella.NAVE;
            } else {
                statoCella[nave.getRigaNave()][nave.getColonnaNave() + i] = StatoCella.NAVE;
            }
        }
    }

    public void setNaveAi() {
        Nave naveAi = new Nave(TipoNave.INCROCIATORI);
        naveAi.setVerticale(setOrientamentoNaveAi(naveAi));

        int[] posizione = setPosizionamentoRigaColonnaNaveAi(naveAi); // chiami UNA sola volta
        naveAi.setRigaNave(posizione[0]);
        naveAi.setColonnaNave(posizione[1]);

        System.out.println("orientamento nave: " + naveAi.isVerticale());
        System.out.println("Riga nave ai:" + naveAi.getRigaNave() + " Colonna nave ai:" + naveAi.getColonnaNave());
        ia.getNavi().add(naveAi);
        posizionaNaveAi(naveAi);
    }

    private void posizionaNaveAi(Nave nave) {
        for (int i = 0; i < nave.getLunghezza(); i++) {
            if (nave.isVerticale()) {
                statoCella[nave.getRigaNave() + i][nave.getColonnaNave()] = StatoCella.NAVE;
            } else {
                statoCella[nave.getRigaNave()][nave.getColonnaNave() + i] = StatoCella.NAVE;
            }
        }
    }

    private int[] setPosizionamentoRigaColonnaNaveAi(Nave nave) {
        Random rnd = new Random();
        int[] posizione = new int[2];

        if (nave.isVerticale()) {
            // riga: parte da 1, e deve avere spazio per tutta la lunghezza senza uscire dalla griglia
            posizione[0] = 1 + rnd.nextInt(DIMENSIONE - nave.getLunghezza() - 1); // es: 1...(10-lunghezza-1)
            // colonna: parte da 1
            posizione[1] = 1 + rnd.nextInt(DIMENSIONE - 1); // es: 1...9
        } else {
            // riga: parte da 1
            posizione[0] = 1 + rnd.nextInt(DIMENSIONE - 1); // es: 1...9
            // colonna: parte da 1, e deve avere spazio per tutta la lunghezza
            posizione[1] = 1 + rnd.nextInt(DIMENSIONE - nave.getLunghezza() - 1); // es: 1...(10-lunghezza-1)
        }

        return posizione;
    }

    private boolean setOrientamentoNaveAi(Nave nave) {
        //TODO finire
        //Metodo: per ogni nave presente nell'array genero un nuovo valore
        Random rnd = new Random();
        nave.setVerticale(rnd.nextBoolean());
        return nave.isVerticale();
    }


}
