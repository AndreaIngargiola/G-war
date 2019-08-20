package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * Controller view of main menu.
 */
public class MainMenuGameController extends ViewControllerImpl {

    @FXML
    private VBox primaryPane;
    @FXML
    private Label titleMainMenu;
    @FXML
    private Button newGameButton;
    @FXML
    private Button leaderboardButton;
    @FXML
    private Button quitButton;
    @FXML
    private VBox secondPane;

    private static final double STAGE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    private static final double STAGE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
    private static final double TOP_PANE = STAGE_HEIGHT / 8;
    private static final double TITLE_WIDTH = (STAGE_WIDTH * 3) / 5;
    private static final double TITLE_HEIGHT = (STAGE_HEIGHT * 3) / 8;
    private static final double TOP_SECOND_PANE = (STAGE_HEIGHT * 7) / 16;
    private static final double BOTTOM_SECOND_PANE = STAGE_HEIGHT / 16;
    private static final double BUTTON_WIDTH = TITLE_WIDTH / 3;
    private static final double BUTTON_HEIGHT = TITLE_HEIGHT / 6;

    /**
     * Method to initialize any controls.
     */
    @FXML
    public void initialize() {
        final BackgroundFill bg = new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY);
        final Background myBg = new Background(bg);
        this.primaryPane.setBackground(myBg);
        this.primaryPane.setMinSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.primaryPane.setMaxSize(STAGE_WIDTH, STAGE_HEIGHT);
        this.secondPane.setMinSize(TITLE_WIDTH, TITLE_HEIGHT);
        this.secondPane.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT);
        this.secondPane.setPadding(new Insets(TOP_SECOND_PANE, 0, 0, BOTTOM_SECOND_PANE));
        this.titleMainMenu.setGraphic(new ImageView(new Image("/title.png", TITLE_WIDTH, TITLE_HEIGHT, false, false)));
        this.titleMainMenu.setPadding(new Insets(TOP_PANE, 0, 0, 0));
        this.titleMainMenu.setMinSize(TITLE_WIDTH, TITLE_HEIGHT);
        this.titleMainMenu.setMaxSize(TITLE_WIDTH, TITLE_HEIGHT);
        this.newGameButton.setBackground(myBg);
        this.newGameButton.setGraphic(new ImageView(new Image("/newgameButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));
        this.leaderboardButton.setBackground(myBg);
        this.leaderboardButton.setGraphic(new ImageView(new Image("/leaderboardButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));
        this.quitButton.setBackground(myBg);
        this.quitButton.setGraphic(new ImageView(new Image("/quitButton.png", BUTTON_WIDTH, BUTTON_HEIGHT, false, false)));
    }

    /**
     * Method for new game.
     * @throws IOException
     *         the exception.
     */
    @FXML
    protected void newGame() throws IOException {
        this.getController().newGame();
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
     *         the exception.
     */
    @FXML
    protected void leaderboard() throws IOException {
        this.getView().setViewState(ViewState.LEADERBOARD, null);
    }
}
