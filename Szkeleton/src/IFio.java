import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Interfesz leiro nyelv input-output kezelő osztálya, a protótípus tesztelésére
 */
public class IFio {
    public static boolean printtofile = false;
    public static boolean savetobuffer = false;

    private static String buffer;

    /**
     * Beolvassa a fájlt
     *
     * @param s
     */
    public static void print(String s) {
        System.out.println(s);
        if (savetobuffer) {
            buffer += s + "\n";
        }
        if (printtofile) {
            FileOutputStream f;
            File fajl;
            try {
                fajl = new File("output.txt");
                f = new FileOutputStream(fajl);
                if (!fajl.exists()) fajl.createNewFile();
                s = s + "\n";
                f.write(s.getBytes());
                f.flush();
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * vissza adj a buffert
     *
     * @return
     */
    public static String readbuffer() {
        return buffer;
    }

    /**
     * törli a buffert
     *
     */
    public static void clearbuffer() {
        buffer = "";
    }
}
