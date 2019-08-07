package view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginApp extends Application {

	private Stage loginStage;
	private OrderFileByScore orderFile = new OrderFileByScore();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    this.loginStage = primaryStage;
        primaryStage.setTitle("Login app");
        //this.orderFile.readFile();

        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("view/gameOver.fxml"));
        Parent root = loader.load();
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
