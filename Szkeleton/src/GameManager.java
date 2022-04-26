import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class GameManager implements Serializable, Leptetheto {
    /**
     * Csak tesztelés céljából (determinisztukus teszteléshez):
     */
    public static boolean rand = true;
    public static boolean kopeny_elfogad = false;


    private ArrayList<Virologus> virologusok;
    public ArrayList<Virologus> getVirologusok() { return virologusok; }

    private ArrayList<Mezo> mezok;
    private int soros;

    public ArrayList<Mezo> getMezok() {
        return mezok;
    }

    public void setSoros(int soros) {
        this.soros = soros;
    }

    /**
     * GameMangaer konstruktora
     */
    public GameManager() {
        this.virologusok  = new ArrayList<>();
        this.mezok = new ArrayList<>();
        soros = 0;

    }

    /**
     * Minden körben új játékost léptet.
     */
    public void lep() {
        //IFio.print("GameManager.lep() -> GameManeger leptetes.");
        checkEnd();
        // a soron kovetkezo virologus lep. Ha a virológusok lista végére értünk kezdjük elolrol.

        virologusok.get(soros).lep();
        if(virologusok.size()-1 == soros){
            soros = 0;
        }
        else {
            soros++;
        }
    }

    /**
     * A játék elején legenerálja a pályát.
     * Úgy függ a játékosok számától a pálya,hogy mindig 4x több mezőelem jön létre,
     * mint ahány játékos játszik, de minimum 12.
     * Úgy döntődik el, hogy milyen típusú mező jön létre, hogy a mezők listában
     * létrejövő új mező indexének oszthatóságát vizsgáljuk, Így Üres mezőből
     * keletkezik a legtöbb, aztán raktár, labor és város, legkevesebb pedig óvóhelyből.
     */
    public void palya_generalas(int jatekosok)
    {
        //palya minimum mérete legyen 12:
        int meret = (jatekosok <= 3) ? 12 : jatekosok*4;
        if(!GameManager.rand) { //Random ki van kapcsolva
            mezok = new ArrayList<>();

            for (int j = 0; j < meret; ++j) {
                if (j % 3 == 0) {
                    Mezo m = new Raktar();
                    mezok.add(m);
                    for (Mezo mezo : mezok) m.setSzomszedok(mezo);

                } else if (j % 2 == 0) {
                    Mezo m1 = new Labor();
                    mezok.add(m1);
                    for (Mezo mezo : mezok) m1.setSzomszedok(mezo);

                    Mezo m2 = new Varos();
                    mezok.add(m2);
                    for (Mezo mezo : mezok) m2.setSzomszedok(mezo);

                }
                else {
                    Mezo m = new Ures();
                    mezok.add(m);
                    for (Mezo mezo : mezok) m.setSzomszedok(mezo);
                }
                if (j % 5 == 0) {
                    Mezo m = new Ovohely();
                    mezok.add(m);
                    for (Mezo mezo : mezok) m.setSzomszedok(mezo);

                }
            }
        }else { //Random be van kapcsolva
            mezok = new ArrayList<>();
            Random rando = new Random();

            for (int j = 0; j < meret; ++j) {
                int innen = rando.nextInt(mezok.size());
                int eddig = Integer.min(innen+rando.nextInt(mezok.size()), mezok.size()-1)+1;

                if (j % (rando.nextInt(4)+1) == 0) {
                    Mezo m = new Raktar();
                    mezok.add(m);
                    for (int k = innen; k < eddig; ++k)
                        m.setSzomszedok(mezok.get(k));

                } else if (j % (rando.nextInt(4)+1) == 0) {
                    Mezo m1 = new Labor();
                    mezok.add(m1);
                    for (int k = innen; k < eddig; ++k)
                        m1.setSzomszedok(mezok.get(k));

                    Mezo m2 = new Varos();
                    mezok.add(m2);
                    for (int k = innen; k < eddig; ++k)
                        m2.setSzomszedok(mezok.get(k));

                } else if (j % (rando.nextInt(5)+1) == 0) {
                    Mezo m = new Ovohely();
                    mezok.add(m);
                    for (int k = innen; k < eddig; ++k)
                        m.setSzomszedok(mezok.get(k));

                } else {
                    Mezo m = new Ures();
                    mezok.add(m);
                    for (int k = innen; k < eddig; ++k)
                        m.setSzomszedok(mezok.get(k));
                }
            }
        }
    }

    /**
     * A játékosoktól lekérdezi a pontjaikat.
     */
    public int getScore(Virologus v) {
        //IFio.print("GameManager.getScore() -> Score getter.");
        return v.getIsmert_hatasok().size();
    }

    /**
     * Ellenőrzi, hogy vége van-e a játéknak
     */
    public void checkEnd() {
        for (Virologus virologus : virologusok)
            if (getScore(virologus) == 4)
                endGame();
    }

    /**
     * A játék végén hívódik meg.
     */
    public void endGame() {
        IFio.print("Játéknak vége van!");
        for (Virologus virologus : virologusok) {
            IFio.print(virologus.getUserName() + " " + getScore(virologus));
        }
    }

    /**
     * Elinditja a játékot a játékosokat elhelyezi a játéktéren.
     */
    public void startGame() {
        palya_generalas(getVirologusok().size());
        IFio.print("GameManager.startGame() -> Jatek inditasa.");
    }
}


