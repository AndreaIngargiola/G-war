package model.math;

/**
 * An interface for the probability urn/ball system. A ball is not a class, but is a concept used for clarity.
 */
public interface BallsUrn {

    /**
     * Get a ball from the urn.
     * @return the color of the ball.
     */
    Color getBall();

    /**
     * Refill the urn with some new balls.
     * @param ballsNumber is the number of balls to insert in the urn.
     */
    void refill(int ballsNumber);

    /**
     * Insert a single ball in the urn.
     * @param ballColor is the color of the ball to insert.
     */
    void insertSingleBall(Color ballColor);

    /**
     * Represent the possible colors of a ball: this is a inner class because it's strictly meaningful for the whole contract to work.
     */
    enum Color {
        BLACK,
        WHITE;
    }
}
