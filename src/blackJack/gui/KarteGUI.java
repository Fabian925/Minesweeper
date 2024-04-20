package blackJack.gui;

import blackJack.klassen.Karte;

import javax.swing.*;
import java.awt.*;

class KarteGUI extends JPanel {
    private boolean verdeckt = false;
    private Karte karte;

    public KarteGUI() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(null);
        setPreferredSize(new Dimension(70, 100));
    }

    public KarteGUI(Karte karte) {
        this();
        this.karte = karte;

        var zahl = new JLabel(karte.getZahl().toString());
        zahl.setFont(new Font(null, Font.PLAIN, 10));
        zahl.setBounds(10, 10, 50, 20);
        add(zahl);

        var farbe = new JLabel(karte.getFarbe().toString());
        farbe.setBounds(10, 35, 50, 20);
        add(farbe);
    }

    public KarteGUI(Karte karte, boolean verdeckt) {
        this(karte);
        this.verdeckt = verdeckt;
    }


    public void addKarte(Karte karte) {

    }
}
