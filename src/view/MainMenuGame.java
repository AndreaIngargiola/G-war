package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Main Menu.
 */
public class MainMenuGame extends Application {

    private Stage primaryStage;
    private ReadAndOrderFileByScore orderFileByScore = new ReadAndOrderFileByScore();

    @Override
    public final void start(final Stage stage) throws Exception {
        this.primaryStage = stage;
        primaryStage.setTitle("Geometric Warfare");
        this.getMainMenu();
    }
    /**
     * Method for get the Main Menu.
     * @throws IOException when file not found.
     */
    public void getMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("view/mainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        MainMenuGameController controllerMain = loader.getController();
        controllerMain.setMainMenuGame(this);
        /*GameOverController controllerGameOver = loader.getController();
        controllerGameOver.setMainMenuGame(this);*/
    }

    /**
     * Method for get the Leaderboard.
     * @throws IOException when file not found.
     */
    public void showLeaderboard() throws IOException {
        orderFileByScore.readFileAndOrder();
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("view/leaderboard.fxml"));
        Parent newRoot = loader.load();

        primaryStage.setScene(new Scene(newRoot));
        primaryStage.show();

        LeaderboardController controllerLeaderboard = loader.getController();
        controllerLeaderboard.setMainMenuGame(this);
    }

    /*public void setControllerGameOver() throws IOException {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("view/mainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        GameOverController controllerGameOver = loader.getController();
        controllerGameOver.setMainMenuGame(this);
    }*/

    /**
     * Method for returns the main stage.
     * @return the main stage.
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    /**
     * Main.
     * @param args
     *         The args of main.
     */
    public static void main(final String[] args) {
        launch(args);

    }

}
