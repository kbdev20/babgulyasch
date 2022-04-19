public class Anyag {
    private int nukleotid;
    private int aminosav;

    public Anyag(int nuk, int ami) {
        nukleotid = nuk;
        aminosav = ami;
    }

    public int getAminosav() {
        return aminosav;
    }

    public void setAminosav(int aminosav) {
        this.aminosav = aminosav;
    }

    public int getNukleotid() {
        return nukleotid;
    }

    public void setNukleotid(int nukleotid) {
        this.nukleotid = nukleotid;
    }

    public void increase(Anyag a) {
        nukleotid += a.getNukleotid();
        aminosav += a.getAminosav();
    }

    public void decrease(Anyag a) {
        nukleotid -= a.getNukleotid();
        aminosav -= a.getAminosav();
    }
}
