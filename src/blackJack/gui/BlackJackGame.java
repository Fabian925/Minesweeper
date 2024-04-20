package blackJack.gui;

import blackJack.klassen.BlackJackPlayer;
import blackJack.klassen.KartenStapel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class BlackJackGame {

    private KartenStapel stapel;
    private BlackJackPlayer dealer;
    private int player; //TODO Player object machen Array
    private BlackJackFrame blackJackGUI;
    private KartenContainerGUI dealerlayout;
    private List<KartenContainerGUI> playerLayouts;

    private int turn = 0;

    public BlackJackGame() {
        stapel = new KartenStapel();
        dealer = new BlackJackPlayer();
        playerLayouts = new LinkedList<>();

        blackJackGUI = new BlackJackFrame("Test", 1);

        dealerlayout = new KartenContainerGUI(dealer);
        dealerlayout.setBounds(10, 10, 420, 120);
        dealerlayout.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        dealerlayout.setVisible(true);
        blackJackGUI.add(dealerlayout);


        int playerSize = 3;
        for (int i = 0; i < playerSize; i++) {
            var playerLayout = new KartenContainerGUI(new BlackJackPlayer());
            playerLayout.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            playerLayout.setBounds(10, 150 + i * 160, 530, 120);
            playerLayout.setVisible(true);
            if (i == 0)
                playerLayout.setEnableButtons(true);
            playerLayout.addHitActionListener(e -> playerLayout.addKarte(stapel.drawKarte()));
            playerLayout.addStayActionListener(e -> nextPlayer());
            this.playerLayouts.add(playerLayout);
            blackJackGUI.add(playerLayout);
        }

        blackJackGUI.setVisible(true);
    }

    public void deal() {
        for (int i = 0; i < 2; i++) {
            var dealerKarte = stapel.drawKarte();
            dealerlayout.addKarte(dealerKarte);
            //TODO for player in players hit kart
        }
    }


    public void nextPlayer() {
        playerLayouts.get(turn).setEnableButtons(false);
        turn++;
        if (turn >= playerLayouts.size())
            return;
        playerLayouts.get(turn).setEnableButtons(true);
    }

    public void dealersTurn() {
        while (dealer.getKartenValue() < 17) {
            var dealerKarte = stapel.drawKarte();
            dealer.hit(dealerKarte);
            dealerlayout.addKarte(dealerKarte);
        }
    }
}
