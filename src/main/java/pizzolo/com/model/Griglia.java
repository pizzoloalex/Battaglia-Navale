package pizzolo.com.model;

import java.util.Random;

public class Griglia {
    private final int DIMENSIONE = 10;
    private StatoCella[][] statoCella;
    private Giocatore giocatore;
    private Giocatore ia;

    public Griglia() {
        giocatore = new Giocatore();
        ia = new Giocatore();
        statoCella = new StatoCella[DIMENSIONE][DIMENSIONE];
        setCelleIniziali();
    }

    public void posizionaNaviGiocatore() {
        setNaviGiocatore();
    }

    public void posizionaNaviAi() {
        setNaveAi();
    }

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
        //TODO
        //aggiungere parametro restituito da metodo posizionaNaveAi() per la riga e la colonna e la direzione
        Nave naveAi = new Nave(TipoNave.INCROCIATORI);
        naveAi.setVerticale(setOrientamentoNaveAi(naveAi));
        naveAi.setRigaNave(setPosizionamentoRigaColonnaNaveAi()[0]);
        naveAi.setColonnaNave(setPosizionamentoRigaColonnaNaveAi()[1]);
        System.out.println("orientamento nave: " + naveAi.isVerticale());
        System.out.println("Riga nave  ai:" + naveAi.getRigaNave() + " Colonna  nave ai:" + naveAi.getColonnaNave());
        ia.getNavi().add(naveAi);
    }

    public void posizionaNaveAi(Nave nave) {
        //TODO
    }

    private int[] setPosizionamentoRigaColonnaNaveAi() {
        Random rnd = new Random();
        int[] posizione = new int[2];
        posizione[0] = rnd.nextInt(9) + 1;
        posizione[1] = rnd.nextInt(9) + 1;
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
