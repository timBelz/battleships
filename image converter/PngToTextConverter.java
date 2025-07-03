import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class PngToTextConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Pfad zur PNG-Datei angeben: ");
        String dateipfad = scanner.nextLine().replace("\"", "");

        System.out.print("Dateiname für Ausgabe (ohne .txt): ");
        String ausgabedatei = scanner.nextLine() + ".txt";

        try {
            BufferedImage bild = ImageIO.read(new File(dateipfad));
            int breite = bild.getWidth();
            int hoehe = bild.getHeight();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ausgabedatei))) {
                writer.write(breite + " " + hoehe);
                writer.newLine();

                for (int x = 0; x < breite; x++) {
                    int[] spalte = new int[hoehe];

                    for (int y = 0; y < hoehe; y++) {
                        int argb = bild.getRGB(x, y);
                        spalte[y] = argb;
                    }

                    for (int i = 0; i < hoehe; i += 2) {
                        String zeile = pixelToString(spalte[i]);

                        if (i + 1 < hoehe) {
                            zeile += " " + pixelToString(spalte[i + 1]);
                        }

                        writer.write(zeile);
                        writer.newLine();
                    }
                }
            }

            System.out.println("Fertig! Ausgabe in '" + ausgabedatei + "'");

        } catch (IOException e) {
            System.err.println("Fehler beim Verarbeiten der Datei: " + e.getMessage());
        }
    }

    private static String pixelToString(int argb) {
        int alpha = (argb >> 24) & 0xff;
        if (alpha == 0) {
            return "-1 -1 -1";
        } else {
            int r = (argb >> 16) & 0xff;
            int g = (argb >> 8) & 0xff;
            int b = argb & 0xff;
            return r + " " + g + " " + b;
        }
    }
}
