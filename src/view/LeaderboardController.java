package view;

import javafx.fxml.FXML;

/**
 * Controller of rankingPoints view.
 */
public class LeaderboardController {
    private MainMenuGame mainMenuGame;

    @FXML
    private void showMainMenu() {
        try {
            this.mainMenuGame.getMainMenu();
        } catch (Exception e) { }
    }

    /**
     * Sets main menu.
     * @param mainMenu
     *         The main menu.
     */
    public void setMainMenuGame(final MainMenuGame mainMenu) {
        this.mainMenuGame = mainMenu;
    }
}
