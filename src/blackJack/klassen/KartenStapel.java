package blackJack.klassen;

import java.util.LinkedList;
import java.util.List;

public class KartenStapel {
    private final List<Karte> kartenStapel;

    public KartenStapel() {
        this.kartenStapel = new LinkedList<>();
        fillKartenStapel();
    }

    private void fillKartenStapel() {
        this.kartenStapel.clear();
        for (Farbe farbe : Farbe.values()) {
            for (Zahl zahl : Zahl.values())
                this.kartenStapel.add(new Karte(farbe, zahl));
        }
        this.shuffel();
    }

    private void shuffel() {
        for (int i = 0; i < 52; i++) {
            int rand = (int) (Math.random() * (52 - i));
            this.kartenStapel.add(this.kartenStapel.remove(rand));
        }
    }
    public Karte drawKarte() {
        return this.kartenStapel.remove(this.kartenStapel.size() - 1);
    }
}
