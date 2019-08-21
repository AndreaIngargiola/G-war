package controller;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import model.engine.GameModel;
import model.engine.GameModelImpl;
import view.MainView;
import view.ViewState;
import viewGame.GameView;
import viewGame.GameViewImpl;

/**
 * Implementation of {@link GameController}.
 */
public class GameControllerImpl implements GameController {

    private static final double FRAMERATE = 1.0 / 60;
    private final  GameModel model;
    private final GameView view;
    private final MainView mainView;

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
    public GameControllerImpl(final Stage stage, final MainView mainView) {
        this.mainView = mainView;
        this.view = new GameViewImpl(stage);
        this.model = new GameModelImpl();
    }

    @Override
    public void start()  {
        animationTimer.start();
        model.start();
        view.start();
        System.out.println("start");
    }

    @Override
    public void stop()  {
        animationTimer.stop();
        model.stop();
    }

    private void update() {
        boolean deadPlayer = this.model.updateEntity(FRAMERATE);

        if (deadPlayer) {
            stop();
            this.mainView.setViewState(ViewState.GAME_OVER, GameViewImpl.getStatistics().getCurrentPoint());
            GameViewImpl.getStatistics().setPoints(0);

        }
    }
}
