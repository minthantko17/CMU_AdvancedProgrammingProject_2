package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import se233.advprogrammingproject2.Launcher;

public class largeEnemyShip extends EnemyShips{
    double targetX, targetY;
    double curX, curY;
    boolean isFollowing;
    int movementDirectionAngle;
    int targetDirectionAngle;
    private long enemyLastShotTime;

    public largeEnemyShip(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
        super(image, startX, startY, speed, playerShip);
        curX=startX;
        curY=startY;

        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.movementDirectionAngle = 0; //movement direction angle
        targetDirectionAngle=(int)Math.toDegrees(Math.atan2(this.targetY-this.getY(), this.targetX-this.getX()));

        this.size=40;
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size);
//        this.imageView.setRotate(movementDirectionAngle-90);
        enemyLastShotTime=0;

        isFollowing=false;
    }

    @Override
    public void update(){
        if(curX< 0 || curX > Launcher.WIDTH){
            movementDirectionAngle=(movementDirectionAngle+180)%360;
//            speed=-speed;
//            System.out.println("curX: "+curX+ " launcherW: "+Launcher.WIDTH+" movementDirectionAngle: "+movementDirectionAngle+" speed: "+speed);
        }
        double deltaX= speed*Math.cos(Math.toRadians(movementDirectionAngle));
        curX=curX+deltaX;
        imageView.setX(curX);
//        System.out.println("After: deltaX"+deltaX +", curx"+ curX + " imgX "+ imageView.getX());
    }

    public Bullet shootBullet(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getWidth() /2;
        this.targetX=playerShip.getCurrX()+ (playerShip.getImageView().getFitWidth()/2);
        this.targetY=playerShip.getCurrY()+ (playerShip.getImageView().getFitHeight()/2);
        targetDirectionAngle = (int) Math.toDegrees(Math.atan2(targetY-shipY, targetX-shipX));
        double displayAngle=targetDirectionAngle-90;

        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserBlue.png"));
        double bulletSpeed=5;
        Bullet bullet=new Bullet(this, bulletImage, shipX, shipY, targetDirectionAngle, displayAngle, bulletSpeed);
        return bullet;
    }

    public boolean canEnemyShoot(long currentTime, long interval) {
        if(currentTime-enemyLastShotTime>=interval){
            enemyLastShotTime=currentTime;
            return true;
        }
        return false;
    }
}
