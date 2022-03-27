public class Amnezia extends Agens {
    public Amnezia() {}

    /**
     * Körönként csökkenti a hatás időtartamát.
     */
    public void lep() {
        System.out.println("Amnezia hatas leptetese.");
    }

    /**
     * A virológus elfelejti az összes addig ismert genetikai kódot.
     * @param v Ez a virológus fogja elfelejteni a megtanult genetikai kódokat.
     */
    public void hatas (Virologus v) {
        System.out.println("Amnezia hatasa.");
    }
}
