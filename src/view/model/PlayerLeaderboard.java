package view.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Model class for a player in loaderboard.
 */
public class PlayerLeaderboard {

    private final StringProperty playerName;
    private final IntegerProperty playerScore;

    /**
     * Default constructor.
     */
    public PlayerLeaderboard() {

        this(null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param playerName
     *         the name of player.
     * @param playerScore
     *         the score of player.
     */
    public PlayerLeaderboard(final String playerName, final Integer playerScore) {

        this.playerName = new SimpleStringProperty(playerName);
        this.playerScore = new SimpleIntegerProperty(playerScore);
    }

    /**
     * method for get the name of player.
     * @return
     *         the name of score.
     */
    public StringProperty playerNameProperty() {

        return playerName;
    }

    /**
     * method for get the score of player.
     * @return
     *         the name of score.
     */
    public IntegerProperty playerScoreProperty() {

        return playerScore;
    }

}
