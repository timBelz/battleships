package game;

import java.io.IOException;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.Indexed;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;

public class Game {

	public enum Richtung {
		Rechts, Links, Unten, Oben
	}

	public static int gameHeight = 68;
	public static int gameWidth = 240;
	public static Pixel[][] game;
	public static TextColor DefaultBackColor = TextColor.ANSI.BLACK;
	public static TextColor DefaultTextColor = TextColor.ANSI.WHITE;
  
    public static MusikPlayer music = new MusikPlayer();

	public static void main(String[] args) throws IOException {

		// Spielfeld mit "Pixeln" wird angelegt
		// jedes Pixel kann einen char, eine Hintergrundfarbe und eine Textfarbe haben
		game = new Pixel[gameWidth][gameHeight];
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				game[i][j] = new Pixel();
			}
		}

		// Fenster (Terminal) erstellen und anzeigen
		TerminalFactory factory = new DefaultTerminalFactory()
				.setInitialTerminalSize(new TerminalSize(gameWidth, gameHeight));
		Terminal terminal = factory.createTerminal();
		terminal.setCursorVisible(false);

		// "Application-loop" - kehrt immer wieder zum Startbildschirm zurück
		// wird beim Startbildschirm ESCAPE gedrückt wird die Anwendung beendet
		while (true) {
			// zeigt simple Startseite an, die mit "Enter" oder "Escape" verlassen wird
			showStartseite(terminal);

			// "Game-loop" wird hier ausgeführt
			double delay = 10;
			runGame(delay, terminal);
			
			// GAME OVER  hier hinzufügen
		}

	}

	private static void runGame(double delay, Terminal terminal) throws IOException {

		// initiale Spieleinstellungen
		Richtung richtung = Richtung.Rechts;
		int posX = gameWidth / 2;
		int posY = gameHeight / 2;

		// Startfarbe
		int r = 3, g = 33, b = 66;

		// Spiel in "Dauerschleife" (game loop)
		while (true) {

			ClearSpielfeld();

			// Hintergrundfarbe mit RGB (ACHTUNG 6x6x6 Color Cube)
			// siehe TextColor Klasse in Lanterna

			// obere Zeile einfärben
			for (int i = 0; i < gameWidth; i++) {
				game[i][0].backColor = Indexed.fromRGB(r, g, b);
			}

			// untere Zeile einfärben
			for (int i = 0; i < gameWidth; i++) {
				game[i][gameHeight - 1].backColor = Indexed.fromRGB(r, g, b);
			}

			// linke Spalte einfärben
			for (int i = 0; i < gameHeight; i++) {
				game[0][i].backColor = Indexed.fromRGB(r, g, b);
				game[1][i].backColor = Indexed.fromRGB(r, g, b);
			}

			// rechte Spalte einfärben
			for (int i = 0; i < gameHeight; i++) {
				game[gameWidth - 2][i].backColor = Indexed.fromRGB(r, g, b);
				game[gameWidth - 1][i].backColor = Indexed.fromRGB(r, g, b);
			}

			// Farbe verändern 
			// (Modulo wird verwendet, weil die Zahlen nur zwischen 0-255 liegen dürfen
			r += 3;
			g += 3;
			b += 3;
			r %= 256;
			g %= 256;
			b %= 256;

			// Tastatureinggabe wird gelesen
			//KeyStroke eingabe = terminal.readInput(); 	// stopped und wartet auf Eingabe
			KeyStroke eingabe = terminal.pollInput(); 		// läuft weiter, auch wenn keine Eingabe erfolgt ist
			if (eingabe != null) {

				// wenn die linke Pfeiltaste gedrückt wird
				if (eingabe.getKeyType().equals(KeyType.ArrowLeft)) {
					// kann nicht in entgegen gesetzte Richtung laufen (z.B. Snake)
					if (richtung != Richtung.Rechts) {
						richtung = Richtung.Links;
						
						// wenn der Spielfeld verlassen wird, dann ...
						if (posX <= 1) {
							posX = gameWidth - 1;
						} else {
							posX -= 2;
						}
					}
				}

				// wenn die rechte Pfeiltaste gedrückt wird
				if (eingabe.getKeyType().equals(KeyType.ArrowRight)) {
					if (richtung != Richtung.Links) {
						richtung = Richtung.Rechts;
						if (posX >= gameWidth - 2) {
							posX = 0;
						} else {
							posX += 2;
						}
					}
				}

				// wenn die Pfeil nach oben Taste gedrückt wird
				if (eingabe.getKeyType().equals(KeyType.ArrowUp)) {
					if (richtung != Richtung.Unten) {
						richtung = Richtung.Oben;
						if (posY <= 0) {
							posY = gameHeight - 1;
						} else {
							posY -= 1;
						}
					}
				}

				// wenn die Pfeil nach unten Taste gedrückt wird
				if (eingabe.getKeyType().equals(KeyType.ArrowDown)) {
					if (richtung != Richtung.Oben) {
						richtung = Richtung.Unten;
						if (posY >= gameHeight - 1) {
							posY = 0;
						} else {
							posY += 1;
						}
					}
				}

				// wenn ESC Taste gedrückt wird
				if (eingabe.getKeyType().equals(KeyType.Escape)) {
					// Spielfeld leerräumen für den GAME OVER / Startbildschirm
					ClearSpielfeld();
					WriteSpielfeld(terminal);
					break;
				}
			}

			// Zeichen setzen
			game[posX][posY].Text = '\u2580';

			try {
				// zeichnet das gesamte Spielfeld auf einmal
				WriteSpielfeld(terminal);
				// kurzer "Schlaf", es kann hier mit der Verzoegerung die
				// Spielgeschwindigkeit eingestellt werden
				Thread.sleep((int) delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void showStartseite(Terminal terminal) throws IOException {

		terminal.clearScreen();

		// Startseite mit Text
		// der Text wird hier direkt in das Terminal geschrieben und ncht in das Spielfeld
		terminal.setCursorPosition(6, 6);	
		Write("███╗   ███╗██╗   ██╗     ██████╗  █████╗ ███╗   ███╗███████╗", terminal);
		terminal.setCursorPosition(6, 7);
		Write("████╗ ████║╚██╗ ██╔╝    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝", terminal);
		terminal.setCursorPosition(6, 8);
		Write("██╔████╔██║ ╚████╔╝     ██║  ███╗███████║██╔████╔██║█████╗  ", terminal);
		terminal.setCursorPosition(6, 9);
		Write("██║╚██╔╝██║  ╚██╔╝      ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝  ", terminal);
		terminal.setCursorPosition(6, 10);
		Write("██║ ╚═╝ ██║   ██║       ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗", terminal);
		terminal.setCursorPosition(6, 11);
		Write("╚═╝     ╚═╝   ╚═╝        ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝", terminal);

		// Cursor auf Position bewegen
		terminal.setCursorPosition(6, 14);
		Write("Willkommen im Spiel", terminal); // Text schreiben
		terminal.setCursorPosition(6, 15);
		Write("Drücke ENTER, um das Spiel zu starten.", terminal);
		terminal.setCursorPosition(6, 16);
		Write("Drücke ESCAPE, um das Spiel zu verlassen.", terminal);

		// Texte im Terminal anzeigen
		terminal.flush();

        // Musik abspielen
        music.startBackgroundMusik(); // start background music as loop
    

		// Eingabe abwarten
		while (true) {

			// Tastatureinggabe wird gelesen
			KeyStroke eingabe = terminal.readInput();
			if (eingabe != null) {

            // System.out.println(eingabe); // zur Kontrolle kann eingebene
            // Taste angezeigt werden

            // wenn die Taste ENTER gedruckt wird
            if (eingabe.getKeyType().equals(KeyType.Enter)) {

                // Startbildschirm wird beendet / while Schleife wird 
                // unterbrochen (GAME-Loop wird danach gestartet - siehe main-Methode 
                break;
            }

        if (eingabe.getCharacter() == 'w') {
          // handle w
        }
        else if (eingabe.getCharacter() == 'a') {
          // handle a
        }
        else if (eingabe.getCharacter() == 's') {
          // handle s
        }
        else if (eingabe.getCharacter() == 'd') {
          // handle d
        }

				// wenn die Taste ESC gedrückt wird, beendet sich das Programm
				if (eingabe.getKeyType().equals(KeyType.Escape)) {
					System.exit(0);
				}
			}
		}
	}

	// Diese Methode hilft einen String zu "Zeichnen"
	private static void Write(String print, Terminal terminal) throws IOException {
		char[] printToChar = print.toCharArray();
		for (int i = 0; i < print.length(); i++) {
			terminal.putCharacter(printToChar[i]);
		}
	}

	// Diese Methode zeichnet das gesamte Spielfeld auf einmal
	private static void WriteSpielfeld(Terminal terminal) throws IOException {

		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				terminal.setCursorPosition(i, j);
				terminal.setForegroundColor(game[i][j].textColor);
				terminal.setBackgroundColor(game[i][j].backColor);
				terminal.putCharacter(game[i][j].Text);
			}
		}

		terminal.flush();
	}

	// löscht den Inhalt vom Spielfeld
	private static void ClearSpielfeld() {

		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game[i].length; j++) {
				game[i][j].textColor = DefaultTextColor;
				game[i][j].backColor = DefaultBackColor;
				game[i][j].Text = ' ';
			}
		}
	}

}
