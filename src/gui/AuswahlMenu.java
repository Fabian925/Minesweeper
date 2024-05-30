package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.*;

import gameCode.Schwierigkeit;

public class AuswahlMenu extends JFrame{

    private final JComboBox<Schwierigkeit> schwierigkeit = new JComboBox<>();
	private Schwierigkeit auswahl = null;
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public AuswahlMenu(String title) {
		setTitle(title);
		setBounds(10, 10, 352, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
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
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				synchronized (closed) {
					closed.set(true);
					closed.notify();
				}
			}
		});
	}

	/**
	 * Liefert die Auswahl. Blockiert, solange das Fenster noch nicht geschlossen wurde.
	 * @return die getroffene Auswahl. Bei keinem ausgewählten Schwierigkeitsgrad wird null geliefert.
	 */
	public Schwierigkeit getAuswahl() {
		synchronized (closed) {
			while(!closed.get()) {
				try {
					closed.wait();
				} catch (InterruptedException ignore) { }
			}
		}
		return this.auswahl;
	}

	public static void main(String[] args) {
		boolean again = false;
		do {
			var auswahl = new AuswahlMenu("Minesweeper");
			auswahl.setLocationRelativeTo(null);
			auswahl.setVisible(true);
			Schwierigkeit s = auswahl.getAuswahl();
			if (s != null) {
				var mine = new MinesweeperGUI("Minesweeper: " + s, s); // TODO Minesweeper wird nicht auto. im Vordergrund angezeigt.
				mine.setLocationRelativeTo(null);
				mine.setVisible(true);
				again = mine.getAgain();
			}
		} while (again);
	}
}

