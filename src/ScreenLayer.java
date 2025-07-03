//create Pixel Array | used to manipulate before drawing on the terminal
public class ScreenLayer {
    Texel[][] layer;
    public ScreenLayer(int breite, int hoehe) {
        layer = new Texel[breite][hoehe];
    }
}
