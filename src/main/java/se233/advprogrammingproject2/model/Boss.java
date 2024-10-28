package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.util.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public class Boss extends EnemyShips{
    double targetX, targetY;
    double curX, curY;
    int movementDirectionAngle;
    int targetDirectionAngle;
    public int bossHP;

    public Boss(Image image, double startX, double startY, double speed, PlayerShip playerShip) {
        super(image, startX, startY, speed, playerShip);
        curX=startX;
        curY=startY;
        bossHP=50;

        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.movementDirectionAngle = 0; //movement direction angle
        targetDirectionAngle=(int)Math.toDegrees(Math.atan2(this.targetY-this.getY(), this.targetX-this.getX()));

        this.size=100;
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size);
        enemyLastShotTime=0;
    }

    public Boss(AnimatedSprite animatedSprite, double startX, double startY, double speed, PlayerShip playerShip) {
        this.imageView=animatedSprite;
        this.imageView.setX(startX);
        this.imageView.setY(startY);

        curX=startX;
        curY=startY;
        bossHP=50;

        this.targetX=playerShip.getCurrX();
        this.targetY=playerShip.getCurrY();
        this.movementDirectionAngle = 0; //movement direction angle
        targetDirectionAngle=(int)Math.toDegrees(Math.atan2(this.targetY-this.getY(), this.targetX-this.getX()));

        this.size=100;
        this.imageView.setPreserveRatio(true);
        this.imageView.setFitWidth(size);

        this.speed=speed;
        this.playerShip=playerShip;

        enemyLastShotTime=0;
    }

    @Override
    public void update(){
        //move horizontally
        if(curX< 0 || curX+100 > Launcher.WIDTH){
            movementDirectionAngle=(movementDirectionAngle+180)%360;
        }
        double deltaX= speed*Math.cos(Math.toRadians(movementDirectionAngle));
        curX=curX+deltaX;
        imageView.setX(curX);
    }

    public void update(long now){
        ((AnimatedSprite)imageView).update(now);
        if(curX< 0 || curX+100 > Launcher.WIDTH){
            movementDirectionAngle=(movementDirectionAngle+180)%360;
        }
        double deltaX= speed*Math.cos(Math.toRadians(movementDirectionAngle));
        curX=curX+deltaX;
        imageView.setX(curX);
    }

    public void bossComing(){
        curY=curY+0.5;
        imageView.setY(curY);
    }

    public List<Bullet> shootBullet(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getHeight();
        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserBlue.png"));
        double bulletSpeed=10;

        List<Bullet> bullets=new ArrayList<Bullet>();

        Bullet bullet0=new Bullet(this, bulletImage,shipX, shipY,0, 90, bulletSpeed);
        Bullet bullet1=new Bullet(this, bulletImage,shipX, shipY,20,110,bulletSpeed);
        Bullet bullet2=new Bullet(this, bulletImage, shipX, shipY, 40, 130, bulletSpeed);
        Bullet bullet3=new Bullet(this, bulletImage, shipX, shipY, 60, 150, bulletSpeed);
        Bullet bullet4=new Bullet(this, bulletImage, shipX, shipY, 80, 170, bulletSpeed);
        Bullet bullet5=new Bullet(this, bulletImage, shipX, shipY, 100, 190, bulletSpeed);
        Bullet bullet6=new Bullet(this, bulletImage, shipX, shipY, 120, 210, bulletSpeed);
        Bullet bullet7=new Bullet(this, bulletImage, shipX, shipY, 140, 230, bulletSpeed);
        Bullet bullet8=new Bullet(this, bulletImage, shipX, shipY, 160, 250, bulletSpeed);
        Bullet bullet9=new Bullet(this, bulletImage, shipX, shipY, 180, 270, bulletSpeed);

        bullets.add(bullet0);
        bullets.add(bullet1);
        bullets.add(bullet2);
        bullets.add(bullet3);
        bullets.add(bullet4);
        bullets.add(bullet5);
        bullets.add(bullet6);
        bullets.add(bullet7);
        bullets.add(bullet8);
        bullets.add(bullet9);

        return bullets;
    }

    public void setBossHP(int bossHP) {
        this.bossHP=bossHP;
    }
    public int getBossHP() {
        return bossHP;
    }

    public double getCurX() {
        return curX;
    }
    public double getCurY() {
        return curY;
    }
}
