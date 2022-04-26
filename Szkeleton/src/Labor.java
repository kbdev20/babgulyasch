import java.util.Random;

public class Labor extends Mezo {
    private Kod kod;
    private Medvetanc m;

    /**
     * Labor konstruktor
     */
    public Labor() {
        if (GameManager.rand) {
            Random ra = new Random();
            int r = ra.nextInt(100);
            m = (r < 25) ? (new Medvetanc()) : null;

            Random rand_kod = new Random();
            int r_kod = rand_kod.nextInt(3);
            switch (r_kod){
                case 0:
                    kod = new Amnezia_kod();
                    break;
                case 1:
                    kod = new Benitottsag_kod();
                    break;
                case 2:
                    kod = new Vedettseg_kod();
                    break;
                case 3:
                    kod = new Vitustanc_kod();
                    break;
                default: break;
            }
        }
        else {
            m = null;
        }
    }
    /**
     * Getterek - Setterek
     */
    public Kod getKod() {return kod;}
    public void setKod(Kod k) {this.kod = k;}

    public void setM(Medvetanc m) {
        this.m = m;
    }

    @Override
    public String info(){
        StringBuilder sb = new StringBuilder();
        if (kod != null){
            sb.append("Kod: ").append(kod).append("\n");
        }
        if (m == null){
            sb.append("Medvetanc: nincs\n");
        }
        else{
            sb.append("Medvetanc: van\n");
        }
        return super.info() + sb;
    }

    @Override
    public String toString() {
        return "Labor";
    }

    @Override
    public void elfogad (Virologus vir) {
        super.elfogad(vir);
        if(m != null){
            vir.getRakenve().add(m);
        }
    }


}
