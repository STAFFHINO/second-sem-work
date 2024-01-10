package itis.second_sem_work.files.game.control;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import itis.second_sem_work.files.utils.ArraySet;

public class SetOfAbilities implements Serializable {
    @Serial
    private static final long serialVersionUID = -4852037557772448218L;

    private final ArrayList<Abilities> instantActions;
    private final ArraySet<Abilities> longActions;

    public SetOfAbilities() {
        instantActions = new ArrayList<Abilities>();
        longActions = new ArraySet<Abilities>();
    }

    public ArrayList<Abilities> getInstantActions() {
        return instantActions;
    }

    public ArraySet<Abilities> getLongActions() {
        return longActions;
    }

    @Override
    public String toString() {
        return "ActionSet [instantActions=" + instantActions + ", longActions=" + longActions + "]";
    }
}