public class Vitustanc_kod extends Kod {
    /**
     * Vitustánc kód konstruktora
     */
    public Vitustanc_kod(Virologus v) {
        setV(v);
        setAr(new Anyag(10, 10));
    }

    /**
     * Vitustanc kód Virológus nélküli konsruktora, ha nem tárolja senki csak a laborban van
     */
    public Vitustanc_kod() {
        setV(null);
        setAr(new Anyag(10, 10));
    }
    /**
     * Ezzel a metódussal hozzuk létre az vítustánc ágenst.
     */
    public Vitustanc letrehoz (){
        //IFio.print("Vitustanc_kod.letrehoz() ->Letrehoz vitustanc.");
        return new Vitustanc(this.getV());
    }

    @Override
    public String toString() {
        return "Vitustanc";
    }

}
