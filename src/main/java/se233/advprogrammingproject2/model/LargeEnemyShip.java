package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.util.AnimatedSprite;

public class LargeEnemyShip extends EnemyShips{
    double targetX, targetY;
    double curX, curY;
    boolean isFollowing;
    int movementDirectionAngle;
    int targetDirectionAngle;
    double initSpeed;
//    AnimatedSprite animatedSprite;

    public LargeEnemyShip(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
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
        enemyLastShotTime=0;
        isFollowing=false;
    }

    public LargeEnemyShip(AnimatedSprite animatedSprite, double startX, double startY, double speed, PlayerShip playerShip) {
        this.imageView=animatedSprite;
        imageView.setX(startX);
        imageView.setY(startY);

        enemyLastShotTime=0;

        curX=startX;
        curY=startY;
        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.movementDirectionAngle = 0; //movement direction angle
        targetDirectionAngle=(int)Math.toDegrees(Math.atan2(this.targetY-this.getY(), this.targetX-this.getX()));
        this.size=60;
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(size);

        initSpeed=speed;
        this.speed=speed;
        this.playerShip=playerShip;
        enemyLastShotTime=0;
        isFollowing=false;
    }

    @Override
    public void update(){
        if(curX< 0 || curX+40 > Launcher.WIDTH){
            movementDirectionAngle=(movementDirectionAngle+180)%360;
        }
        double deltaX= speed*Math.cos(Math.toRadians(movementDirectionAngle));
        curX=curX+deltaX;
        imageView.setX(curX);
    }

    public void update(long now){
//        this.animatedSprite.update(now);
        ((AnimatedSprite)imageView).update(now);
        if(curX< 0 || curX+60 > Launcher.WIDTH){
            movementDirectionAngle=(movementDirectionAngle+180)%360;
        }
        double deltaX= speed*Math.cos(Math.toRadians(movementDirectionAngle));
        curX=curX+deltaX;
        imageView.setX(curX);
    }

    public Bullet shootBullet(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getHeight() /2;
        this.targetX=playerShip.getCurrX()+ (playerShip.getImageView().getFitWidth()/2);
        this.targetY=playerShip.getCurrY()+ (playerShip.getImageView().getFitHeight()/2);
        targetDirectionAngle = (int) Math.toDegrees(Math.atan2(targetY-shipY, targetX-shipX));
        double displayAngle=targetDirectionAngle-90;

        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserBlue.png"));
        double bulletSpeed=5;
        Bullet bullet=new Bullet(this, bulletImage, shipX, shipY, targetDirectionAngle, displayAngle, bulletSpeed);
        return bullet;
    }

    public void setSpeed(double speed){
        this.speed=speed;
    }
    public void setBackInitSpeed(){
        this.speed=initSpeed;
    }

//    public AnimatedSprite getAnimatedSprite(){
//        return animatedSprite;
//    }
}
