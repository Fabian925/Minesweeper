package blackJack.klassen;

import java.util.LinkedList;
import java.util.List;

// Interface für dealer und Player


/**
 * Delear:
 * - var hand
 * - hit()
 * - stay()
 *
 * Player:
 * - var hand
 * - var moeny
 * - hit()
 * - stay()
 * - double()
 * - split()
 * - bet()S
 */
public class BlackJackPlayer {
    private List<Karte> hand = new LinkedList<>();

    public void hit(Karte karte) {
        this.hand.add(karte);
    }

    public int getKartenValue() {
        int anzahl = getRawKartenValue();

        // Die Schleife wird auch aufgerufen, wenn die anzahl < 21. Ist ein wenig unnötig.
        List<Karte> alleAssen = hand.stream().filter(karte -> karte.getZahl() == Zahl.ASS).toList();
        for (Karte ignore : alleAssen) {
            if (anzahl > 21)
                anzahl -= 10;
        }
        return anzahl;
    }

    private int getRawKartenValue() {
        return hand.stream().mapToInt(karte ->
            switch (karte.getZahl()) {
                case ZWEI -> 2;
                case DREI -> 3;
                case VIER -> 4;
                case FUENF -> 5;
                case SECHS -> 6;
                case SIEBEN -> 7;
                case ACHT -> 8;
                case NEUN -> 9;
                case ZEHN, BUBE, DAME, KOENING -> 10;
                case ASS -> 11;
            }
        ).sum();
    }

    public List<Karte> getHand() {
        return hand;
    }
}
