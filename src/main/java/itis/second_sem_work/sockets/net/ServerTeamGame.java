package itis.second_sem_work.sockets.net;

import itis.second_sem_work.files.game.items.Teammate;
import itis.second_sem_work.files.utils.ArraySet;
import itis.second_sem_work.files.game.items.U_turn;

public class ServerTeamGame extends ServerGame {
    private final ArraySet<Teammate> redTeamPlayers = new ArraySet<>(), blueTeamPlayers = new ArraySet<>();

    public ServerTeamGame() {
        super();

        System.out.println("Playing teams.");
    }

    @Override
    public int spawnPlayerEntity() {
        Teammate playerEntity;
        if (redTeamPlayers.size() < blueTeamPlayers.size()) {
            playerEntity = new Teammate(this, Teammate.Team.RED, 1, 3,
                    U_turn.HorDirection.RIGHT);
            redTeamPlayers.add(playerEntity);
            System.out.println("New red team player.");
        } else {
            playerEntity = new Teammate(this, Teammate.Team.BLUE, 8, 3,
                    U_turn.HorDirection.LEFT);
            blueTeamPlayers.add(playerEntity);
            System.out.println("New blue team player.");
        }
        return playerEntity.getId();
    }

    @Override
    public void removeEntity(final int id) {
        if (getEntities().get(id) instanceof final Teammate teamedPlayerEntity) {
            if (teamedPlayerEntity.getTeam() == Teammate.Team.RED) {
                redTeamPlayers.remove(teamedPlayerEntity);
            } else {
                blueTeamPlayers.remove(teamedPlayerEntity);
            }
        }
        super.removeEntity(id);
    }
}