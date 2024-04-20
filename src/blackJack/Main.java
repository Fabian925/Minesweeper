package blackJack;

import blackJack.gui.BlackJackGame;

public class Main {

    public static void main(String[] args) {

        var spiel = new BlackJackGame();
        spiel.deal();
        spiel.dealersTurn();
    }
}
