package game.second_sem_work.shooter.game.entities;

import game.second_sem_work.shooter.game.ServerGame.GameSettings;

public interface GravitationalEntity extends KineticEntity {
    public default double getGravitationalForce() {
        return GameSettings.GLOBAL_GRAVITY;
    }

    public default void applyGravity() {
        shiftYVel(getGravitationalForce());
    }
}