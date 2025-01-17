public class Balta extends Felszereles {
    /**
     * Balta instruktor
     */
    public Balta() {
        setLejarat(1);
        setAktiv(true);
    }

    /**
     * A felszerelés hatásba lép
     *
     * @param forras az a virologus, aaki használni akarja a felszerelést
     * @param cel az a virologus, akire ha a felszerelés
     * @param a .
     */
    @Override
    public void felszerelesHatas(Virologus forras, Virologus cel, Agens a) {
        forras.getFelszereles().remove(this);
        forras.megol(cel);

        getVirologus().getFelszereles().remove(this);
        setVirologus(null);

    }

    /**
     * vissza adja a balta tipusat stringben
     *
     * @return felszerelés típusa
     */
    @Override
    public String toString() {
        return "Balta";
    }
}
