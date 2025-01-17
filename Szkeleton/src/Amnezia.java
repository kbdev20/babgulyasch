public class Amnezia extends Agens {
    /**
     * Amnezia Kosntruktor
     *
     * @param virologus az a virológus, aki meg lett kenve az ágenssel
     */

    public Amnezia(Virologus virologus) {
        setIdotartam(5);
        setKenve(true);
        setVirologus(virologus);
    }

    /**
     * Körönként csökkenti a hatás időtartamát.
     */
    public void lep() {
        setIdotartam(getIdotartam() - 1);
        if (getIdotartam() <= 0) {
            getVirologus().getRakenve().remove(this);
        }
    }

    /**
     * A virológus elfelejti az összes addig ismert genetikai kódot, törli a paraméterként megadott virológus
     * ismert_hatasok listáját.
     *
     * @param v Ez a virológus fogja elfelejteni a megtanult genetikai kódokat.
     */
    public void hatas(Virologus v) {
        if (getKenve()) {
            v.getIsmert_hatasok().clear();
            v.getAgens().clear();
            //v.getRakenve().remove(this);
        }
    }

    /**
     * vissza adja a amnézia tipusát stringben.
     *
     * @return agens típusa
     */
    @Override
    public String toString() {
        return "Amnezia";
    }
}
