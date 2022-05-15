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

    /**
     * Egy kiválasztott virológust helyezünk ágens hatása alá.
     */
    void ken() {

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
            System.out.println("HIBA");
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
            GameManager gm = (GameManager) in.readObject();
            in.close();
            gameManager = gm;
            //Test
            System.out.println(gameManager.getVirologusok().get(0).getUserName());
        }
        catch(FileNotFoundException fe){
            throw fe;
        }
        catch(IOException | ClassNotFoundException ex) {
            System.out.println("HIBA");
            return;
        }
    }
    public void ujJatekos(String jatekos){ gameManager.addPlayer(jatekos); }

    public String getSorosNev() {
        return gameManager.getVirologusok().get(gameManager.getSoros()).getUserName();
    }

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
                System.out.println(gameManager.getVirologusok().get(gameManager.getSoros()).getMezo().getSzomszedok().get(i).toString());
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
}
