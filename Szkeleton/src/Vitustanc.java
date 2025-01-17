import java.util.List;
import java.util.Random;

public class Vitustanc extends Agens{
    /**
     * Vitustánc konstruktora
     */
    public Vitustanc (Virologus v) {
        setIdotartam(5);
        setKenve(true);
        setVirologus(v);
    }

    /**
     * Körönként csökkenti a hatás időtartamát.
     */
    public void lep() {
        setIdotartam(getIdotartam()-1);
        if(getIdotartam() <= 0){
            this.getVirologus().getRakenve().remove(this);
        }
    }

    /**
     * Vítustáncra kényszerít egy virológust.
     * @param v A vítustáncra kényszerített virológus.
     */
    public void hatas (Virologus v) {
        if(getKenve()){
            List<Mezo> mezok = v.getMezo().getSzomszedok();
            int rnd;
            if(mezok.size() > 0){

                rnd = new Random().nextInt(mezok.size());
                if(!GameManager.rand){
                    rnd = 0;
                }
                Mezo lep = mezok.get(rnd);
                v.mozgas(lep);
            }
        }
    }

    @Override
    public String toString() {
        return "Vitustanc";
    }
}
