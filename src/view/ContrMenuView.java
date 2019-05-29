package view;

/**
 * Interface for controlling a menu.
 * @param <T>
 *         Controller interface type.
 */
public interface ContrMenuView<T> extends ScreenFX {
    /**
     * @param observer
     *          The observer.
     */
    void setObserver(T observer);

    /**
     * Quits the application.
     */
    void quit();

}
