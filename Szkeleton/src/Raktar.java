public class Raktar extends Mezo {
public Raktar(){};
    public Raktar (Anyag a){
        setTarolo(a);
    }

    /**
     * Elfogadja a megadott virologust a mez�re
     *
     * @param vir Ezt a virol�gust fogadja el az adott mez�re
     */
    public void elfogad(Virologus vir) {
    }

    /**
     * Elt�volitja az adott virologust a mez�r�l
     *
     * @param vir Ezt a virol�gust t�vol�tja el az adott mez�r�l.
     */
    public void eltavolit(Virologus vir) {
    }

    @Override
    public void anyagElpusztit() {
        setTarolo(new Anyag(0,0));
    }

    @Override
    public String toString() {
        return "Raktar";
    }
}
