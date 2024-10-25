package se233.advprogrammingproject2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.View.MainMenu;

public class Launcher extends Application {
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;
    public static Stage primaryStage;
    public static boolean isGameOver = false;
    public static boolean isVictory = false;

    public void start(Stage stage) {
        primaryStage=stage;

        MainMenu mainMenu = new MainMenu();
        Scene mainMenuScene = new Scene(mainMenu, WIDTH, HEIGHT);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        mainMenuScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                OtherHandlers.changeToGamestage();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
