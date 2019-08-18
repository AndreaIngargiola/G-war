package view;

/**
 * Interface of Main View.
 */
public interface MainView {

    /**
     * Set state of application.
     * @param state
     *         the state
     * @param score
     *         the hypothetical score of player.
     */
    void setViewState(ViewState state, Integer score);

}