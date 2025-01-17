import java.util.Random;

public class Varos extends Mezo {
    private boolean ures;

    /**
     * Varos konstruktorai
     */
    public Varos(){
        if (GameManager.rand) {
            Random n = new Random();
            int ra = n.nextInt(2);
            switch (ra) {
                case 0:
                    getFelszerelesek().add(new Kesztyu());
                    break;
                case 1:
                    getFelszerelesek().add(new Zsak());
                    break;
                case 2:
                    getFelszerelesek().add(new Vedokopeny());
                    break;
                default: break;
            }
            ures = false;
        }
        else {
            ures = true;
        }

    } //leironyelvhez
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
