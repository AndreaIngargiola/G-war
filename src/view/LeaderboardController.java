package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.PlayerLeaderboard;

/**
 * Controller of rankingPoints view.
 */
public class LeaderboardController {

    @FXML
    private TableView<PlayerLeaderboard> playerTable;
    @FXML
    private TableColumn<PlayerLeaderboard, String> playerNameColumn;
    @FXML
    private TableColumn<PlayerLeaderboard, Integer> playerScoreColumn;

    private MainMenuGame mainMenuGame;

    /**
     * create list and add some sample data.
     * @return
     *         the list of player.
     */
    public ObservableList<PlayerLeaderboard> getPlayerList() {
        ObservableList<PlayerLeaderboard> playerList = FXCollections.observableArrayList();
        playerList.add(new PlayerLeaderboard("E", 5000));
        playerList.add(new PlayerLeaderboard("A", 3000));
        playerList.add(new PlayerLeaderboard("V", 2000));
        return playerList;
    }

    @FXML
    private void initialize() {
        playerNameColumn.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
        playerScoreColumn.setCellValueFactory(cellData -> cellData.getValue().playerScoreProperty().asObject());
        playerTable.setItems(this.getPlayerList());

    }


    @FXML
    private void showMainMenu() {
        try {
            this.mainMenuGame.getMainMenu();
        } catch (Exception e) { }
    }

    @FXML
    private void exitL() {
        Platform.exit();
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
