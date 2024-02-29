package gui;

import javax.swing.*;
import java.awt.*;

public class ErgebnisDialog extends JDialog {

    private final int WIDTH = 300;
    private final int HEIGHT = 180;

    public ErgebnisDialog(Frame owner, String title, boolean gewonnen) {
        super(owner, title);
        setBounds(10, 10, WIDTH, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
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

        setVisible(true);
    }

    private static JPanel getActionsJPanel() {
        JPanel actions = new JPanel();
        JButton nochmalButton = new JButton("Nochmal");
        JButton endeButton = new JButton("Ende");
        var layout = new GroupLayout(actions);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(nochmalButton)
                .addComponent(endeButton)
        );
        return actions;
    }

    private static JPanel getInfoJPanel() {
        var layout = new GridLayout(2, 2);
        layout.setHgap(15);
        JPanel infos = new JPanel(layout);

        JLabel zeit = new JLabel("Zeit:", SwingConstants.RIGHT);
        JLabel zeitValue = new JLabel("00:00:00");
        zeit.setLabelFor(zeitValue);
        infos.add(zeit);
        infos.add(zeitValue);

        JLabel bestZeit = new JLabel("Bestzeit:", SwingConstants.RIGHT);
        JLabel bestZeitValue = new JLabel("00:00:00");
        bestZeit.setLabelFor(bestZeitValue);
        infos.add(bestZeit);
        infos.add(bestZeitValue);
        return infos;
    }

    public static void main(String[] args) {
        new ErgebnisDialog(new JFrame(), "Florian isch a cooler Nome", false);
    }
}
