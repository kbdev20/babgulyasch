import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

import java.io.Serializable;
import java.util.ArrayList;

public class View extends JFrame implements Serializable {

    private Controller controller;

    //Listek es modellek for Kati:
    private JList JlKod, JlHatas, JlAgens, JlFelszereles, JlAnyag, JlSzomszed, JlVirologusok;
    private DefaultListModel<String> kodmodell, hatasmodell, agensmodell,
            felszerelesmodell, anyagmodell, szomszedmodell, virmodell;
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
        kodmodell = controller.getkodmodell();
        JlKod = new JList<>(kodmodell);
        JPanelEast.add(JlKod);

        //hatas lista
        hatasmodell = controller.gethatasmodell();
        JlHatas = new JList<>(hatasmodell);
        JPanelEast.add(JlHatas);

        //felszereles lista
        felszerelesmodell = controller.getfelszerelesmodell();
        JlFelszereles = new JList<>(felszerelesmodell);
        JPanelEast.add(JlFelszereles);

        //agens lista
        agensmodell = controller.getagensmodell();
        JlAgens = new JList<>(agensmodell);
        JPanelWest.add(JlAgens);

        //szomszed mezők lista
        szomszedmodell = controller.getszomszedokmodell();
        JlSzomszed = new JList<>(szomszedmodell);
        JSPszomszed = new JScrollPane(JlSzomszed);
        JPanelWest.add(JSPszomszed);

        //anyag lista
        anyagmodell = controller.getanyagmodell();


        JlAnyag = new JList<>(anyagmodell);


        JPanelWest.add(JlAnyag);
        //virologusok lista
        virmodell = controller.getvirmodell();
        JlVirologusok = new JList<>(virmodell);


        JbAgensL = new JButton("Ágens létrehozása");
        JbKenes = new JButton("Kenés");
        JbKorvege = new JButton("Kör vége");
        JbOl = new JButton("Öl");

        JPanelCenter.setLayout(new GridLayout(5, 1));
        JPanelCenter.add(JbAgensL);
        JPanelCenter.add(JbKenes);
        JPanelCenter.add(JbKorvege);
        JPanelCenter.add(JbOl);


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
            startGame();
        }

    }

    /**
     * Ágens létrehozása listener.
     */
    class AgensLetrehozListener implements ActionListener {
        @Override
        //TODO ok-ra hozzá adja, ha nincs elég anyagja kiirja, még valami van a fejmbe, ja szét szedni kisebb függvényekre
        public void actionPerformed(ActionEvent e) {
            controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).kod_hozzaad(new Vedettseg_kod());
            controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).setTarolo(new Anyag(20, 20));
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size() == 0) {
                textDialogueBox("Nincs ismert hatás, ezért nem tud létrehozni ágenst");
            } else {
                JFrame Jfpopup = new JFrame("Ágens létrehoz");
                Jfpopup.setLayout(new BorderLayout());
                ArrayList<String> hatasok = new ArrayList<>();
                for (int i = 0; i < controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size(); i++) {
                    hatasok.add(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(i).toString());
                }
                JComboBox agensList = new JComboBox(hatasok.toArray(new String[hatasok.size()]));
                Jfpopup.add(agensList);

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
                controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).agens_letrehoz(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(agensList.getSelectedIndex()));
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
}

