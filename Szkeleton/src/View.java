import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;

public class View extends JFrame implements Serializable {

    private Controller controller;

    //Listek es modellek for Kati:
    private JList JlKod, JlHatas, JlAgens, JlFelszereles, JlAnyag, JlSzomszed, JlVirologusok;
    private JScrollPane JSPszomszed;

    //Game fo paneljei:
    private JPanel JpMainGame, JPanelNorth, JPanelEast, JPanelWest, JPanelCenter;

    //Game fo paneljenek gombjai CENTER panelben:
    private JButton JbAgensL, JbKenes, JbKorvege, JbOl;

    //Game fo paneljenek gombjai NORTH panelben:
    private JLabel JlVirNev, JlMezoNev;

    //Kezdo panel paneljei es gombjai:
    private JPanel JpMainMenu, JpTextInput, JpAdd, JpStart, JpLoad;
    private JTextField JtNameInput;
    private JButton JbStart, JbLoad, JbAdd, JbSave;

    //gameOVer metodushoz:
    private JButton JBujjatek;
    private JPanel JPuj, JPnyertes, JPGameend;

    //gyilkolashoz:
    private JList virlista;
    private JFrame virvalaszt;

    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Új játékos felvétele listener.
     */
    class UjJatekosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JtNameInput.getText();
            if (input.equals("")) textDialogueBox("A felhasználónév nem lehet üres.");
            else {
                controller.ujJatekos(input);
                JtNameInput.setText("");
            }
        }
    }

    /**
     * Főképernyőt jeleníti meg
     */
    private void GamePanelInit() {
        remove(JpMainMenu);
        setSize(600, 600);
        JButton JMenuItemSave, JMenuItemExit;
        JMenuBar mb = new JMenuBar();
        JMenuItemSave = new JButton("Save");
        JMenuItemExit = new JButton("Exit");
        mb.add(JMenuItemExit);
        mb.add(JMenuItemSave);
        setJMenuBar(mb);
        JpMainGame = new JPanel();
        JpMainGame.setLayout(new BorderLayout());
        JPanelNorth = new JPanel();
        JPanelNorth.setLayout(new BorderLayout());
        JPanelEast = new JPanel();
        JPanelEast.setLayout(new GridLayout(3, 1));
        JPanelWest = new JPanel();
        JPanelWest.setLayout(new GridLayout(3, 1));
        JPanelCenter = new JPanel();

        JpMainGame.add(JPanelCenter, BorderLayout.CENTER);
        JpMainGame.add(JPanelEast, BorderLayout.EAST);
        JpMainGame.add(JPanelWest, BorderLayout.WEST);
        JpMainGame.add(JPanelNorth, BorderLayout.NORTH);
        add(JpMainGame);

        virNevRajzol();
        virMezoRajzol();
        //kod lista
        JlKod = new JList<>(controller.getkodmodell());
        JPanelEast.add(JlKod);

        //hatas lista
        JlHatas = new JList<>(controller.gethatasmodell());
        JPanelEast.add(JlHatas);

        //felszereles lista
        JlFelszereles = new JList<>(controller.getfelszerelesmodell());
        JPanelEast.add(JlFelszereles);

        //agens lista
        JlAgens = new JList<>(controller.getagensmodell());
        JPanelWest.add(JlAgens);

        //szomszed mezők lista
        JlSzomszed = new JList<>(controller.getszomszedokmodell());
        JSPszomszed = new JScrollPane(JlSzomszed);
        JPanelWest.add(JSPszomszed);

        //anyag lista
        JlAnyag = new JList<>(controller.getanyagmodell());

        JPanelWest.add(JlAnyag);
        //virologusok lista
        JlVirologusok = new JList<>(controller.getvirmodell());


        JbAgensL = new JButton("Ágens létrehozása");
        JbKenes = new JButton("Kenés");
        JbKorvege = new JButton("Kör vége");
        JbOl = new JButton("Öl");

        JPanelCenter.setLayout(new GridLayout(5, 1));
        JPanelCenter.add(JbAgensL);
        JPanelCenter.add(JbKenes);
        JPanelCenter.add(JbKorvege);
        JPanelCenter.add(JbOl);
        JPanelCenter.add(JlVirologusok);


        JbAgensL.addActionListener(new AgensLetrehozListener());
        JbKorvege.addActionListener(new LepesListener());
        JbKenes.addActionListener(new KenesListener());
        JbOl.addActionListener(new KillButtonListener());
        JlSzomszed.addMouseListener(new ListSzomszedokListener());
        JbSave.addActionListener(new SaveGameListener());

        JMenuItemExit.addActionListener(new ExitGameListener());
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!controller.startGame()) textDialogueBox("Adjon hozzá játékosokat");
            else GamePanelInit();
        }
    }

    class ListSzomszedokListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                controller.mozgas(JlSzomszed.getSelectedIndex() - 1);
                frissit();
            }
        }
    }

    class LoadGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                controller.load();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    class KillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!controller.gyilkol()) textDialogueBox("Nincs baltája a virológusnak");
            else {
                aldozatValasztDialogue();
            }
        }
    }

    class SaveGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.ment();
        }

    }

    class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            remove(JPnyertes);
            remove(JPGameend);
            remove(JPuj);
            controller = new Controller();
            // todo ? System.exit(0);
            startGame();
        }

    }

    /**
     * Ágens létrehozása listener.
     */
    class AgensLetrehozListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // ha nincs semmilyen kodja
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size() == 0) {
                textDialogueBox("Nincs ismert hatás, ezért nem tud létrehozni ágenst");
            } else {
                //feljön a dialoge ablak
                JFrame Jfpopup = new JFrame("Ágens létrehoz");
                Jfpopup.setLayout(new BorderLayout());
                ArrayList<String> hatasok = new ArrayList<>();
                // JComboboxba kirija hogy milyen ágenskeet tud késziteni
                for (int i = 0; i < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size(); i++) {
                    hatasok.add(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(i).toString());
                }
                JComboBox agensList = new JComboBox(hatasok.toArray(new String[hatasok.size()]));
                Jfpopup.add(agensList);

                JButton Jbok = new JButton("OK");
                Jfpopup.add(Jbok, BorderLayout.SOUTH);
                Jfpopup.setSize(500, 120);
                Jfpopup.setLocationRelativeTo(null);
                Jfpopup.setResizable(false);
                Jfpopup.setVisible(true);
                /**
                 *   ha megnyomja az ok gobot két dolog történhet:
                 *   1. nincs elég anyaga, akkor kiirja azt és bezárja a dolgokat
                 *   2. van elég és akkor meghivja az ágens_létrehoz függvényt
                 */

                Jbok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Jfpopup.dispose();
                        //omg de hosszu sorok sorry
                        if(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo().getNukleotid() < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()).getAr().getNukleotid() || controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo().getAminosav() < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()).getAr().getAminosav()){
                            textDialogueBox("Nincs elég anyagod hozzá!");}
                        else{
                            controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).agens_letrehoz(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()));
                            frissit();

                        }
                    }
                });

            }
        }
    }


    class ExitGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
     * Kenes listener.
     */
    class KenesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().size() == 0) {
                textDialogueBox("Nincs ágense, ezért nem tud kenni");
            } else {
                //feljön a dialoge ablak
                JFrame Jfpopup = new JFrame("Kenés");
                Jfpopup.setLayout(new BorderLayout());

                // JComboboxba kirija hogy milyen ágenskeet tud  kenni
                ArrayList<String> agensek = new ArrayList<>();
                for (int i = 0; i < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().size(); i++) {
                    agensek.add(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().get(i).toString());
                }
                JComboBox agensList = new JComboBox(agensek.toArray(new String[agensek.size()]));
                Jfpopup.add(agensList);

                // JComboboxba kirija hogy kiket tud kenni
                ArrayList<String> virek = new ArrayList<>();
                for (int i = 0; i < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getMezo().getVirologusok().size(); i++) {
                    agensek.add(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getMezo().getVirologusok().get(i).getUserName());
                }
                JComboBox virLista = new JComboBox(virek.toArray(new String[virek.size()]));
                Jfpopup.add(virLista);

                JButton Jbok = new JButton("OK");
                Jfpopup.add(Jbok, BorderLayout.SOUTH);
                Jfpopup.setSize(500, 120);
                Jfpopup.setLocationRelativeTo(null);
                Jfpopup.setResizable(false);
                Jfpopup.setVisible(true);
                Jbok.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Jfpopup.dispose();
                        //omg de hosszu sorok sorry
                        if(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo().getNukleotid() < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()).getAr().getNukleotid() || controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo().getAminosav() < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()).getAr().getAminosav()){
                            textDialogueBox("Nincs elég anyagod hozzá!");}
                        else{
                              controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).agens_letrehoz(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()));
                            frissit();

                        }
                    }
                });

            }
        }
    }

    /**
     * Lepes listener.
     */
    class LepesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!controller.lep()) gameOver();
            else {
                virNevRajzol();
                virMezoRajzol();
            }
            frissit();
        }
    }

    private void virNevRajzol() {
        String virnev = controller.getSorosNev();
        if (JlVirNev != null) JlVirNev.setText(virnev);
        else JlVirNev = new JLabel("Virologus neve: "+virnev);
        JPanelNorth.add(JlVirNev);
        super.update(getGraphics());
    }

    private void virMezoRajzol(){
        String mezonev = controller.getSorosMezo();
        if (JlMezoNev != null) JlMezoNev.setText(mezonev);
        else JlMezoNev = new JLabel("Aktualis mezo: " + mezonev);
        JPanelNorth.add(JlMezoNev, BorderLayout.EAST);
        super.update(getGraphics());
    }

    /**
     * Java Swing metódusok használatával kirajzolja az adott objektumot.
     */
    void rajzol() {

    }

    /**
     * Körönként és/vagy felhasználói input esetén változtatja a kinézetet.
     */
    void frissit() {
        //Ujraervenyesitjuk es ujra kirajzoljuk
        JlAnyag.setModel(controller.getanyagmodell());
        JlFelszereles.setModel(controller.getfelszerelesmodell());
        JlHatas.setModel(controller.gethatasmodell());
        JlAgens.setModel(controller.getagensmodell());
        JlKod.setModel(controller.getkodmodell());
        JlSzomszed.setModel(controller.getszomszedokmodell());
        JlVirologusok.setModel(controller.getvirmodell());
        super.update(getGraphics());

    }

    /**
     * Kiírja a játék befejezésével járó információkat (helyezések, nyertes stb…)
     */
    void gameOver() {
        remove(JPanelNorth);
        remove(JPanelEast);
        remove(JPanelWest);
        remove(JPanelCenter);
        remove(JpMainGame);

        setSize(600, 240);
        setLayout(new GridLayout(3, 3));
        JPnyertes = new JPanel();
        JPGameend = new JPanel();
        JPuj = new JPanel();

        JBujjatek = new JButton();
        JBujjatek.setText("Új játék");
        add(JPGameend);
        add(JPnyertes);
        add(JPuj);
        JPGameend.add(new Label("Game end"));
        JPnyertes.add(new JLabel("Nyertes(ek) játékos neve:"));
        DefaultListModel<String> nyertesek = new DefaultListModel<>();
        // berakja egy listába a nyertesek nevét, Jlistet használva kiirja majd

        for (int i = 0; i < controller.gameManager.getVirologusok().size(); i++) {
            if (controller.gameManager.getScore(controller.gameManager.getVirologusok().get(i)) == 4) {
                nyertesek.addElement(controller.gameManager.getVirologusok().get(i).getUserName());
            }
        }

        JList list = new JList(nyertesek);
        JPnyertes.add(list);

        JPuj.add(JBujjatek);
        JBujjatek.addActionListener(new NewGameListener());
        setVisible(true);

    }

    /**
     * A játék elindításával járó információkat jeleníti meg a képernyőn
     * és bekéri a szükséges inputokat (pl.: játékosok nevei).
     */
    public void startGame() {
        try {
            initialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * start game ablak létrehozza, gombokat inicializál
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void initialize() throws IOException, ClassNotFoundException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Game");
        setSize(600, 240);
        setLocationRelativeTo(null);
        //setFocusable(true);
        setResizable(false);

        JpMainMenu = new JPanel();
        JpMainMenu.setLayout(new GridLayout(4, 3));
        JpTextInput = new JPanel();
        JpAdd = new JPanel();
        JpStart = new JPanel();
        JpLoad = new JPanel();

        JpMainMenu.add(JpTextInput);
        JpMainMenu.add(JpAdd);
        JpMainMenu.add(JpStart);
        JpMainMenu.add(JpLoad);
        add(JpMainMenu);
        JpTextInput.add(new JLabel("Új játékos neve:"));
        JbAdd = new JButton();
        JbAdd.setText("Hozzáad");
        JbStart = new JButton();
        JbStart.setText("Start");
        JbLoad = new JButton();
        JbLoad.setText("Előző játék betöltése");
        JbSave = new JButton();
        JbSave.setText("Játék mentése");
        JtNameInput = new JTextField(16);

        JpStart.add(JbStart);
        JpLoad.add(JbLoad);
        JpTextInput.add(JtNameInput);
        JpAdd.add(JbAdd);

        JbStart.addActionListener(new StartGameListener());
        JbLoad.addActionListener(new LoadGameListener());
        JbAdd.addActionListener(new UjJatekosListener());
        // JbSave.addActionListener(new SaveGameListener());

        setVisible(true);
    }

    /**
     * A félbehagyott játékot tudunk betölteni fájlból.
     */
    void loadGame() {

    }

    /**
     * Kirajzol egy adott objektumhoz szükséges gombot, amit a felhasználó megnyomhat (JButton használatával)
     */
    void gombRajzol() {

    }

    public void textDialogueBox(String msg) {
        JFrame Jfpopup = new JFrame("Message");
        Jfpopup.setLayout(new BorderLayout());
        JLabel text = new JLabel(msg);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        Jfpopup.add(text, BorderLayout.NORTH);
        JButton Jbok = new JButton("OK");
        Jbok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Jfpopup.dispose();
            }
        });
        Jfpopup.add(Jbok, BorderLayout.SOUTH);
        Jfpopup.setSize(500, 120);
        Jfpopup.setLocationRelativeTo(null);
        Jfpopup.setResizable(false);
        Jfpopup.setVisible(true);
    }

    public void aldozatValasztDialogue() {
        virvalaszt = new JFrame("Játékos választás");
        virvalaszt.setLayout(new BorderLayout());
        virlista = new JList();
        virlista.setModel(controller.getvirmodell());
        virlista.addMouseListener(new ListVirologusokListener());

        virvalaszt.add(virlista);

        virvalaszt.setSize(500, 320);
        virvalaszt.setLocationRelativeTo(null);
        virvalaszt.setResizable(false);
        virvalaszt.setVisible(true);
    }

    class ListVirologusokListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                int idx = virlista.getSelectedIndex()-1;
                if (!controller.getVirSzomszed(idx).getUserName().equals(controller.getSorosNev())) controller.baltaHasznal(controller.getVirSzomszed(idx));
                virvalaszt.dispose();
            }
        }
    }

}