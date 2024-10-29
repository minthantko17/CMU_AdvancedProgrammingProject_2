package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;

import java.util.ArrayList;
import java.util.List;

public class Asteroids extends Characters{
    private double rotationSpeed;
    private int size;
    double initRotationSpeed;

    public Asteroids(GameStage gameStage,Image image, double startX, double startY, double speed, int directionAngle, double rotationSpeed, int size) {
        super(new ImageView(image), startX, startY, speed, directionAngle);
        this.gameStage=gameStage;
        this.size=size;
        this.imageView.setX(startX);
        this.imageView.setY(startY);
        this.imageView.setFitWidth(size);
        this.imageView.setPreserveRatio(true);
        this.rotationSpeed = rotationSpeed;
        this.initSpeed = speed;
        initRotationSpeed=rotationSpeed;
    }

    @Override
    public void update() {
        updateWhenOffScreen();
        super.update();
        this.imageView.setRotate(imageView.getRotate()+this.rotationSpeed);
    }

    public boolean isOffScreen(){
        return (imageView.getX()<0 || imageView.getX()> Launcher.WIDTH || imageView.getY()<0 || imageView.getY()> Launcher.HEIGHT);
    }

    public void updateWhenOffScreen(){
        if(imageView.getX()<0){
            imageView.setX(Launcher.WIDTH);
        }
        if(imageView.getX()>Launcher.WIDTH){
            imageView.setX(0);
        }
        if(imageView.getY()<0){
            imageView.setY(Launcher.HEIGHT);
        }
        if(imageView.getY()>Launcher.HEIGHT){
            imageView.setY(0);
        }
    }

    public List<Asteroids> spawnSmaller(double startX, double startY, int size){
        List<Asteroids> smallerAsteroids=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asteroids smallerAsteroid = new Asteroids(gameStage,
                    new Image(Launcher.class.getResourceAsStream("assets/asteroid2.png")),
                    startX,
                    startY,
                    2,
                    (int) (Math.random() * 361),
                    1 + Math.random() * 2,
                    size
            );
            smallerAsteroids.add(smallerAsteroid);
            gameStage.getChildren().add(smallerAsteroid.getImageView());
        }
        return smallerAsteroids;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public int getSize(){
        return size;
    }

    public void setSpeed(double speed){
        this.speed=speed;
    }
    public void setBackInitSpeed(){
        this.speed=initSpeed;
    }
    public void setRotationSpeed(double rotationSpeed){
        this.rotationSpeed=rotationSpeed;
    }
    public void setBackToInitRotationSpeed(){
        this.rotationSpeed=initRotationSpeed;
    }


}
