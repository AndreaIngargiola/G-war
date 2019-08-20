package view;

/**
 * Interface for controller all views.
 */
public interface ViewController {

    /**
     * Initialize the view controller.
     * 
     * @param view
     *          the view
     * @param controller
     *          the controller.
     */
    void initializeViewController(MainView view, Controller controller);

    /**
     * Method to get the controller.
     * @return
     *         the controller.
     */
    Controller getController();

    /**
     * Method to get the view.
     * @return
     *         the view.
     */
    MainView getView();

}
