import java.io.Serializable;

public abstract class Agens implements Serializable, Leptetheto {
    private int idotartam;
    private boolean kenve;
    private Virologus virologus;

    /**
     * Getter-settere az idotartamnak
     *
     * @return visszaadja/beállítja mennyi ideig használható az agens
     */
    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    /**
     * Getter-setternek a kenvének
     *
     * @return visszaadja/beállítja, hogy megvan-e kenve a virologus az agenssel
     */
    public boolean getKenve() {
        return kenve;
    }

    public void setKenve(boolean kenve) {
        this.kenve = kenve;
    }

    /**
     * Getter-setter a virologusnak
     *
     * @return visszaadja/hozzáadja a virologust, aki az agenst birtokolja
     */
    public Virologus getVirologus() {
        return virologus;
    }

    public void setVirologus(Virologus virologus) {
        this.virologus = virologus;
    }

    /**
     * Agens instruktora paraméter nélküli.
     */
    public Agens() {
        kenve = true;
    }

    /**
     * Ágens instruktor, paraméterrel ami hozzá rendeli az ágenshez a birtokló virologust.
     *
     * @param v A virologsu aki birtokolja az ágenst bármilyen módón.
     */
    public Agens(Virologus v) {
        kenve = true;
        virologus = v;
    }

    /**
     * Körönként csökkenti az Ágens objektum időtartamát.
     */
    public abstract void lep();

    /**
     * Az Ágens objektum speciális hatását valósítja meg.
     *
     * @param v Erre a virológusra hat a hatás.
     */
    public abstract void hatas(Virologus v);

    /**
     * Lekéri az objektum adatait
     */
    void observe()
    {

    }


}
