package model.components;

/**
 * Models a timer for {@link Grill}.
 *
 */
public interface TimerGrill extends EntityComponent {

    /**
     * 
     * @return isDangerous
     *                   true if the grill can hurt the player
     */
    boolean getIsDangerous();

    /**
     * Change the grill state (dangerous/not dangerous).
     */
    void changeState();
}
