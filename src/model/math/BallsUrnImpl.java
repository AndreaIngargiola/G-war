package model.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the BallsUrn interface optimized for two colors and equal probability between them.
 */
public final class BallsUrnImpl implements BallsUrn {

    private List<Color> balls;

    /**
     * For grant the equal probability of the two colors at the first extraction, an odd number will be rounded at the first grater even number.
     * @param initialBallsNumber is the total number of balls in the urn, an odd number will be rounded at the first grater even number.
     */
    public BallsUrnImpl(final int initialBallsNumber) {
        this.balls = new ArrayList<>();
        this.refill(initialBallsNumber);
    }

    @Override
    public Color getBall() {
        final Random randomIterator = new Random(); 
        final int randomIndex = randomIterator.nextInt(this.balls.size());
        final Color extractedBall = this.balls.get(randomIndex);
        this.balls.remove(randomIndex);
        return extractedBall;
    }

    @Override
    public void refill(final int ballsNumber) {
        int realBallsNumber = (ballsNumber % 2 == 1) ? ballsNumber + 1 : ballsNumber;
        for (int i = 0; i < (realBallsNumber / 2); i++) {
            this.insertSingleBall(Color.BLACK);
            this.insertSingleBall(Color.WHITE);
        }
    }

    @Override
    public void insertSingleBall(final Color ballColor) {
        balls.add(ballColor);
    }

}
