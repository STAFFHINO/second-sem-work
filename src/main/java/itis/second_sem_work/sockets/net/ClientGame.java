package itis.second_sem_work.sockets.net;

import java.util.TreeMap;
import itis.second_sem_work.sockets.net.ServerGame.Entity;
import itis.second_sem_work.files.game.control.SetOfAbilities;
import itis.second_sem_work.files.game.items.Player;

public class ClientGame {
    private final int playerId;
    private final Client client;
    private final SetOfAbilities actionSet;
    private TreeMap<Integer, Entity> entities;

    public ClientGame(final Client client, final int clientId) {
        this.client = client;
        playerId = clientId;
        actionSet = new SetOfAbilities();
    }

    public SetOfAbilities getActionSet() {
        return actionSet;
    }

    @Deprecated
    public Player getPlayerEntity() {
        return (Player) entities.get(playerId);
    }

    public TreeMap<Integer, Entity> getEntities() {
        return entities;
    }

    public void processEntityList(final TreeMap<Integer, Entity> incomingEntityList) {
        entities = incomingEntityList;
    }

    public void tick() {
        if (!getEntities().containsKey(playerId)) {
            client.disconnect();
        }
    }
}
