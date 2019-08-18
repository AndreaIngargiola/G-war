package view;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The Main View.
 */
public class MainViewImpl extends Application implements MainView {

    private final Controller controller;
    private Stage stage;
    private ViewState currentState;
    private ViewController viewController;
    private Node node;
    private final OrderReadFileByScore orderFileByScore = new OrderReadFileByScore();


    /**
     * Create a new View.
     */
    public MainViewImpl() {
        super();
        this.controller = new ControllerImpl(this);
    }

    @Override
    public final void start(final Stage primaryStage) throws Exception {

        this.stage = primaryStage;
        this.stage.centerOnScreen();

        stage.setTitle("Geometric Warfare");
        stage.setResizable(true);
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinY());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        setViewState(ViewState.MAIN_MENU, null);
    }

    @Override
    public final void setViewState(final ViewState state, final Integer score) {

        this.currentState = state;
        if (this.currentState == ViewState.GAME_OVER) {
            GameOverController.setScore(score);
        }
        this.uploadFxmlFile(state.getSceneNode(), this.controller, this);
        this.getController().initializeViewController(this, this.controller);
        final Parent parent = (Parent) this.getNode();
        final Scene newScene = new Scene(parent);
        final Stage stage = this.stage;
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinY());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Method to upload the FXML file.
     * @param sceneNode
     *         the scene to upload
     * @param controller
     *         the controller of the application
     * @param view
     *         the view.
     */
    public void uploadFxmlFile(final SceneNode sceneNode, final Controller controller, final MainView view) {
        try {
            if (this.currentState == ViewState.LEADERBOARD) {
                this.orderFileByScore.readFileAndOrder();
            }
            final FXMLLoader loader = new FXMLLoader();
            final URL location = ClassLoader.getSystemClassLoader().getResource(sceneNode.getPath());
            loader.setLocation(location);
            final Node node = (Node) loader.load();
            final ViewControllerImpl viewController = loader.getController();
            this.node = node;
            this.viewController = viewController;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get ViewController.
     * 
     * @return ViewController
     *         the viewController of node.
     */
    public ViewController getController() {
        return this.viewController;
    }

    /**
     * Method to get the Node.
     * 
     * @return Node
     *         the node of SceneNode. 
     */
    public Node getNode() {
        return this.node;
    }

    /**
     * Main.
     * @param args
     *         The args of main.
     */
    public static void main(final String[] args) {
        launch(args);

    }

}
