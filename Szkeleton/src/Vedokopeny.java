import java.util.Random;

public class Vedokopeny extends Felszereles {
    /**
     * A felkent ágenseket nullázza le 82.3% valószinűséggel.
     */
    public Vedokopeny(){
        setAktiv(false);
    }

    /**
     * A védö köpeny 82.3% veri le magárol az ágenseket, ezt a felszerelés hatás függvény meghivásával érjük el
     */
    @Override
    public void felszerelesHatas(Virologus forras, Virologus cel, Agens a) {

        float rnd;
        rnd = new Random().nextFloat();

        //csak tesztelésre a protótípusban, a kész (grafikus) programban nem fog szereplni: (nem OO)
        if(GameManager.rand == false){
            if (Main.gm.kopeny_elfogad)
            cel.getRakenve().remove(a);
        }
        else{
            if(rnd <= 0.823){
                cel.getRakenve().remove(a);
            }
        }
    }

    @Override
    public String toString() {
        return "Vedokopeny";
    }
}
