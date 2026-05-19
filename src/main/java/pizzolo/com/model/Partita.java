package pizzolo.com.model;

import java.util.Random;

/**
 * classe che gestisce tutta la partita
 */
public class Partita {

    private Griglia grigliaGiocatore;
    private Griglia grigliaAi;
    private boolean turno; //true = turno del giocatore ---- false = turno ai
    private boolean iaBoolean;
    private Nave ultimaNaveAffondata;

    public Partita() {
        grigliaAi = new Griglia();
        grigliaGiocatore = new Griglia();
    }

    public int getDimensione() {
        return grigliaGiocatore.getDIMENSIONE();
    }

    public Griglia getGrigliaGiocatore() {
        return grigliaGiocatore;
    }

    public Griglia getGrigliaAi() {
        return grigliaAi;
    }

    public Nave getUtlimaNaveAffondata() {
        return ultimaNaveAffondata;
    }

    public void setUtlimaNaveAffondata(Nave nave) {
        this.ultimaNaveAffondata = nave;
    }

    public boolean isIaBoolean() {
        return iaBoolean;
    }

    public void setIaBoolean(boolean iaBoolean) {
        this.iaBoolean = iaBoolean;
    }

    public void mostraGrigliaIniziale() {
        System.out.println("GRIGLIA SENZA NAVI");
        System.out.println(grigliaGiocatore.toString());
    }

    public void mostraGrigliaConNavi() {
        grigliaGiocatore.posizionaNaviGiocatore();
        System.out.println("GRIGLIA CON NAVI");
        System.out.println(grigliaGiocatore.toString());
    }

    public void mostraGrigliaAi() {
        System.out.println("GESTIONE GRIGLIA AI");
        grigliaAi.posizionaNavi();
        System.out.println(grigliaAi.toString());
    }

    public void iniziaPartita() {
        turno = true;
        grigliaGiocatore.getGiocatore().setTurno(turno);
    }

    /**
     * Gestisce l'attacco del giocatore sulla griglia dell'AI
     */
    public void gestioneTurnoGiocatore(int riga, int colonna) {
        if (grigliaGiocatore.getGiocatore().isTurno()) {
            if (grigliaAi.getStatoCella(riga, colonna) != StatoCella.COLPITA && grigliaAi.getStatoCella(riga, colonna) != StatoCella.MANCATA) {
                boolean colpita = false;
                for (Nave n : grigliaAi.getIa().getNavi()) {
                    if (n.colpito(riga, colonna)) {
                        colpita = true;
                        grigliaAi.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                        System.out.println("Giocatore colpisce riga " + riga + " e colonna " + colonna);
                        if (n.affondato()) {
                            ultimaNaveAffondata = n;
                            System.out.println("Giocattore affonda  nave");
                        }
                    }
                }
                if (!colpita) {
                    grigliaAi.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
                    grigliaGiocatore.getGiocatore().setTurno(false);
                    grigliaAi.getIa().setTurno(true);
                    System.out.println("Turno cambiato,  tocca  AI");
                }
            }
        }
    }

    /**
     * Gestisce il turno dell'AI
     */
    public boolean gestioneTurnoAi() {

        while (grigliaAi.getIa().isTurno()) {
            int[] posizione = gestioneColpoCellaAi();
            int riga = posizione[0];
            int colonna = posizione[1];
            if (grigliaGiocatore.getStatoCella(riga, colonna) != StatoCella.COLPITA && grigliaGiocatore.getStatoCella(riga, colonna) != StatoCella.MANCATA) {
                boolean colpita = false;
                for (Nave n : grigliaGiocatore.getGiocatore().getNavi()) {
                    if (n.colpito(riga, colonna)) {
                        colpita = true;
                        grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                        System.out.println("AI colpisce riga " + riga + " e colonna " + colonna);
                        if (n.affondato()) {
                            ultimaNaveAffondata = n;
                            System.out.println("AI affonda  nave");
                        }
                    }
                }
                if (colpita) {
                    return true;
                } else {
                    grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
                    grigliaGiocatore.getGiocatore().setTurno(true);
                    grigliaAi.getIa().setTurno(false);
                    System.out.println("Turno cambiato , tocca  al  giocatore");
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Genera coordinate casuali per l'attacco dell'AI
     */
    private int[] gestioneColpoCellaAi() {
        Random rnd = new Random();
        int[] posizione = new int[2];
        posizione[0] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1);
        posizione[1] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1);
        return posizione;
    }
}