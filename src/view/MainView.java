package view;

import controller.GameController;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Interface of MainView.
 */
public interface MainView {

    /**
     * Set state of application.
     * @param state
     *         the state
     * @param score
     *         the hypothetical score of player.
     */
    void setViewState(ViewState state, Integer score);

    /**
     * Method to get the stage.
     * @return Stage
     *         the stage.
     */
    Stage getStage();

    /**
     * Method to get ViewController.
     * @return ViewController
     *         the viewController of node.
     */
    ViewController getViewController();

    /**
     * Method to get the Node.
     * @return Node
     *         the node of SceneNode. 
     */
    Node getNode();

    /**
     * Method to get the Game Controller.
     * @return GameController
     *          the game controller.
     */
    GameController getGameController();

}
