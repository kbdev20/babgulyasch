import java.util.List;
import java.util.Random;

public class Medvetanc extends Agens{
    /**
     * Medvetánc konstruktora
     */
    public Medvetanc() {
        setKenve(true);
        setIdotartam(1);
    }

    @Override
    public void lep() {

    }

    /**
     *
     * @param v Erre a virológusra hat a hatás.
     */
    @Override
    public void hatas(Virologus v) {
        for(int i = 0; i < v.getRakenve().size(); i++){
            Agens h = v.getRakenve().get(i);
            if (h != this) v.getRakenve().remove(h);
        }
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

        if (getVirologus() != null) getVirologus().getMezo().anyagElpusztit();
    }

    @Override
    public String toString() {
        return "Medvetanc";
    }
}


