package itis.second_sem_work.files.game.items;

import itis.second_sem_work.sockets.net.ServerGame;
import itis.second_sem_work.sockets.net.ServerGame.Entity;

import java.io.Serial;

public class Shot extends Entity implements U_turn {
    @Serial
    private static final long serialVersionUID = 2690256651740709424L;

    private final Player shooter;
    private int age = 0;
    private final double horVel;

    public Shot(final ServerGame game, final Player shooter, final double x, final double y,
                final double horVel,
                final OX xAxisType, final OY yAxisType) {

        super(game, 2. / 8., 1. / 8., x, y);

        this.shooter = shooter;

        switch (xAxisType) {
            case LEFT:
                setX(x);
                break;
            case RIGHT:
                setX(x - this.getWidth());
                break;
            case CENTER:
                // idk
                break;
        }

        switch (yAxisType) {
            case BOTTOM:
                setY(y);
                break;
            case TOP:
                setY(y - this.getHeight());
                break;
            case CENTER:
                // idk
                break;
        }

        age = 0;
        this.horVel = horVel;
    }

    @Override
    public void tick() {
        if (age >= ServerGame.GameSettings.BULLET_LIFESPAN) {
            getGame().removeEntity(getId());
            return;
        }

        shiftX(horVel);

        age++;
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
        if (otherEntity instanceof Floor) {
            getGame().removeEntity(getId());
        }
    }

    @Override
    public HorDirection getHorDirection() {
        return horVel > 0 ? HorDirection.RIGHT : HorDirection.LEFT;
    }

    public Player getShooter() {
        return shooter;
    }

    @Override
    public void setHorDirection(final HorDirection horDirection) {
        throw new UnsupportedOperationException("Unimplemented method 'setHorDirection'");
    }
}