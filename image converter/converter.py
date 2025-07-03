from PIL import Image

def main():
    # Pfad vom Benutzer einlesen (Windows-Anführungszeichen beachten)
    dateipfad = input('Pfad zur PNG-Datei angeben: ').strip('"')

    # Bild im RGBA-Modus öffnen, um Transparenz zu erhalten
    bild = Image.open(dateipfad).convert('RGBA')

    breite, hoehe = bild.size

    # Ergebnisdatei
    ausgabe_datei = input('Dateiname: ') + ".txt"

    with open(ausgabe_datei, 'w') as f:
        # Breite und Höhe in der ersten Zeile
        f.write(f"{breite} {hoehe}\n")

        # Spaltenweise durch das Bild iterieren
        for x in range(breite):
            # pro Spalte alle Pixel auslesen
            spalten_pixel = [bild.getpixel((x, y)) for y in range(hoehe)]

            # Pixel paarweise ausgeben (2 Pixel pro Zeile)
            for i in range(0, hoehe, 2):
                def rgb_or_invalid(p):
                    r, g, b, a = p
                    return (-1, -1, -1) if a == 0 else (r, g, b)

                p1 = rgb_or_invalid(spalten_pixel[i])

                # prüfen, ob es ein zweites Pixel im Paar gibt
                if i + 1 < hoehe:
                    p2 = rgb_or_invalid(spalten_pixel[i + 1])
                    zeile = f"{p1[0]} {p1[1]} {p1[2]} {p2[0]} {p2[1]} {p2[2]}"
                else:
                    zeile = f"{p1[0]} {p1[1]} {p1[2]}"
                f.write(zeile + "\n")

    print(f"Fertig! Ausgabe in '{ausgabe_datei}'")

if __name__ == "__main__":
    main()
