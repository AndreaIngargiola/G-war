package view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GameOverController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private Button toRecordBtn;
    @FXML
    private Label scorePlayerLeaderboard;

    private String playerName;
    private MainMenuGame mainMenuGame;
    private OrderFileByScore orderFile = new OrderFileByScore();
    private final static int MAXPLAYER = 10;
    
    private Integer scoreTest = 100;

    @FXML
    private void initialize() {
    	orderFile.readFile();
        this.toRecordBtn.setOnAction(event);
        this.scorePlayerLeaderboard.setText("Score:  " + String.valueOf(this.scoreTest));
        /*if((this.orderFile.getNumberPlayerInLeaderboard() < MAXPLAYER) || (this.scoreTest > this.orderFile.getLastScore())) {
        	//aggiungo player. Chiamo classe toRecordPlayer
        }else {
        	this.usernameTextField.setVisible(false);
        	this.toRecordBtn.setVisible(false);
        }*/
        //this.orderFile.readFile();
        
        System.out.println(orderFile.getNumberPlayerInLeaderboard());

    }
	/*@FXML
	private void recordInLeaderboard() {
		toRecordBtn.pressedProperty().addListener((o, old, newValue) -> {
			String name = username.getText();
	        System.out.print(name);
		});

	}*/
	
	private EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		public void handle(final ActionEvent e) {
			playerName = usernameTextField.getText();
			System.out.print(getPlayerName());
		}
	};
	
	@FXML
    private void showMainMenu() {
        try {
            this.mainMenuGame.getMainMenu();
        } catch (Exception e) { }
    }
	
	@FXML
	private void exitGO() {
        Platform.exit();
	}
	
	private String getPlayerName() {
		return this.playerName;
	}

	/**
     * Sets main menu.
     * @param mainMenu
     *         The main menu.
     */
    /*public void setMainMenuGame(final MainMenuGame mainMenu) {
        this.mainMenuGame = mainMenu;
    }*/

}
