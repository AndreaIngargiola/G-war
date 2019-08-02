package view;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Controller view of main menu.
 */
public class MainMenuGameController {
    private MainMenuGame mainMenuGame;
    /**
     * Method for new game.
     */
    @FXML
    private void newGame() {
        System.out.println("Avvio del gioco");
    }

    /**
     * Method to exit the game.
     */
    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void leaderboard() {
        try {
            this.mainMenuGame.showLeaderboard();
        } catch (Exception e) { }
    }

    /**
     * Sets main menu.
     * @param mainMenu
     */
    public void setMainMenuGame(MainMenuGame mainMenu) {
        this.mainMenuGame = mainMenu;
    }
}
