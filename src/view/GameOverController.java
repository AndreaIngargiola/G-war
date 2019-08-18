package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Controller of game over view.
 */
public class GameOverController extends ViewControllerImpl {

    @FXML
    private TextField usernameTextField;
    @FXML
    private Button toRecordBtn;
    @FXML
    private Label scorePlayerLeaderboard;

    private String playerName;
    private final OrderReadFileByScore orderFile = new OrderReadFileByScore();
    private final RecordScore addPlayerLeaderboard = new RecordScore();
    private static final int MAXPLAYER = 10;
    private static Integer scoreTest;

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
        orderFile.readFileAndOrder();
        this.scorePlayerLeaderboard.setText("Score:  " + scoreTest);
        if ((this.orderFile.getNumberPlayerInLeaderboard() < MAXPLAYER) || (scoreTest > this.orderFile.getLastScore())) {
            this.toRecordBtn.setOnAction(event);
        } else {
            this.usernameTextField.setVisible(false);
            this.toRecordBtn.setVisible(false);
        }
    }

    private final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(final ActionEvent e) {
            playerName = usernameTextField.getText();
            toRecordBtn.setVisible(false);
            addPlayerLeaderboard.addRecord(getPlayerName(), scoreTest);

        }
    };

    /**
     * Method to show the main menu.
     * @throws IOException
     *         the IOException.
     */
    @FXML
    public void showMainMenu() throws IOException {
        this.getView().setViewState(ViewState.MAIN_MENU, null);
    }

    /**
     * Method to exit from game.
     */
    @FXML
    public void exitGO() {
        this.getController().closeApplication();
    }

    private String getPlayerName() {
        return this.playerName;
    }

    /**
     * Set the score of player.
     * @param score
     *         the score of player.
     */
    public static void setScore(final Integer score) {
        scoreTest = score;
    }
}
