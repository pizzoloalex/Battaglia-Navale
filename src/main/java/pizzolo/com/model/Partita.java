package pizzolo.com.model;


/**
 * classe che gestisce tutta la partita
 */
public class Partita {

    private Giocatore giocatore;
    private Giocatore ia;
    private Griglia grigliaGiocatore;
    private Griglia grigliaAi;
    private boolean turno; //true = turno del giocatore ---- false = turno ai
    private boolean iaBoolean;

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
        this.turno = true;//inizia il giocatore
        giocatore = new Giocatore();
        ia = new Giocatore();
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
        System.out.println("NAVI IA");
        System.out.println(grigliaAi.toStringAi());
    }

}
