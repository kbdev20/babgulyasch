//import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;

public class Leironyelv {
    //minden parancsra egy fv.

    /**
     * Létrehozható ezzel a paranccsal új virológus,
     * vagy egy adott típusú mező - város, labor, üres, óvóhely, raktár.
     *
     */
    public void create(String parameter) {
        switch (parameter) {
            case "varos":
                Varos v = new Varos();
                Main.gm.getMezok().add(v);
                break;
            case "ures":
                Ures u = new Ures();
                Main.gm.getMezok().add(u);
                break;
            case "labor":
                Labor l = new Labor();
                Main.gm.getMezok().add(l);
                break;
            case "raktar":
                Raktar r = new Raktar();
                Main.gm.getMezok().add(r);
                break;
            case "ovohely":
                Ovohely ov = new Ovohely();
                Main.gm.getMezok().add(ov);
                break;
            default:
                //Ellenőrizzük, hogy van-e már virológus ilyen néven:
                for (Virologus temp_vir : Main.gm.getVirologusok()) {
                    if (temp_vir.getUserName().equals(parameter)) {
                        IFio.print("Hiba: Ilyen nevű virológus már létezik");
                        return;
                    }
                }
                Virologus vi = new Virologus(null, new Anyag(0, 0));
                vi.setUserName(parameter);
                Main.gm.getVirologusok().add(vi);
                if (!Main.gm.getMezok().isEmpty()) {
                    Mezo m = Main.gm.getMezok().get(0);
                    vi.setMezo(m);
                    m.getVirologusok().add(vi);
                }
                break;
        }
    }

    /**
     * Információ kérhető egy adott parancsról, vagy kilistázható az összes parancs.
     *
     */
    public void help(String parameter) {
        switch (parameter) {
            case "create":
                IFio.print("Létrehozható ezzel a paranccsal új virológus, vagy egy adott típusú mezõ - város, labor, üres, óvóhely, raktár.");
                IFio.print("\nParaméterei: Create <virológus_neve> vagy Create <varos/ures/labor/raktar/ovohely>");
                break;
            case "help":
                IFio.print(" Információ kérhetõ egy adott parancsról, vagy kilistázható az összes parancs.");
                IFio.print("\nHelp [parancs_neve] - ahol a paraméter csak opcionális!");
                break;
            case "info":
                IFio.print("Elsõ paraméterként megadhatunk egy típust a második paraméterként a megfelelõ objektum nevét, ezután a program kilistázza az objektum információit.");
                IFio.print("\nInfo <Objektum_típusa> <Objektum_neve>");
                break;

            case "put":
                IFio.print(" Elhelyezi (“elteleportálja”) a megadott virológust egy adott mezõre, mely bárhol lehet a pályán, így a játék szokványos mûködésének részét képezõ Move parancsot megkerülve. Ezért nem a mozgás tesztelésére van (lásd: Move),\n hanem a teszthez szükséges állapot gyors létrehozására. ");
                IFio.print("\nParaméterei: Put <virologus_neve> <mezo_azonosito>");
                break;
            case "connect":
                IFio.print("Két mezõt összekapcsol, szomszédossá teszi õket.");
                IFio.print("\nParaméterei:Connect <mezõ_azonosító1> <mezõ_azonosító2>");
                break;
            case "list":
                IFio.print("Kilistázza a játékban szereplõ mezõket vagy virológusokat a megadott paraméterne megfelelõen.");
                IFio.print("\nParaméterei: List <mezok> vagy List <virologus> ");
                break;
            case "load":
                IFio.print(" Fájlbol betölt egy korábbi játék állást.");
                IFio.print("\nParaméterei: Load <fajlnev>");
                break;
            case "save":
                IFio.print("Fájlba ment egy aktuális játék állást.");
                IFio.print("\nParaméterei:Save <fajlnev>");
                break;
            case "move":
                IFio.print("Virológus mozog egy szomszédos mezõre. Error-t kell dobnia ha például a virológus bénult, vagy az adott mezõ nem szomszédos.");
                IFio.print("\nParaméterei: Move <virologus> <mezo>");
                break;
            case "print_to_file":
                IFio.print("on paraméter esetén kiírja a fájlba a konzol kimenetét, off esetén nem írja ki fájlba.");
                IFio.print("\nParaméterei:Print_to_file <on/off>");
                break;
            case "give":
                IFio.print("A megadott virológushoz adhat anyagot vagy felszerelést vagy ágenst a meghatározott mennyiségben.");
                IFio.print("\nParaméterei:Give <vir>  <nukleotid>/<aminosav>/<felszereles>/<agens>/<kod> <anyag_mennyiseg>/<felszereles_neve>/<agens_neve>/<kod_neve>\n");
                IFio.print("\nLehetséges értékei: Agens_neve vagy  kod_neve paraméterek lehetséges értékei: \nAmnezia \nBenitottsag \nVitustanc \nVedettseg \nMedvetanc (csak mint ágens, nem kód!)" +
                        " \nFelszereles_neve paraméter lehetséges értékei: \nKesztyu \nVedokopeny \nZsak \nBalta ");
                break;
            case "step":
                IFio.print(" Léptet egy megadott virológust (meghívva a lép() függvényét) vagy a játékban a soron következõ virológust lépteti, ha nincs megadva paraméter.");
                IFio.print("\nParaméterei:Step <Virologus> - paraméter csak opcionális.");
                break;
            case "generate_map":
                IFio.print("Ha a random ki van kapcsolva: A megadott játékos szám függvényében létrehoz egy pályát, ahol létrehoz adott arányban raktárat, labort, várost, óvóhelyet és a maradék mezõk pedig üres mezõk. Minden mezõt mindegyikkel összeköti. A megadott paraméter függvényében determinisztikusan hozza létre a pályát.\n" +
                        "\tHa a random be van kapcsolva: \n" +
                        "A megadott játékos szám függvényében véletlenszerûen generálja a mezõket és a mezõk egymással való szomszédságát.\n");
                IFio.print("\nParaméterei:Generate_map <int: size>");
                break;
            case "smear_virus":
                IFio.print(" Egy virológus megken egy másik virológust, vagy önmagát egy adott ágenssel.");
                IFio.print("\nParaméterei:Smear_virus <virologus_aki_ken> <virologus2> <agens_neve>");
                IFio.print("\nLehetséges értékei: Agens_neve paraméterek lehetséges értékei: \nAmnezia \nBenitottsag \nVitustanc \nVedettseg \nMedvetanc ");
                break;
            case "kill":
                IFio.print("Kettõ paramétere van, mindkettõ paraméterként egy-egy virológus nevet vár. Az elsõ virológus megöli a másikat baltával. ");
                IFio.print("\nParaméterei:Kill <vir1_name> <victim_name>");
                break;
            case "make_agent":
                IFio.print("Ágenst hoz létre egy adott virológus, ha ismeri a hozzá szükséges kódot és van hozzá elegendõ anyaga.");
                IFio.print("\nParaméterei:Make_agent <virologus> <agens>");
                break;
            case "random":
                IFio.print("Ki- és bekapcsolja, hogy a program random eseményeket tényleg véletlenszerû kimenetellel hajtson végre, vagy determinisztikusan minden teszteléskor ugyanazt a kimenetelt kapjuk.");
                IFio.print("\nParaméterei: Random <on/off>");
                break;
            case "clear":
                IFio.print(" Egy mezõ összes törölhetõ rajta tárolt objektumát töröljük.");
                IFio.print("\nParaméterei:Clear <mezõ_azonosító>");
                break;
            case "defensivecoat":
                IFio.print("A védõköpeny mûködése determinisztikusan állítható, hogy mindig védjen, vagy semmiképp se védje ki a másik virológus általi ágens kenést. \nEz a parancs csak akkor releváns, ha a Random off van beállítva (determinisztikus tesztelés esetén).");
                IFio.print("\nParaméterei:DefensiveCoat <on/off>");
                break;
            case "test_mode":
                IFio.print("Egy speciális parancs, amellyel a program automatikus tesztelés üzemmódba helyezhetõ");
                break;
            case "set":
                IFio.print("Mezõkre helyez felszerelést, anyagot, vagy kódot (csak laboroknál). \nHa az adott kategóriájú objektumból már van egy az adott mezõn, akkor lecseréli azt. Ha például nem laborra akarunk kódot elhelyezni akkor hibát ad vissza.");
                IFio.print("\nParaméterei:Set <mezõ_azonosítója> <típus> [objektum_neve] [mennyiség]");
                IFio.print("\nA <mezõ_azonosítója> paraméter a List mezok parancs által visszaadott kódok lehetnek.\n" +
                        "A <típus> paraméter lehetséges értékei:\n" +
                        "felszereles\n" +
                        "kod\n" +
                        "anyag\n" +
                        "medvetanc\n");
                break;
            default:
                IFio.print("Parancsok:\ncreate\nhelp\ninfo\nput\nconnect\nlist\nload\nsave\nmove\nprint_to_file\ngive\nstep\ngenerate_map\nsmear_virus\nkill\nmake_agent\nrandom\nclear\ndefensivecoat\ntest_mode\nTöbb információ: help <parancs_neve>\n");
                break;
        }
    }

    /**
     * Első paraméterként megadhatunk egy típust a második paraméterként a megfelelő objektum nevét,
     * ezután a program kilistázza az objektum információit.
     *
     */
    public void info(String parameter) {
        String[] array = parameter.split(" ");
        if (array.length < 2) {
            invalid_param();
            return;
        }

        if (array[0].equals("virologus")) {
            for (Virologus vir : Main.gm.getVirologusok()) {
                if (array[1].equals(vir.getUserName())) {
                    IFio.print(vir.toString());
                }
            }
        } else if (array[0].equals("mezo")) {
            for (int i = 0; i < Main.gm.getMezok().size(); i++) {
                if (Integer.parseInt(array[1]) == (i + 1)) {
                    IFio.print(Main.gm.getMezok().get(i).toString());
                    IFio.print(Main.gm.getMezok().get(i).info());
                }
            }
        } else {
            invalid_param();
        }
    }

    /**
     * Elhelyezi (“elteleportálja”) a megadott virológust egy adott mezőre, mely bárhol lehet a pályán, így a játék szokványos működésének részét képező
     * Move parancsot megkerülve. Ezért nem a mozgás tesztelésére van (lásd: Move), hanem a teszthez szükséges állapot gyors létrehozására.
     *
     */
    public void put(String parameter) {
        String[] array = parameter.split(" ");

        int idx = Integer.parseInt(array[1]) - 1;
        Mezo m = Main.gm.getMezok().get(idx);


        for (Virologus vir : Main.gm.getVirologusok()) {
            if (array[0].equals(vir.getUserName())) {
                vir.setMezo(m);
                Mezo regi = vir.getMezo();
                if (regi != null) regi.getVirologusok().remove(vir);
                m.getVirologusok().add(vir);
            }
        }
    }

    /**
     * Két mezőt összekapcsol, szomszédossá teszi őket.
     *
     */
    public void connect(String parameter) {
        String[] array = parameter.split(" ");
        Mezo m1 = Main.gm.getMezok().get(Integer.parseInt(array[0]) - 1);
        Mezo m2 = Main.gm.getMezok().get(Integer.parseInt(array[1]) - 1);
        m1.getSzomszedok().add(m2);
        m2.getSzomszedok().add(m1);
    }

    /**
     * Kilistázza a játékban szereplő
     * mezőket vagy virológusokat a megadott paraméterne megfelelően.
     *
     */
    public void list(String parameter) {
        switch (parameter) {
            case "virologus":
                for (Virologus vir : Main.gm.getVirologusok()) {
                    IFio.print(vir.getUserName());
                }
                break;
            case "mezok":
                int i = 1;
                for (Mezo m : Main.gm.getMezok()) {
                    IFio.print(i + " " + m.toString());
                    i++;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Fájlbol betölt egy korábbi játék állást.
     *
     */
    public void load(String parameter) {
        try (
                FileInputStream file = new FileInputStream(parameter);
                ObjectInputStream in = new ObjectInputStream(file)
        ) {
            Main.gm = (GameManager) in.readObject();
        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * Fájlba ment egy aktuális játék állást.
     *
     */
    public void save(String parameter) {
            try
                    (
                            FileOutputStream file = new FileOutputStream(parameter);
                            ObjectOutputStream out = new ObjectOutputStream(file)
                    ) {
                out.writeObject(Main.gm);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Virológus mozog egy szomszédos mezőre.
     * Error-t kell dobnia ha például a virológus bénult, vagy az adott mező nem szomszédos.
     *
     */
    public void move(String parameter) {
        String[] array = parameter.split(" ");
        Virologus vir = null;
        boolean benult = false;
        for (Virologus v : Main.gm.getVirologusok()) {
            String name = v.getUserName();
            if (array[0].equals(name)) {
                vir = v;

                //ellenorizzük, hogy a virológus bénult-e:
                for (Agens a : vir.getRakenve()) {
                    if (a.toString().equals("Benitottsag") && a.getKenve()) benult = true;
                }
            }
        }

        if (vir == null) {
            IFio.print("A megadott virológus nem létezik");
        } else if (benult) {
            IFio.print("Az adott virológus bénult, ezért nem képes mozogni");
        } else if (vir.getMezo().getSzomszedok().contains(Main.gm.getMezok().get(Integer.parseInt(array[1])-1))) {
            vir.mozgas(Main.gm.getMezok().get(Integer.parseInt(array[1])-1));
        } else {
            IFio.print("Az adott mező a virológus mezőjével nem szomszédos");
        }
    }

    /**
     * on paraméter esetén kiírja a fájlba a konzol kimenetét,
     * off esetén nem írja ki fájlba.
     *
     */
    public void print_to_file(String parameter) {
        switch (parameter) {
            case "on":
                IFio.printtofile = true;
                break;
            case "off":
                IFio.printtofile = false;
                break;
            default:
                invalid_param();
                break;
        }
    }

    /**
     * A megadott virológushoz adhat anyagot vagy
     * felszerelést vagy ágenst a meghatározott mennyiségben.
     *
     */
    public void give(String parameter) {
        String[] array = parameter.split(" ");
        Virologus vir = virologust_keres(array[0]);

        //mivel az agens és kod nevei ugyanazok a segéd fuggvénynél ezzel nézi meg hogy melyiket kell adni neki
        switch (array[1]) {
            case "nukleotid":
                if (vir != null) {
                    vir.getTarolo().increase(new Anyag(Integer.parseInt(array[2]), 0));
                }
                break;
            case "aminosav":
                if (vir != null) {
                    vir.getTarolo().increase(new Anyag(0, Integer.parseInt(array[2])));
                }
                break;
            case "felszereles":
                give_seged_fel(array[2].toLowerCase(), vir); //segéd fg
                break;
            case "agens":
                give_seged_agens(array[2].toLowerCase(), false, vir); //seged fg
                break;
            case "kod":
                give_seged_agens(array[2].toLowerCase(), true, vir);
                break;
        }
    }

    /**
     * Segéd függvény a Give fg-hez
     */
    public void give_seged_fel(String param, Virologus vir) {
        switch (param) {
            case "kesztyu":
                vir.felszereles_hozzaad(new Kesztyu());
                break;
            case "vedokopeny":
                vir.felszereles_hozzaad(new Vedokopeny());
                break;
            case "zsak":
                vir.felszereles_hozzaad(new Zsak());
                break;
            case "balta":
                vir.felszereles_hozzaad(new Balta());
                break;
        }
    }

    /**
     * Segés függvény a Give fg-hez
     *
     */
    public void give_seged_agens(String param, boolean kod, Virologus vir) {
        switch (param) {
            case "amnezia":
                if (kod) {
                    vir.kod_hozzaad(new Amnezia_kod(vir));
                } else {
                    vir.getAgens().add(new Amnezia(vir));
                }
                break;
            case "benitottsag":
                if (kod) {
                    vir.kod_hozzaad(new Benitottsag_kod(vir));
                } else {
                    vir.getAgens().add(new Benitottsag(vir));
                }
                break;
            case "vitustanc":
                if (kod) {
                    vir.kod_hozzaad(new Vitustanc_kod(vir));
                } else {
                    vir.getAgens().add(new Vitustanc(vir));
                }
                break;
            case "vedettseg":
                if (kod) {
                    vir.kod_hozzaad(new Vedettseg_kod(vir));
                } else {
                    vir.getAgens().add(new Vedettseg(vir));
                }
                break;
            case "medvetanc":
                if (kod) {
                    IFio.print("Hiba: Medvetánc kód nem létezik");
                } else {
                    vir.getAgens().add(new Medvetanc());
                }
        }
    }

    /**
     * Léptet egy megadott virológust (meghívva a lép() függvényét) vagy a játékban
     * a soron következő virológust lépteti, ha nincs megadva paraméter.
     *
     */
    public void step(String parameter) {
        if (parameter.equals("")) {
            Main.gm.lep();
        } else {
            Virologus v1 = virologust_keres(parameter);
            if (v1 == null) IFio.print("Hiba: Ismeretlen virológus");
            else v1.lep();
            Main.gm.setSoros(Main.gm.getVirologusok().indexOf(v1));
            Main.gm.checkEnd();
        }
    }

    /**
     * Ha a random ki van kapcsolva: A megadott játékos szám függvényében létrehoz egy pályát, ahol létrehoz adott arányban raktárat, labort, várost,
     * óvóhelyet és a maradék mezők pedig üres mezők. Minden mezőt mindegyikkel összeköti. A megadott
     * paraméter függvényében determinisztikusan hozza létre a pályát.
     * Ha a random be van kapcsolva:
     * A megadott játékos szám függvényében véletlenszerűen generálja a mezőket és a mezők egymással való szomszédságát.
     *
     */
    public void generate_map(String parameter) {
        Main.gm.palya_generalas(Integer.parseInt(parameter));
    }

    /**
     * Egy virológus megken egy másik virológust,
     * vagy önmagát egy adott ágenssel.
     *
     */
    public void smear_virus(String parameter) {
        String[] array = parameter.split(" ");
        Virologus v1 = virologust_keres(array[0]);
        Virologus v2 = virologust_keres(array[1]);

        if (v1 == null || v2 == null) {
            IFio.print("Hiba: Ismeretlen virológus");
            return;
        }
        //v1 keni v2-t, agens ellenorzese:
        Agens a = null;
        //
        for (Agens temp_agens : v1.getAgens()) {
            switch(temp_agens.toString().toLowerCase()){
                case "vitustanc":
                case "vedettseg":
                case "benitottsag":
                case "amnezia":
                    a = temp_agens;
                    break;
                default: break;
            }
        }

        if (a == null) IFio.print("Hiba: A megadott virológus nem rendelkezik a szükséges ágenssel");
        else {
            v1.kenes(v2, a);
        }
    }

    /**
     * Kettő paramétere van, mindkettő paraméterként egy-egy virológus nevet vár.
     * Az első virológus megöli a másikat baltával.
     *
     */
    public void kill(String parameter) {
        String[] array = parameter.split(" ");

        Virologus v1 = virologust_keres(array[0]);
        Virologus v2 = virologust_keres(array[1]);
        Balta b = null;
        if (v1 != null) {
            if (v2 != null) {
                if (v1.getMezo() == v2.getMezo()) {
                    for (int i = 0; i  < v1.getFelszereles().size(); i ++) {
                        if (v1.getFelszereles().get(i).toString().equals("Balta")) {
                            b = (Balta) v1.getFelszereles().get(i);
                            b.felszerelesHatas(v1, v2, null);
                        }
                    }
                } else {
                    System.out.print("Hiba: Nem egy mezőn állnak!");
                }
            }
        }
        if (b == null){
            System.out.print("Nincs baltája!");
        }
    }

    /**
     * Ágenst hoz létre egy adott virológus, ha ismeri a hozzá szükséges kódot és van
     * hozzá elegendő anyaga.
     *
     */
    public void make_agent(String parameter) {
        String[] array = parameter.split(" ");
        for (Virologus v : Main.gm.getVirologusok()) {
            String name = v.getUserName();
            if (array[0].equals(name)) {
                for (int j = 0; j < v.getIsmert_hatasok().size(); j++) {
                    Kod temp_hatas = v.getIsmert_hatasok().get(j);
                    if (array[1].equals(temp_hatas.toString().toLowerCase())) {
                        v.agens_letrehoz(temp_hatas);
                        return;
                    }
                }
                IFio.print("A virológus nem ismeri a szükséges kódot");
            }
        }
    }

    /**
     * Ki- és bekapcsolja, hogy a program random eseményeket
     * tényleg véletlenszerű kimenetellel hajtson végre, vagy determinisztikusan minden teszteléskor ugyanazt akimenetelt kapjuk.
     *
     */
    public void random(String parameter) {
        switch (parameter) {
            case "on":
                GameManager.rand = true;
                break;
            case "off":
                GameManager.rand = false;
                break;
            default:
                invalid_param();
                break;
        }
    }

    /**
     * Egy mező összes törölhető rajta tárolt objektumát töröljük.
     *
     */
    public void clear(String parameter) {
        Mezo m = Main.gm.getMezok().get(Integer.parseInt(parameter) - 1);
        m.getFelszerelesek().clear();
        m.setTarolo(new Anyag(0, 0));
        if (m.toString().equals("Labor")) {
            ((Labor) m).setM(null);
            ((Labor) m).setKod(null);
        }
    }

    /**
     * A védőköpeny működése determinisztikusan állítható, hogy mindig védjen, vagy semmiképp se védje ki a másik virológus általi ágens kenést.
     * Ez a parancs csak akkor releváns, ha a Random off van beállítva(determinisztikus tesztelés esetén).
     *
     */
    public void defensivecoat(String parameter) {
        switch (parameter) {
            case "on":
                GameManager.kopeny_elfogad = true;
                break;
            case "off":
                GameManager.kopeny_elfogad = false;
                break;
            default:
                invalid_param();
                break;
        }
    }

    public void test_mode() {
        Scanner sc = new Scanner(System.in);
        IFio.print("Teszt üzemmód - fájlból való input és output összehasonlítására");
        String fajlbe, fajlki;

        //Ez a változó menti el majd a tesztelő által beállított fájlba írási beállításokat:
        while (true) {
            IFio.print("Adja meg a bemeneti fájl nevét: ");
            fajlbe = sc.nextLine();
            if (fajlbe.isEmpty()) break;
            IFio.print("Adja meg az elvárt kimenetet taralmazó fájl nevét: ");
            fajlki = sc.nextLine();
            if (fajlki.isEmpty()) break;
            String sorokbe = read_test_file(fajlbe);
            String sorokki = read_test_file(fajlki);

            String[] sorok = sorokbe.split("\n");

            //Töröljük az eddig elmentett outputot
            IFio.clearbuffer();
            IFio.savetobuffer = true;
            for (String sor : sorok) {
                minimain(sor.trim());
            }
            IFio.savetobuffer = false;
            if (sorokki.replaceAll("\\s","").equalsIgnoreCase(
                    IFio.readbuffer().replaceAll("\\s",""))) {
                IFio.print("A teszt kimenete megegyezik az elvárt kimenettel");
            }
            else {
                IFio.print("A teszt kimenete NEM egyezik meg az elvárt kimenettel");
            }
            Main.gm = new GameManager();
        }
        IFio.print("Fájlból tesztelés vége.");
    }

    private String read_test_file(String fajlnev) {
        StringBuilder data = new StringBuilder();
        try {
            File myObj = new File("../tesztek/"+fajlnev);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                data.append(myReader.nextLine()).append("\n");
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            IFio.print("Hiba: "+"\""+fajlnev+"\""+" file not found");
            //e.printStackTrace();
        }
        return data.toString();
    }

    private void minimain(String sor) {
            String[] bemenet = sor.toLowerCase().split(" ", 2);
            String parancs = bemenet[0];
            String parameter = "";
            if (bemenet.length > 1) parameter = bemenet[1];

            command_switch(parancs, parameter);
    }


    /**
     * Mezőkre helyez felszerelést, anyagot, vagy kódot (csak laboroknál).
     * Ha az adott kategóriájú objektumból már van egy az adott mezőn, akkor lecseréli azt. Ha például nem laborra akarunk kódo elhelyezni akkor hibát ad vissza.
     *
     */
    public void set(String parameter) {
        String[] array = parameter.split(" ");
        int idx = Integer.parseInt(array[0]) - 1;
        Mezo m = Main.gm.getMezok().get(idx);
        boolean labor_e = m.toString().equals("Labor");
        try {
            switch (array[1]) {
                case "felszereles":
                    set_felszerel(array[2], idx);
                    break;
                case "kod":
                    if (labor_e) {
                        Labor l = (Labor) m;
                        set_kod(array[2], l);
                    }
                    else {
                        IFio.print("Hiba: Csak laborra lehet kódot helyezni");
                    }
                    break;
                case "anyag":
                    set_anyag(array[2], array[3], idx);
                    break;
                case "medvetanc":
                    if (labor_e) ((Labor) m).setM(new Medvetanc());
                    else IFio.print("Hiba: Csak laborra lehet medvetáncot helyezni");
                    break;
            }
        } catch (Exception e) {
            IFio.print("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Seged függvény a set fg-hez
     *
     */
    private void set_anyag(String param, String mennyiseg, int index) {
        switch (param) {
            case "nukleotid":
                Main.gm.getMezok().get(index).setTarolo(new Anyag(Integer.parseInt(mennyiseg), Main.gm.getMezok().get(index).getTarolo().getAminosav()));
                break;
            case "aminosav":
                Main.gm.getMezok().get(index).setTarolo(new Anyag(Main.gm.getMezok().get(index).getTarolo().getNukleotid(), Integer.parseInt(mennyiseg)));
                break;
        }
    }

    /**
     * Seged függvény a set fg-hez
     */
    private void set_kod(String param, Labor m) {
        switch (param) {
            case "amnezia":
                Amnezia_kod a = new Amnezia_kod();
                m.setKod(a);
                break;
            case "benitottsag":
                m.setKod(new Benitottsag_kod());
                break;
            case "vitustanc":
                m.setKod(new Vitustanc_kod());
                break;
            case "vedettseg":
                m.setKod(new Vedettseg_kod());
                break;
            default: break;
        }
    }

    /**
     * Seged függvény a set fg-hez
     */
    private void set_felszerel(String param, int index) {
        Mezo m = Main.gm.getMezok().get(index);
        switch (param) {
            case "kesztyu":
                m.setFelszerelesek(new Kesztyu());
                break;
            case "vedokopeny":
                m.setFelszerelesek(new Vedokopeny());
                break;
            case "zsak":
                m.setFelszerelesek(new Zsak());
                break;
            case "balta":
                m.setFelszerelesek(new Balta());
                break;
        }
    }

    /**
     *
     * Seged fuggvény , ha invalid paraméter kapott valahol ez a függvény hívodik meg
     */
    private void invalid_param() {
        IFio.print("Hiba: Rossz a megadott paraméter");
    }

    /**
     * Segéd függvény meg keresük a név alapján megadott virologust a listából
     *
     *
     */
    private Virologus virologust_keres(String nev) {
        for (Virologus v : Main.gm.getVirologusok()) {
            String name = v.getUserName();
            if (nev.equalsIgnoreCase(name)) return v;
        }
        return null;
    }

    public void command_switch(String parancs, String parameter) {
        switch (parancs) {
            case "create":
                create(parameter);
                break;
            case "help":
                help(parameter);
                break;
            case "info":
                info(parameter);
                break;
            case "put":
                put(parameter);
                break;
            case "list":
                list(parameter);
                break;
            case "load":
                load(parameter);
                break;
            case "save":
                save(parameter);
                break;
            case "move":
                move(parameter);
                break;
            case "print_to_file":
                print_to_file(parameter);
                break;
            case "give":
                give(parameter);
                break;
            case "step":
                step(parameter);
                break;
            case "generate_map":
                generate_map(parameter);
                break;
            case "smear_virus":
                smear_virus(parameter);
                break;
            case "kill":
                kill(parameter);
                break;
            case "make_agent":
                make_agent(parameter);
                break;
            case "random":
                random(parameter);
                break;
            case "clear":
                clear(parameter);
                break;
            case "defensivecoat":
                defensivecoat(parameter);
                break;
            case "test_mode":
                test_mode();
                break;
            case "connect":
                connect(parameter);
                break;
            case "set":
                set(parameter);
                break;
        }
    }
}
