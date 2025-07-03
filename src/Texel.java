import com.googlecode.lanterna.TextColor;

public class Texel {
    public TextColor font_color;
    public TextColor bg_color;
    public char text;
    public boolean font_transparent;
    public boolean bg_transparent;

    public Texel() {
        this.font_color = TextColor.ANSI.BLACK;
        this.bg_color = TextColor.ANSI.BLACK;
        this.text = '\u2580';
        this.font_transparent = false;
        this.bg_transparent = false;
    }
    public void setFontColor(int r, int g, int b) {
        font_color = new TextColor.RGB(r, g, b);
    }
    public void setBgColor(int r, int g, int b) {
        bg_color = new TextColor.RGB(r, g, b);
    }
}
