public class Vedettseg_kod extends  Kod {
    /**
     * Vedettseg_kod konstruktora
     */
    public Vedettseg_kod(Virologus v) {
        setV(v);
        setAr(new Anyag(10, 10));
    }
    public Vedettseg_kod() {
        setV(null);
        setAr(new Anyag(10, 10));
    }

    /**
     * Ezzel a metódussal hozzuk létre az védettség ágenst.
     */
    public Vedettseg letrehoz (){
        return new Vedettseg(this.getV());
    }

    @Override
    public String toString() {
        return "Vedettseg";
    }

}
