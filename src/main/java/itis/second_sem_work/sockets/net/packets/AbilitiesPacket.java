package itis.second_sem_work.sockets.net.packets;

import itis.second_sem_work.sockets.net.ClientGame;
import itis.second_sem_work.sockets.net.ServerGame.Entity;
import itis.second_sem_work.files.game.control.SetOfAbilities;

public class AbilitiesPacket extends ClientPacket {
    private static final long serialVersionUID = -710902470934092114L;
    public final SetOfAbilities actionSet;

    @Deprecated
    public AbilitiesPacket(final Entity entity) {
        actionSet = entity.getActionSet();
    }

    public AbilitiesPacket(final ClientGame game) {
        this.actionSet = game.getActionSet();
        // this.actionSet = new ActionSet();
        // this.actionSet.getLongActions().add(null);
    }

    @Override
    public String toString() {
        return "Packet [actionSet=" + actionSet + "]";
    }
}