public class Raktar extends Mezo {
    public Raktar(){};
    public Raktar (Anyag a){
        setTarolo(a);
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
