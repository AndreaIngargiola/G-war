package view;

/**
 * The general controller of view.
 */
public abstract class ViewControllerImpl implements ViewController  {

    private MainView view;

    @Override
    public final void initializeViewController(final MainView view) {
        this.view = view;
    }

    /**
     * Method to get the view.
     * @return
     *          the view.
     */
    protected MainView getView() {
        return this.view;
    }
}

