package itis.second_sem_work.files.gui.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import itis.second_sem_work.sockets.net.ClientGame;
import itis.second_sem_work.files.game.control.Abilities;
import itis.second_sem_work.files.utils.ArraySet;

public class ClientInputHandler implements KeyListener {

    private final boolean[] previouslyPressed;
    private final ClientGame game;

    public ClientInputHandler(final ClientGame game) {
        this.game = game;
        previouslyPressed = new boolean[0xFFFF];
        Arrays.fill(previouslyPressed, false);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final int keyCode = e.getKeyCode();

        if (previouslyPressed[keyCode]) {
            return;
        }

        final ArraySet<Abilities> longActions = game.getActionSet().getLongActions();
        final ArrayList<Abilities> instantActions = game.getActionSet().getInstantActions();
        switch (keyCode) {
            case KeyEvent.VK_A: {
                longActions.add(Abilities.LEFT_WALK);
                break;
            }

            case KeyEvent.VK_D: {
                longActions.add(Abilities.RIGHT_WALK);
                break;
            }

            case KeyEvent.VK_SPACE: {
                instantActions.add(Abilities.JUMP);
                break;
            }

            case KeyEvent.VK_ENTER: {
                instantActions.add(Abilities.SHOOT);
                break;
            }

            default:
                break;
        }
        previouslyPressed[keyCode] = true;
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int keyCode = e.getKeyCode();

        final ArraySet<Abilities> longActions = game.getActionSet().getLongActions();

        switch (keyCode) {
            case KeyEvent.VK_A: {
                longActions.remove(Abilities.LEFT_WALK);
                break;
            }

            case KeyEvent.VK_D: {
                longActions.remove(Abilities.RIGHT_WALK);
                break;
            }

            default:
                break;
        }
        previouslyPressed[keyCode] = false;
    }
}
