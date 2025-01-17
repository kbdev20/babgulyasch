import java.io.Serializable;

public class Anyag implements Serializable {
    private int nukleotid;
    private int aminosav;

    /**
     * Anyag instruktor
     */
    public Anyag(int nuk, int ami) {
        nukleotid = nuk;
        aminosav = ami;
    }

    /**
     * Aminosav get-set
     *
     * @return visszaadja/beállítja a tárolt aminosavak számát
     */
    public int getAminosav() {
        return aminosav;
    }

    public void setAminosav(int aminosav) {
        this.aminosav = aminosav;
    }

    /**
     * Nukleotid get -set
     *
     * @return visszaadja/beállítja a tárolt nukleotidok számát
     */
    public int getNukleotid() {
        return nukleotid;
    }

    public void setNukleotid(int nukleotid) {
        this.nukleotid = nukleotid;
    }

    /**
     * Nőveli az anyag nukleodidját és aminosavját, a megadott Anyag alapján
     *
     * @param a anyagtároló, aminek a tárolt nukleodidját és aminosavját szeretnénk növelni
     */

    public void increase(Anyag a) {
        nukleotid += a.getNukleotid();
        aminosav += a.getAminosav();
    }

    /**
     * Csokkenti az anyag nukleodidját és aminosavját, a megadott Anyag alapján
     *
     * @param a a anyagtároló, aminek a tárolt nukleodidját és aminosavját szeretnénk csökkenteni
     */

    public void decrease(Anyag a) {
        nukleotid -= a.getNukleotid();
        aminosav -= a.getAminosav();
    }

    /**
     * Kiirja az anyag adatait
     *
     * @return visszaadja az anyag által tárolt nukleotidot, aminosavat és azok számát stringben
     */
    @Override
    public String toString() {
        return
                "Nukleotid=" + nukleotid +
                "\nAminosav=" + aminosav ;
    }
}
