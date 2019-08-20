package view;

/**
 * The general controller of view.
 */
public class ViewControllerImpl implements ViewController  {

    private MainView view;
    private Controller controller;

    @Override
    public final void initializeViewController(final MainView view, final Controller controller) {
        this.view = view;
        this.controller = controller;
    }

    /**
     * Method to get the controller.
     * @return
     *         the controller.
     */
    @Override
    public Controller getController() {

        return this.controller;
    }

    /**
     * Method to get the view.
     * @return
     *          the view.
     */
    @Override
    public MainView getView() {

        return this.view;
    }

}
