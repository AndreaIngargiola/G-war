package controller;

import java.util.LinkedHashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import model.engine.GameModel;
import model.engine.GameModelImpl;
import view.GameView;
import view.GameViewImpl;

/**
 * Models the game controller.
 */
public class GameControllerImpl implements GameController {

    private static final double FRAMERATE = 1.0 / 60;
    private GameModel model;
    private GameView view;
    private Set<EntityController> entities;
    private final AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(final long now) {
            update();
        }
    };

    /**
     * 
     * @param stage
     *           the stage
     */
    public GameControllerImpl(final Stage stage) {

        this.view = new GameViewImpl(stage);
        this.model = new GameModelImpl();
        entities = new LinkedHashSet<>();
        spawnEntity();
        animationTimer.start();
    }

//    @Override
//    public void stop() {
//        animationTimer.stop();
//    }
//
//    @Override
//    public EntityStatistic getPlayerStatistic() {
//        return loader.getPlayerStatistic();
//    }




    public void spawnEntity() {
    	EntityController player = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(40 ,40)).createPlayer(GameViewImpl.getScene(), GameViewImpl.getStatistics());
	    entities.add(player);
	    
	    EntityController coward = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(15, 40)).createCoward();
	    entities.add(coward);

	    EntityController platform1 = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(10, 50 )).createPlatform();
	    entities.add(platform1);
	    
	    EntityController platform2 = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(20, 50 )).createPlatform();
	    entities.add(platform2);
	    
	    EntityController platform3 = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(30, 50 )).createPlatform();
	    entities.add(platform3);
	    
	    EntityController platform4 = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(40, 50 )).createPlatform();
	    entities.add(platform4);
	    
	    EntityController floor = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(50, 80 )).createFloor();
	    entities.add(platform4);
	    
	    EntityController grill = new EntityFactoryImpl(GameViewImpl.getRoot(), new Vec2(50, 50 )).createGrill();
	    entities.add(grill);
    }
    private void update() {

        updateEntity(FRAMERATE);
    }
    
    public void updateEntity(final double dt) {
        entities.stream()
                .forEach(e -> e.update(dt));
        //DEVO TOGLIERLE SE MUOIONO
    }

}
