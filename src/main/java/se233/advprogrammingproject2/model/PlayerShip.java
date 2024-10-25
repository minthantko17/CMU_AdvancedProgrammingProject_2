package se233.advprogrammingproject2.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import se233.advprogrammingproject2.Controllers.GameController;
import se233.advprogrammingproject2.Launcher;
import se233.advprogrammingproject2.View.GameStage;

public class PlayerShip extends Characters {
    double currX;
    double currY;
    KeyCode topKey, rightKey, bottomKey, leftKey;
    boolean isMoveTop, isMoveRight, isMoveBottom, isMoveLeft;
    private boolean isDestroyed;
    private int life = 3;
    private boolean isInvincible;
    private long invincibleStartTime;
    private long invincibleDuration = 3000;


    public PlayerShip(GameStage gameStage, Image image, double startX, double startY, KeyCode topKey, KeyCode rightKey, KeyCode bottomKey, KeyCode leftKey, double speed) {
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

    }

    public void update(){
        if (isInvincible()) {
            long elapsedTime = System.currentTimeMillis() - invincibleStartTime;
            imageView.setVisible(elapsedTime / 200 % 2 != 0);
        } else {
            imageView.setVisible(true);
        }

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
        if(isMoveLeft){
            currX=currX-speed;
        }
        if(isMoveRight){
            currX=currX+speed;
        }
        imageView.setX(currX);
    }
    public void moveVertical(){
        if(isMoveTop){
            currY = currY -speed;
        }
        if(isMoveBottom){
            currY = currY +speed;
        }
        imageView.setY(currY);
    }

    public Bullet shootBullet(){
        double shipX=this.getImageView().getBoundsInParent().getMinX()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipY=this.getImageView().getBoundsInParent().getMinY()+this.getImageView().getBoundsInParent().getWidth() /2;
        double shipDirection=this.getImageView().getRotate()-90;     // adjust like +- 90 according to the input image
        double shipLastDirectedAngle= this.getImageView().getRotate();

//        double shipHeight = this.getImageView().getBoundsInParent().getHeight() / 2; // or another suitable value
//        double shipLength = this.getImageView().getBoundsInParent().getWidth() / 2; // or another suitable value
//
//        // Use trigonometry to calculate the position of the bullet at the front of the ship
//        double bulletX = shipX + Math.cos(Math.toRadians(shipDirection)) * shipLength;
//        double bulletY = shipY + Math.sin(Math.toRadians(shipDirection)) * shipHeight;

        Image bulletImage=new Image(Launcher.class.getResourceAsStream("assets/laserRed.png"));
        double bulletSpeed=15;
        Bullet bullet=new Bullet(this, bulletImage, shipX, shipY, shipDirection, shipLastDirectedAngle, bulletSpeed);
        return bullet;
    }

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


    //score

    //game finish


}
