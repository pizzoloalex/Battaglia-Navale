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
    private Nave utlimaNaveAffondata;

    public void setUtlimaNaveAffondata(Nave utlimaNaveAffondata) {
        this.utlimaNaveAffondata = utlimaNaveAffondata;
    }

    public int getDimensione() {
        return grigliaGiocatore.getDIMENSIONE();
    }

    public Nave getUtlimaNaveAffondata() {
        return utlimaNaveAffondata;
    }

    public boolean isIaBoolean() {
        return iaBoolean;
    }

    public void setIaBoolean(boolean iaBoolean) {
        this.iaBoolean = iaBoolean;
    }

    public Griglia getGrigliaAi() {
        return grigliaAi;
    }

    public Partita() {
        grigliaAi = new Griglia();
        grigliaGiocatore = new Griglia();
    }

    public Griglia getGrigliaGiocatore() {
        return grigliaGiocatore;
    }

    //METODO DI DEBUG SENZA NAVI
    public void mostraGrigliaIniziale() {
        System.out.println("GRIGLIA SENZA NAVI");
        System.out.println(grigliaGiocatore.toString());
    }

    //METODO DI DEBUG CON NAVI
    public void mostraGrigliaConNavi() {
        grigliaGiocatore.posizionaNaviGiocatore();
        System.out.println("GRIGLIA CON NAVI");
        System.out.println(grigliaGiocatore.toString());
    }

    //METODO DEBUG NAVI AI
    public void mostraGrigliaAi() {
        System.out.println("GESTIONE GRIGLIA AI");
        grigliaAi.posizionaNavi();
        System.out.println(grigliaAi.toString());
//        System.out.println("NAVI IA");
//        System.out.println(grigliaAi.toStringAi());
    }

    /**
     * metodo che gestisce l'inizio della  partita
     */
    public void iniziaPartita() {
        turno = true; //turno del giocatore
        grigliaGiocatore.getGiocatore().setTurno(turno);
    }

    public void gestioneTurnoGiocatore(int riga, int colonna) {
        if (grigliaGiocatore.getGiocatore().isTurno()) {
            System.out.println("Navi AI:" + grigliaAi.getIa().getNavi().size());
            if (grigliaAi.getStatoCella(riga, colonna) != StatoCella.COLPITA && grigliaAi.getStatoCella(riga, colonna) != StatoCella.MANCATA) {

                boolean colpita = false; // variabile di appoggio

                for (Nave n : grigliaAi.getIa().getNavi()) {
                    if (n.colpito(riga, colonna)) {
                        colpita = true; // trovata una nave colpita
                        grigliaAi.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
                        System.out.println("Colpita");
                        if (n.affondato()) {
                            utlimaNaveAffondata = n;
                            System.out.println("Affondata");
                        }
                    }
                }

                // controllo se nessuna nave e stata colpita
                if (!colpita) {
                    grigliaAi.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
                    System.out.println("MANCATA");
                    //scambia il turno
                    grigliaGiocatore.getGiocatore().setTurno(false);
                    grigliaAi.getIa().setTurno(true);
                }
            }
        }
    }

//    public void gestioneTurnoAi(int riga, int colonna){
//        if (grigliaAi.getIa().isTurno()){
//            if (getGrigliaGiocatore().getStatoCella(riga,colonna) != StatoCella.MANCATA && getGrigliaGiocatore().getStatoCella(riga, colonna) != StatoCella.COLPITA){
//
//                boolean colpita = false; // variabile di appoggio
//
//                for (Nave n : grigliaAi.getIa().getNavi()) {
//                    if (n.colpito(riga, colonna)) {
//                        colpita = true; // trovata una nave colpita
//                        grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.COLPITA;
//                        System.out.println("Colpita");
//                        if (n.affondato()) {
//                            utlimaNaveAffondata = n;
//                            System.out.println("Affondata");
//                        }
//                    }
//                }
//                if (!colpita) {
//                    grigliaGiocatore.getStatoCella()[riga][colonna] = StatoCella.MANCATA;
//                    System.out.println("MANCATA");
//                    //scambia il turno
//                    grigliaGiocatore.getGiocatore().setTurno(true);
//                    grigliaAi.getIa().setTurno(false);
//                }
//            }
//        }
//
//    }

//    private int[] posizioneCellaColpita(int row, int col){
//
//        Random rnd = new Random();
//
//    }
}
