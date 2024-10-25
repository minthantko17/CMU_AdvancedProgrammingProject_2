package se233.advprogrammingproject2.Controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;


public class OtherHandlers {
    static Label gameEndLabel;
    static Label scoreLabel;

    public static void changeToGamestage(){
        GameStage gameStage = new GameStage();
        Scene gameScene=new Scene(gameStage, Launcher.WIDTH, Launcher.HEIGHT);
        GameController gameController=new GameController(gameScene,   gameStage);
        Launcher.primaryStage.setScene(gameScene);
    }

    public static void gameEndScreen(GameLoop gameLoop){
        gameLoop.stop();


        gameEndLabel=new Label();
        scoreLabel=new Label();

        if(Launcher.isGameOver){
            gameEndLabel.setText("Game Over");
            gameEndLabel.setLayoutX(320);
        }else if(Launcher.isVictory){
            gameEndLabel.setText("Victory");
            gameEndLabel.setLayoutX(370);
        }
        gameEndLabel.setStyle("-fx-text-fill: white;");
        gameEndLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 80));
        gameEndLabel.setAlignment(Pos.CENTER);
        gameEndLabel.setLayoutY(250);


        scoreLabel.setText("Your Score: "+ GameStage.score);
        scoreLabel.setStyle("-fx-text-fill: white;");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 30));
        scoreLabel.setAlignment(Pos.CENTER);
        scoreLabel.setLayoutX(400);
        scoreLabel.setLayoutY(360);

        Button playAgainButton=new Button("Play Again");
        Button exitButton=new Button("Exit");


        Launcher.isGameOver=false;
        Launcher.isVictory=false;

        Pane gameEndPane = new Pane();
        Image backgroundImg = new Image(Launcher.class.getResourceAsStream("assets/backgroundImg.png"));
        BackgroundImage background=new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT );
        gameEndPane.setBackground(new Background(background));

        gameEndPane.getChildren().addAll(gameEndLabel,scoreLabel,playAgainButton,exitButton);

        Scene scene = new Scene(gameEndPane, Launcher.WIDTH, Launcher.HEIGHT);
        Launcher.primaryStage.setScene(scene);
    }
}
