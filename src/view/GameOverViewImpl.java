package view;

import controller.menu.GameOverController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Game Ober View.
 */
public class GameOverViewImpl implements GameOverView {
    private final Group view = new Group();
    private GameOverController controller;
    private GeneralButton jbMenu;
    private GeneralButton jbExit;
    private static final int FONT = 70;

    /**
     * Constructor of GameOverView.
     * @param stage
     *         The related stage.
     * @param score
     *         The total score.
     */
    public GameOverViewImpl(final Stage stage, final int score) {
        final VBox layout = new VBox(8);
        //final StackPane pane = new StackPane();

        this.jbMenu = new GeneralButton("Menu");
        jbMenu.setOnMouseClicked(event -> {
            controller.toGeneralMenu();
        });

        this.jbExit = new GeneralButton("Exit");
        jbExit.setOnMouseClicked(event -> {
            controller.quit();
        });

        layout.getChildren().addAll(jbMenu, jbExit);

        final Text text = new Text("Final Score: " + score);
        text.setFont(Font.font(FONT));
        text.setFill(Color.BLACK);

        view.getChildren().addAll(text, layout);
    }

    @Override
    public final void setController(final GameOverController controller) {
        this.controller = controller;
    }

    @Override
    public final void quit() {
        Platform.exit();
    }

    @Override
    public final Node getNode() {
        return this.view;
    }

}
