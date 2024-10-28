package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.View.GameStage;

public class EnemyShips extends Characters{
    protected int size;
    protected long enemyLastShotTime;

    public EnemyShips(){

    }

    public EnemyShips(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
        super(new ImageView(image), startX, startY, speed, playerShip);
        this.imageView.setX(startX);
        this.imageView.setY(startY);
        this.imageView.setFitWidth(size);
        this.imageView.setPreserveRatio(true);
        enemyLastShotTime=0;
    }

    public boolean canShoot(long currentTime, long interval) {
        if(currentTime-enemyLastShotTime>=interval){
            enemyLastShotTime=currentTime;
            return true;
        }
        return false;
    }
}
