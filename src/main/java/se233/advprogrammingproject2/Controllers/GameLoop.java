package se233.advprogrammingproject2.Controllers;

import javafx.animation.AnimationTimer;
import se233.advprogrammingproject2.model.Bullet;
import se233.advprogrammingproject2.model.PlayerShip;
import se233.advprogrammingproject2.View.GameStage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameLoop extends AnimationTimer {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private List<Bullet> bullets;

    public GameLoop(GameStage gameStage, PlayerShip playerShip) {
        this.gameStage = gameStage;
        this.playerShip = playerShip;
        bullets = new ArrayList<>();
    }

    @Override
    public void handle(long l) {
        //update bullet
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();
            if(bullet.isOffScreen()){
                gameStage.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
            }
        }

        //update enemy, asteroids, ... grrrrrrrrrrr

        //update ship movements
        playerShip.update();
//        playerShip.moveHorizontal();
//        playerShip.moveVertical();
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        System.out.println("bullet added"+ bullets.size());
        gameStage.getChildren().addAll(bullet.getImageView());
        System.out.println("bullet added to GS"+ gameStage.getChildren().size());
    }
}
