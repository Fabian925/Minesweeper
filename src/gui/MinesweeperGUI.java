package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import gameCode.*;
@SuppressWarnings("serial")
public class MinesweeperGUI extends JFrame{

	private JButton[][] felder = null;
	private final int BREITE;
	private final int HOEHE;
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
			case EINFACH:
				BREITE = 10;
				HOEHE = 10;
				break;
			case MITTEL:
				BREITE = 15;
				HOEHE = 15;
				break;
			case SCHWIERIG:
				BREITE = 20;
				HOEHE = 20;
				break;
			default:
				BREITE = 10;
				HOEHE = 10;
				break;

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
		for (int i = 0; i < HOEHE; i++) {
			for (int j = 0; j < BREITE; j++) {
				final int I = i;
				final int J = j;
				felder[i][j] = new JButton();
				felder[i][j].setBounds(10 + j * 60, 100 + i * 60, 60, 60);

				felder[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (felder[I][J].getIcon() == null) {
							//Generiere es erst beim ersten Klick
							if (firstClick) {
								minenfeld = new MinenFeld(schwierigkeit, I, J);
								System.out.println(minenfeld.toString());
								firstClick = false;
							}
							int zahl = minenfeld.aufdecken(J, I);
							switch (zahl) {
								case -2:
									break;

								case -1:
									MinesweeperGUI.this.felder[I][J].setIcon(new ImageIcon("Smiley.svg.png"));
									break;

								case 0:
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									aufdeckenRekusiv(J, I);
									break;

								case 1:
									MinesweeperGUI.this.felder[I][J].setForeground(Color.BLUE);
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									break;

								case 2:
									MinesweeperGUI.this.felder[I][J].setForeground(Color.CYAN);
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									break;

								case 3:
									MinesweeperGUI.this.felder[I][J].setForeground(Color.GREEN);
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									break;

								case 4:
									MinesweeperGUI.this.felder[I][J].setForeground(Color.ORANGE);
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									break;

								case 5:
									MinesweeperGUI.this.felder[I][J].setForeground(Color.RED);
									MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
									break;
							}
						}
					}

					private void aufdeckenRekusiv(int x, int y) {
						for (int i = -1; i <= 1; i++) {
							for (int j = -1; j <= 1; j++) {
								try {
									if (felder[y+i][x+j].getIcon() == null) {
										int zahl = minenfeld.aufdecken(x+j, y+i);
										switch(zahl) {
											case -2:
												break;

											case 0:
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												aufdeckenRekusiv(x+j, y+i);
												break;

											case 1:
												MinesweeperGUI.this.felder[y+i][x+j].setForeground(Color.BLUE);
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												break;

											case 2:
												MinesweeperGUI.this.felder[y+i][x+j].setForeground(Color.CYAN);
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												break;

											case 3:
												MinesweeperGUI.this.felder[y+i][x+j].setForeground(Color.GREEN);
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												break;

											case 4:
												MinesweeperGUI.this.felder[y+i][x+j].setForeground(Color.ORANGE);
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												break;

											case 5:
												MinesweeperGUI.this.felder[y+i][x+j].setForeground(Color.RED);
												MinesweeperGUI.this.felder[y+i][x+j].setText(Integer.toString(zahl));
												break;
										}
									}
								} catch(ArrayIndexOutOfBoundsException e) { ; }
							}
						}
					}

				});
				
				felder[i][j].addMouseListener(new MouseAdapter (){
					public void mousePressed(MouseEvent me) {
						// Rechte MouseTaste
						if (me.getButton() == MouseEvent.BUTTON3 && felder[I][J].getText() == "") {
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