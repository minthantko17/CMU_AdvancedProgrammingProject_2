package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.util.AnimatedSprite;

public class SmallEnemyShip extends EnemyShips{
    long initTime;
    long changeDirTime;
    double targetX, targetY;
    double curX, curY;
    boolean isFollowing;

    public SmallEnemyShip(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
        super(image, startX, startY, speed, playerShip);
        curX=startX;
        curY=startY;

        initTime=System.currentTimeMillis();
        changeDirTime=2500;
        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.directionAngle = (int)Math.toDegrees(Math.atan2(this.targetY-startY, this.targetX-startX));
        this.size=40;
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size);
        this.imageView.setRotate(directionAngle-90);
        isFollowing=false;
    }

    public SmallEnemyShip(AnimatedSprite animatedSprite, double startX, double startY, double speed, PlayerShip playerShip) {
        this.imageView=animatedSprite;
        this.imageView.setX(startX);
        this.imageView.setY(startY);

        curX=startX;
        curY=startY;

        initTime=System.currentTimeMillis();
        changeDirTime=2500;
        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.directionAngle = (int)Math.toDegrees(Math.atan2(this.targetY-startY, this.targetX-startX));
        this.size=40;
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size);
        this.imageView.setRotate(directionAngle-90);
        this.speed=speed;
        this.playerShip=playerShip;
        isFollowing=false;
    }

    public void setDirectionAngle(int directionAngle){
        this.directionAngle=directionAngle;
    }

    public void setSpeed(double speed){
        this.speed=speed;
    }

    public void isFollowing(){
        //follow every 400 seconds
        if((System.currentTimeMillis()-initTime)%changeDirTime < 100){
            isFollowing=true;
        }else{
            isFollowing=false;
        }
    }

    @Override
    public void update(){
        updateWhenOffScreen();

        curX=this.imageView.getX();
        curY=this.imageView.getY();
        isFollowing();

        //to follow continuously or with tick time, use alternative if condition below
        if((System.currentTimeMillis()-initTime) > changeDirTime){
//        if(isFollowing){
            this.speed=2.7;
            this.targetX=playerShip.getCurrX();
            this.targetY=playerShip.getCurrY();
            this.directionAngle=(int)Math.toDegrees(Math.atan2(targetY-curY, targetX-curX));
            this.imageView.setRotate(this.directionAngle-90);
            isFollowing=false;
        }

        double deltaX= speed*Math.cos(Math.toRadians(directionAngle));
        double deltaY= speed*Math.sin(Math.toRadians(directionAngle));
        imageView.setX(imageView.getX()+deltaX);
        imageView.setY(imageView.getY()+deltaY);

    }

    public void update(long now){
        updateWhenOffScreen();
        ((AnimatedSprite)imageView).update(now);

        curX=this.imageView.getX();
        curY=this.imageView.getY();
        isFollowing();

        //to follow continuously or with tick time, use alternative if condition below
        if((System.currentTimeMillis()-initTime) > changeDirTime){
//        if(isFollowing){
            this.speed=2.7;
            this.targetX=playerShip.getCurrX();
            this.targetY=playerShip.getCurrY();
            this.directionAngle=(int)Math.toDegrees(Math.atan2(targetY-curY, targetX-curX));
            this.imageView.setRotate(this.directionAngle-90);
            isFollowing=false;
        }

        double deltaX= speed*Math.cos(Math.toRadians(directionAngle));
        double deltaY= speed*Math.sin(Math.toRadians(directionAngle));
        imageView.setX(imageView.getX()+deltaX);
        imageView.setY(imageView.getY()+deltaY);

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
}
