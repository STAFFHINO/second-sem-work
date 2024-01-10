package itis.second_sem_work.files.gui.client;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JFrame;
import itis.second_sem_work.sockets.net.Client;

public class ClientMain extends JFrame {
    private Client client;

    public ClientMain() {
        super("Game");

        setMenuPanel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (client != null) {
                    client.disconnect();
                }

                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    public void setMenuPanel() {
        getContentPane().removeAll();

        add(new ClientMenu());

        pack();
        revalidate();
        repaint();
    }

    public void startGame(final String ipAddress, final int port) {
        System.out.println("Starting game.");
        try {
            client = new Client(this, ipAddress, port);
        } catch (final ConnectException e) {
            System.out.println("Connection refused.");
            return;
        } catch (final IOException e) {
            e.printStackTrace();
        }

        getContentPane().removeAll();

        final ClientGamePanel gamePanel = new ClientGamePanel(client.getGame());

        add(gamePanel, BorderLayout.CENTER);

        gamePanel.requestFocusInWindow();
        pack();
        revalidate();
        repaint();

        client.run();
    }

    public void handleDeath() {
        getContentPane().removeAll();

        add(new DeathPanel());

        pack();
        revalidate();
        repaint();
    }

    public static void main(final String[] args) {
        new ClientMain();
    }
}