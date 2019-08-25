package view;

import java.io.IOException;
import java.net.URL;

import controller.GameController;
import controller.GameControllerImpl;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.model.OrderReadFileByScore;

/**
 * The Main View.
 */
public class MainViewImpl implements MainView {

    private final Stage stage;
    private ViewState currentState;
    private ViewController viewController;
    private Node node;
    private final OrderReadFileByScore orderFileByScore = new OrderReadFileByScore();
    private final GameController gameController;

    /**
     * Constructor of MainViewImpl.
     * @param primaryStage
     *         the stage.
     */
    public MainViewImpl(final Stage primaryStage) {
        super();
        this.stage = primaryStage;
        this.gameController = new GameControllerImpl(this.stage, this);
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setTitle("Geometric Warfare");
        stage.setResizable(false);
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setOnCloseRequest(e -> System.exit(0));
        setViewState(ViewState.MAIN_MENU, null);
    }

    @Override
    public final void setViewState(final ViewState state, final Integer score) {
        this.currentState = state;
        if (this.currentState.equals(ViewState.GAME_OVER)) {
            GameOverController.setScore(score);
        }
        this.uploadFxmlFile(state.getSceneNode());
        this.getViewController().initializeViewController(this);
        final Parent parent = (Parent) this.getNode();
        final Scene newScene = new Scene(parent);
        final Stage stage = this.stage;
        stage.setScene(newScene);
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setResizable(false);
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.show();
    }

    /**
     * Method to upload the FXML file.
     * @param sceneNode
     *         the scene to upload.
     */
    public void uploadFxmlFile(final SceneNode sceneNode) {
        try {
            if (this.currentState.equals(ViewState.LEADERBOARD)) {
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

    @Override
    public final ViewController getViewController() {
        return this.viewController;
    }

    @Override
    public final Node getNode() {
        return this.node;
    }

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @Override
    public final GameController getGameController() {
        return this.gameController;
    }

}
