public class Controller {
    GameManager gameManager;

    public Controller(GameManager gameManager){
        this.gameManager = gameManager;
    }
    /**
     * Meghívásával a virológust mozgatjuk egyik mezőről a másikra.
     */
    void mozgas()
    {

    }

    /**
     * Meghívásával egy virológust ütünk le egy a baltával, akinek ezzel a játék befejeződött.
     */
    void gyilkol()
    {

    }

    /**
     * Egy kiválasztott virológust helyezünk ágens hatása alá.
     */
    void ken()
    {

    }

    /**
     * Kimentjük a játékot egy fájlba.
     */
    void ment()
    {

    }

    /**
     * Játék megkezdése.
     */
    void startGame()
    {

    }

    /**
     * Játék vége.
     */
    void endGame()
    {

    }

    /**
     * A félbehagyott játékot tudjuk betölteni egy fájlból
     */
    void load()
    {

    }
    public void ujJatekos(String jatekos){ gameManager.addPlayer(jatekos); }

}
