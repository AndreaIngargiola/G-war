package view;

import java.util.Timer;

import org.jbox2d.dynamics.World;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class GameViewImpl implements GameView {

    private static final StatisticsView STATISTICS = new StatisticsViewImpl();
    private static final Group TRUE_ROOT = new Group();
    private static final Scene SCENE = new Scene( TRUE_ROOT );
    
	private static final Group ROOT = new Group();
	private final Group background = new Group(ROOT);
	private final Canvas canvas = new Canvas( 1380, 780 );
    
	
	public GameViewImpl(Stage stage) {

		stage.setTitle( "Timeline Example" );
   	    stage.setScene( SCENE );
   	    stage.setScene( SCENE );
   	    Image img = new Image("img/SFONDO.jpg");
   	    background.getChildren().add(new ImageView(new Image("img/SFONDO.jpg")));
   	    background.getTransforms().addAll(new Scale(1.35f, 1.47f));
   	    ROOT.getTransforms().addAll(new Scale(5, 5 ));

   	    TRUE_ROOT.getChildren().add( canvas );
   	    TRUE_ROOT.getChildren().add(background);
   	    TRUE_ROOT.getChildren().add(STATISTICS.getRoot());
   	    TRUE_ROOT.getChildren().add(ROOT);
   	    
	    stage.show();
	}
	
    public static StatisticsView getStatistics() {
		return STATISTICS;
	}
    
    public static Group getRoot() {
		return ROOT;
	} 
    
    public static Scene getScene() {
		return SCENE;
	} 
}
