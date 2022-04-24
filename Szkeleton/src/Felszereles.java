public abstract class Felszereles {
    private int lejarat;
    private Virologus vir;
    private boolean aktiv;

    /**
     * Felszereles konstruktor
     */
    public Felszereles() {
        vir = null;
    }

    /**
     * Getterek-Setterek
     * @return
     */
    public int getLejarat() {
        return lejarat;
    }
    public void setLejarat(int lejarat) {
        this.lejarat = lejarat;
    }

    /**
     * A felszerelések hatásainak absztrakt függvénye.
     *
     */
    public abstract void felszerelesHatas(Virologus forras, Virologus cel, Agens a);

    public void setVirologus( Virologus virologus) { this.vir = vir; }
    public Virologus getVirologus() { return vir; }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
    public  void onRemove(){}
}