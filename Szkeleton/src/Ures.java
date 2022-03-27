public class Ures extends Mezo{

    /**
     * Elfogadja a megadott virologust a mezőre
     *
     * @param vir Ezt a virológust fogadja el az adott mezőre
     */
    public void elfogad(Virologus vir) {
        System.out.println("Virologus ures mezore lep.");
    }

    /**
     * Eltávolitja az adott virologust a mezőről
     *
     * @param vir Ezt a virológust távolítja el az adott mezőről
     */
    public void eltavolit(Virologus vir) {
        System.out.println("Virologus elhagyja az ures mezot");
    }
}
