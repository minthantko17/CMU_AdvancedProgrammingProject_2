package se233.advprogrammingproject2.View;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import se233.advprogrammingproject2.model.Keys;
import se233.advprogrammingproject2.model.PlayerShip;
import se233.advprogrammingproject2.Launcher;

public class GameStage extends Pane {
    private Image playerShipImg;
    private PlayerShip playerShip;
    private Keys keys;
    public static int score;
    Label scoreLabel;
    public int playerLife;
    Label playerLifeLabel;

    public GameStage(){
        setUpGame();
    }

    public void setUpGame(){
        Image backgroundImg = new Image(Launcher.class.getResourceAsStream("assets/backgroundImg.png"));
        BackgroundImage background=new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT );
        this.setBackground(new Background(background));

        score=0;
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-text-fill: white;");

        keys=new Keys();
        playerShipImg=new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip=new PlayerShip( this, playerShipImg, Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);

        playerLife=playerShip.getLife();
        playerLifeLabel = new Label("Life: " + playerShip.getLife());
        playerLifeLabel.setStyle("-fx-text-fill: white;");
        playerLifeLabel.setLayoutY(10);

        this.getChildren().addAll(scoreLabel, playerLifeLabel, playerShip.getImageView());
        this.setFocusTraversable(true); // Ensure the pane can capture key events
        this.requestFocus(); // Request focus when the stage is shown
    }

    public PlayerShip getPlayerShip(){
        return this.playerShip;
    }

    public Keys getKeys(){
        return keys;
    }

    public int getScore(){
        return score;
    }
    public void setScore(int score){
        GameStage.score =score;
    }

    public void updateScoreLabel(int score){
        scoreLabel.setText("Score: " + score);
    }
    public void updatePlayerLifeLabel(int playerLife){
        this.playerLifeLabel.setText("Life: " + playerLife);
    }
}
