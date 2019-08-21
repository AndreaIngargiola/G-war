package controller;


import javafx.application.Application;
import javafx.stage.Stage;
import view.MainViewImpl;

/**
 * The game launcher.
 */
public final class Launcher extends Application {

    /**
     * 
     * @param args
     *            args passed to the main method
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    public void start(final Stage stage) {
       new MainViewImpl(stage);
    } 

}
