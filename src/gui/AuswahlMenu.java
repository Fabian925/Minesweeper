package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import gameCode.Schwierigkeit;

public class AuswahlMenu extends JFrame{

    private final JComboBox<Schwierigkeit> schwierigkeit = new JComboBox<>();
	private Schwierigkeit auswahl = null;
	
	public AuswahlMenu(String title) {
		setTitle(title);
		setBounds(10, 10, 352, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		Container cp = getContentPane();

        JLabel titelVomSpiel = new JLabel("<html>Minesweeper" + "<br/>" + "Von Roalter Fabian</html>", SwingConstants.CENTER);
		titelVomSpiel.setBounds(0, 10, 325, 100);
		titelVomSpiel.setFont(new Font("SansSerif", Font.BOLD, 20));
		cp.add(titelVomSpiel);

		for (Schwierigkeit s : Schwierigkeit.values())
			schwierigkeit.addItem(s);
		schwierigkeit.setBounds(55, 125, 200, 20);
		cp.add(schwierigkeit);

        JButton losButton = new JButton("Los Gehts!");
		losButton.setBounds(190, 225, 125, 30);
		losButton.addActionListener(e -> {
            auswahl = (Schwierigkeit) schwierigkeit.getSelectedItem();
			AuswahlMenu.this.dispose();
        });
		cp.add(losButton);

        JButton schliessenButton = new JButton("Tschüss!");
		schliessenButton.setBounds(55, 225, 125, 30);
		schliessenButton.addActionListener(e -> AuswahlMenu.this.dispose());
		cp.add(schliessenButton);
		setVisible(true);
	}

	/** Liefert den Schwierigkeitsgrad. Wenn keiner ausgewählt wurde, dann wird null geliefert */
	public Schwierigkeit wahl() {
		return this.auswahl;
	}
	public static void main(String[] args) {
		var auswahl = new AuswahlMenu("Minesweeper");
		auswahl.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Schwierigkeit s = auswahl.wahl();
				if (s != null)
					new MinesweeperGUI("Minesweeper: " + s, s);
				else
					auswahl.dispose();
			}
		});
	}
}

