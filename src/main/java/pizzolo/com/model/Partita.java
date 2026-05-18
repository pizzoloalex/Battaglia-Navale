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
    private Nave ultimaNaveAffondataGiocatore;
    private Nave ultimaNaveAffondataAi;

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

    public Nave getUtlimaNaveAffondataGiocatore() {
        return ultimaNaveAffondataGiocatore;
    }

    public void setUtlimaNaveAffondataGiocatore(Nave nave) {
        this.ultimaNaveAffondataGiocatore = nave;
    }

    public Nave getUltimaNaveAffondataAi() {
        return ultimaNaveAffondataAi;
    }

    public void setUltimaNaveAffondataAi(Nave nave) {
        this.ultimaNaveAffondataAi = nave;
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
        if (!grigliaGiocatore.getGiocatore().isTurno()) {
            return;
        }

        boolean colpita = false;

        for (Nave n : grigliaAi.getIa().getNavi()) {
            if (n.colpito(riga, colonna)) {
                colpita = true;
                grigliaAi.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                System.out.println("Giocatore colpisce [" + riga + "," + colonna + "]");
                if (n.affondato()) {
                    ultimaNaveAffondataAi = n;
                    System.out.println("Giocatore affonda nave AI");
                }
                break;
            }
        }

        if (!colpita) {
            grigliaAi.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
            System.out.println("Giocatore manca [" + riga + "," + colonna + "]");
            grigliaGiocatore.getGiocatore().setTurno(false);
            grigliaAi.getIa().setTurno(true);
        }
    }

    /**
     * Gestisce il turno dell'AI
     * Ritorna true se l'AI ha colpito, false se ha mancato
     */
    public boolean gestioneTurnoAi() {
        if (!grigliaAi.getIa().isTurno()) {
            return false;
        }

        int[] posizione = gestioneColpoCellaAi();
        int riga = posizione[0];
        int colonna = posizione[1];

        // Se la cella è già stata giocata, ritenta con nuove coordinate
        if (grigliaGiocatore.getStatoCella(riga, colonna) == StatoCella.COLPITA ||
                grigliaGiocatore.getStatoCella(riga, colonna) == StatoCella.MANCATA) {
            return gestioneTurnoAi(); // Richiama ricorsivamente
        }

        boolean colpita = false;
        for (Nave n : grigliaGiocatore.getGiocatore().getNavi()) {
            if (n.colpito(riga, colonna)) {
                colpita = true;
                grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                System.out.println("AI colpisce [" + riga + "," + colonna + "]");
                if (n.affondato()) {
                    ultimaNaveAffondataGiocatore = n;
                    System.out.println("AI affonda nave giocatore");
                }
                break;
            }
        }

        if (!colpita) {
            grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
            System.out.println("AI manca [" + riga + "," + colonna + "], turno al giocatore");
            grigliaAi.getIa().setTurno(false);
            grigliaGiocatore.getGiocatore().setTurno(true);
        }

        return colpita;
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