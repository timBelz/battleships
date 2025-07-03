import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Image {
    public static Texel[][] loadImage(String filename, int start_x, int start_y) {
        //!!! File path !!!
        try (Scanner scanner = new Scanner(new File("assets\\" + filename))) {
            int img_breite = scanner.nextInt();
            int img_hoehe = scanner.nextInt();

            Texel[][] buffer = initialise();
            for (int i = start_x; i < start_x + img_breite; i++) {
                for (int k = start_y; k < start_y + img_hoehe / 2; k++) {
                    if (k > Main.hoehe || i > Main.breite) break;
                    //font color
                    int r1 = scanner.nextInt();
                    int g1 = scanner.nextInt();
                    int b1 = scanner.nextInt();
                    //bg color
                    int r2 = scanner.nextInt();
                    int g2 = scanner.nextInt();
                    int b2 = scanner.nextInt();

                    if (r1 >= 0) {
                        buffer[i][k].font_transparent = false;
                        buffer[i][k].setFontColor(r1, g1, b1);
                    } else {
                        buffer[i][k].font_transparent = true;
                    }
                    if (r2 >= 0) {
                        buffer[i][k].bg_transparent = false;
                        buffer[i][k].setBgColor(r2, g2, b2);
                    } else {
                        buffer[i][k].bg_transparent = true;
                    }
                }
            }
            return buffer;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static Texel[][] initialise() {
        Texel[][] layer = new Texel[Main.breite][Main.hoehe];
        for (int i = 0; i < Main.breite; i++) {
            for (int k = 0; k < Main.hoehe; k++) {
                layer[i][k] = new Texel();
                layer[i][k].font_transparent = true;
                layer[i][k].bg_transparent = true;
            }
        }
        return layer;
    }
    public static ScreenLayer combinedLayers(ScreenLayer[] layers) {
        for (int i = 1; i < layers.length; i++) {
            for (int x = 0; x < Main.breite; x++) {
                for (int y = 0; y < Main.hoehe; y++) {
                    Texel src = layers[i].layer[x][y];
                    Texel dst = layers[0].layer[x][y];

                    if (src == null || dst == null) continue;

                    if (!src.font_transparent) {
                        dst.font_color = src.font_color;
                        dst.font_transparent = false;
                    }
                    if (!src.bg_transparent) {
                        dst.bg_color = src.bg_color;
                        dst.bg_transparent = false;
                    }
                }
            }
        }
        return layers[0];
    }
    public static Texel[][] loadIntoLayer(ScreenLayer layer, String filename, int start_x, int start_y) {
        ScreenLayer layer2 = new ScreenLayer(Main.breite, Main.hoehe);
        layer2.layer = loadImage(filename, start_x, start_y);
        layer = combinedLayers(new ScreenLayer[] {layer, layer2});
        return layer.layer;
    }
}
