package itis.second_sem_work.files.gui.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import itis.second_sem_work.sockets.net.Server;

public class ClientMenu extends JPanel {
    private final JTextField ipAddress, portNumber;

    public ClientMenu() {
        ipAddress = new JTextField("localhost");
        add(ipAddress);

        portNumber = new JTextField("" + Server.DEFAULT_PORT_NUMBER);
        add(portNumber);

        add(new JButton("Join Game") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        ((ClientMain) ClientMenu.this.getTopLevelAncestor()).startGame(ipAddress.getText(),
                                Integer.parseInt(portNumber.getText()));
                    }
                });
            }
        });
    }
}