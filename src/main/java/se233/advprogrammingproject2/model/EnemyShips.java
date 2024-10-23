package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.View.GameStage;

public class EnemyShips extends Characters{
    protected int size;

    public EnemyShips(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
        super(new ImageView(image), startX, startY, speed, playerShip);
        this.imageView.setX(startX);
        this.imageView.setY(startY);
        this.imageView.setFitWidth(size);
        this.imageView.setPreserveRatio(true);
    }
}
