public class Benitottsag_kod extends Kod{
    /**
     * Benitottsag_kod konstruktor
     * @param v
     */
    public Benitottsag_kod(Virologus v) {
        setV(v);
        setAr(new Anyag(10, 10));
    }
    /**
     * Ezzel a metódussal hozzuk létre az bénítottság ágenst.
     */
    public Benitottsag letrehoz (){
        //System.out.println("Benitottsag_kod.letrehoz() ->Letrehoz benitottsag.");
        return new Benitottsag(getV());
    }

    @Override
    public String toString() {
        return "Benitottsag";
    }

}
