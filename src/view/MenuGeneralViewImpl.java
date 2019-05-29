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
    private MenuUpdate observer;
    //private final GeneralButton jbBack;

    /**
     * Constructors of MenuGeneralView.
     * @param stage
     *          The stage.
     */
    public MenuGeneralViewImpl(final Stage stage) {

        super();
        final VBox layout0 = new VBox(8);
        jbNewGame = new GeneralButton("New Game");
        jbNewGame.setOnMouseClicked(event -> {
            observer.newGame();
        });

        jbExit = new GeneralButton("Exit");
        jbExit.setOnMouseClicked(exit -> {
            observer.quit();
        });

        /*jbBack = new GeneralButton("Back");
         *jbBack.setOnMouseClicked(event ->{
              menuView.getChildren.add(layout0);
          }
        */
        layout0.getChildren().addAll(jbNewGame, jbExit);
    }

    @Override
    public final void setObserver(final MenuUpdate observer) {
        // TODO Auto-generated method stub
        this.observer = observer;
}
    @Override
    public final void quit() {
        // TODO Auto-generated method stub
        Platform.exit();

    }
    @Override
    public final Node getNode() {
        // TODO Auto-generated method stub
        return menuView;
    }

}
