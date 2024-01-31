package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import gameCode.*;
import libary.ExecptionHandler;

@SuppressWarnings("serial")
public class MinesweeperGUI extends JFrame{

	private JButton[][] felder = null;
	private int BREITE = 10;
	private int HOEHE = 10;
	private MinenFeld minenfeld = null;

	private JLabel lbl_verbleibendeBomben = null;
	private JLabel lbl_titel = null;
	private boolean firstClick = true;
	private int verbleibendeBomben = 0;

	public MinesweeperGUI(String title, Schwierigkeit schwierigkeit) {
		setTitle(title);
		setBounds(10, 10 , 1000, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		Container cp = getContentPane();

		//Feld vorbereiten
		switch (schwierigkeit) {
			case EINFACH -> {
				BREITE = 10;
				HOEHE = 10;
			}
			case MITTEL -> {
				BREITE = 15;
				HOEHE = 15;
			}
			case SCHWIERIG -> {
				BREITE = 20;
				HOEHE = 20;
			}
			default -> ExecptionHandler.handleExceptionGUI(new Exception("Kein Vorhandener Schwierigkeitsgrad"));
		}

		//JLabel nur für titel
		lbl_titel = new JLabel(title, SwingConstants.CENTER);
		lbl_titel.setFont(new Font("SansSerif", Font.BOLD, 20));
		lbl_titel.setBounds(0, 10, 1000, 80);
		cp.add(lbl_titel);

		//JLabel das Anzeigt wie viele Bomben noch sind
		verbleibendeBomben = schwierigkeit.getAnzahlBomben();
		lbl_verbleibendeBomben = new JLabel(Integer.toString(verbleibendeBomben));
		lbl_verbleibendeBomben.setBounds(800, 50, 200, 30);
		cp.add(lbl_verbleibendeBomben);

		//MinenFeld mit JButtons um über der GUI mit dem MinenFeld in der MinenFeld.java interagieren zu können
		felder = new JButton[HOEHE][BREITE];
		for(int i = 0; i < HOEHE; i++) {
			for(int j = 0; j < BREITE; j++) {
				final int I = i;
				final int J = j;
				felder[i][j] = new JButton();
				felder[i][j].setBounds(10 + j * 60, 100 + i * 60, 60, 60);

				felder[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Wenn eine Flagge gesetzt ist, soll der Klick ignoriert werden
						if (felder[I][J].getIcon() != null)
							return;

						//Generiere es erst beim ersten Klick
						if (firstClick) {
							minenfeld = new MinenFeld(schwierigkeit, I, J);
							System.out.println(minenfeld);
							firstClick = false;
						}

						if (minenfeld.getZahl(J, I) == 0)
							aufdeckenRekusiv(J, I);
						else
							aufdeckenGUI(J, I);
					}

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

					private void aufdeckenGUI(int x, int y) {
						if (x >= HOEHE || y >= BREITE || x < 0 || y < 0)
							return;

						int zahl = minenfeld.aufdecken(x, y);
						switch (zahl) {
							case -1 -> MinesweeperGUI.this.felder[y][x].setIcon(new ImageIcon("Smiley.svg.png"));
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
				});

				felder[i][j].addMouseListener(new MouseAdapter (){
					public void mousePressed(MouseEvent me) {
						if (me.getButton() == MouseEvent.BUTTON3 && felder[I][J].getText().isEmpty()) {
							//FIXME Bug: Text wird angezeigt nachdem man flagge darauf gesetzt hat, Lösung no koan Bock bleib amol aso
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
				getContentPane().add(felder[i][j]);
			}
		}
		setVisible(true);
	}

	public static void main(String[] args) {
		new MinesweeperGUI("Hallo Welt", Schwierigkeit.EINFACH);
	}

}