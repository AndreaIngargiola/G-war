package model;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import controller.EntityController;
import controller.EntityFactory;
import controller.EntityFactoryImpl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import model.components.Points;
import model.components.TimerGrill;
import model.entities.InvisibleWall;
import model.levelsgenerator.LevelGenerator;
import model.levelsgenerator.LevelGeneratorImpl;
import model.physics.BodyBuilder;
import model.physics.BodyBuilderImpl;
import model.physics.MyContactListener;

import view.world.GameViewImpl;

/**
 * An implementation of the game loop model that uses a dynamic creation of levels via LevelGeneratorImpl and EntityFactoryImpl.
 */
public class GameModelImpl implements GameModel {

    /**
     * ENTITIES is the list of active entities in the game engine that will be updated each frame.
     */
    public static final List<List<EntityController>> ENTITIES = new ArrayList<>();

    private static final Vec2 GRAVITY =  new Vec2(0, 60);
    private static final World MYWORLD = new World(GRAVITY, true);
    private static final int LEVEL_EXTENSION = 200;
    private static final double FRAMERATE = 1.0 / 60;             // 60 FPS
    private static final int ENTITIES_PIXEL_DIMENSION = 10;
    private static final int POINTS = 50;
    private static final float TIMESTEP = 0.017f;
    private static final int VELOCITY_ITERATION = 5;
    private static final int POSITION_ITERATION = 2;
    private static final Vec2 INVISIBLE_WALL_DIM = new Vec2(10, 400);
    private static final int LEVELS_IN_STACK = 3;
    private static final int TIMER_TIME = 200;

    private InvisibleWall levelLimit;
    private final Timeline gameLoop = new Timeline();
    private final EntityFactory entityFactory = new EntityFactoryImpl();
    private LevelGenerator lg;
    private EntityController player;
    private float previousPosition;
    private int iterationBeforeChange;
 
    private final KeyFrame kf = new KeyFrame(
                Duration.seconds(GameModelImpl.FRAMERATE),
                new EventHandler<ActionEvent>() {
                    public void handle(final ActionEvent ae) {
                        MYWORLD.step(GameModelImpl.TIMESTEP, 
                                     GameModelImpl.VELOCITY_ITERATION, 
                                     GameModelImpl.POSITION_ITERATION);
                    }
                }
);

    /**
     * A constructor without parameters that initialize the local variables.
     */
    public GameModelImpl() {
            MYWORLD.setContactListener(new MyContactListener());
            gameLoop.setCycleCount(Timeline.INDEFINITE);
            GameModelImpl.ENTITIES.add(new ArrayList<>());
            gameLoop.getKeyFrames().add(kf);

            final BodyBuilder bodyWall = new BodyBuilderImpl();
            this.levelLimit = new InvisibleWall(bodyWall, new Vec2(-GameModelImpl.INVISIBLE_WALL_DIM.x, GameModelImpl.INVISIBLE_WALL_DIM.y / 2));
    }

    @Override
    public final void start() {
        this.iterationBeforeChange = 0;
        this.lg = new LevelGeneratorImpl();
        try {
            this.generateLevel();
            this.generateLevel();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { 
            e.printStackTrace();
        }
        gameLoop.play();

        //a little override of previous position that prevents delays in the level spawn.
        this.previousPosition = 0;
    }
 
    @Override
    public final void stop() {
        gameLoop.stop();
            reset();
    }

    private void reset() {
        GameModelImpl.ENTITIES.stream()
        .forEach(e -> e.stream().forEach(i -> i.getEntityModel().destroy()));
        GameModelImpl.ENTITIES.stream().forEach(e -> e.clear());
        GameModelImpl.ENTITIES.clear();
        this.player.getEntityModel().destroy();
        GameViewImpl.getStatistics().setMaxHealth();
        this.setLevelDelimiter(new Vec2(-GameModelImpl.INVISIBLE_WALL_DIM.x, GameModelImpl.INVISIBLE_WALL_DIM.y / 2));
    }

    private void generateLevel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        //add the iteration-th array to ENTITIES and update the offset.
        GameModelImpl.ENTITIES.add(new ArrayList<>());
        final float offset = GameModelImpl.LEVEL_EXTENSION * lg.getIteration();

        //get a new level from the level generator and process the output
        final Map<Point, String> levelDraft = this.lg.getNewLevel();

        for (final Point p : levelDraft.keySet()) { 
            final Vec2 convertedCoordinate = new Vec2((p.x * GameModelImpl.ENTITIES_PIXEL_DIMENSION) + offset, 
                                                     -(p.y * GameModelImpl.ENTITIES_PIXEL_DIMENSION) + 150);
                Method m;
                try {
                    if (levelDraft.get(p).equals("Player")) {
                        this.player = this.entityFactory.createPlayer(convertedCoordinate);
                    } else {
                        m = this.entityFactory.getClass().getMethod("create" + levelDraft.get(p), Vec2.class);
                        m.setAccessible(true);
                        final EntityController ent = (EntityController) m.invoke(this.entityFactory, convertedCoordinate);
                        GameModelImpl.ENTITIES.get(this.lg.getIteration() - 1).add(ent);
                    }
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }

        }
        this.previousPosition = this.player.getEntityModel().getBody().getPosition().x;
    }

    /**
     * Get a snapshot of the game world.
     * @return a snapshot of the current situation in the Physics World.
     */
    public static World getWorld() {
                return MYWORLD;
        }

    /**
     * Get the current active entities.
     * @return all the entities in the list.
     */
    public static List<List<EntityController>> getEntities() {
        return ENTITIES;
    }

    @Override
    public final boolean updateEntity(final double dt) {

        this.iterationBeforeChange++;

        if (this.iterationBeforeChange == GameModelImpl.TIMER_TIME) {
            GameModelImpl.ENTITIES.stream().forEach(l -> l.stream()
                                                          .filter(g -> g.getEntityModel().toString().equals("Grill"))
                                                          .forEach(g -> g.getEntityModel().get(TimerGrill.class).changeState()));
            this.iterationBeforeChange = 0;
        }
        //check if the player is alive
        if (!this.player.getEntityModel().isAlive()) {
            stop(); 
        } else {

            //update all entities.
            GameModelImpl.ENTITIES.stream()
                                  .forEach(l -> l.stream().forEach(e -> e.update(dt)));
            this.player.update(dt);

            //manage level creation and destruction
            this.manageLevels();

            return false;
        }
        return true;
    }

    private void manageLevels() {
      //check if another level must be created
        if (this.player.getEntityModel().getBody().getPosition().x - this.previousPosition > GameModelImpl.LEVEL_EXTENSION) {
            try {
                this.generateLevel();
                this.player.getEntityModel().get(Points.class).addPoints(POINTS);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }

            //check if the past levels must be erased.
            if (this.lg.getIteration() > GameModelImpl.LEVELS_IN_STACK) {
                final int desideredLevelNumber = this.lg.getIteration() - (GameModelImpl.LEVELS_IN_STACK + 1);
                GameModelImpl.ENTITIES.get(desideredLevelNumber).stream().forEach(e -> e.getEntityModel().destroy());
                GameModelImpl.ENTITIES.get(desideredLevelNumber).clear();

                //place an invisible wall so the player cannot reach deallocated areas.
                this.setLevelDelimiter(new Vec2((desideredLevelNumber + 1) * GameModelImpl.LEVEL_EXTENSION, GameModelImpl.LEVEL_EXTENSION));
            }
        }
    }

    private void setLevelDelimiter(final Vec2 newPosition) {
        this.levelLimit.destroy();
        final BodyBuilder bodyWall = new BodyBuilderImpl();
        this.levelLimit = new InvisibleWall(bodyWall, newPosition);
    }
}
