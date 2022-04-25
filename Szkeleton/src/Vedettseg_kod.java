public class Vedettseg_kod extends  Kod {
    /**
     * Vedettseg_kod konstruktora
     * @param v
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
        //System.out.println("Vedettseg_kod.letrehoz() ->Letrehoz vedettseg.");
        return new Vedettseg(this.getV());
    }

    @Override
    public String toString() {
        return "Vedettseg";
    }

}
