package itis.second_sem_work.sockets.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.TreeMap;

import itis.second_sem_work.sockets.net.ServerGame.Entity;
import itis.second_sem_work.files.gui.client.ClientMain;
import itis.second_sem_work.sockets.net.packets.AbilitiesPacket;
import itis.second_sem_work.sockets.net.packets.ClientPacket;
import itis.second_sem_work.sockets.net.packets.DisconnectPacket;

public class Client implements Runnable {
    private boolean isRunning;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private ClientGame game;
    private final ClientMain mainFrame;

    public ClientGame getGame() {
        return game;
    }

    public Client(final ClientMain mainFrame, final String ipAddress, final int port) throws IOException {
        socket = new Socket(ipAddress, port);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        this.mainFrame = mainFrame;

        initialServerCommunication();
    }

    @SuppressWarnings("unchecked")
    private void initialServerCommunication() {
        try {
            final int clientId = inputStream.readInt();
            game = new ClientGame(this, clientId);

            game.processEntityList(((TreeMap<Integer, Entity>) inputStream.readObject()));

            System.out.println("Finished initial server communication.");
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        new Thread(this::startReadAndWriteLoop).start();
        new Thread(this::startGameloop).start();
    }

    @SuppressWarnings("unchecked")
    private void startReadAndWriteLoop() {
        while (isRunning) {
            try {
                game.processEntityList(((TreeMap<Integer, Entity>) inputStream.readObject()));
                sendPacket(new AbilitiesPacket(game));
                game.getActionSet().getInstantActions().clear();
            } catch (final SocketException e) {
                break;
            } catch (final IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void startGameloop() {
        while (isRunning) {
            game.tick();
            mainFrame.repaint();
        }
    }

    public void sendPacket(final ClientPacket packet) {
        try {
            outputStream.writeObject(packet);
            outputStream.reset();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        System.out.println("Disconnecting from server.");
        isRunning = false;
        sendPacket(new DisconnectPacket());
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        mainFrame.handleDeath();
    }
}