package itis.second_sem_work.files.game.items;

import itis.second_sem_work.sockets.net.ServerGame;
import itis.second_sem_work.sockets.net.ServerGame.Entity;
import itis.second_sem_work.sockets.net.ServerGame.GameSettings;
import itis.second_sem_work.files.game.control.Abilities;

import java.io.Serial;

public class Player extends Entity implements U_turn, Gravitation {
    @Serial
    private static final long serialVersionUID = -3022640676588904126L;

    private final Gun pistol;

    private HorDirection horDirection;
    private double xVel, yVel;

    public Player(final ServerGame game, final double x, final double y,
                  final HorDirection direction) {
        super(game, 1, 2, x, y);

        this.horDirection = direction;

        xVel = 0;
        yVel = 0;

        this.pistol = new Gun(this);
    }

    @Override
    public double getYVel() {
        return yVel;
    }

    @Override
    public void setYVel(final double yVel) {
        this.yVel = yVel;
    }

    @Override
    public double getXVel() {
        return xVel;
    }

    @Override
    public void setXVel(final double xVel) {
        this.xVel = xVel;
    }

    @Override
    public HorDirection getHorDirection() {
        return horDirection;
    }

    @Override
    public void setHorDirection(final HorDirection horDirection) {
        this.horDirection = horDirection;
    }

    @Override
    public void tick() {
        for (final Abilities action : getActionSet().getInstantActions()) {
            switch (action) {
                case JUMP: {
                    if (getYVel() != 0) {
                        break;
                    }
                    shiftYVel(GameSettings.JUMP_VEL);
                    break;
                }

                case SHOOT: {
                    if (pistol.getHorDirection() == HorDirection.LEFT) {
                        new Shot(getGame(), this, pistol.getLeftX(), pistol.getTopY(),
                                -GameSettings.BULLET_SPEED,
                                OX.LEFT, OY.TOP);
                    } else if (pistol.getHorDirection() == HorDirection.RIGHT) {
                        new Shot(getGame(), this, pistol.getRightX(), pistol.getTopY(),
                                GameSettings.BULLET_SPEED,
                                OX.RIGHT, OY.TOP);
                    } else {
                        ServerGame.getLogger().warning("Unknown direction \"" + pistol.getHorDirection() + "\".");
                    }
                    break;
                }

                default:
                    ServerGame.getLogger().warning("Unknown action \"" + action + "\" in instant actions.");
                    break;
            }
        }
        getActionSet().getInstantActions().clear();

        for (final Abilities action : getActionSet().getLongActions()) {
            switch (action) {
                case LEFT_WALK: {
                    setHorDirection(HorDirection.LEFT);
                    shiftX(-GameSettings.WALK_SPEED);
                    break;
                }

                case RIGHT_WALK: {
                    setHorDirection(HorDirection.RIGHT);
                    shiftX(GameSettings.WALK_SPEED);
                    break;
                }

                default:
                    ServerGame.getLogger().warning("Unknown action \"" + action + "\" in long actions.");
                    break;
            }
        }
        applyGravity();
        applyVelocity();
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
        if (otherEntity instanceof Floor) {
            final Vectors collisionNormal = getCollisionNormal(otherEntity);

            if (collisionNormal.x() > 0) {
                this.setX(otherEntity.getX() - this.getWidth());
                this.setXVel(0);
            } else if (collisionNormal.x() < 0) {
                this.setX(otherEntity.getX() + otherEntity.getWidth());
                this.setXVel(0);
            }

            if (collisionNormal.y() > 0) {
                this.setY(otherEntity.getY() - this.getHeight());
                this.setYVel(0);
            } else if (collisionNormal.y() < 0) {
                this.setY(otherEntity.getY() + otherEntity.getHeight());
                this.setYVel(0);
            }
        } else if (otherEntity instanceof Shot) {
            die();
        }
    }

    private void die() { // might cause concurrent mod issues
        getGame().removeEntity(getId());
    }
}