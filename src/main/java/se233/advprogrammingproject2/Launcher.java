package se233.advprogrammingproject2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.View.GameStage;

public class Launcher extends Application {
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;

    public void start(Stage primaryStage) {
        GameStage gameStage = new GameStage();
        Scene scene=new Scene(gameStage, WIDTH, HEIGHT);
        GameController gameController=new GameController(scene,   gameStage);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
