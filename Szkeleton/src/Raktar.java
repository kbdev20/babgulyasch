public class Raktar extends Mezo {
    private Anyag tarolo;
    private boolean ures;

    public Anyag getTarolo() {return tarolo;}
    public void setTarolo(Anyag tarolo) {this.tarolo = tarolo;}

    public boolean getUres() {return ures;}
    public void setUres(boolean ures) {this.ures = ures;}

    public Raktar (Anyag a, boolean ures){
        tarolo = a;
        this.ures = ures;
    }

    /**
     * Elfogadja a megadott virologust a mezőre
     *
     * @param vir Ezt a virológust fogadja el az adott mezőre
     */
    public void elfogad(Virologus vir) {
        System.out.println("Virologus belepett a raktarba.");
    }

    /**
     * Eltávolitja az adott virologust a mezőről
     *
     * @param vir Ezt a virológust távolítja el az adott mezőről.
     */
    public void eltavolit(Virologus vir) {
        System.out.println("Virologus kilepett a raktarbol.");
    }
}
