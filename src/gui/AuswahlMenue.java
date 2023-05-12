package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import gameCode.Schwierigkeit;

public class AuswahlMenue extends JFrame{

	private JLabel titelVomSpiel = null;
	private JButton los = null;
	private JButton schliesen = null;
	private JComboBox<Schwierigkeit> schwierigkeit = null;
	
	public AuswahlMenue(String title) {
		setTitle(title);
		setBounds(10, 10, 352, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		Container cp = getContentPane();
	
		titelVomSpiel = new JLabel("<html>Minesweeper" + "<br/>" + "Von Roalter Fabian</html>", SwingConstants.CENTER);
		titelVomSpiel.setBounds(0, 10, 325, 100);
		titelVomSpiel.setFont(new Font("SansSerif", Font.BOLD, 20));
		cp.add(titelVomSpiel);
		
		schwierigkeit = new JComboBox<Schwierigkeit>();
		schwierigkeit.addItem(Schwierigkeit.EINFACH);
		schwierigkeit.addItem(Schwierigkeit.MITTEL);
		schwierigkeit.addItem(Schwierigkeit.SCHWIERIG);
		schwierigkeit.setBounds(55, 125, 200, 20);
		cp.add(schwierigkeit);
		
		los = new JButton("Los Gehts!");
		los.setBounds(190, 225, 125, 30);
		los.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuswahlMenue.this.dispose();
				Schwierigkeit s = (Schwierigkeit) schwierigkeit.getSelectedItem();
				new MinesweeperGUI("Minesweeper: " + s.toString(), s);
			}
		});
		cp.add(los);
		
		schliesen = new JButton("Tschüss!");
		schliesen.setBounds(55, 225, 125, 30);
		schliesen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AuswahlMenue.this.dispose();
			}
		});
		cp.add(schliesen);
		setVisible(true);
	}
	public static void main(String[] args) {
		new AuswahlMenue("Minesweeper");
	}
}
