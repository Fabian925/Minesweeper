package blackJack.gui;

import blackJack.klassen.Karte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class KartenContainerGUI extends JPanel {

    private JLabel kartenLayout;
    private JButton hitButton;
    private JButton stayButton;
    private JLabel anzahl;

    public KartenContainerGUI() {
        setLayout(null);
        kartenLayout = new JLabel();
        kartenLayout.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        kartenLayout.setBounds(0, 0, 420, 120);
        kartenLayout.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        add(kartenLayout);

        hitButton = new JButton("Hit");
        hitButton.setBounds(430, 95, 100, 25);
        hitButton.setEnabled(false);
        add(hitButton);

        stayButton = new JButton("Stay");
        stayButton.setBounds(430, 65, 100, 25);
        stayButton.setEnabled(false);
        add(stayButton);

        anzahl = new JLabel("0");
        anzahl.setBounds(430, 35, 100, 25);
        add(anzahl);
    }

    public void addKarte(Karte karte) {
        var newKartenGUI = new KarteGUI(karte);
        newKartenGUI.setVisible(true);
        kartenLayout.add(newKartenGUI);
        kartenLayout.revalidate();
        kartenLayout.repaint();
    }

    public void addKarte(Karte karte, boolean aufgedeckt) {
        var newKartenGUI = new KarteGUI(karte, aufgedeckt);
        newKartenGUI.setVisible(true);
        kartenLayout.add(newKartenGUI);
        kartenLayout.revalidate();
        kartenLayout.repaint();
    }

    public void addHitActionListener(ActionListener e) {
        this.hitButton.addActionListener(e);
    }
    public void addStayActionListener(ActionListener e) {
        this.stayButton.addActionListener(e);
    }

    public void setEnableButtons(boolean enable) {
        hitButton.setEnabled(enable);
        stayButton.setEnabled(enable);
    }

    public void setAnzahl(int anzahl) {
        this.anzahl.setText(Integer.toString(anzahl));
    }
}
