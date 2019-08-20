package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * Implementation of {@link GameView}.
 *
 */
public class GameViewImpl implements GameView {

    private static final StatisticsView STATISTICS = new StatisticsViewImpl();
    private static final Group HEAD = new Group();
    private static final Scene SCENE = new Scene(HEAD);
    private static final Group ROOT = new Group();
    private final Group background = new Group(ROOT);
    private final Canvas canvas = new Canvas(1380, 780);

    /**
     * 
     * @param stage
     *           the stage
     */
    public GameViewImpl(final Stage stage) {

        stage.setTitle("G-war");
        stage.setScene(SCENE);
        background.getChildren().add(new ImageView(new Image("/img/background.jpg")));
        background.getTransforms().addAll(new Scale(1.35f, 1.47f));
        ROOT.getTransforms().addAll(new Scale(5, 5));

        HEAD.getChildren().add(canvas);
        HEAD.getChildren().add(background);
        HEAD.getChildren().add(STATISTICS.getRoot());
        HEAD.getChildren().add(ROOT);

        stage.show();
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
