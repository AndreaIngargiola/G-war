package view;

import controller.menu.MenuUpdate;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The menu view.
 */
public class MenuGeneralViewImpl extends Parent implements MenuGeneralView {
    private final Group menuView = new Group();
    private final GeneralButton jbNewGame;
    private final GeneralButton jbExit;
    private MenuUpdate controller;

    /**
     * Constructors of MenuGeneralView.
     * @param stage
     *          The stage.
     */
    public MenuGeneralViewImpl(final Stage stage) {

        super();
        final VBox layout = new VBox(8);
        jbNewGame = new GeneralButton("New Game");
        jbNewGame.setOnMouseClicked(event -> {
            controller.newGame();
        });

        jbExit = new GeneralButton("Exit");
        jbExit.setOnMouseClicked(exit -> {
            controller.quit();
        });

        layout.getChildren().addAll(jbNewGame, jbExit);
    }

    @Override
    public final void setController(final MenuUpdate controller) {
        this.controller = controller;
}
    @Override
    public final void quit() {
        Platform.exit();

    }
    @Override
    public final Node getNode() {
        return menuView;
    }

    @Override
    public final void updateView() {
        jbNewGame.updateBtn("New Game");
        jbExit.updateBtn("Exit");
    }

}
