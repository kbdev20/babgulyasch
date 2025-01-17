import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameManager implements Serializable, Leptetheto {
    /**
     * Csak tesztelés céljából (determinisztukus teszteléshez):
     */
    public static boolean rand = true;
    public static boolean kopeny_elfogad = false;

    private ArrayList<Mezo> mezok;
    private int soros;
    private int jatekosszam;
    private ArrayList<Virologus> virologusok;

    /**
     * get - set virologus
     *
     * @return visszaadja/beállítja a játékban lévő virológusok listáját
     */
    public ArrayList<Virologus> getVirologusok() {
        return virologusok;
    }
    public void setVirologusok(ArrayList<Virologus> virologusok) {
        this.virologusok = virologusok;
    }


    /**
     * get - set mezok lista
     * @return visszaadja/beállítja a játékban lévő mezők listáját
     */
    public ArrayList<Mezo> getMezok() {
        return mezok;
    }
    public void setMezok(ArrayList<Mezo> mezok) {
        this.mezok = mezok;
    }

    /**
     * set - set soros
     * mindig ez az indexu virologus van soron:
     * @return visszaadja/beállítva a játékban soron lévő virologus sorszámát
     */

    public int getSoros() {
        return soros;
    }
    public void setSoros(int soros) {
        this.soros = soros;
    }

    /**
     * GameMangaer konstruktora
     */
    public GameManager() {
        this.virologusok = new ArrayList<>();
        this.mezok = new ArrayList<>();
        soros = 0;
    }

    /**
     * Minden körben új játékost léptet.
     */
    public void lep() {
        // a soron kovetkezo virologus lep. Ha a virológusok lista végére értünk kezdjük elolrol.

        virologusok.get(soros).lep();
        do {
            ++soros;
            soros %= virologusok.size();
        } while (virologusok.get(soros).isHalott());
    }

    /**
     * * A játék elején legenerálja a pályát.
     * * Úgy függ a játékosok számától a pálya,hogy mindig 4x több mezőelem jön létre,
     * * mint ahány játékos játszik, de minimum 12.
     * * Úgy döntődik el, hogy milyen típusú mező jön létre, hogy a mezők listában
     * * létrejövő új mező indexének oszthatóságát vizsgáljuk, Így Üres mezőből
     * * keletkezik a legtöbb, aztán raktár, labor és város, legkevesebb pedig óvóhelyből.
     * @param jatekosok a játékban részt vevő játékosok száma
     */
    public void palya_generalas(int jatekosok) {
        //palya minimum mérete legyen 14:
        int meret = (jatekosok <= 3) ? 14 : jatekosok * 4;
        Random rando = new Random();

        mezok.add(new Ures());
        mezok.add(new Ures());
        for (int j = 2; j < meret; ++j) {
            int innen = rando.nextInt(mezok.size());
            int eddig = Integer.min(innen + rando.nextInt(mezok.size()), mezok.size() - 1) + 1;

            if (j % (rando.nextInt(6) + 1) == 0) {
                Mezo m = new Raktar();
                mezok.add(m);
                for (int k = innen; k < eddig; ++k) {
                    m.setSzomszedok(mezok.get(k));
                    mezok.get(k).setSzomszedok(m);
                }


            } else if (j % (rando.nextInt(8) + 1) == 0) {
                Mezo m1 = new Labor();
                mezok.add(m1);
                for (int k = innen; k < eddig; ++k) {
                    m1.setSzomszedok(mezok.get(k));
                    mezok.get(k).setSzomszedok(m1);
                }

            } else if (j % (rando.nextInt(6) + 1) == 0) {
                Mezo m2 = new Varos();
                mezok.add(m2);
                for (int k = innen; k < eddig; ++k) {
                    m2.setSzomszedok(mezok.get(k));
                    mezok.get(k).setSzomszedok(m2);

                }


            } else if (j % (rando.nextInt(6) + 1) == 0) {
                Mezo m = new Ovohely();
                mezok.add(m);
                for (int k = innen; k < eddig; ++k) {
                    m.setSzomszedok(mezok.get(k));
                    mezok.get(k).setSzomszedok(m);
                }


            } else {
                Mezo m = new Ures();
                mezok.add(m);
                for (int k = innen; k < eddig; ++k) {
                    m.setSzomszedok(mezok.get(k));
                    mezok.get(k).setSzomszedok(m);
                }

            }

        }
        //hogy tuti legyen négy különböző labor
        for (int i = 0; i < 4; ++i) {
            Kod kod;
            Mezo m1 = new Labor();
            switch (i) {
                case 0:
                    kod = new Amnezia_kod();
                    ((Labor) m1).setKod(kod);
                    break;
                case 1:
                    kod = new Benitottsag_kod();
                    ((Labor) m1).setKod(kod);
                    break;
                case 2:
                    kod = new Vedettseg_kod();
                    ((Labor) m1).setKod(kod);
                    break;
                case 3:
                    kod = new Vitustanc_kod();
                    ((Labor) m1).setKod(kod);
                    break;
                default:
                    break;
            }
            mezok.add(m1);
            for (int k = (rando.nextInt(mezok.size())); k < (Integer.min(rando.nextInt(mezok.size()) + rando.nextInt(mezok.size()), mezok.size() - 1) + 1); ++k) {
                m1.setSzomszedok(mezok.get(k));
                mezok.get(k).setSzomszedok(m1);
            }
        }
    }

    /**
     * A játékosoktól lekérdezi a pontjaikat.
     * @param v virologus, akinek le szeretnénk kérdezni a pontját
     * @return visszaadja a paraméterül kapott játékos pontszámát
     */

    public int getScore(Virologus v) {
        //IFio.print("GameManager.getScore() -> Score getter.");
        return v.getIsmert_hatasok().size();
    }

    /**
     * Ellenőrzi, hogy vége van-e a játéknak
     */
    public boolean checkEnd() {
        for (int i = 0; i < virologusok.size(); i++) {
            if (getScore(virologusok.get(i)) == 4)
                return true;
        }
        return false;
    }


    /**
     * új játékost ad hozzá a játékhoz
     * @param name a virologus neve, aki hozzá akarunk adni a játékhoz
     */
    public void addPlayer(String name) {
        jatekosszam += 1;
        Virologus vir = new Virologus(null, new Anyag(0,0));
        vir.setUserName(name);
        virologusok.add(vir);
    }

    /**
     * Elinditja a játékot a játékosokat elhelyezi a játéktéren.
     */
    public void startGame() {
        palya_generalas(jatekosszam);
        IFio.print("GameManager.startGame() -> Jatek inditasa.");
    }
    /**
     * Lekéri az objektum adatait
     */
    void observe(){}


    /**
     * Elhelyezi a virológusokat a pályán (random)
     */
    public void placeplayers() {
        Random r = new Random();
        for (Virologus v : getVirologusok()) {
            Mezo m = mezok.get(r.nextInt(mezok.size()-1));
            m.getVirologusok().add(v);
            v.setMezo(m);
        }
    }
}


