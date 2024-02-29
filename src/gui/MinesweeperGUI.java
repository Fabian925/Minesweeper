package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import gameCode.*;

public class MinesweeperGUI extends JFrame {

	private JButton[][] felder;
	private final int BREITE;
	private final int HOEHE;
	private MinenFeld minenfeld = null;

	private JLabel lbl_verbleibendeBomben;
	private JLabel lbl_titel;
	private boolean firstClick = true;
	private int verbleibendeBomben;

	public MinesweeperGUI(String title, Schwierigkeit schwierigkeit) {
		setTitle(title);
		setPreferredSize(new Dimension(1000, 1000));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		pack();
		Container cp = getContentPane();

		BREITE = schwierigkeit.getDimension();
		HOEHE = schwierigkeit.getDimension();

		//JLabel nur für titel
		lbl_titel = new JLabel(title, SwingConstants.CENTER);
		lbl_titel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lbl_titel.setBounds(0, 10, 1000, 80);
		cp.add(lbl_titel);

		//JLabel das Anzeigt wie viele Bomben noch sind
		verbleibendeBomben = schwierigkeit.getAnzahlBomben();
		lbl_verbleibendeBomben = new JLabel("Verbleibende Bomben: " + verbleibendeBomben);
		lbl_verbleibendeBomben.setBounds(800, 50, 200, 30);
		cp.add(lbl_verbleibendeBomben);

		JButton btn_AllesAufdecken = new JButton("Alle Aufdecken");
		btn_AllesAufdecken.setEnabled(false);
		btn_AllesAufdecken.setBounds(10, 50, 200, 30);
		btn_AllesAufdecken.addActionListener(e -> {
			 int option = JOptionPane.showOptionDialog(MinesweeperGUI.this,
					 "Wenn Sie alles aufdecken, dann gilt das Spiel als Verloren. Fortfahren?",
					 "Alles Aufdecken", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
			 if (option == JOptionPane.YES_OPTION)
				 allesAufdecken();
		});
		cp.add(btn_AllesAufdecken);

		// JPanel voll mit Minen!
		JPanel minenPanel = new JPanel(new GridLayout(HOEHE, BREITE));
		minenPanel.setBounds(10, 100, cp.getWidth() - 20, cp.getHeight() - 110);
		cp.add(minenPanel);

		//MinenFeld mit JButtons um über der GUI mit dem MinenFeld in der MinenFeld.java interagieren zu können
		felder = new JButton[HOEHE][BREITE];
		for(int i = 0; i < HOEHE; i++) {
			for(int j = 0; j < BREITE; j++) {
				final int I = i;
				final int J = j;
				felder[i][j] = new JButton();

				felder[i][j].addActionListener(e -> {
                    // Wenn eine Flagge gesetzt ist, soll der Klick ignoriert werden
                    if (felder[I][J].getIcon() != null)
                        return;

                    //Generiere es erst beim ersten Klick
                    if (firstClick) {
                        minenfeld = new MinenFeld(schwierigkeit, I, J);
                        System.out.println(minenfeld);
                        firstClick = false;
						btn_AllesAufdecken.setEnabled(true);
                    }

                    if (minenfeld.getZahl(J, I) == 0)
                        aufdeckenRekusiv(J, I);
                    else
                        aufdeckenGUI(J, I);
					checkGewonnen();
                });

				felder[i][j].addMouseListener(new MouseAdapter (){
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON3 && felder[I][J].getText().isEmpty()) {
							//FIXME Smile.svg kann entfehrnt werden mit Rechtsklick. Nicht so schlimm des past schun.
							if (felder[I][J].getIcon() == null && verbleibendeBomben > 0) {
								felder[I][J].setIcon(new ImageIcon("Flagge.png"));
								verbleibendeBomben--;
								lbl_verbleibendeBomben.setText(Integer.toString(verbleibendeBomben));
							}
							else if (felder[I][J].getIcon() != null){
								felder[I][J].setIcon(null);
								verbleibendeBomben++;
								lbl_verbleibendeBomben.setText(Integer.toString(verbleibendeBomben));
							}
						}
					}
				});
				minenPanel.add(felder[i][j]);
			}
		}
		setVisible(true);
	}

	/** Deck alle Felder auf */
	private void allesAufdecken() {
		for (int i = 0; i < HOEHE; i++) {
			for (int j = 0; j < BREITE; j++) {
				if (felder[i][j].getIcon() != null)
					felder[i][j].setIcon(null);
				aufdeckenGUI(j, i);
			}
		}
		verloren();
	}

	/** Deckt in der GUI ein Feld auf. Falls dieses Feld 0 ist, werden automatisch auch die Nachbarn aufgedeckt. */
	private void aufdeckenGUI(int x, int y) {
		if (x >= HOEHE || y >= BREITE || x < 0 || y < 0)
			return;

		int zahl = minenfeld.aufdecken(x, y);
		switch (zahl) {
            case -1 -> {
                MinesweeperGUI.this.felder[y][x].setIcon(new ImageIcon("Smiley.png"));
                verloren();
            }
            case 0 ->  MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			case 1 -> {
				MinesweeperGUI.this.felder[y][x].setForeground(Color.BLUE);
				MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			}
			case 2 -> {
				MinesweeperGUI.this.felder[y][x].setForeground(Color.CYAN);
				MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			}
			case 3 -> {
				MinesweeperGUI.this.felder[y][x].setForeground(Color.GREEN);
				MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			}
			case 4 -> {
				MinesweeperGUI.this.felder[y][x].setForeground(Color.ORANGE);
				MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			}
			case 5 -> {
				MinesweeperGUI.this.felder[y][x].setForeground(Color.RED);
				MinesweeperGUI.this.felder[y][x].setText(Integer.toString(zahl));
			}
		}
	}

	/** Checkt die Nachbarn des Feldes und deckt sie auf. Falls dieses Feld eine 0 ist, werden wiederum seine Nachbarn
	 *  aufgedeckt.
	 */
	private void aufdeckenRekusiv(int x, int y) {
		for (int i = y-1; i <= y+1; i++) {
			for (int j = x-1; j <= x+1; j++) {
				try {
					if (felder[i][j].getIcon() != null)
						continue;
				} catch (ArrayIndexOutOfBoundsException ignore) {}

				int minenfeldZahl = minenfeld.getZahl(j, i);
				if (minenfeldZahl != -2)
					aufdeckenGUI(j, i);
				if (minenfeldZahl == 0)
					aufdeckenRekusiv(j, i);
			}
		}
	}

	private void checkGewonnen() {
		if (minenfeld.gewonnen()) {
			System.out.println("Juhu");//TODO
			new ErgebnisDialog(this, "Gewonnen", true);
			//this.dispose();
		}
	}

	private void verloren() {
		System.out.println("verloren"); //TODO
		new ErgebnisDialog(this, "Verloren", false);
		//this.dispose();
	}

	public static void main(String[] args) {
		new MinesweeperGUI("Hallo Welt", Schwierigkeit.EINFACH);
	}
}