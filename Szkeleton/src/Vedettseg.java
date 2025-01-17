public class Vedettseg extends Agens {
    /**
     * Vedettseg konstruktora
     */
    public Vedettseg (Virologus v){
        setIdotartam(5);
        setKenve(true);
        setVirologus(v);
    }

    /**
     * Körönként csökkenti a hatás időtartamát.
     */
    public void lep() {
        setIdotartam(getIdotartam()-1);
        for(int i = 0; i < getVirologus().getRakenve().size(); i++){
            getVirologus().getRakenve().get(i).setKenve(false);
        }
        if(getIdotartam() <= 0){
            for(int i = 0; i < getVirologus().getRakenve().size(); i++){
                getVirologus().getRakenve().get(i).setKenve(true);
            }
            getVirologus().getRakenve().remove(this);
        }

        //IFio.print("Vedettseg.lep() ->Védettség hatás léptetése.");
    }

    /**
     * Védett lesz a virológus az ágensekkel szemben.
     * @param v A vítustáncra kényszerített virológus.
     */
    public void hatas(Virologus v) {
        for(int i = 0; i < v.getRakenve().size(); i++){
                v.getRakenve().get(i).setKenve(false);

        }

    }

    @Override
    public String toString() {
        return "Vedettseg";
    }
}
