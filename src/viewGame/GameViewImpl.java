package viewGame;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Implementation of {@link GameView}.
 *
 */
public class GameViewImpl implements GameView {

    private static StatisticsView STATISTICS = new StatisticsViewImpl();
    private static Group HEAD = new Group();
    private static Scene SCENE = new Scene(HEAD);
    private static Group ROOT = new Group();
    private final Stage stage;
    private final Group background = new Group(ROOT);
    private final Canvas canvas = new Canvas(1380, 780);
    private static final double STAGE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double STAGE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double SCALE = STAGE_WIDTH / 300;
    
    /**
     * 
     * @param stage
     *           the stage
     */
    public GameViewImpl(final Stage stage) {

    	this.stage = stage;
       // stage.setScene(SCENE);
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
    //	stage.show();
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
