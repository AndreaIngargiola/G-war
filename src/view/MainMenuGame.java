package view;

import java.io.IOException;

import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
//import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.scene.transform.Scale;

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
        //Pane root = (Pane) loader.load();
        //Scene scene = new Scene(new Group(root));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMinY());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        primaryStage.setScene(new Scene(root));
        //primaryStage.setScene(scene);
        primaryStage.show();
        //letterbox(scene, root);
        //primaryStage.setFullScreen(true);
        //primaryStage.setResizable(true);
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
        //Pane root = (Pane) loader.load();
        //Scene scene = new Scene(new Group(root));
        primaryStage.setScene(new Scene(newRoot));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMinY());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());
        //primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
        //letterbox(scene, root);
        //primaryStage.setFullScreen(true);
        //primaryStage.setFullScreen(true);
        //primaryStage.setResizable(true);
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

    /*private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio      = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
      }

      private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
          this.scene = scene;
          this.ratio = ratio;
          this.initHeight = initHeight;
          this.initWidth = initWidth;
          this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          final double newWidth  = scene.getWidth();
          final double newHeight = scene.getHeight();

          double scaleFactor =
              newWidth / newHeight > ratio
                  ? newHeight / initHeight
                  : newWidth / initWidth;

          if (scaleFactor >= 1) {
            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);

            contentPane.setPrefWidth (newWidth  / scaleFactor);
            contentPane.setPrefHeight(newHeight / scaleFactor);
          } else {
            contentPane.setPrefWidth (Math.max(initWidth,  newWidth));
            contentPane.setPrefHeight(Math.max(initHeight, newHeight));
          }
        }
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
