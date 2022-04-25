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
    public void setKod(Kod k) {this.kod = kod;}

    public Medvetanc getM() {
        return m;
    }
    public void setM(Medvetanc m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "Labor";
    }
}
