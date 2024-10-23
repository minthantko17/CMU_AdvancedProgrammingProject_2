package se233.advprogrammingproject2.View;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.advprogrammingproject2.model.Keys;
import se233.advprogrammingproject2.model.PlayerShip;
import se233.advprogrammingproject2.Launcher;

public class GameStage extends Pane {
    private Image playerShipImg;
    private PlayerShip playerShip;
    private Keys keys;

    public GameStage(){
        setUpGame();
    }

    public void setUpGame(){
        keys=new Keys();
        playerShipImg=new Image(Launcher.class.getResourceAsStream("assets/playerShip1_red.png"));
        playerShip=new PlayerShip( this, playerShipImg, Launcher.WIDTH/2 -25, Launcher.HEIGHT/2 -20, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.A, 5);

        this.getChildren().addAll(playerShip.getImageView());
        this.setFocusTraversable(true); // Ensure the pane can capture key events
        this.requestFocus(); // Request focus when the stage is shown
    }

    public PlayerShip getPlayerShip(){
        return this.playerShip;
    }

    public Keys getKeys(){
        return keys;
    }
}
