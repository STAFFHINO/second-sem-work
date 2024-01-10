package itis.second_sem_work.files.gui.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import itis.second_sem_work.sockets.net.Server;

public class ServerMenu extends JPanel {
    private final JComboBox<String> gameType;
    private final JTextField portNumber;

    public ServerMenu() {
        final String[] gameTypes = {
                "1 vs 1",
                "2 vs 2"
        };
        gameType = new JComboBox<String>(gameTypes);
        add(gameType);

        portNumber = new JTextField("" + Server.DEFAULT_PORT_NUMBER);
        add(portNumber);

        add(new JButton("Start Server") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        ((ServerMain) ServerMenu.this.getTopLevelAncestor())
                                .startServer(Integer.parseInt(portNumber.getText()),
                                        gameTypes[gameType.getSelectedIndex()]);
                    }
                });
            }
        });
    }
}