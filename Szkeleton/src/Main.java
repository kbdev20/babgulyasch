import java.util.Scanner;

public class Main {
    public static GameManager gm = new GameManager();

    public static void main(String[] args) {
        IFio.print("Prototípus: Bezárható üres soron ENTER lenyomásával.");
        IFio.print("Információ parancsokról \"help\" segítségével vagy a dokumentációban.");
        Scanner sc = new Scanner(System.in);
        String sor = null;
        while (!(sor = sc.nextLine().toLowerCase()).isEmpty()) {
            String[] bemenet = sor.split(" ", 2);
            String parancs = bemenet[0];
            String parameter = "";
            if (bemenet.length > 1) parameter = bemenet[1];
            Leironyelv interfesz = new Leironyelv();

            interfesz.command_switch(parancs, parameter);
            }
        }
    }