package itis.second_sem_work.files.gui.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import itis.second_sem_work.sockets.net.Server;

public class ServerRun extends JPanel {
    public ServerRun(final Server server) {
        try {
            add(new JLabel(
                    "Running. Port: " +
                            server.getServerSocket().getLocalPort() +
                            ". IP address: " +
                            InetAddress.getLocalHost().getHostAddress()));
        } catch (final UnknownHostException e) {
            e.printStackTrace();
        }
    }
}