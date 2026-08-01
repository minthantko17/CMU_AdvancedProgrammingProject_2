package se233.advprogrammingproject2.View;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import se233.advprogrammingproject2.util.AnimatedSprite;
import se233.advprogrammingproject2.util.Keys;
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
    Label lifeLabel;
    public static Label bossHpLabel;
    public static Rectangle bossHpBar;
    public static Rectangle bossHpBarBorder;
    HBox playerLifeHBox;
    Image playerShipImgMini;
    Image shipImage;

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

        Image cursorImage=new Image(Launcher.class.getResourceAsStream("assets/redTarget.png"), 48, 48, true, true);
        Cursor cursor=new ImageCursor(cursorImage, cursorImage.getWidth()/2, cursorImage.getHeight()/2);
        this.setCursor(cursor);

        score=0;
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        scoreLabel.setLayoutX(10);
        scoreLabel.setLayoutY(10);

        specialEnergy=0;
        specialEnergyLabel = new Label("Special Energy: " + specialEnergy+"/30");
        specialEnergyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        specialEnergyLabel.setLayoutX(100);
        specialEnergyLabel.setLayoutY(10);

        bossHpLabel=new Label("Boss HP: ");
        bossHpLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        bossHpLabel.setLayoutX(300);
        bossHpLabel.setLayoutY(10);
        bossHpLabel.setVisible(false);

        bossHpBarBorder=new Rectangle(399,9,202,22);
        bossHpBarBorder.setFill(Color.TRANSPARENT);
        bossHpBarBorder.setStroke(Color.LIGHTGRAY);
        bossHpBarBorder.setStrokeWidth(2);
        bossHpBarBorder.setVisible(false);

        bossHpBar=new Rectangle();
        bossHpBar.setX(400);
        bossHpBar.setY(10);
        bossHpBar.setWidth(200);
        bossHpBar.setHeight(20);
        bossHpBar.setFill(Color.RED);
        bossHpBar.setVisible(false);

        keys=new Keys();
//        playerShipImg=new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
//        playerShip=new PlayerShip( this, playerShipImg, Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);

        if(Launcher.choosenShip==2){
            AnimatedSprite animatedSprite=new AnimatedSprite(
                    new Image(Launcher.class.getResourceAsStream("assets/playerShip2.png")),
                    100, 100, 6, 150
            );
            playerShip=new PlayerShip(this, animatedSprite,
                    Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);
        }else{  //need to update with new ship2 image
            AnimatedSprite animatedSprite=new AnimatedSprite(
                    new Image(Launcher.class.getResourceAsStream("assets/player.png")),
                    100, 100, 6, 150
            );
            playerShip=new PlayerShip(this, animatedSprite,
                    Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);
        }



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

        lifeLabel=new Label("Life :");
        lifeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15");
        lifeLabel.setLayoutX(850);
        lifeLabel.setLayoutY(15);


        this.getChildren().addAll(scoreLabel, specialEnergyLabel, bossHpLabel, bossHpBarBorder, bossHpBar, lifeLabel, playerLifeHBox, playerShip.getImageView());
        this.setFocusTraversable(true); // Ensure the pane can capture key events
        this.requestFocus(); // Request focus when the stage is shown
    }

    public PlayerShip getPlayerShip(){
        return this.playerShip;
    }

    public Keys getKeys(){
        return keys;
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
