package se233.advprogrammingproject2.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.util.AnimatedSprite;

public abstract class Characters extends ImageView {
    protected double startX;
    protected double startY;
    protected int hp=1;
    protected int directionAngle;
    protected ImageView imageView;
    protected GameStage gameStage;
    protected double speed;
    protected PlayerShip playerShip;
    protected boolean isExploding;
    double initSpeed;

    protected Characters(){
        this.isExploding=false;
    }

    protected Characters(ImageView imageView, double speed, int directionAngle){
        this.imageView = imageView;
        this.speed = speed;
        this.directionAngle = directionAngle;
    }

    protected Characters(ImageView imageView, double startX, double startY, double speed, PlayerShip playerShip){
        this.imageView = imageView;
        this.startX=startX;
        this.startY=startY;
        this.speed = speed;
        this.playerShip=playerShip;
        this.isExploding=false;
    }

    protected Characters(ImageView imageView, double startX, double startY, double speed, int directionAngle){
        this.imageView = imageView;
        this.startX=startX;
        this.startY=startY;
        this.speed = speed;
        this.directionAngle = directionAngle;
    }

    //move position
    public void update(){
        double deltaX= speed*Math.cos(Math.toRadians(directionAngle));
        double deltaY= speed*Math.sin(Math.toRadians(directionAngle));
        imageView.setX(imageView.getX()+deltaX);
        imageView.setY(imageView.getY()+deltaY);
    }

    public boolean checkCollision(Characters other){
        return this.imageView.getBoundsInParent().intersects(other.getImageView().getBoundsInParent());
    }

    public void collide(Characters obj){
        if(this instanceof Asteroids && obj instanceof Bullet){
//            System.out.println("Collided");

            this.explode();
            this.remove();
        }
    }

    public void explode(){
        //explode
//        System.out.println("Exploded");
//        PlayerShip.logger.warn("PlayerShip exploded!!!");
        //remove imageview from gameStage

    }

    public void explode(AnimatedSprite animatedSprite, GameStage gameStage){
        this.gameStage=gameStage;
        if(isExploding){ return; }
        isExploding=true;

        AnimatedSprite explosionAnimation=animatedSprite;
        explosionAnimation.setX(imageView.getX());
        explosionAnimation.setY(imageView.getY());
        explosionAnimation.setPreserveRatio(true);
        explosionAnimation.setFitWidth(imageView.getFitWidth());
        gameStage.getChildren().add(explosionAnimation);

        Timeline explosionTime=new Timeline(new KeyFrame(Duration.millis(100), e -> explosionAnimation.update(System.nanoTime())));
        explosionTime.setCycleCount(4);
        explosionTime.setOnFinished(e->{
            gameStage.getChildren().remove(explosionAnimation);
            isExploding=false;
//            this.remove();
        });
        explosionTime.play();

    }

    public void remove(){
//        System.out.println("Removed");
    }

    public ImageView getImageView(){
        return imageView;
    }

    public void setSpeed(double speed){
        this.speed=speed;
    }
    public void setBackInitSpeed(){
        this.speed=initSpeed;
    }

    public boolean isExploding(){
        return isExploding;
    }
    public void setExploding(boolean isExploding){
        this.isExploding=isExploding;
    }
}
