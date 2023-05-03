package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import gameCode.*;

@SuppressWarnings("serial")
public class MinesweeperGUI extends JFrame{
	
	private JButton[][] felder = null;
	private final int BREITE = 10;
	private final int HOEHE = 10;
	private MinenFeld minenfeld = null;
	private boolean firstClick = true;
	
	public MinesweeperGUI(String title, Schwierigkeit schwierigkeit) {
		setTitle(title);
		setBounds(10, 10 , 1000, 1000);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		
		//Feld vorbereiten
		felder = new JButton[BREITE][HOEHE];
		for(int i = 0; i < BREITE; i++) {
			for(int j = 0; j < HOEHE; j++) {
				final int I = i;
				final int J = j;
				felder[i][j] = new JButton();
				felder[i][j].setBounds(10 + i * 60, 10 + j * 60, 60, 60);

				//TODO Feature das wenn 0 angeklickt werd, dass olles in dor nähe a ausgwählt werd :-) Hel schofsch
				felder[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(felder[I][J].getIcon() == null) {
							//Generiere es erst beim ersten Klick
							if(firstClick == true) {
								minenfeld = new MinenFeld(schwierigkeit, I, J);
							}
							int zahl = minenfeld.aufdecken(I, J);
							switch(zahl) {
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
									int zahl = minenfeld.aufdecken(x+i, y+j);
									switch(zahl) {
									case 0:	
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										aufdeckenRekusiv(x+i, y+j);
										break;

									case 1:
										MinesweeperGUI.this.felder[x+i][y+j].setForeground(Color.BLUE);
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										break;

									case 2:
										MinesweeperGUI.this.felder[x+i][y+j].setForeground(Color.CYAN);
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										break;

									case 3:
										MinesweeperGUI.this.felder[x+i][y+j].setForeground(Color.YELLOW);
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										break;

									case 4:
										MinesweeperGUI.this.felder[x+i][y+j].setForeground(Color.ORANGE);
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										break;

									case 5:
										MinesweeperGUI.this.felder[x+i][y+j].setForeground(Color.RED);
										MinesweeperGUI.this.felder[x+i][y+j].setText(Integer.toString(zahl));
										break;
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