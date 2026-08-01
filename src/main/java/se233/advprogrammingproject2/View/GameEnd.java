package se233.advprogrammingproject2.View;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import se233.advprogrammingproject2.Controllers.OtherHandlers;
import se233.advprogrammingproject2.Launcher;

public class GameEnd extends Pane {
    private Label gameEndLabel;
    private Label scoreLabel;
    public GameEnd() {

        Image cursorImage=new Image(Launcher.class.getResourceAsStream("assets/redTarget.png"), 48, 48, true, true);
        Cursor cursor=new ImageCursor(cursorImage, cursorImage.getWidth()/2, cursorImage.getHeight()/2);
        this.setCursor(cursor);

        gameEndLabel = new Label();
        scoreLabel = new Label();

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

        Button playAgainBtn=new Button("Play Again");
        playAgainBtn.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: Transparent;");
        playAgainBtn.setLayoutX(550);
        playAgainBtn.setLayoutY(450);
        playAgainBtn.setOnMouseClicked(e->{
            OtherHandlers.changeToGameStage();});

        Button mainMenuBtn=new Button("Main Menu");
        mainMenuBtn.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold; -fx-background-color: Transparent;");
        mainMenuBtn.setLayoutX(350);
        mainMenuBtn.setLayoutY(450);
        mainMenuBtn.setOnMouseClicked(e->{OtherHandlers.changeToMainMenu();});

        Launcher.isGameOver=false;
        Launcher.isVictory=false;

        Image backgroundImg = new Image(Launcher.class.getResourceAsStream("assets/backgroundImg.png"));
        BackgroundImage background=new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT );
        this.setBackground(new Background(background));

        this.getChildren().addAll(gameEndLabel,scoreLabel,playAgainBtn,mainMenuBtn);
    }
}
