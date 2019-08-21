package model;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

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
import model.engine.TimerTaskGrill;
import model.entities.Entity;
import model.entities.InvisibleWall;
import model.levelsgenerator.LevelGenerator;
import model.levelsgenerator.LevelGeneratorImpl;
import model.physics.BodyBuilder;
import model.physics.BodyBuilderImpl;
import model.physics.MyContactListener;
import view.GameViewImpl;


public class GameModelImpl implements GameModel{

    private final static Vec2 GRAVITY =  new Vec2(0, 60);
    private final static World MYWORLD = new World(GRAVITY, true);
    private static final double FRAMERATE = 1.0 / 60;
    private final static TimerTaskGrill TIMER_TASK = new TimerTaskGrill(); 
    public final static List<List<EntityController>> ENTITIES = new ArrayList<>();
    
    private final Timer timer = new Timer();
    private final long timeStart = System.currentTimeMillis();
    private final float timestep = 0.017f;
    private final int velocityIteration = 5;
    private final int positionIteration = 2;
    private final Timeline gameLoop = new Timeline();
    private final LevelGenerator lg;
    private final EntityFactory entityFactory = new EntityFactoryImpl(GameViewImpl.getRoot());
    private EntityController player;
    private float previousPosition;
    private final KeyFrame kf = new KeyFrame(
                Duration.seconds(GameModelImpl.FRAMERATE),                // 60 FPS
                new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent ae)
                    {
                        
                        double t = (System.currentTimeMillis() - timeStart) / 1000.0; 
                        MYWORLD.step(timestep, velocityIteration, positionIteration);
                       // if()

                    }
                }
);
    
    public  GameModelImpl () {
            timer.schedule(TIMER_TASK, 0, 3000);
            
            this.lg = new LevelGeneratorImpl();
            
            
            MYWORLD.setContactListener(new MyContactListener());

            gameLoop.setCycleCount(Timeline.INDEFINITE);
            GameModelImpl.ENTITIES.add(new ArrayList<>());
            this.player = entityFactory.createPlayer(GameViewImpl.getScene(), 
                    GameViewImpl.getStatistics(), 
                    new Vec2(20, 50));
            
            
            BodyBuilder wall = new BodyBuilderImpl();
            wall.setPosition(new Vec2(0, 200))
            .setSize(new Vec2(10, 400))
            .setIsMoveable(false)
            .setSubjectToForces(false)
            .build();
            
            gameLoop.getKeyFrames().add( kf );
            gameLoop.play();
            try {
                this.generateLevel();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         //   EntityController player = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(0,0)).createPlayer(GameViewImpl.getScene(), GameViewImpl.getStatistics());
           // player.getEntityModel().getBody().getPosition();
    }
    
    private void generateLevel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        GameModelImpl.ENTITIES.add(new ArrayList<>());
        int larghezzaLivello = 200;
        float offset = larghezzaLivello * lg.getIteration();
        final Map<Point, String> levelDraft = this.lg.getNewLevel();
        

        for (Point p : levelDraft.keySet()) { 
            final Vec2 convertedCoordinate = new Vec2((p.x*10) + offset, -(p.y*10)+150);
                Method m;
                try {
                    m = this.entityFactory.getClass().getMethod("create" + levelDraft.get(p), Vec2.class);
                    m.setAccessible(true);
                    EntityController ent = (EntityController) m.invoke(this.entityFactory, convertedCoordinate);
                    GameModelImpl.ENTITIES.get(this.lg.getIteration()-1).add(ent);
                } catch (NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
           // }  
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
        
        if (this.player.getEntityModel().getBody().getPosition().x - this.previousPosition > 200) {
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
                Entity wall = new InvisibleWall(bodyWall, new Vec2((desideredLevelNumber + 1) * 200, 200));
            }
        }
    }

}
