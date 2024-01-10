package itis.second_sem_work.files.gui.server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import itis.second_sem_work.sockets.net.ServerGame;
import itis.second_sem_work.sockets.net.ServerTeamGame;
import itis.second_sem_work.sockets.net.Server;

public class ServerMain extends JFrame {
    private Server server;

    public ServerMain() {
        super("Server Manager");

        add(new ServerMenu());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (server != null) {
                    server.closeServer();
                }
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    public void startServer(final int port, final String gameType) {
        getContentPane().removeAll();

        System.out.println("Starting server.");

        server = new Server(switch (gameType) {
            case ("2 vs 2") -> new ServerGame();
            case ("1 vs 1") -> new ServerTeamGame();
            default -> throw new IllegalArgumentException("Unexpected value: " + gameType);
        }, port);

        add(new ServerRun(server));

        pack();
        revalidate();
        repaint();

        server.run();
    }

    public static void main(final String[] args) {
        new ServerMain();
    }
}