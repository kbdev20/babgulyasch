import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A virológus a karakter amit a játékos mozgat. Tárolja, hogy melyik mezőn
 * van éppen. Fel tud venni és le tud adni valamint lophat is védőfelszerelést és
 * anyagokat. Meg tud tanulni Hatásokat azaz kódokat. Minden körben lép. És le tud
 * bénulni ilyenkor nem mozog és a nála lévő anyagok elvehetőek.
 */
public class Virologus implements Serializable, Leptetheto {
    private Mezo mezo;
    private Anyag tarolo;
    private List<Kod> ismert_hatasok = new ArrayList<>();
    private List<Agens> rakenve = new ArrayList<>();
    private List<Agens> agens = new ArrayList<>();
    private List<Felszereles> felszereles = new ArrayList<>();
    private String userName;
    private boolean halott = false;
    private boolean benult = false;
    private int stepcount;
    private boolean moveable;

    //A virológus max anyag tárolási kapacitása (minden fajta anyagból külön-külön ugyanannyi) zsák kapacitását nem számolva:
    private int maxAnyag = 20; //Külön-külön limit a két anyagtípusra


    /**
     * Getterek-Setterek
     *
     * @return
     */
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Mezo getMezo() {return mezo; }
    public void setMezo(Mezo mezo) {this.mezo = mezo; }

    public Anyag getTarolo() {return tarolo; }
    public void setTarolo(Anyag tarolo) {this.tarolo = tarolo; }

    public int getMaxAnyag() {return maxAnyag; }
    public void setMaxAnyag(int maxAnyag) {this.maxAnyag = maxAnyag; }

    public boolean isBenult() {return benult;}
    public void setBenult(boolean benult) {this.benult = benult;}

    public List<Felszereles> getFelszereles() {return felszereles; }
    public List<Agens> getRakenve(){return rakenve;}

    public List<Agens> getAgens() {
        return agens;
    }

    public void setAgens(List<Agens> agens) {
        this.agens = agens;
    }

    public List<Kod> getIsmert_hatasok() { return ismert_hatasok; }
    public void setIsmert_hatasok(List<Kod> ismert_hatasok) { this.ismert_hatasok = ismert_hatasok; }

    public boolean isHalott() {return halott;}
    public void setHalott(boolean halott) {this.halott = halott;}
    public void  setrakenve(Agens a){ rakenve.add(a);}

    public Virologus () {};

    /**
     * Virológus osztály konstruktora.
     * @param mezo   Aktuális mező ahol a virológus tartózkodik.
     * @param tarolo A virológus által eltárolt anyag (nukleotid és aminosav).
     */

    public Virologus(Mezo mezo, Anyag tarolo) {
        this.mezo = mezo;
        this.tarolo = tarolo;
        stepcount = 0;
        moveable = true;
    }

    /**
     * A cél virológusra felkeni a kiválasztott ágenst.
     *
     * @param cel Virologus akit kennek.
     * @param a   Ágens, amivel kennek.
     */
    public void kenes(Virologus cel, Agens a) {
        agens.remove(a);

        if (a != null) {
            cel.megkenve(a);
        }
    }

    /**
     * Ha az adott virológust megkenik, akkor ez a függvény hívodik meg, és az adott ágens bekerül a "rakenve" listájába.
     *
     * @param a Az ágens, amivel kenték a virológust.
     */
    public void megkenve(Agens a) {
        boolean vaneilyen = false;
        for (int i = 0; i < rakenve.size(); i++) {if (rakenve.get(i).equals(a)) vaneilyen = true; return;}
        if (!vaneilyen) rakenve.add(a);
        //a.setVirologus(this);
        boolean vanfel = false;
        for (int i = 0; i < felszereles.size(); i++) {
            if (!getFelszereles().get(i).isAktiv()) {
                getFelszereles().get(i).felszerelesHatas(a.getVirologus(), this, a);
                 vanfel = true;
            }
        }

        if(!vanfel){
            a.hatas(this);
        }
        for (Agens r : rakenve) {
            if (r.toString() == "Vedettseg" ) {
                r.hatas(this);
            }
        }
        stepcount++;
        if(stepcount > 3){
            moveable =false;
        }


}

    /**
     * Ellenőrzi van-e baltája a virológusnak
     */
    public boolean hasBalta() {
        for (Felszereles f : getFelszereles()) {
            if (f.toString().equals("Balta")) return true;
        }
        return false;
    }


    /**
     * új kódot ismer meg a virológus
     *
     * @param k Letapogatott kód.
     */
    public void kod_hozzaad(Kod k) {
        ismert_hatasok.add(k);
    }

    /**
     * Új felszerelést vesz fel.
     *
     * @param f Felvett felszerelés.
     */
    public void felszereles_hozzaad(Felszereles f) {
        int idx = getFelszereles().size()-1;
        f.setVirologus(this);
        getFelszereles().add(f);
        //IFio.print("Virologus.felszereles_hozzaad() -> Uj felszereles hozzaad.");
    }

    /**
     * A virológus leadja a felszerelést.
     *
     * @param f Leadott felszerelés.
     */
    public void felszereles_leadas(Felszereles f) {
        //IFio.print("Virologus.felszereles_leadas() -> Felszereles leadva.");
        felszereles.remove(f);
        mezo.getFelszerelesek().add(f);
    }

    /**
     * A virológus új mezőre ugrik.
     *
     * @param m Az a mező, amelyre a virológust áthelyezzük.
     */
    public void mozgas(Mezo m) {
        //IFio.print("Virologus.mozgas() -> Virologus elmozdul");
        mezo.eltavolit(this);
        m.elfogad(this);
        mezo = m;
        if (m.toString().equals("Labor")) kod_megkap(((Labor) m).getKod());
        anyag_felvesz();
        felszereles_felvesz();
        stepcount++;
        if(stepcount > 3){
            moveable =false;
        }
    }

    /**
     * A léptethető függvénye, minden körben lép a virológus/körönként meghívjuk.
     */
    public void lep() {
        felszereles_felvesz();
        anyag_felvesz();
        for (int i = 0; i < rakenve.size(); i++) {
            rakenve.get(i).lep();
        }
        for (int i = 0; i < rakenve.size(); i++) {
            rakenve.get(i).hatas(this);
        }
        stepcount = 0;
        moveable = true;

    }


    /**
     * Felvesz anyagot a mezőről (amennyit tud)
     * Minden lépéskor meghívódik
     */
    public void felszereles_felvesz(){
        boolean benne = false;
        if(mezo.getFelszerelesek() != null){
            for(int i = 0; i < mezo.getFelszerelesek().size(); i++){
                Felszereles f = mezo.getFelszerelesek().get(i);
                benne = false;
                for(Felszereles f1: felszereles){
                    if(f1.toString().equals(f.toString())){
                        benne = true;
                    }
                }
                if(!benne){
                    felszereles_hozzaad(f);
                    f.setVirologus(this);
                    mezo.getFelszerelesek().remove(f);
                }
            }
        }
    }
    public void anyag_felvesz() {
        Anyag mennyi_fer = new Anyag(maxAnyag-getTarolo().getNukleotid(),maxAnyag-getTarolo().getAminosav());
        int nukfelvesz = Integer.min(getMezo().getTarolo().getNukleotid(), mennyi_fer.getNukleotid());
        int aminofelvesz = Integer.min(getMezo().getTarolo().getAminosav(), mennyi_fer.getAminosav());
        Anyag ujanyag = new Anyag(nukfelvesz, aminofelvesz);
        getTarolo().increase(ujanyag);
        getMezo().getTarolo().decrease(ujanyag);
    }


    /**
     * Visszaadja, hogy megvehető-e az ágens.
     * * @param k Kód, amelynek az árát ellenőrizzük.
     * * @return Megvehető-e az ágens (true/false).
     */
    public boolean check_ar(Kod k) {
        if (tarolo.getAminosav() >= k.getAr().getAminosav() && tarolo.getNukleotid() >= k.getAr().getAminosav()) {
            return true;
        }
        return false;
    }

    /**
     * Ismert kódok (ismert_hatasok lista) alapján lérehozunk egy ágenst, a megfelelő anyagmennyiséget felhasználva.
     *
     * @param k Kód, amelyből ágenst szeretnénk lélrehozni.
     */
    public void agens_letrehoz(Kod k) {
        if(check_ar(k)) {
            Anyag ujTarolo = new Anyag(tarolo.getNukleotid()-k.getAr().getNukleotid(),
                    tarolo.getAminosav()-k.getAr().getAminosav());
            agens.add(k.letrehoz());
            setTarolo(ujTarolo);
        }
        else{
            IFio.print("Nincs eleg anyag az agens elkesziteseshez.");
        }
    }

    /**
     * Egy adott virológus és zsákja anyagtároló kapacitása szerint a lehető maximális anyagmennyiséggel megtölti annak tárolóit,
     * a saját anyagmennyiséget annyival csökkentve (bénultság esetén lopásnál).
     *
     * @param v Virológus, aki megkapja az anyagot.
     */
    public void megtolt_tarolo(Virologus v) {
    }

    /**
     * Átállítja halottra az áldozat virológust.
     *
     * @param v
     */
    public void megol(Virologus v) {
        v.setHalott(true);
        for (int i = 0; i < v.getFelszereles().size(); i++) {
            v.felszereles_leadas(v.getFelszereles().get(i));
        }
        v.getIsmert_hatasok().clear();
        v.getAgens().clear();
        v.getMezo().getTarolo().increase(v.getTarolo());
        v.setTarolo(new Anyag(0, 0));
        v.getMezo().eltavolit(v);
        v.setMezo(null);

        //TODO: GameManager feladatai ezzel kapcsolatban: Elmenti az áldozat virológus pontszámát (játék végére), majd törli a játékosok listájából
    }

    /**
     * Hozzáadja a kapott kódot a virológus ismert listájába, de ellenőrzi, hogy már megvan-e
     * @param k A megadott kód amit a virológus a laborban talált
     */
    public void kod_megkap(Kod k) {
        if (k == null) return;
        for (Kod temp : getIsmert_hatasok()) {
            if (temp.toString().equals(k.toString())) return;
        }
        getIsmert_hatasok().add(k);
    }

    @Override
    public String toString() {
        //Main.gm.getMezok() csak a prototípus tesztelésének céljából, kész grafikus programban nem lesz benne
        String mezo=null;
        if(getMezo()!=null) {
             mezo = "Mezo: " + (Main.gm.getMezok().indexOf(getMezo())+1) + ", " + getMezo() + "\n";
        }else mezo ="Mezo: - \n";

        String anyag;
        if(getTarolo().getAminosav()>0||getTarolo().getNukleotid()>0) {
             anyag = "Tarolo: aminosav: " + getTarolo().getAminosav() + " nukleotid: " + getTarolo().getNukleotid() + "\n";
        }else anyag ="Tarolo: - \n";

        StringBuilder hatas_lista = new StringBuilder();
        hatas_lista.append("Kodok: ");
        if (getIsmert_hatasok().size() > 0) {
            hatas_lista.append(getIsmert_hatasok().get(0));
            Kod k;
            for (int i = 1; i < getIsmert_hatasok().size(); i++) {
                k = getIsmert_hatasok().get(i);
                hatas_lista.append(", " + k.toString());
            }
            hatas_lista.append("\n");
        }else hatas_lista.append("-\n");

            StringBuilder kenve_lista = new StringBuilder();
                 kenve_lista.append("Rakenve: ");
            if (getRakenve().size() > 0) {
                kenve_lista.append(getRakenve().get(0));
                Agens a;
                for (int i = 1; i<getRakenve().size(); i++ ) {
                    if(getRakenve().get(i).getKenve()) {
                        kenve_lista.append(", " + getRakenve().get(i).toString());
                    }
                }
                kenve_lista.append("\n");
            }else kenve_lista.append("-\n");


            StringBuilder agens_lista = new StringBuilder();
                agens_lista.append("Agensek: ");
            if (getAgens().size() > 0) {
                agens_lista.append(getAgens().get(0));
                Agens a;
                for (int i = 1; i < getAgens().size(); i++) {
                    a = getAgens().get(i);
                    agens_lista.append(", " + a.toString());
                }
                agens_lista.append("\n");
            }else agens_lista.append("-\n");

            StringBuilder felsz_lista = new StringBuilder();
                 felsz_lista.append("Felszerelések: ");
            if (getFelszereles().size() > 0) {
                if(getFelszereles().get(0).getLejarat()>0) felsz_lista.append(getFelszereles().get(0)+ " (" + getFelszereles().get(0).getLejarat()+ " hasznalat)");
                else felsz_lista.append(getFelszereles().get(0));
                Felszereles f;
                for (int i = 1; i < getFelszereles().size(); i++) {
                    f = getFelszereles().get(i);
                    if(f.getLejarat()>0)
                    felsz_lista.append(", " + f.toString() + " (" + f.getLejarat()+ " hasznalat)");
                    else felsz_lista.append(", " + f.toString());
                }
                felsz_lista.append("\n");
            }else felsz_lista.append("-\n");

            String usernam = "Felhasznalonev: " + getUserName() + " \n";
            String halott = "Halott: " + isHalott() + "\n";
            return usernam +mezo + anyag + felsz_lista.toString() + hatas_lista.toString() + agens_lista.toString() + kenve_lista.toString()  + halott;
        }
    /**
     * Lekéri az objektum adatait
     */
    void observe(){}

    public int getStepcount() {
        return stepcount;
    }

    public void setStepcount(int stepcount) {
        this.stepcount = stepcount;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
}



