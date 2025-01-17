public class Benitottsag_kod extends Kod {
    /**
     * Benitottsag_kod konstruktor
     *
     * @param v a virologus aki birtokolja ezt a kodot
     */
    public Benitottsag_kod(Virologus v) {
        setV(v);
        setAr(new Anyag(10, 10));
    }

    public Benitottsag_kod() {
        setV(null);
        setAr(new Anyag(10, 10));
    }

    /**
     * Ezzel a metódussal hozzuk létre az bénítottság ágenst.
     */
    public Benitottsag letrehoz() {
        //IFio.print("Benitottsag_kod.letrehoz() ->Letrehoz benitottsag.");
        return new Benitottsag(getV());
    }

    /**
     * Vissza adj a tipusát stringben
     *
     * @return kód típusa
     */
    @Override
    public String toString() {
        return "Benitottsag";
    }

}
