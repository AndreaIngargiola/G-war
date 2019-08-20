package menu;

import javafx.application.Platform;

/**
 * Implementation the controller for test.
 */
public class ControllerImpl implements Controller {

    private final MainView view;

    /**
     * Constructor.
     * @param view
     *         the view reference.
     */
    public ControllerImpl(final MainView view) {
        this.view = view;
    }

    /**
     * New Game.
     */
    @Override
    public void newGame() {
        System.out.print("New Game");
    }

    /**
     * Close application.
     */
    @Override
    public void closeApplication() {
        Platform.exit();
    }

}
