import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Controller {
    GameManager gameManager;

    public Controller(){
        gameManager = new GameManager();
    }
    /**
     * Meghívásával a virológust mozgatjuk egyik mezőről a másikra.
     */
    void mozgas(int idx) {
        Virologus vir = gameManager.getVirologusok().get(gameManager.getSoros());
        if (vir.getMezo().getSzomszedok().size() < 1) return;
        boolean benult = false;
        for (Agens a : vir.getRakenve()) {
            if (a.toString().equals("Benitottsag") && a.getKenve()) benult = true;
        }
        if (!benult) vir.mozgas(vir.getMezo().getSzomszedok().get(idx));
    }

    /**
     * Meghívásával egy virológust ütünk le egy a baltával, akinek ezzel a játék befejeződött.
     */
    public boolean gyilkol() {
        Virologus gyilkos = gameManager.getVirologusok().get(gameManager.getSoros());
        if (!gyilkos.hasBalta()) return false;

        //gyilkossag megirasa xd
        return true;
    }

    public void baltaHasznal(Virologus aldozat) {
        Virologus gyilkos = gameManager.getVirologusok().get(gameManager.getSoros());
        Balta b;
        if (gyilkos.getMezo() == aldozat.getMezo()) {
            for (int i = 0; i  < gyilkos.getFelszereles().size(); i ++) {
                if (gyilkos.getFelszereles().get(i).toString().equals("Balta")) {
                    b = (Balta) gyilkos.getFelszereles().get(i);
                    b.felszerelesHatas(gyilkos, aldozat, null);
                }
            }
        }
    }

    /**
     * Egy kiválasztott virológust helyezünk ágens hatása alá.
     */
    /*void ken(int vir, int agens) {
        gameManager.getVirologusok().get(gameManager.getSoros()).kenes(gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().get(vir), gameManager.getVirologusok().get(gameManager.getSoros()).getAgens().get(agens));
        gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().get(vir).megkenve(gameManager.getVirologusok().get(gameManager.getSoros()).getAgens().get(agens));
    }*/

    public boolean kenheto(int idx) {
        Virologus megkent = gameManager.getVirologusok().get(idx);
        for (int i = 0; i  < megkent.getRakenve().size(); i ++) {
            if (megkent.getRakenve().get(i).toString().equals("Vedettseg") )
                return false;
        }

        return true;
    }
    /**
     * Egy kiválasztott virológust helyezünk ágens hatása alá.
     *//*
    void ken(Virologus megkent,Agens agens) {
        Virologus keno = gameManager.getVirologusok().get(gameManager.getSoros());
        if (keno.getMezo() == megkent.getMezo()) {
            for (int i = 0; i  < keno.getAgens().size(); i ++) {
                if (keno.getAgens().get(i).toString().equals(agens.toString())) {
                    agens = keno.getAgens().get(i);
                    agens.hatas(megkent);
                    megkent.megkenve(agens);

                }
            }
        }

    }*/

    void ken(Virologus megkent,Agens agens) {
        Virologus keno = gameManager.getVirologusok().get(gameManager.getSoros());
        if (keno.getMezo() == megkent.getMezo()) {
            for (int i = 0; i  < keno.getAgens().size(); i ++) {
                if (keno.getAgens().get(i).toString().equals(agens.toString())) {
                    agens = keno.getAgens().get(i);
                    agens.hatas(megkent);
                    megkent.megkenve(agens);
                }
            }
        }

    }

    /**
     * Kimentjük a játékot egy fájlba.
     */
    void ment() {
        try {
            FileOutputStream f = new FileOutputStream("GameManager");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(gameManager);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Játék megkezdése.
     */
    public boolean startGame() {
        int jatekosokSzama = gameManager.getVirologusok().size();
        if (jatekosokSzama < 1) return false;
        gameManager.palya_generalas(jatekosokSzama);
        gameManager.placeplayers();
        return true;
    }

    /**
     * Játék vége.
     */
    void endGame() {

    }
    /**
     * Játék frissítése.
     */
    void update() {


    }

    /**
     * A félbehagyott játékot tudjuk betölteni egy fájlból
     */
    void load() throws FileNotFoundException {
        try {
            FileInputStream f = new FileInputStream("GameManager");
            ObjectInputStream in = new ObjectInputStream(f);
            GameManager gman = (GameManager) in.readObject();
            in.close();
            gameManager = gman;
            //Test
            System.out.println(gameManager.getVirologusok().get(0).getUserName());
        }
        catch(FileNotFoundException fe){
            throw fe;
        }
        catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public void ujJatekos(String jatekos){ gameManager.addPlayer(jatekos); }

    public String getSorosNev() {
        return gameManager.getVirologusok().get(gameManager.getSoros()).getUserName();
    }
    public String getSorosMezo(){ return gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().toString();}

    public boolean lep() {
        if (gameManager.checkEnd()) return false;
        else {
            gameManager.lep();
        }
        return true;
    }

    public DefaultListModel<String> getszomszedokmodell() {
        DefaultListModel<String> szomszedmodell = new DefaultListModel<String>();
        szomszedmodell.addElement("Szomszédos mezők:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getSzomszedok().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getSzomszedok().size()); ++i){
                szomszedmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getSzomszedok().get(i).toString());
        }} else {
            szomszedmodell.addElement("Nincsenek szomszedok");
        }
        return szomszedmodell;
    }

    public DefaultListModel<String> getanyagmodell() {
        DefaultListModel<String> anyagmodell = new DefaultListModel<String>();
        anyagmodell.addElement("Tároló:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getTarolo() != null) {
            anyagmodell.addElement("Aminosav: "+gameManager.getVirologusok().get(gameManager.getSoros()).getTarolo().getAminosav());
            anyagmodell.addElement("Nukleotid: "+gameManager.getVirologusok().get(gameManager.getSoros()).getTarolo().getNukleotid());
        } else {
            anyagmodell.addElement("Nincs anyag a tárolóban");
        }
        return anyagmodell;
    }

    public DefaultListModel<String> getagensmodell() {
        DefaultListModel<String>agensmodell = new DefaultListModel<String>();
        agensmodell.addElement("Ágensek:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getAgens().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getAgens().size()); ++i)
                agensmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getAgens().get(i).toString());
        } else {
            agensmodell.addElement("Nincs ágens");
        }
        return agensmodell;
    }

    public DefaultListModel<String> getfelszerelesmodell() {
        DefaultListModel<String> felszerelesmodell = new DefaultListModel<String>();
        felszerelesmodell.addElement("Felszerelések:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getFelszereles().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getFelszereles().size()); ++i)
                felszerelesmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getFelszereles().get(i).toString());
        } else {
            felszerelesmodell.addElement("Nincs felszerelés");
        }
        return felszerelesmodell;
    }

    public DefaultListModel<String> gethatasmodell() {
        DefaultListModel<String> hatasmodell = new DefaultListModel<String>();
        hatasmodell.addElement("Aktív hatások:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getRakenve().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getRakenve().size()); ++i)
                hatasmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getRakenve().get(i).toString());
        } else {
            hatasmodell.addElement("Nincs aktív hatás");
        } return hatasmodell;
    }

    public DefaultListModel<String> getkodmodell() {
        DefaultListModel<String> kodmodell = new DefaultListModel<String>();
        kodmodell.addElement("Ismert kódok:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getIsmert_hatasok().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getIsmert_hatasok().size()); ++i)
                kodmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getIsmert_hatasok().get(i).toString());
        } else {
            kodmodell.addElement("Nincs még ismert kód");
        } return  kodmodell;
    }

    public DefaultListModel<String> getvirmodell() {
        DefaultListModel<String> virmodell = new DefaultListModel<String>();
        virmodell.addElement("A mezőn tartozkodó játékosok:");
        if (gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().size() > 0) {
            for (int i = 0; i < (gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().size()); ++i)
                virmodell.addElement(gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().get(i).getUserName());
        } else {
            virmodell.addElement("");
        }
        return virmodell;
    }

    public Virologus getVirSzomszed(int idx) {
        return gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getVirologusok().get(idx);
    }
}
