import java.util.Scanner;

public class Main {
    public static GameManager gm = new GameManager(4);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sor = null;
        while (!(sor = sc.nextLine().toLowerCase()).isEmpty()) {
            String[] bemenet = sor.split(" ", 2);
            String parancs = bemenet[0];
            String parameter = "";
            if (bemenet.length > 1) parameter = bemenet[1];
            Leironyelv interfesz = new Leironyelv();

            switch (parancs) {
                case "create":
                    interfesz.create(parameter);
                    break;
                case "help":
                    interfesz.help(parameter);
                    break;
                case "info":
                    interfesz.info(parameter);
                    break;
                case "put":
                    interfesz.put(parameter);
                    break;
                case "list":
                    interfesz.list(parameter);
                    break;
                case "load":
                    interfesz.load(parameter);
                    break;
                case "save":
                    interfesz.save(parameter);
                    break;
                case "move":
                    interfesz.move(parameter);
                    break;
                case "print_to_file":
                    interfesz.print_to_file(parameter);
                    break;
                case "give":
                    interfesz.give(parameter);
                    break;
                case "step":
                    interfesz.step(parameter);
                    break;
                case "generate_map":
                    interfesz.generate_map(parameter);
                    break;
                case "smear_virus":
                    interfesz.smear_virus(parameter);
                    break;
                case "kill":
                    interfesz.kill(parameter);
                    break;
                case "make_agent":
                    interfesz.make_agent(parameter);
                    break;
                case "random":
                    interfesz.random(parameter);
                    break;
                case "clear":
                    interfesz.clear(parameter);
                    break;
                case "defensivecoat":
                    interfesz.defensivecoat(parameter);
                    break;
                case "test_mode":
                    interfesz.test_mode(parameter);
                    break;
                case "connect":
                    interfesz.connect(parameter);
                    break;
            }
        }
    }
}