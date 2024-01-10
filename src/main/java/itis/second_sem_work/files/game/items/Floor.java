package itis.second_sem_work.files.game.items;

import itis.second_sem_work.sockets.net.ServerGame;
import itis.second_sem_work.sockets.net.ServerGame.Entity;

import java.io.Serial;

public class Floor extends Entity {
    @Serial
    private static final long serialVersionUID = 423302831329368937L;

    public Floor(final ServerGame game, final double width, final double height, final double x,
                 final double y) {
        super(game, width, height, x, y);
    }

    @Override
    public void tick() {
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
    }
}