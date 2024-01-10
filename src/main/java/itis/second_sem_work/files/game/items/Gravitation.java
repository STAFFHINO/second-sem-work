package itis.second_sem_work.files.game.items;

import itis.second_sem_work.sockets.net.ServerGame.GameSettings;

public interface Gravitation extends Kinetic {
    public default double getGravitationalForce() {
        return GameSettings.GLOBAL_GRAVITY;
    }

    public default void applyGravity() {
        shiftYVel(getGravitationalForce());
    }
}