package itis.second_sem_work.files.gui.client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeathPanel extends JPanel {
    public DeathPanel() {
        add(new JLabel("You died."));
        add(new JButton("Replay") {
            {
                addActionListener(e -> ((ClientMain) DeathPanel.this.getTopLevelAncestor()).setMenuPanel());
            }
        });
    }
}