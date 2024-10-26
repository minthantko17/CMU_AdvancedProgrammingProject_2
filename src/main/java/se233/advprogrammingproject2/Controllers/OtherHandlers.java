package se233.advprogrammingproject2.Controllers;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameEnd;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.View.MainMenu;


public class OtherHandlers {
    static Label gameEndLabel;
    static Label scoreLabel;

    public static void changeToMainMenu(){
        MainMenu mainMenu = new MainMenu();
        Scene mainMenuScene = new Scene(mainMenu, Launcher.WIDTH, Launcher.HEIGHT);
        Launcher.primaryStage.setScene(mainMenuScene);

        mainMenuScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                OtherHandlers.changeToGameStage();
            }
        });
    }

    public static void changeToGameStage(){
        GameStage gameStage = new GameStage();
        Scene gameScene=new Scene(gameStage, Launcher.WIDTH, Launcher.HEIGHT);
        GameController gameController=new GameController(gameScene,   gameStage);
        Launcher.primaryStage.setScene(gameScene);
    }

    public static void gameEndScreen(GameLoop gameLoop){
        gameLoop.stop();
        GameEnd gameEndPane = new GameEnd();
        Scene scene = new Scene(gameEndPane, Launcher.WIDTH, Launcher.HEIGHT);
        Launcher.primaryStage.setScene(scene);
    }
}
