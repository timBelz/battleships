import com.googlecode.lanterna.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import java.io.IOException;

public class Screen {
    public int breite;
    public int hoehe;
    TerminalFactory factory;
    Terminal terminal;

    public Screen(int breite, int hoehe) {
        this.breite = breite;
        this.hoehe = hoehe;
        factory = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(breite, hoehe));
    }
    public void openTerminal() throws IOException {
        terminal = factory.createTerminal();
        terminal.setCursorVisible(false);
    }
    public void drawScreen(ScreenLayer texel_buffer) throws IOException {
        //Draw texel_buffer Array in/on terminal/screen
        for (int i = 0; i < texel_buffer.layer.length; i++) {
            for (int k = 0; k < texel_buffer.layer[0].length; k++) {
                terminal.setCursorPosition(i, k);
                terminal.setForegroundColor(texel_buffer.layer[i][k].font_color);
                terminal.setBackgroundColor(texel_buffer.layer[i][k].bg_color);
                terminal.putCharacter(texel_buffer.layer[i][k].text);
                /*terminal.flush();
                Thread.sleep(1);*/
            }
        }
        terminal.flush();
    }
    public void drawLayers(ScreenLayer[] layers) throws IOException {
        drawScreen(Image.combinedLayers(layers));
    }
}
