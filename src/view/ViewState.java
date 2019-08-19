package view;

/**
 * The state of the application.
 */
public enum ViewState {

    /**
     * Menu state.
     */
    MAIN_MENU(SceneNode.MAIN_MENU),

    /**
     * Leaderboard state.
     */
    LEADERBOARD(SceneNode.LEADERBOARD),

    /**
     * Game over state.
     */
    GAME_OVER(SceneNode.GAME_OVER);

    private final SceneNode nodeView;

    ViewState(final SceneNode nodeView) {
        this.nodeView = nodeView;
    }

    /**
     * Method to get the view node of this view state.
     * @return
     *         the view node.
     */
    public SceneNode getSceneNode() {
        return this.nodeView;
    }
}
