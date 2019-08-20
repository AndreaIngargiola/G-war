package controller;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import model.engine.GameModel;
import model.engine.GameModelImpl;
import view.GameView;
import view.GameViewImpl;

/**
 * Implementation of {@link GameController}.
 */
public class GameControllerImpl implements GameController {

    private static final double FRAMERATE = 1.0 / 60;
    private final  GameModel model;
    private final GameView view;

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

        animationTimer.start();
    }

    private void update() {
        this.model.updateEntity(FRAMERATE);
    }

}
