package pizzolo.com.model;

import java.util.Random;

public class Partita {

    private Griglia grigliaGiocatore;
    private Griglia grigliaAi;
    private boolean turno;
    private boolean iaBoolean;
    private Nave ultimaNaveAffondataAi;        // nave AI affondata dal giocatore
    private Nave ultimaNaveAffondataGiocatore; // nave giocatore affondata dall'AI

    public Partita() {
        grigliaAi = new Griglia();
        grigliaGiocatore = new Griglia();

        // Crea le navi disponibili per il giocatore
        grigliaGiocatore.creaNaviGiocatoreDisponibili();

        // Crea le navi dell'AI in posizioni casuali
        grigliaAi.creaNaviAiCasuali();
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

    // --- getter/setter separati per le due navi affondate ---

    public Nave getUltimaNaveAffondataAi() {
        return ultimaNaveAffondataAi;
    }

    public void setUltimaNaveAffondataAi(Nave nave) {
        this.ultimaNaveAffondataAi = nave;
    }

    public Nave getUtlimaNaveAffondataGiocatore() {
        return ultimaNaveAffondataGiocatore;
    }

    public void setUtlimaNaveAffondataGiocatore(Nave nave) {
        this.ultimaNaveAffondataGiocatore = nave;
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
        System.out.println("GRIGLIA CON NAVI GIOCATORE");
        System.out.println(grigliaGiocatore.toString());
    }

    public void mostraGrigliaAi() {
        System.out.println("GESTIONE GRIGLIA AI");
        System.out.println(grigliaAi.toString());
    }

    public void iniziaPartita() {
        turno = true;
        grigliaGiocatore.getGiocatore().setTurno(turno);
    }

    public void gestioneTurnoGiocatore(int riga, int colonna) {
        if (grigliaGiocatore.getGiocatore().isTurno()) {
            if (grigliaAi.getStatoCella(riga, colonna) != StatoCella.COLPITA &&
                    grigliaAi.getStatoCella(riga, colonna) != StatoCella.MANCATA) {
                boolean colpita = false;
                for (Nave n : grigliaAi.getIa().getNavi()) {
                    if (n.colpito(riga, colonna)) {
                        colpita = true;
                        grigliaAi.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                        System.out.println("Giocatore colpisce riga " + riga + " e colonna " + colonna);
                        if (n.affondato()) {
                            ultimaNaveAffondataAi = n; // nave dell'AI affondata
                            System.out.println("Giocatore affonda nave AI");
                        }
                    }
                }
                if (!colpita) {
                    grigliaAi.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
                    grigliaGiocatore.getGiocatore().setTurno(false);
                    grigliaAi.getIa().setTurno(true);
                    System.out.println("Turno cambiato, tocca AI");
                }
            }
        }
    }

    public boolean gestioneTurnoAi() {
        while (grigliaAi.getIa().isTurno()) {
            int[] posizione = gestioneColpoCellaAi();
            int riga = posizione[0];
            int colonna = posizione[1];
            if (grigliaGiocatore.getStatoCella(riga, colonna) != StatoCella.COLPITA &&
                    grigliaGiocatore.getStatoCella(riga, colonna) != StatoCella.MANCATA) {
                boolean colpita = false;
                for (Nave n : grigliaGiocatore.getGiocatore().getNavi()) {
                    if (n.colpito(riga, colonna)) {
                        colpita = true;
                        grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                        System.out.println("AI colpisce riga " + riga + " e colonna " + colonna);
                        if (n.affondato()) {
                            ultimaNaveAffondataGiocatore = n; // nave del giocatore affondata
                            System.out.println("AI affonda nave giocatore");
                        }
                    }
                }
                if (colpita) {
                    return true;
                } else {
                    grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
                    grigliaGiocatore.getGiocatore().setTurno(true);
                    grigliaAi.getIa().setTurno(false);
                    System.out.println("Turno cambiato, tocca al giocatore");
                    break;
                }
            }
        }
        return false;
    }

    private int[] gestioneColpoCellaAi() {
        Random rnd = new Random();
        int[] posizione = new int[2];
        posizione[0] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1);
        posizione[1] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1);
        return posizione;
    }
}
