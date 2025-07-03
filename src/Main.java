/*TODO aber kein bock:  beim laden größerer Bilder in existierende layer exeption fixen
*                       machen wir aber eigentlich eh nicht...
*/
public class Main {
    //Max 240x68 auf Win10 1920x1080 100%
    public static int breite = 120;
    public static int hoehe = 40;
    public static ScreenLayer background = new ScreenLayer(breite, hoehe);
    public static ScreenLayer foreground = new ScreenLayer(breite, hoehe);

    public static void main(String[] args) throws Exception {
        Screen terminal = new Screen(breite, hoehe);
        terminal.openTerminal();

        background.layer = Image.loadImage("battelship.txt", 0, 0);
        foreground.layer = Image.loadImage("test10.txt", 20, 10);
        foreground.layer = Image.loadIntoLayer(foreground, "test10.txt", 50, 30);

        terminal.drawLayers(new ScreenLayer[] {background, foreground});
    }
}
