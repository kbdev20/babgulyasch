import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TestClass test = new TestClass();

        System.out.println("\n1. Jatek kezdes");
        System.out.println("\n2. Jatekos hozzaadsa");

        Scanner in = new Scanner(System.in);
        int num = 2;
        while (num == 2) {
            num = in.nextInt();
            switch (num) {
                case 1:
                    /**
                     *Játék kezdes
                     */
                    System.out.println("\nJatek kezdes.");
                    test.JatekKezdes();
                    Jatek(num, in, test);
                    test.JatekVege();
                    //TODO: amit irtam commitba
                    break;
                case 2:
                    System.out.println("\nUj jatekost adott hozza!");
            }
        }

        /**
         * Benitas alatt lep.
         */
        System.out.println("\nBenitas alatt lep");
        test.BenitasAlattLep();

        /**
         * Amnezia alatt lep.
         */
        System.out.println("\nAmnezia alatt lep");
        test.AmneziaAlattLep();

        /**
         * Vitustanc alatt lep.
         */
        //TODO: EZ valamiért nem uködik????
        //System.out.println("\nVítustancalatt lep");
        // test.VitustáncAlattLep();

        /**
         * Vedettseg alatt lep.
         */
        System.out.println("\nVedettseg alatt lep");
        test.VedettsegAlattLep();

        /**
         * Anyag felvétel bénult játékostól
         */
        System.out.println("\n Anyag felvétel bénult játékostól");
        test.AnyagfelvetelBenultJatekostol();
    }

    /**
     * A játék menüje , választási lehetőségei
     * @param num  -beolvasott szam
     * @param in   -Scanner
     * @param test _teszt osztály
     */
    public static void Jatek(int num, Scanner in, TestClass test) {
        boolean vege = true;
        while (vege) {
            //Kb. amiket tud majd csinálni
            System.out.println("1. Mozog");
            System.out.println("2. Keni magat");
            System.out.println("3. Keni mast");
            System.out.println("4. Vitustanc agenst csinal");
            System.out.println("5. Vege");
            num = in.nextInt();
            switch (num) {
                case 1:
                    //választ hogy hova lép
                    Kiiras2();
                    num = in.nextInt();
                    switch (num) {
                        case 1:
                            test.Ures2Ures();
                            break;
                        case 2:
                            test.AnyagfelvetelRaktarbol();
                            break;
                        case 3:
                            test.FelszerelesFelvetele();
                            break;
                        case 4:
                            test.AmneziaLetapogatas();
                            break;
                    }
                    break;
                case 2:
                    Kiiras();
                    // választ hogy mit ken
                    num = in.nextInt();
                    switch (num) {
                        case 1:
                            Vitustanc a = new Vitustanc();
                            test.VirMagaraAgenstKen(a);
                            break;
                        case 2:
                            Benitottsag b = new Benitottsag();
                            test.VirMagaraAgenstKen(b);
                            break;
                        case 3:
                            Vedettseg c = new Vedettseg();
                            test.VirMagaraAgenstKen(c);
                            break;
                        case 4:
                            Amnezia d = new Amnezia();
                            test.VirMagaraAgenstKen(d);
                            break;
                    }
                    break;
                case 3:
                    Kiiras();
                    num = in.nextInt();
                    switch (num) {
                        case 1:
                            Vitustanc a = new Vitustanc();
                            test.VirKenVirt(a);
                            break;
                        case 2:
                            Benitottsag b = new Benitottsag();
                            test.VirKenVirt(b);
                            break;
                        case 3:
                            Vedettseg c = new Vedettseg();
                            test.VirKenVirt(c);
                            break;
                        case 4:
                            Amnezia d = new Amnezia();
                            test.VirKenVirt(d);
                            break;
                    }
                    break;
                case 4:
                    Vitustanc_kod k = new Vitustanc_kod();
                    test.VitustancAgenstLetrehoz(k);
                    break;
                case 5:
                    //csak hogy egyszer kilépjen
                    vege = false;

                    break;


            }
            test.JatekLeptetes();
        }
    }

    //csak kirija ezeket a dolgokat
    public static void Kiiras() {
        System.out.println("1. Vitustanccal");
        System.out.println("2. Benitoval");
        System.out.println("3. Vedettseggel");
        System.out.println("4. Amneziaval");
    }

    //csak kirija ezeket a dolgokat
    public static void Kiiras2() {
        System.out.println("1. Ures mezore");
        System.out.println("2. Raktarra");
        System.out.println("3. Varosra");
        System.out.println("4. Laborra");
    }

}