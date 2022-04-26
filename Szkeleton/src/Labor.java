import java.util.Random;

public class Labor extends Mezo {
    private Kod kod;
    private Medvetanc m;

    /**
     * Labor konstruktor
     */
    public Labor() {
        if (GameManager.rand == true) {
            Random ra = new Random();
            int r = (int) ra.nextInt(100);
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
     * @return
     */
    public Kod getKod() {return kod;}
    public void setKod(Kod k) {this.kod = k;}

    public Medvetanc getM() {
        return m;
    }
    public void setM(Medvetanc m) {
        this.m = m;
    }

    @Override
    public String info(){
        StringBuilder sb = new StringBuilder();
        if (kod != null){
            sb.append("Kod: " + kod.toString() + "\n");
        }
        if (m == null){
            sb.append("Medvetanc: nincs\n");
        }
        else{
            sb.append("Medvetanc: van\n");
        }
        return super.info() + sb.toString();
    }

    @Override
    public String toString() {
        return "Labor";
    }

    @Override
    public void elfogad (Virologus vir) {
        super.elfogad(vir);
        if(m != null){
            vir.megkenve(m);
        }
    }


}
