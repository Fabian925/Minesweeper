package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import gameCode.*;
//FIXME Irgentwo isch x und y (bzw i und j) vertauscht. SEHR WICHTIG!!!
@SuppressWarnings("serial")
public class MinesweeperGUI extends JFrame{
	
	private JButton[][] felder = null;
	private final int BREITE;
	private final int HOEHE;
	private MinenFeld minenfeld = null;
	private boolean firstClick = true;
	
	private int verbleibendeBomben = 0;
	
	public MinesweeperGUI(String title, Schwierigkeit schwierigkeit) {
		setTitle(title);
		setBounds(10, 10 , 1000, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		//Feld vorbereiten
		switch(schwierigkeit) {
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

		verbleibendeBomben = schwierigkeit.getAnzahlBomben();
		felder = new JButton[BREITE][HOEHE];
		for(int i = 0; i < HOEHE; i++) {
			for(int j = 0; j < BREITE; j++) {
				final int I = i;
				final int J = j;
				felder[i][j] = new JButton();
				felder[i][j].setBounds(10 + j * 60, 10 + i * 60, 60, 60);

				felder[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(felder[I][J].getIcon() == null) {
							//Generiere es erst beim ersten Klick
							if(firstClick == true) {
								minenfeld = new MinenFeld(schwierigkeit, I, J);
								System.out.println(minenfeld.toString());
								firstClick = false;
							}
							int zahl = minenfeld.aufdecken(I, J);
							switch(zahl) {
							case -2:
								break;

							case -1:
								MinesweeperGUI.this.felder[I][J].setIcon(new ImageIcon("Smiley.svg.png"));
								break;

							case 0:	
								MinesweeperGUI.this.felder[I][J].setText(Integer.toString(zahl));
								aufdeckenRekusiv(I, J);
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
						for(int i = -1; i <= 1; i++) {
							for(int j = -1; j <= 1; j++) {
								try {
									if(felder[y+i][x+j].getIcon() == null) {
										int zahl = minenfeld.aufdecken(y+i, x+j);
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
						if(me.getButton() == MouseEvent.BUTTON3 && felder[I][J].getText() == "") {
							//FIXME Bug: Text wird angezeigt nachdem man flagge darauf gesetzt hat, Lösung no koan Bock bleib amol aso
							if(felder[I][J].getIcon() == null) {
								felder[I][J].setIcon(new ImageIcon("Flagge.png"));
							}
							else {
								felder[I][J].setIcon(null);
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