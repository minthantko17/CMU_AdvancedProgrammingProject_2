package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;
import se233.advprogrammingproject2.util.AnimatedSprite;

import java.util.ArrayList;
import java.util.List;

public class PlayerShip extends Characters {
    double currX;
    double currY;
    KeyCode topKey, rightKey, bottomKey, leftKey;
    boolean isMoveTop, isMoveRight, isMoveBottom, isMoveLeft;
    private boolean isDestroyed;
    private int life;
    private boolean isInvincible;
    private long invincibleStartTime;
    private long invincibleDuration = 3000;
    private boolean isMoving;


    public PlayerShip(GameStage gameStage, Image image, double startX, double startY, KeyCode topKey, KeyCode rightKey, KeyCode bottomKey, KeyCode leftKey, double speed) {
        life=3;

        this.gameStage=gameStage;
        this.startX=startX;
        this.startY=startY;

        this.currX= this.startX;
        this.currY = this.startY;
        this.speed=speed;

        this.topKey=topKey;
        this.rightKey=rightKey;
        this.bottomKey=bottomKey;
        this.leftKey=leftKey;
        this.imageView=new ImageView(image);

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(this.startX);
        imageView.setY(this.startY);

        isDestroyed=false;
        isInvincible=false;

        isMoving=false;
    }

    public PlayerShip(GameStage gameStage, AnimatedSprite animatedSprite, double startX, double startY, KeyCode topKey, KeyCode rightKey, KeyCode bottomKey, KeyCode leftKey, double speed) {
        life=3;

        this.gameStage=gameStage;
        this.startX=startX;
        this.startY=startY;

        this.currX= this.startX;
        this.currY = this.startY;
        this.speed=speed;

        this.topKey=topKey;
        this.rightKey=rightKey;
        this.bottomKey=bottomKey;
        this.leftKey=leftKey;
        this.imageView=animatedSprite;

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setX(this.startX);
        imageView.setY(this.startY);

        isDestroyed=false;
        isInvincible=false;
        isMoving=false;
    }

    public void update(long now){
        if (isInvincible()) {
            long elapsedTime = System.currentTimeMillis() - invincibleStartTime;
            imageView.setVisible(elapsedTime / 200 % 2 != 0);
        } else {
            imageView.setVisible(true);
        }

        ((AnimatedSprite)imageView).update(now);
//        if(isMoving){
//            ((AnimatedSprite)imageView).update(now);
//        }else{
//            ((AnimatedSprite)imageView).drawFirst();
//        }

        if(isDestroyed){
            return;
        }

        boolean leftPressed= gameStage.getKeys().isPressed(this.leftKey);
        boolean rightPressed= gameStage.getKeys().isPressed(this.rightKey);
        boolean topPressed= gameStage.getKeys().isPressed(this.topKey);
        boolean bottomPressed= gameStage.getKeys().isPressed(this.bottomKey);

        if(leftPressed && rightPressed){
            this.stopHorizontal();
        }else if(leftPressed){
            this.moveLeft();
            this.moveHorizontal();
        }else if(rightPressed){
            this.moveRight();
            this.moveHorizontal();
        }else{
            this.stopHorizontal();
        }

        if(topPressed && bottomPressed){
            this.stopVertical();
        }else if(topPressed){
            this.moveTop();
            this.moveVertical();
        }else if(bottomPressed){
            this.moveBottom();
            this.moveVertical();
        }else{
            this.stopVertical();
        }
    }

    //---------movements---------
    public void moveLeft(){
        isMoveLeft=true;
        isMoveRight=false;
        GameController.logger.info("Moving Left");
    }
    public void moveRight(){
        isMoveRight=true;
        isMoveLeft=false;
        GameController.logger.info("Moving Right");
    }
    public void moveTop(){
        isMoveTop=true;
        isMoveBottom=false;
        GameController.logger.info("Moving Top");
    }
    public void moveBottom(){
        isMoveBottom=true;
        isMoveTop=false;
        GameController.logger.info("Moving Bottom");
    }

    public void stopHorizontal(){
        isMoveRight=false;
        isMoveLeft=false;
    }
    public void stopVertical(){
        isMoveTop=false;
        isMoveBottom=false;
    }

    public void moveHorizontal(){
        if(isMoveLeft && currX>=0){
            currX=currX-speed;
        }
        if(isMoveRight && currX+ imageView.getFitWidth()<=Launcher.WIDTH){
            currX=currX+speed;
        }
        imageView.setX(currX);
    }
    public void moveVertical(){
        if(isMoveTop && currY>=0){
            currY = currY -speed;
        }
        if(isMoveBottom && currY+imageView.getFitHeight()+30<=Launcher.HEIGHT){
            currY = currY +speed;
        }
        imageView.setY(currY);
    }

    //--------------------------
    public Bullet shootBullet(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getHeight() /2;
        double shipDirection=this.getImageView().getRotate()-90;     // adjust like +- 90 according to the input image
        double shipLastDirectedAngle= this.getImageView().getRotate();

        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserRed.png"));
        double bulletSpeed=15;
        Bullet bullet=new Bullet(this, bulletImage, shipX, shipY, shipDirection, shipLastDirectedAngle, bulletSpeed);
        return bullet;
    }

    public List<Bullet> shootSpecialAttack(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getWidth() /2;
        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserRed2.png"));
        double bulletSpeed=10;

        List<Bullet> bullets=new ArrayList<Bullet>();
        Bullet bullet0=new Bullet(this, bulletImage,shipX, shipY,0, 90, bulletSpeed);
        Bullet bullet1=new Bullet(this, bulletImage,shipX, shipY,30,120,bulletSpeed);
        Bullet bullet2=new Bullet(this, bulletImage, shipX, shipY, 60, 150, bulletSpeed);
        Bullet bullet3=new Bullet(this, bulletImage, shipX, shipY, 90, 180, bulletSpeed);
        Bullet bullet4=new Bullet(this, bulletImage, shipX, shipY, 120, 210, bulletSpeed);
        Bullet bullet5=new Bullet(this, bulletImage, shipX, shipY, 150, 240, bulletSpeed);
        Bullet bullet6=new Bullet(this, bulletImage, shipX, shipY, 180, 270, bulletSpeed);
        Bullet bullet7=new Bullet(this, bulletImage, shipX, shipY, 210, 300, bulletSpeed);
        Bullet bullet8=new Bullet(this, bulletImage, shipX, shipY, 240, 330, bulletSpeed);
        Bullet bullet9=new Bullet(this, bulletImage, shipX, shipY, 270, 0, bulletSpeed);
        Bullet bullet10=new Bullet(this, bulletImage, shipX, shipY, 300, 30, bulletSpeed);
        Bullet bullet11=new Bullet(this, bulletImage, shipX, shipY, 330, 60, bulletSpeed);

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
        bullets.add(bullet10);
        bullets.add(bullet11);

        return bullets;
    }

    //need to add special att for ship2

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public void destroy(){
        isDestroyed=true;
    }

    public void respawn(){
        life--;
        isDestroyed=false;
        invincibleStartTime=System.currentTimeMillis();
        isInvincible=true;
        this.currX=startX;
        this.currY =startY;
        imageView.setX(startX);
        imageView.setY(startY);
    }

    public int getLife(){
        return life;
    }

    public boolean isInvincible(){
        return isInvincible && (System.currentTimeMillis()-invincibleStartTime<invincibleDuration);
    }

    public double getCurrX(){
        return this.currX;
    }
    public double getCurrY(){
        return this.currY;
    }

    public void setIsMoving(boolean isMoving){
        this.isMoving=isMoving;
    };
    public boolean isMoving(){
        return isMoving;
    }

    public boolean isMoveBottom() {
        return isMoveBottom;
    }

    public boolean isMoveTop() {
        return isMoveTop;
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public void setInvincibleStartTime(long invincibleStartTime) {
        this.invincibleStartTime = invincibleStartTime;
    }

    public void setInvincibleDuration(long invincibleDuration) {
        this.invincibleDuration = invincibleDuration;
    }

    public KeyCode getTopKey() {
        return topKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getBottomKey() {
        return bottomKey;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public long getInvincibleDuration() {
        return invincibleDuration;
    }

    public void setCurrX(double currX) {
        this.currX = currX;
    }

    public void setCurrY(double currY) {
        this.currY = currY;
    }
}
