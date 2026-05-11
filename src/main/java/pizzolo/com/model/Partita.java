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

    public void gestioneTurnoAi(int riga, int colonna) {
        //TODO
        //Metodo: prende le cordinate restituite da gestioneColpoCellaAi() per gestire la cella cliccata
        //Controllare lo stato delle varie celle
        //Implementare la logica del colpo del AI cosicche dopo due colpi di fila capisca dove sia la nave e il suo orientamento
    }

    private int[] gestioneColpoCellaAi() {
        Random rnd = new Random();
        int[] posizione = new int[2];
        // riga: parte da 1, e deve avere spazio per tutta la lunghezza senza uscire dalla griglia
        posizione[0] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 2); // es: 1...(10-lunghezza-1)
        // colonna: parte da 1
        posizione[1] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1); // es: 1...9
        // riga: parte da 1
        posizione[0] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 1); // es: 1...9
        // colonna: parte da 1, e deve avere spazio per tutta la lunghezza
        posizione[1] = 1 + rnd.nextInt(grigliaGiocatore.getDIMENSIONE() - 2); // es: 1...(10-lunghezza-1)


        return posizione;
    }
}
