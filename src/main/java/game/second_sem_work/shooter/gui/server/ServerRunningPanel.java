package game.second_sem_work.shooter.gui.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.second_sem_work.shooter.net.Server;

public class ServerRunningPanel extends JPanel {
    public ServerRunningPanel(final Server server) {
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