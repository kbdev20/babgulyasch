public class Zsak extends Felszereles {
    private int plusAnyag = 0;
    private Anyag tarolo;
    private boolean hozzaadva = false;
    /**
     * Zsák konstruktor
     */
    public Zsak(){
        setAktiv(false);
    }

    /**
     * Visszaadja  a zsák tárolói kapacitását
     * @return
     */
    public int getMaxAnyag() {return plusAnyag; }

    /**
     * Visszaadja a zsákban tárolt anyagot
     * @return
     */
    public Anyag getTarolo() {return tarolo; }

    /**
     * Lecseréli a zsákban tárolt anyagot egy adott Anyag objektumra
     * @param tarolo
     */
    public void setTarolo(Anyag tarolo) {this.tarolo = tarolo; }



    /**
     * A virológus tárolójának méretét növeli.
     * @param cel Virológus akire hat a felszerelés hatás.
     */
    @Override
    public void felszerelesHatas(Virologus forras, Virologus cel, Agens a) {
        if(!hozzaadva){
            cel.setMaxAnyag(getMaxAnyag()+plusAnyag);
            hozzaadva = true;
        }
    }

    /**
     * Ha a felszerelések listából kidobjuk a zsákot akkor csökkenti a zsák méretét és eldobja a felesleges anyagokat.
     */
    @Override
    public void onRemove(){
        Zsak zsak = new Zsak();
        int nuk = 0;
        int amino = 0;
        getVirologus().getMezo().getFelszerelesek().add(zsak);

        if(getVirologus().getTarolo().getNukleotid() - (getVirologus().getMaxAnyag() - plusAnyag) > 0){
            nuk = getVirologus().getTarolo().getNukleotid() - (getVirologus().getMaxAnyag() - plusAnyag);
        }

        if(getVirologus().getTarolo().getAminosav() - (getVirologus().getMaxAnyag() - plusAnyag) > 0){
            amino = getVirologus().getTarolo().getAminosav() - (getVirologus().getMaxAnyag() - plusAnyag);
        }

        zsak.setTarolo(new Anyag(nuk,amino));
        getVirologus().setMaxAnyag(getVirologus().getMaxAnyag()-plusAnyag);
        // Eldobja azokat az anyagokat amik nem férnek el a tárolóban
        getVirologus().getTarolo().decrease(new Anyag(nuk, amino));
    }

    @Override
    public String toString() {
        return "Zsak";
    }
}
