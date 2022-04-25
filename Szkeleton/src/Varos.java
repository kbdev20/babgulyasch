public class Varos extends Mezo {
    private boolean ures;

    /**
     * Varos konstruktorai
     */
    public Varos(){}; //leironyelvhez
    public Varos(Felszereles felszereles, boolean ures) {
        getFelszerelesek().add(felszereles);
        this.ures = ures;
    }
    public Varos(Felszereles felszereles) {
        getFelszerelesek().add(felszereles);
        this.ures = false;
    }

    public boolean getUres() {return ures;}
    public void setUres(boolean ures) {this.ures = ures;}

    @Override
    public String toString() {
        return "Varos";
    }
}
