package itis.second_sem_work.files.game.items;


import itis.second_sem_work.sockets.net.ServerGame;
import itis.second_sem_work.sockets.net.ServerGame.Entity;

public class Teammate extends Player {
    public enum Team {
        RED, BLUE;
    }

    private final Team team;

    public Teammate(final ServerGame game, final Team team, final double x, final double y,
                    final HorDirection direction) {
        super(game, x, y, direction);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public void handleCollision(final Entity otherEntity) {
        if (otherEntity instanceof final Shot bulletEntity) {
            if (bulletEntity.getShooter() instanceof final Teammate shooter) {
                if (shooter.team == this.team) {
                    return;
                }
            }
        }

        super.handleCollision(otherEntity);
    }
}