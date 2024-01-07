package game.second_sem_work.shooter;

import game.second_sem_work.shooter.gui.client.ClientMainFrame;
import game.second_sem_work.shooter.net.Server;

public class SinglePlayerRunner {
    public static void main(final String[] args) {
        Server.main(args);
        ClientMainFrame.main(args);
    }
}