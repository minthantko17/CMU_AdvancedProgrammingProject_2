package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.Launcher;

public class Bullet extends Characters{
    private Characters ship;
    private final ImageView imageView;
    private final double bulletMovementDirectionAngle;
    private double speed=5;

    public Bullet(Characters ship, Image image, double startX, double startY, double bulletMovementDirectionAngle, double bulletDisplayAngle, double speed){
        this.ship =ship;
        this.imageView=new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(4);
        imageView.setX(startX);
        imageView.setY(startY);
        this.bulletMovementDirectionAngle = bulletMovementDirectionAngle;
        this.speed=speed;
        imageView.setRotate(bulletDisplayAngle);
    }

    public void update(){
        double deltaX= speed*Math.cos(Math.toRadians(bulletMovementDirectionAngle));
        double deltaY= speed*Math.sin(Math.toRadians(bulletMovementDirectionAngle));
        imageView.setX(imageView.getX()+deltaX);
        imageView.setY(imageView.getY()+deltaY);
    }

    public boolean isOffScreen(){
        return (imageView.getX()<0 || imageView.getX()> Launcher.WIDTH || imageView.getY()<0 || imageView.getY()> Launcher.HEIGHT);
    }

    public ImageView getImageView(){
        return imageView;
    }

    public Characters getShipType(){
        return ship;
    }

}
