package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

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
    @FXML
    private Label titleGameOver;
    @FXML
    private VBox primaryPane;
    @FXML
    private Button menuButton;
    @FXML
    private Button quitButton;

    private String playerName;
    private final OrderReadFileByScore orderFile = new OrderReadFileByScore();
    private final RecordScore addPlayerLeaderboard = new RecordScore();
    private static final int MAXPLAYER = 10;
    private static Integer scoreTest;
    private static final double STAGE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double STAGE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double TOP_PANE = STAGE_HEIGHT / 8;
    private static final double TITLE_WIDTH = (STAGE_WIDTH * 3) / 5;
    private static final double TITLE_HEIGHT = (STAGE_HEIGHT * 3) / 16;
    private static final double TITLE_HEIGHT_MAX = STAGE_HEIGHT / 4;
    private static final double BUTTON_WIDTH = TITLE_WIDTH / 3;
    private static final double BUTTON_HEIGHT = TITLE_HEIGHT / 3;

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
        final BackgroundFill bg = new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY);
        final Background myBg = new Background(bg);
        this.primaryPane.setBackground(myBg);
        this.primaryPane.setMinSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.primaryPane.setMaxSize(STAGE_WIDTH, STAGE_WIDTH);
        this.titleGameOver.setGraphic(new ImageView(new Image("/gameOver.png", TITLE_WIDTH, TITLE_HEIGHT, false, false)));
        this.titleGameOver.setPadding(new Insets(TOP_PANE, 0, 0, 0));
        this.titleGameOver.setMinSize(TITLE_WIDTH, TITLE_HEIGHT);
        this.titleGameOver.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT_MAX);
        this.menuButton.setBackground(myBg);
        this.menuButton.setGraphic(new ImageView(new Image("/menuButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));
        this.quitButton.setBackground(myBg);
        this.quitButton.setGraphic(new ImageView(new Image("/quitButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));


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
     */
    @FXML
    protected void showMainMenu() {
        this.getView().setViewState(ViewState.MAIN_MENU, null);
    }

    /**
     * Method to exit from game.
     */
    @FXML
    protected void exitGO() {
        this.getController().closeApplication();
    }

    /**
     * Method to get the name of the player.
     * @return
     *         the name of the player.
     */
    public final String getPlayerName() {
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
