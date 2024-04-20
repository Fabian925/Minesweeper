package blackJack.gui;

import gameCode.Schwierigkeit;

import javax.swing.*;
import java.awt.*;

public class BlackJackFrame extends JFrame {

    public BlackJackFrame(String title, int players) {
        super();
        setTitle(title);
        setPreferredSize(new Dimension(1000, 700));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        pack();
    }

}
