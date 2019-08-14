package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * Controller of game over view.
 */
public class GameOverController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private Button toRecordBtn;
    @FXML
    private Label scorePlayerLeaderboard;

    private String playerName;
    private final ReadAndOrderFileByScore orderFile = new ReadAndOrderFileByScore();
    private final ToRecordPlayer addPlayerLeaderboard = new ToRecordPlayer();
    private static final int MAXPLAYER = 10;
    private final Integer scoreTest = 10;

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
        orderFile.readFileAndOrder();
        this.scorePlayerLeaderboard.setText("Score:  " + this.scoreTest);
        if ((this.orderFile.getNumberPlayerInLeaderboard() < MAXPLAYER) || (this.scoreTest > this.orderFile.getLastScore())) {
            //aggiungo player. Chiamo classe toRecordPlayer
            this.toRecordBtn.setOnAction(event);
            //System.out.println("Number player in Leaderboard: " + orderFile.getNumberPlayerInLeaderboard());
            //System.out.println("The last score: " + orderFile.getLastScore());
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
            //System.out.print(getPlayerName());

        }
    };

    /**
     * Method to show the main menu.
     */
    @FXML
    public void showMainMenu() {
        /*try {
            this.mainMenuGame.setControllerGameOver();
        } catch (Exception e) { }*/
    }

    /**
     * Method to exit from game.
     */
    @FXML
    public void exitGO() {
        Platform.exit();
    }

    private String getPlayerName() {
        return this.playerName;
    }

}
