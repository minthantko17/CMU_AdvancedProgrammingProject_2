package se233.advprogrammingproject2.model;

import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;

abstract class Characters extends ImageView {
    protected double startX;
    protected double startY;
    protected int hp=1;
    protected int directionAngle;
    protected ImageView imageView;
    protected GameStage gameStage;
    protected double speed;
    protected PlayerShip playerShip;

    protected Characters(){
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

    //check collision
    public boolean checkCollision(Characters other){
        return this.imageView.getBoundsInParent().intersects(other.getImageView().getBoundsInParent());
    }

    //collide
    public void collide(Characters obj){
        if(this instanceof Asteroids && obj instanceof Bullet){
            System.out.println("Collided");
            this.explode();
            this.remove();
        }
    }

    //explode method
    public void explode(){
        //explode
        System.out.println("Exploded");
        //remove imageview from gameStage
    }

    public void remove(){
        System.out.println("Removed");
    }


    public ImageView getImageView(){
        return imageView;
    }

}
