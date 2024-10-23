package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.Launcher;

public class Bullet extends Characters{
    private PlayerShip playerShip;
    private final ImageView imageView;
    private final double directionAngle;
    private double speed=5;

    public Bullet(PlayerShip playerShip, Image image, double startX, double startY, double directionAngle, double speed){
        this.playerShip=playerShip;
        this.imageView=new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(4);
        imageView.setX(startX);
        imageView.setY(startY);
        this.directionAngle=directionAngle;
        this.speed=speed;
        imageView.setRotate(playerShip.getImageView().getRotate());
    }

    //update bullet position
    public void update(){
        double deltaX= speed*Math.cos(Math.toRadians(directionAngle));
        double deltaY= speed*Math.sin(Math.toRadians(directionAngle));
        imageView.setX(imageView.getX()+deltaX);
        imageView.setY(imageView.getY()+deltaY);

    }

    //check if the bullet is out of screen
    public boolean isOffScreen(){
        return (imageView.getX()<0 || imageView.getX()> Launcher.WIDTH || imageView.getY()<0 || imageView.getY()> Launcher.HEIGHT);
    }

    public ImageView getImageView(){
        return imageView;
    }

}
