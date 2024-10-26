package se233.advprogrammingproject2.View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public static int specialEnergy;
    Label scoreLabel;
    Label specialEnergyLabel;
    public static Label bossHpLabel;
    HBox playerLifeHBox;
    Image playerShipImgMini;

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

        specialEnergy=0;
        specialEnergyLabel = new Label("Special Energy: " + specialEnergy+"/30");
        specialEnergyLabel.setStyle("-fx-text-fill: white;");
        specialEnergyLabel.setLayoutX(70);

        bossHpLabel=new Label("Boss HP: ");
        bossHpLabel.setStyle("-fx-text-fill: white;");
        bossHpLabel.setLayoutX(250);
        bossHpLabel.setVisible(false);

        keys=new Keys();
        playerShipImg=new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip=new PlayerShip( this, playerShipImg, Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);

        playerLifeHBox = new HBox();
        playerShipImgMini=new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        for(int i=0; i<playerShip.getLife(); i++){
            ImageView playerShipMiniView = new ImageView(playerShipImgMini);
            playerShipMiniView.setFitWidth(25);
            playerShipMiniView.setPreserveRatio(true);
            playerLifeHBox.getChildren().add(playerShipMiniView);
        }
        playerLifeHBox.setLayoutX(880);
        playerLifeHBox.setLayoutY(5);
        playerLifeHBox.setPadding(new Insets(10,10,10,10));
        playerLifeHBox.setSpacing(10);

        this.getChildren().addAll(scoreLabel, specialEnergyLabel, bossHpLabel, playerLifeHBox, playerShip.getImageView());
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

    //Update Display Status Labels
    public void updateScoreLabel(int score){
        scoreLabel.setText("Score: " + score);
    }
    public void updateSpecialEnergyLabel(int specialEnergy){
        specialEnergyLabel.setText("Special Energy: " + specialEnergy+"/30");
    }
    public void updatePlayerLifeIcon(){
        this.playerLifeHBox.getChildren().removeLast();
    }
}
