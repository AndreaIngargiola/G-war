package view;

import java.io.IOException;

import javafx.fxml.FXML;

/**
 * Controller view of main menu.
 */
public class MainMenuGameController extends ViewControllerImpl {

    /**
     * Method for new game.
     */
    @FXML
    protected void newGame() {

    }

    /**
     * Method to exit the game.
     */
    @FXML
    protected void exit() {
        this.getController().closeApplication();
    }

    /**
     * Method to show leaderboard.
     * @throws IOException
     *         the IOExcption
     */
    @FXML
    public void leaderboard() throws IOException {
        this.getView().setViewState(ViewState.LEADERBOARD, null);
    }
}
