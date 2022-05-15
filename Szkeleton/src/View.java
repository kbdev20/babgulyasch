import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class View extends JFrame implements Serializable {

    private Controller controller;

    //Listek es modellek for Kati:
    private JList JlKod, JlHatas, JlAgens, JlFelszereles, JlAnyag;
    private DefaultListModel<String> kodmodell, hatasmodell, agensmodell, felszerelesmodell, anyagmodell;

    //Game fo paneljenek gombjai CENTER panelben:
    private JButton JbAgensL, JbKenes, JbKorvege;

    //Game fo paneljenek gombjai NORTH panelben:
    private JLabel JlVirNev;

    //Kezdo panel paneljei es gombjai:
    private JPanel JpMainMenu, JpTextInput, JpAdd, JpStart, JpLoad;
    private JPanel JpMainGame, JPanelNorth, JPanelEast, JPanelWest, JPanelCenter;
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
        JPanelEast = new JPanel();
        JPanelWest = new JPanel();
        JPanelCenter = new JPanel();

        //szinezesek for debugging:
        JpMainGame.add(JPanelCenter, BorderLayout.CENTER); JPanelCenter.setBackground(new Color(255, 0, 0));
        JpMainGame.add(JPanelEast, BorderLayout.EAST);JPanelEast.setBackground(new Color(64, 255, 0));
        JpMainGame.add(JPanelWest, BorderLayout.WEST);JPanelWest.setBackground(new Color(0, 60, 255));
        JpMainGame.add(JPanelNorth, BorderLayout.NORTH);JPanelNorth.setBackground(new Color(255, 204, 0));
        add(JpMainGame);

        virNevRajzol();
            //kod listakodmodell = new DefaultListModel<String>();
        /*
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size() > 0) {
                kodmodell.remove(0);
                for (int i = 0; i < (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().size()); ++i)
                    kodmodell.addElement(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getIsmert_hatasok().get(i).toString());
            } else {
                kodmodell.addElement("Nincs még ismert kód");
            }



            JlKod = new JList<>(kodmodell);
            JPanelEast.add(JlKod, BorderLayout.NORTH);

            //hatas listahatasmodell = new DefaultListModel<String>();
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getRakenve().size() > 0) {
                hatasmodell.remove(0);
                for (int i = 0; i < (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getRakenve().size()); ++i)
                    hatasmodell.addElement(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getRakenve().get(i).toString());
            } else {
                hatasmodell.addElement("Nincs aktív hatás");
            }

            JlHatas = new JList<>(hatasmodell);
            JPanelEast.add(JlHatas, BorderLayout.CENTER);

            //felszereles listafelszerelesmodell = new DefaultListModel<String>();
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getFelszereles().size() > 0) {
                felszerelesmodell.remove(0);
                for (int i = 0; i < (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getFelszereles().size()); ++i)
                    felszerelesmodell.addElement(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getFelszereles().get(i).toString());
            } else {
                felszerelesmodell.addElement("Nincs felszerelés");
            }

            JlFelszereles = new JList<>(felszerelesmodell);
            JPanelEast.add(JlFelszereles, BorderLayout.SOUTH);

            //agens listaagensmodell = new DefaultListModel<String>();
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().size() > 0) {
                agensmodell.remove(0);
                for (int i = 0; i < (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().size()); ++i)
                    agensmodell.addElement(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getAgens().get(i).toString());
            } else {
                agensmodell.addElement("Nincs felszerelés");
            }

            JlAgens = new JList<>(agensmodell);
            JPanelWest.add(JlAgens, BorderLayout.NORTH);
        //anyag lista
            anyagmodell = new DefaultListModel<String>();
            if (controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo() != null) {
                anyagmodell.remove(0);
                anyagmodell.addElement(controller.gameManager.getVirologusok().get(controller.gameManager.getSoros()).getTarolo().toString());
            } else {
                anyagmodell.addElement("Nincs anyag a tárolóban");
            }

            JlAnyag = new JList<>(anyagmodell);


            JPanelWest.add(JlAnyag, BorderLayout.CENTER);

         */

            JbAgensL = new JButton();
            JbAgensL.setText("Ágens létrehozása");
            JbKenes = new JButton();
            JbKenes.setText("Kenés");
            JbKorvege = new JButton();
            JbKorvege.setText("Kör vége");

            JPanelCenter.add(JbAgensL);
            JPanelCenter.add(JbKenes);
            JPanelCenter.add(JbKorvege);

            JbAgensL.addActionListener(new AgensLListener());
            JbKorvege.addActionListener(new LepesListener());
            JbKenes.addActionListener(new KenesListener());

        JMenuItemExit.addActionListener(new ExitGameListener());

    }

    class StartGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if (!controller.startGame()) textDialogueBox("Adjon hozzá játékosokat");
            else GamePanelInit();
        }
    }
    class LoadGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                controller.load();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    class SaveGameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            controller.ment();
        }

    }

    /**
     * Ágens létrehozása listener.
     */
    class AgensLListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){

            }
        }

        /**
         * Ágens létrehozása listener.
         */
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

            }
        }

        private void virNevRajzol() {
            String virnev = controller.getSorosNev();
            JlVirNev = new JLabel(virnev);
            JPanelNorth.add(JlVirNev);
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

        }

        /**
         * Kiírja a játék befejezésével járó információkat (helyezések, nyertes stb…)
         */
        void gameOver() {
            //TODO: megnézni h jo e az elrendezés majd
            JPanelNorth.setVisible(false);
            JPanelEast.setVisible(false);
            JPanelWest.setVisible(false);
            JPanelCenter.setVisible(false);


            setSize(600, 600);
            setLayout(new GridLayout(3, 3));
            JPnyertes = new JPanel();
            JPGameend = new JPanel();
            JPuj = new JPanel();

            JBujjatek = new JButton();
            JBujjatek.setText("Új játék");

            add(JPuj);
            JPGameend.add(new Label("Game end"));
            JPnyertes.add(new JLabel("Nyertes(ek) játékos neve:"));
            DefaultListModel<String> nyertesek = new DefaultListModel<>();
            // berakja egy listába a nyertesek nevét, Jlistet használva kiirja majd
            for (int i = 0; i < controller.gameManager.getVirologusok().size(); i++){
                if (controller.gameManager.getScore(controller.gameManager.getVirologusok().get(i)) == 4);
                {
                    nyertesek.addElement(controller.gameManager.getVirologusok().get(i).getUserName());
                }
            }
            JList list = new JList(nyertesek);
            JPnyertes.add(list);
            JPnyertes.add(new JLabel("Nyertes játékos neve:"));

            JPuj.add(JBujjatek);
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

        private void initialize() throws IOException, ClassNotFoundException {
            //TODO: Visszarakni a Start fg-be
            //TODO név bekérése

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setTitle("StartGame");
            setSize(600, 240);
            setLocationRelativeTo(null);
            setFocusable(true);
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
            Jfpopup.setSize(500,120);
            Jfpopup.setLocationRelativeTo(null);
            Jfpopup.setResizable(false);
            Jfpopup.setVisible(true);
        }
    }

