package blackJack.gui;

import blackJack.klassen.BlackJackPlayer;
import blackJack.klassen.Karte;
import blackJack.klassen.KartenStapel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BlackJackGame {
    private record PlayerRecord(KartenContainerGUI gui, BlackJackPlayer player) {
        private static final int MAX_VALUE = 21;
        public void addKarte(Karte karte) {
            gui.addKarte(karte);
            player.hit(karte);
            gui.setAnzahl(player.getKartenValue());
        }
        public void addKarte(Karte karte, boolean verdeckt) {
            gui.addKarte(karte, verdeckt);
            player.hit(karte);
            gui.setAnzahl(99999);
        }

        public boolean checkOverstep() {
            return player.getKartenValue() > 21;
        }
    }
    private KartenStapel stapel;
    private Thread playerTurnUeberwachung;
    private BlackJackFrame blackJackGUI;
    private PlayerRecord dealerlayout;
    private List<PlayerRecord> playerLayouts;
    private int turn = 0;
    private Boolean playerTurn = false;

    public BlackJackGame() {
        stapel = new KartenStapel();
        playerLayouts = new LinkedList<>();

        blackJackGUI = new BlackJackFrame("Test", 1);

        dealerlayout = new PlayerRecord(new KartenContainerGUI(), new BlackJackPlayer());
        dealerlayout.gui.setBounds(10, 10, 420, 120);
        dealerlayout.gui.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        dealerlayout.gui.setVisible(true);

        blackJackGUI.add(dealerlayout.gui);

        int playerSize = 3;
        for (int i = 0; i < playerSize; i++) {
            var currentPlayerLayout = new PlayerRecord(new KartenContainerGUI(), new BlackJackPlayer());
            currentPlayerLayout.gui.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            currentPlayerLayout.gui.setBounds(10, 150 + i * 160, 530, 120);
            currentPlayerLayout.gui.setVisible(true);
            currentPlayerLayout.gui.addHitActionListener(e -> {
                currentPlayerLayout.addKarte(stapel.drawKarte());
                if (currentPlayerLayout.checkOverstep())
                    nextPlayer();
            });
            currentPlayerLayout.gui.addStayActionListener(e -> nextPlayer());
            blackJackGUI.add(currentPlayerLayout.gui);
            playerLayouts.add(currentPlayerLayout);
        }
        blackJackGUI.setVisible(true);
    }

    public void deal() {
        for (int i = 0; i < 2; i++) {
            dealerlayout.addKarte(stapel.drawKarte(), i == 1);
            for (PlayerRecord player : playerLayouts)
                player.addKarte(stapel.drawKarte());
        }
    }

    public void playerTurn() {
        playerTurn = true;
        turn = 0;
        playerLayouts.get(0).gui.setEnableButtons(true);
    }

    private void nextPlayer() {
        playerLayouts.get(turn).gui.setEnableButtons(false);
        turn++;
        if (turn >= playerLayouts.size()) {
            playerTurn = false;
            playerTurnUeberwachung.interrupt();
            return;
        }
        playerLayouts.get(turn).gui.setEnableButtons(true);
    }

    public void dealersTurn() {
        playerTurnUeberwachung = new Thread(() -> {
            while (playerTurn)
                try { new CountDownLatch(1).await(); } catch (InterruptedException ignore) { }
        });
        playerTurnUeberwachung.start();
        try { playerTurnUeberwachung.join(); } catch (Exception ignore) { }

        while (dealerlayout.player.getKartenValue() < 17)
            dealerlayout.addKarte(stapel.drawKarte());
    }
}
