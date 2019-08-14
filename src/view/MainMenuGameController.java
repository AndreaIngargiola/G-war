package view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Controller view of main menu.
 */
public class MainMenuGameController implements ControllerView {
    private MainMenuGame mainMenuGame;

    /**
     * Method for new game.
     */
    @FXML
    public void newGame() {
        //System.out.println("Avvio del gioco");
    }

    /**
     * Method to exit the game.
     */
    @FXML
    public void exit() {
        Platform.exit();
    }

    /**
     * Method to show leaderboard.
     * @throws IOException 
     */
    @FXML
    public void leaderboard() throws IOException {
        this.mainMenuGame.showLeaderboard();
    }

    @Override
    public final void setMainMenuGame(final MainMenuGame mainMenu) {
        this.mainMenuGame = mainMenu;
    }

}
