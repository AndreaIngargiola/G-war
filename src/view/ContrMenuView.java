package view;

/**
 * Interface for controlling a menu.
 * @param <T>
 *         Controller interface type.
 */
public interface ContrMenuView<T> extends ScreenFX {
    /**
     * @param controller
     *          The controller.
     */
    void setController(T controller);

    /**
     * Quits the application.
     */
    void quit();

}
