package blackJack.klassen;

import blackJack.klassen.Karte;
import blackJack.klassen.Zahl;

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
 * - var mondy
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
        int anzahl = hand.stream().mapToInt(karte ->
            switch (karte.getZahl()) {
                case ZWEI -> 2;
                case DREI -> 3;
                case VIER -> 4;
                case FUENF -> 5;
                case SECHS -> 6;
                case SIEBEN -> 7;
                case ACHT -> 8;
                case NEUN -> 9;
                case ZEHN -> 10;
                case BUBE -> 10;
                case DAME -> 10;
                case KOENING -> 10;
                case ASS -> 11;
            }
        ).sum();

        // Die Schleife wird auch aufgerufen, wenn die anzahl < 21. Ist ein wenig unnötig.
        for (Karte karte : hand.stream().filter(karte -> karte.getZahl() == Zahl.ASS).toList()) {
            if (anzahl > 21)
                anzahl -= 10;
        }

        return anzahl;
    }

    public List<Karte> getHand() {
        return hand;
    }

}
