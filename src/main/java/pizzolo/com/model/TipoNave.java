package pizzolo.com.model;

public enum TipoNave {
    PORTAEREI(5), CACCIATORPENDIERE(4), INCROCIATORI(3), SOTTOMARINO(2);

    private int lunghezza;

    TipoNave(int lunghezza) {
        this.lunghezza = lunghezza;
    }

    public int getLunghezza() {
        return lunghezza;
    }
}
