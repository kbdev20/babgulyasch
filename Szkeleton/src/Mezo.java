import java.util.ArrayList;
import java.util.List;

public abstract class Mezo {
    private List<Mezo> szomszedok;
    private List<Felszereles> felszerelesek;
    private Anyag tarolo;
    private List<Virologus> virologusok;

    public Mezo(){
        szomszedok = new ArrayList<Mezo>();
        felszerelesek = new ArrayList<Felszereles>();
        virologusok = new ArrayList<Virologus>();
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
    };

    public void addMezo(Mezo mezo){
        szomszedok.add(mezo);
    }

    /**
     * Medvetanc agens hatása hívja meg, de csak raktárban nem üres, ahol elpusztítja az anyagot.
     */
    public void anyagElpusztit() {}


    public String info() {
        StringBuilder vir_lista = null;
        vir_lista.append(getVirologusok().get(0));
        Virologus v;
        for (int i = 1; i < getVirologusok().size(); i++) {
            v = getVirologusok().get(i);
            vir_lista.append(", "+v.getUserName());
        }
        vir_lista.append("\n");

        StringBuilder felsz_lista = null;
        felsz_lista.append(getFelszerelesek().get(0));
        Felszereles f;
        for (int i = 1; i < getVirologusok().size(); i++) {
            f = getFelszerelesek().get(i);
            felsz_lista.append(", "+f.toString()+" "+f.getLejarat());
        }
        felsz_lista.append("\n");

        String anyag = "Aminosav: "+getTarolo().getAminosav()+" Nukleotid: "+getTarolo().getNukleotid()+"\n";

        StringBuilder szomszed = null;
        szomszed.append(getFelszerelesek().get(0));
        Mezo sz;
        for (int i = 1; i < getSzomszedok().size(); i++) {
            sz = getSzomszedok().get(i);
            szomszed.append(", "+sz.toString());
        }
        szomszed.append("\n");

        for (int j = )
        return

    }
}
