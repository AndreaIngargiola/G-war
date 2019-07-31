package model.components;

/**
 * Models a timer for {@link Grill}.
 *
 */
public interface Timer extends EntityComponent {

    /**
     * 
     * @return isDangerous
     *                   true if the grill can hurt the player
     */
    boolean getIsDangerous();

}
