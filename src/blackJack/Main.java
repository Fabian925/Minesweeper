package blackJack;

import blackJack.gui.BlackJackGame;

import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var spiel = new BlackJackGame();
        spiel.deal();
        spiel.playerTurn();
        spiel.dealersTurn();
    }
}
