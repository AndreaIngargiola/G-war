package view;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * Class to open Game over view.
 */
public class LoginApp extends Application {

    @Override
    public final void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Login app");
        //this.orderFile.readFile();

        final FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("view/gameOver.fxml"));
        final Parent root = loader.load();
        final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMinY());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //System.out.print(orderFile.getNumberPlayerInLeaderboard());
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
