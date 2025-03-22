package gui;

import gameCode.HighScore;
import gameCode.HighScoreStore;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class ErgebnisDialog extends JDialog {

    private Option selectedOption = Option.LEAVE;
    private LocalTime needetTime;

    public ErgebnisDialog(Frame owner, String title, boolean gewonnen, LocalTime needetTime) {
        super(owner, title, true);
        this.needetTime = needetTime;
        final int WIDTH = 300;
        final int HEIGHT = 180;
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(owner);
        setLayout(null);
        Container cp = getContentPane();

        JLabel status = new JLabel("Gewonnen", SwingConstants.CENTER);
        if (!gewonnen)
            status.setText("Verloren");
        status.setFont(new Font("Serif", Font.BOLD, 16));
        status.setBounds(0, 0, WIDTH, 40);
        cp.add(status);

        JPanel infos = getInfoJPanel();
        infos.setBounds(0, 40, WIDTH, 60);
        cp.add(infos);

        JPanel actions = getActionsJPanel();
        actions.setBounds(0, 105, WIDTH, 30);
        cp.add(actions);
    }

    private JPanel getActionsJPanel() {
        JPanel actions = new JPanel();
        JButton nochmalButton = new JButton("Nochmal");
        nochmalButton.addActionListener(e -> {
            selectedOption = Option.AGAIN;
            dispose();
        });
        JButton endeButton = new JButton("Ende");
        endeButton.addActionListener(e -> {
            selectedOption = Option.LEAVE;
            dispose();
        });
        var layout = new GroupLayout(actions);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(nochmalButton)
                .addComponent(endeButton)
        );
        return actions;
    }

    private JPanel getInfoJPanel() {
        var layout = new GridLayout(2, 2);
        layout.setHgap(15);
        JPanel infos = new JPanel(layout);

        JLabel timeLabel = new JLabel("Zeit:", SwingConstants.RIGHT);
        JLabel timeValue = new JLabel(needetTime != null ? needetTime.toString() : "--:--:--");
        timeLabel.setLabelFor(timeValue);
        infos.add(timeLabel);
        infos.add(timeValue);

        HighScore highScore = HighScoreStore.getBestHighScore();
        String time = highScore != null ? highScore.time().toString() : "--:--:--";

        JLabel bestZeit = new JLabel("Bestzeit:", SwingConstants.RIGHT);
        JLabel bestZeitValue = new JLabel(time);
        bestZeit.setLabelFor(bestZeitValue);
        infos.add(bestZeit);
        infos.add(bestZeitValue);
        return infos;
    }

    /** Liefert die ausgewählte Option. Wenn noch keine ausgewählt wurde, liefert die Methode null. */
    public Option getSelectedOption() {
        return selectedOption;
    }

    public static void main(String[] args) {
        new ErgebnisDialog(new JFrame(), "Florian isch a cooler Nome", false, LocalTime.now());
    }
}