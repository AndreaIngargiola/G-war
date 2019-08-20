package menu;

/**
 * The scene of the menu.
 *
 */
public enum SceneNode {

    /**
     * Main menu.
     */
    MAIN_MENU("mainMenu.fxml"),

    /**
     * Leaderboard.
     */
    LEADERBOARD("leaderboard.fxml"),

    /**
     * Game over.
     */
    GAME_OVER("gameOver.fxml");

    private static final String PATH = "menu/";
    private final String nameFxml;

    /**
     * Constructor.
     * @param nameFxml
     *        the name of fxml file.
     */
    SceneNode(final String nameFxml) {
        this.nameFxml = nameFxml;
    }

    /**
     * Method to get path of file.
     * @return
     *         the path of fxml file.
     */
    public String getPath() {
        return PATH + this.nameFxml;
    }
}
