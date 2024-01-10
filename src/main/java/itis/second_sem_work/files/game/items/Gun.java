package itis.second_sem_work.files.game.items;

import itis.second_sem_work.sockets.net.ServerGame.Entity;
import itis.second_sem_work.files.game.items.U_turn.HorDirection;

import java.io.Serial;

public class Gun extends Entity {
    @Serial
    private static final long serialVersionUID = 348408736704866955L;

    private final Player owner;

    public Gun(final Player owner) {
        super(owner.getGame(), 3. / 8., 2. / 8., Double.NaN, Double.NaN);

        this.owner = owner;
    }

    @Override
    public void tick() {
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
    }

    @Override
    public double getX() {
        return getCenterX() - getWidth() / 2;
    }

    @Override
    public double getY() {
        return owner.getBottomY() + 1.25;
    }

    @Override
    public double getCenterX() {
        return owner.getCenterX() + owner.getHorDirection().getSign() * getXOffset();
    }

    private double getXOffset() {
        return owner.getWidth() / 2. + this.getWidth() / 2. + 1. / 8.;
    }

    public HorDirection getHorDirection() {
        return owner.getHorDirection();
    }
}