package view.world;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.entities.StatisticsView;
import view.entities.StatisticsViewImpl;

/**
 * Implementation of {@link GameView}.
 *
 */
public final class GameViewImpl implements GameView {

    private static final StatisticsView STATISTICS = new StatisticsViewImpl();
    private static final Group HEAD = new Group();
    private static final Scene SCENE = new Scene(HEAD);
    private static final Group ROOT = new Group();
    private static final double STAGE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double STAGE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double SCALE = STAGE_WIDTH / 300;
    private final Stage stage;
    private final Group background = new Group(ROOT);
    private final Canvas canvas = new Canvas(1380, 780);

    /**
     * 
     * @param stage
     *           the stage
     */
    public GameViewImpl(final Stage stage) {

        this.stage = stage;
        background.getChildren().add(new ImageView(new Image("/img/background.jpg", STAGE_WIDTH, STAGE_HEIGHT, false, false)));

        ROOT.getTransforms().addAll(new Scale(SCALE, SCALE));

        HEAD.getChildren().add(canvas);
        HEAD.getChildren().add(background);
        HEAD.getChildren().add(STATISTICS.getRoot());
        HEAD.getChildren().add(ROOT);

    }

    @Override
    public void start() {
        stage.setScene(SCENE);
    }
    /**
     * 
     * @return the statistics view
     */
    public static StatisticsView getStatistics() {
            return STATISTICS;
    }

    /**
     * 
     * @return the root for the entities view
     */
    public static Group getRoot() {
        return ROOT;
    } 

    /**
     * 
     * @return the scene
     */
    public static Scene getScene() {
        return SCENE;
    }

}
