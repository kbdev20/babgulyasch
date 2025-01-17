public class Amnezia_kod extends Kod {
    /**
     * Amnezia_kod paraméteres instruktora
     *
     * @param v a virologus aki birtokolja ezt a kodot
     */
    public Amnezia_kod(Virologus v) {
        setV(v);
        setAr(new Anyag(10, 10));
    }

    /**
     * Amnezia_kod paraméter nélküli instruktor
     * <p>
     * A birtoklo virologust null-ra állitaja
     */
    public Amnezia_kod() {
        setV(null);
        setAr(new Anyag(10, 10));
    }

    /**
     * Ezzel a metódussal hozzuk létre az amnézia ágenst.
     */
    public Amnezia letrehoz() {
        //IFio.print("Amnezia_kod.letrehoz() -> Letrehoz amnezia.");
        return new Amnezia(getV());
    }

    /**
     * Viaasza adja stringben a amnezia kod fajtáját
     *
     * @ return kód fajtája
     */
    @Override
    public String toString() {
        return "Amnezia";
    }

}
