package itis.second_sem_work.sockets.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Logger;
import itis.second_sem_work.files.game.control.SetOfAbilities;
import itis.second_sem_work.files.game.items.Floor;
import itis.second_sem_work.files.game.items.Player;
import itis.second_sem_work.files.game.items.Vectors;
import itis.second_sem_work.files.game.items.U_turn;

public class ServerGame {
    public static abstract class Entity implements Serializable {
        @Serial
        private static final long serialVersionUID = -1816334362202070857L;
        private transient final ServerGame game;
        private final int id;
        private final double width, height;
        private double x, y;
        private SetOfAbilities actionSet;

        public Entity(final ServerGame game, final double width, final double height, final double x,
                      final double y) {
            this.game = game;
            this.id = this.game.getSmallestAvailableId();
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            actionSet = new SetOfAbilities();
            game.addEntity(this);
        }

        public ServerGame getGame() {
            return game;
        }

        public SetOfAbilities getActionSet() {
            return actionSet;
        }

        public final int getId() {
            return id;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        public double shiftX(final double shiftFactor) {
            return x += shiftFactor;
        }

        public double shiftY(final double shiftFactor) {
            return y += shiftFactor;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getLeftX() {
            return getX();
        }

        public double getBottomY() {
            return getY();
        }

        public double getRightX() {
            return getLeftX() + getWidth();
        }

        public double getTopY() {
            return getBottomY() + getHeight();
        }

        public double getCenterX() {
            return getLeftX() + getWidth() / 2;
        }

        public void setX(final double x) {
            this.x = x;
        }

        public void setY(final double y) {
            this.y = y;
        }

        public void setActionSet(final SetOfAbilities actionSet) {
            this.actionSet = actionSet;
        }

        public abstract void tick();

        public abstract void handleCollision(Entity otherEntity);

        public boolean isColliding(final Entity otherEntity) {
            return getX() < otherEntity.getX() + otherEntity.getWidth() && getX() + getWidth() > otherEntity.getX()
                    && getY() < otherEntity.getY() + otherEntity.getHeight()
                    && getY() + getHeight() > otherEntity.getY()
                    && this != otherEntity;
        }

        public Vectors getCollisionNormal(final Entity otherEntity) {
            final double xOverlap = Math.min(this.getX() + this.getWidth(), otherEntity.getX() + otherEntity.getWidth())
                    - Math.max(this.getX(), otherEntity.getX());
            final double yOverlap = Math.min(this.getY() + this.getHeight(),
                    otherEntity.getY() + otherEntity.getHeight())
                    - Math.max(this.getY(), otherEntity.getY());

            if (xOverlap > yOverlap) {
                return new Vectors(0, Math.signum(otherEntity.getY() - this.getY()));
            } else if (xOverlap < yOverlap) {
                return new Vectors(Math.signum(otherEntity.getX() - this.getX()), 0);
            } else {
                return new Vectors(Math.signum(otherEntity.getX() - this.getY()),
                        Math.signum(otherEntity.getY() - this.getY()));
            }
        }
    }

    public static class GameSettings {
        public static final double GLOBAL_GRAVITY = -0.05;

        public static final double WALK_SPEED = 0.125;
        public static final double JUMP_VEL = 0.5;

        public static final double BULLET_SPEED = 0.25;
        public static final int BULLET_LIFESPAN = 20;
    }

    private static final Logger logger = Logger.getLogger("Server");

    public static Logger getLogger() {
        return logger;
    }

    private int smallestAvailableId = 0;
    private final TreeMap<Integer, Entity> entities;

    public ServerGame() {
        entities = new TreeMap<Integer, Entity>();

        init();
    }

    public TreeMap<Integer, Entity> getEntities() {
        return entities;
    }

    public void updateActionSet(final int id, final SetOfAbilities actionSet) {
        entities.get(id).setActionSet(actionSet);
    }

    public void removeEntity(final int id) {
        entities.remove(id);
    }

    public void tick() {
        final TreeMap<Integer, Entity> entitiesCopy = new TreeMap<>(entities);
        for (final Entity entity : entitiesCopy.values()) {
            entity.tick();
        }

        for (final Entity entity1 : entitiesCopy.values()) {
            for (final Entity entity2 : entitiesCopy.values()) {
                if (entity1.isColliding(entity2)) {
                    entity1.handleCollision(entity2);
                }
            }
        }
    }

    public int spawnPlayerEntity() {
        final Player player = new Player(this, 4.5, 5, U_turn.HorDirection.LEFT);
        return player.getId();
    }

    private int getSmallestAvailableId() {
        return smallestAvailableId++;
    }

    private void init() {
        // load world platforms
        try (Scanner platforms = new Scanner(new File("src/res/standard-map-platform-rectangles.csv"))) {
            while (platforms.hasNextLine()) {
                final String[] dimensionsString = platforms.nextLine().split(",");
                new Floor(this,
                        Double.parseDouble(dimensionsString[0]),
                        Double.parseDouble(dimensionsString[1]),
                        Double.parseDouble(dimensionsString[2]),
                        Double.parseDouble(dimensionsString[3]));
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addEntity(final Entity entity) {
        entities.put(entity.getId(), entity);
    }
}
