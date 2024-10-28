package se233.advprogrammingproject2;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.View.MainMenu;

public class Launcher extends Application {
    public static final double WIDTH = 1000;
    public static final double HEIGHT = 800;
    public static Stage primaryStage;
    public static boolean isGameOver = false;
    public static boolean isVictory = false;
    public static int choosenShip=1;

    public void start(Stage stage) {
        primaryStage=stage;
        Image cursorImage=new Image(Launcher.class.getResourceAsStream("assets/redTarget.png"));
        Cursor cursor=new ImageCursor(cursorImage, cursorImage.getWidth()/2, cursorImage.getHeight()/2);

        MainMenu mainMenu = new MainMenu();
        mainMenu.setCursor(cursor);
        Scene mainMenuScene = new Scene(mainMenu, WIDTH, HEIGHT);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        mainMenuScene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                OtherHandlers.changeToGamePrepScreen();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
