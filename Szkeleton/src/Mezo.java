import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Mezo implements Serializable {
    private List<Mezo> szomszedok;
    private List<Felszereles> felszerelesek;
    private Anyag tarolo;
    private List<Virologus> virologusok;

    public Mezo(){
        szomszedok = new ArrayList<>();
        felszerelesek = new ArrayList<>();
        virologusok = new ArrayList<>();
        tarolo = new Anyag(0,0);
    }

    public List<Mezo> getSzomszedok() {
        return szomszedok;
    }
    public void setSzomszedok(Mezo szomszed){szomszedok.add(szomszed);}

    public List<Felszereles> getFelszerelesek() {
        return felszerelesek;
    }
    public void setFelszerelesek(Felszereles f) {
        felszerelesek.add(f);
    }

    public Anyag getTarolo() {
        return tarolo;
    }

    public void setTarolo(Anyag tarolo) {
        this.tarolo = tarolo;
    }

    public List<Virologus> getVirologusok() {
        return virologusok;
    }

    /**
     * Elfogadja a megadott virologust a mezőre.
     * @param vir Ezt a virológust fogadja el az adott mezőre
     */
    public void elfogad (Virologus vir) {
        //ellenőrizzük, hogy valóban szomszédos mezőből jön-e:
        if (szomszedok.contains(vir.getMezo())) {
            vir.setMezo(this);
            getVirologusok().add(vir);
        }
    }

    /**
     * Eltávolitja az adott virologust a mezőről.
     * @param vir Ezt a virológust távolítja el az adott mezőről.
     */
    public void eltavolit(Virologus vir) {
        getVirologusok().remove(vir);
    }

    /**
     * Medvetanc agens hatása hívja meg, de csak raktárban nem üres, ahol elpusztítja az anyagot.
     */
    public void anyagElpusztit() {}


    public String info() {
        StringBuilder vir_lista = new StringBuilder();
        if (getVirologusok().size() > 0) {
            vir_lista.append(getVirologusok().get(0).getUserName());
            Virologus v;
            for (int i = 1; i < getVirologusok().size(); i++) {
                v = getVirologusok().get(i);
                vir_lista.append(", ").append(v.getUserName());
            }
            vir_lista.append("\n");
        }

        StringBuilder felsz_lista = new StringBuilder();
        if (getFelszerelesek().size() > 0) {
            felsz_lista.append("Felszerelések:\n");
            felsz_lista.append(getFelszerelesek().get(0)).append(" ").append(getFelszerelesek().get(0).getLejarat());
            Felszereles f;
            for (int i = 1; i < getVirologusok().size(); i++) {
                f = getFelszerelesek().get(i);
                felsz_lista.append(", ").append(f.toString()).append(" ").append(f.getLejarat());
            }
            felsz_lista.append("\n");
        }

        String anyag = "Aminosav: "+getTarolo().getAminosav()+" Nukleotid: "+getTarolo().getNukleotid()+"\n";

        StringBuilder szomszed = new StringBuilder();
        szomszed.append("Szomszédok:\n");
        if (getSzomszedok().size() > 0) {
            ArrayList<Integer> szomszed_idcs = szomszed_azonositok();
            szomszed.append(szomszed_idcs.get(0)).append("\t").append(getSzomszedok().get(0)).append("\n");
            Mezo sz;
            for (int i = 1; i < getSzomszedok().size(); i++) {
                sz = getSzomszedok().get(i);
                szomszed.append(szomszed_idcs.get(i)).append("\t").append(sz.toString()).append("\n");
            }
        }

        return vir_lista +felsz_lista.toString()+ szomszed +anyag;
    }

    /**
     * Csak a prototípus tesztelésére használandó segédfüggvény - kész programban nem lesz benne (OO)
     */
    private ArrayList<Integer> szomszed_azonositok() {
        ArrayList<Integer> indexek = new ArrayList<>();
        for (int i = 0; i < Main.gm.getMezok().size(); i++) {
            indexek.add(i);
        }
        return indexek;
    }
    /**
     * Lekéri az objektum adatait
     */
    void observe(){}
}
