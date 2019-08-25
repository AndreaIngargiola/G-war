package model;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sun.javafx.geom.Vec2d;

import controller.EntityController;
import controller.EntityFactory;
import controller.EntityFactoryImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;
import model.engine.TimerTaskGrill;
import model.entities.Entity;
import model.entities.InvisibleWall;
import model.levelsgenerator.LevelGenerator;
import model.levelsgenerator.LevelGeneratorImpl;
import model.physics.BodyBuilder;
import model.physics.BodyBuilderImpl;
import model.physics.MyContactListener;
import view.GameViewImpl;
import view.StatisticsView;


public class GameModelImpl implements GameModel{

    /**
     * ENTITIES is the list of active entities in the game engine that will be updated each frame.
     */
    public static final List<List<EntityController>> ENTITIES = new ArrayList<>();

    private static final Vec2 GRAVITY =  new Vec2(0, 60);
    private static final World MYWORLD = new World(GRAVITY, true);
    private static final double FRAMERATE = 1.0 / 60;
    private static final TimerTaskGrill TIMER_TASK = new TimerTaskGrill();
    private static final int ENTITIES_PIXEL_DIMENSION = 10;

    private final Timer timer = new Timer();
    private final float timestep = 0.017f;
    private final int velocityIteration = 5;
    private final int positionIteration = 6;
    private final Timeline gameLoop = new Timeline();

    private final int levelExtension = 200;
    private final LevelGenerator lg;
    private final EntityFactory entityFactory = new EntityFactoryImpl(GameViewImpl.getRoot());
    private EntityController player;
    private float previousPosition;
 
    private final KeyFrame kf = new KeyFrame(
                Duration.seconds(GameModelImpl.FRAMERATE),                // 60 FPS
                new EventHandler<ActionEvent>() {
                    public void handle(final ActionEvent ae) {
                        MYWORLD.step(timestep, velocityIteration, positionIteration);
                    }
                }
);

    public  GameModelImpl () {
            timer.schedule(TIMER_TASK, 0, 3000);

            this.lg = new LevelGeneratorImpl();

            MYWORLD.setContactListener(new MyContactListener());

            gameLoop.setCycleCount(Timeline.INDEFINITE);
            GameModelImpl.ENTITIES.add(new ArrayList<>());

            BodyBuilder bodyWall = new BodyBuilderImpl();
            new InvisibleWall(bodyWall, new Vec2(-40, 200));

            gameLoop.getKeyFrames().add(kf);
            gameLoop.play();
            
            try {
                this.generateLevel();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { 
                e.printStackTrace();
            }
            
            //a little override of previous position that prevents delays in the level spawn.
            this.previousPosition = 0;
    }

    private void generateLevel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        //add the iteration-th array to ENTITIES and update the offset.
        GameModelImpl.ENTITIES.add(new ArrayList<>());
        float offset = this.levelExtension * lg.getIteration();

        //get a new level from the level generator and process the output
        final Map<Point, String> levelDraft = this.lg.getNewLevel();

        for (Point p : levelDraft.keySet()) { 
            final Vec2 convertedCoordinate = new Vec2((p.x * 10) + offset, -(p.y * 10) + 150);
                Method m;
                try {
                    if (levelDraft.get(p).equals("Player")) {
                        this.player = this.entityFactory.createPlayer(GameViewImpl.getScene(), 
                                                                      GameViewImpl.getStatistics(), 
                                                                      convertedCoordinate);
                    } else {
                        m = this.entityFactory.getClass().getMethod("create" + levelDraft.get(p), Vec2.class);
                        m.setAccessible(true);
                        EntityController ent = (EntityController) m.invoke(this.entityFactory, convertedCoordinate);
                        GameModelImpl.ENTITIES.get(this.lg.getIteration() - 1).add(ent);
                    }
                } catch (NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        }
        this.previousPosition = this.player.getEntityModel().getBody().getPosition().x;
    }

    public static World getWorld() {
                return MYWORLD;
        }

    public static TimerTaskGrill getTimer() {
                return TIMER_TASK;
        }

    public static List<List<EntityController>> getEntities() {
        return ENTITIES;
    }

    @Override
    public void  updateEntity(final double dt) {
        ENTITIES.stream()
                .forEach(l -> {
                    l.stream().forEach(e -> e.update(dt));
                });
        this.player.update(dt);

        if (this.player.getEntityModel().getBody().getPosition().x - this.previousPosition > this.levelExtension) {
            try {
                this.generateLevel();
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (this.lg.getIteration() > 3) {
                final int desideredLevelNumber = this.lg.getIteration() - 4;

                System.out.println("deallocate lvl: " + desideredLevelNumber);
                GameModelImpl.ENTITIES.get(desideredLevelNumber).stream().forEach(e -> e.getEntityModel().destroy());
                GameModelImpl.ENTITIES.get(desideredLevelNumber).clear();
                BodyBuilder bodyWall = new BodyBuilderImpl();
                new InvisibleWall(bodyWall, new Vec2((desideredLevelNumber + 1) * this.levelExtension, this.levelExtension));
            }
        }
    }

}
