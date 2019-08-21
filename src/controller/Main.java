package controller;


import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;
import view.MainViewImpl;

/**
 * The game launcher.
 */
public final class Main extends Application {

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
        MainView mainView = new MainViewImpl(stage);

        //mainView.setGameController(new GameControllerImpl(stage, mainView));
    } 

}
